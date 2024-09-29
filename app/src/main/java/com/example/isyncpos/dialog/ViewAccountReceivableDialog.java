package com.example.isyncpos.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.adapters.AccountReceivableAdapter;
import com.example.isyncpos.adapters.ReceiptsAdapter;
import com.example.isyncpos.adapters.XReadingAdapter;
import com.example.isyncpos.adapters.ZReadingAdapter;
import com.example.isyncpos.common.APIRequest;
import com.example.isyncpos.common.EncryptDecrypt;
import com.example.isyncpos.entity.CutOff;
import com.example.isyncpos.entity.EndOfDay;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.objects.AccountReceivableDiscountDetailsObject;
import com.example.isyncpos.objects.AccountReceivableDiscountOtherInformationsObject;
import com.example.isyncpos.objects.AccountReceivableDiscountsObject;
import com.example.isyncpos.objects.AccountReceivableOfficialReceiptInformationsObject;
import com.example.isyncpos.objects.AccountReceivableOrdersObject;
import com.example.isyncpos.objects.AccountReceivablePaymentOtherInformationsObject;
import com.example.isyncpos.objects.AccountReceivablePaymentsObject;
import com.example.isyncpos.objects.AccountReceivableTransactionsObject;
import com.example.isyncpos.payload.BranchTransactionSyncPayload;
import com.example.isyncpos.response.entity.AccountReceivableResponse;
import com.example.isyncpos.response.entity.BranchTransactionSyncResponse;
import com.example.isyncpos.viewmodel.CutOffViewModel;
import com.example.isyncpos.viewmodel.EndOfDayViewModel;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class ViewAccountReceivableDialog extends DialogFragment {

    private RecyclerView recyclerViewReceipt;
    private AccountReceivableAdapter accountReceivableAdapter;
    private MaterialButton btnReceiptNegative;
    private AlertDialog alertDialog;
    private View view;
    private TransactionsViewModel transactionsViewModel;
    private boolean hasRequest = false;

    interface RequestAPI {
        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/unredeemed-ar-transactions")
        Call<AccountReceivableResponse> BranchTransactionAccountReceivableFetch(@Header("Authorization") String authorization, @Query("branch_id") int branchId, @Query("device_id") int deviceId);
    }

    public static ViewAccountReceivableDialog newViewAccountReceivableDialog(){
        return new ViewAccountReceivableDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.dialog_account_receivable,null);
        builder.setView(view);
        alertDialog = builder.create();
        //This will set the focus of the touch keyboard of the android to this dialog order to use on the recycler view edittext
//        alertDialog.setOnShowListener(dialogInterface -> alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM));
        //Initialize
        initialize();

        //Get
        getAccountReceivableInformation();

        btnReceiptNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return alertDialog;
    }

    private void initialize(){
        initViewModel();
        btnReceiptNegative = view.findViewById(R.id.btnReceiptNegative);
        initRecyclerView();
    }

    private void initRecyclerView(){
        recyclerViewReceipt = view.findViewById(R.id.recyclerViewReceipt);
        recyclerViewReceipt.setHasFixedSize(true);
        recyclerViewReceipt.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        accountReceivableAdapter = new AccountReceivableAdapter(getActivity(), getActivity().getSupportFragmentManager(), this);
        recyclerViewReceipt.setAdapter(accountReceivableAdapter);
    }

    private void initViewModel(){
        transactionsViewModel = POSApplication.getInstance().getTransactionsViewModel();
    }

    public void getAccountReceivableInformation(){
        if(!hasRequest){
            hasRequest = true;
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchTransactionAccountReceivableFetch("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getDeviceDetails().getCoreId())
                    .enqueue(new Callback<AccountReceivableResponse>() {
                        @Override
                        public void onResponse(Call<AccountReceivableResponse> call, Response<AccountReceivableResponse> response) {
                            if(response.isSuccessful()){
                                if(response.body().isSuccess()){
                                    accountReceivableAdapter.submitList(setAccountReceivableObjects(response));
                                    Log.d("ToFetchAccountReceivable", "onResponseSuccess: " + response.body().getMessage());
                                    hasRequest = false;
                                }
                                else{
                                    Log.d("ToFetchAccountReceivable", "onResponseError: " + response.body().getMessage());
                                    hasRequest = false;
                                }
                            }
                            else{
                                Log.d("ToFetchAccountReceivable", "onResponseError: " + response.body().getMessage());
                                hasRequest = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<AccountReceivableResponse> call, Throwable t) {
                            Log.d("ToFetchAccountReceivable", "onFailure: " + t.getMessage());
                            hasRequest = false;
                        }
                    });
        }
    }

    private ArrayList<AccountReceivableTransactionsObject> setAccountReceivableObjects(Response<AccountReceivableResponse> response){
        ArrayList<AccountReceivableTransactionsObject> transactions = new ArrayList<>();
        response.body().getData().forEach(item -> {
            //This will filter the other AR transaction of other machine number
            if(POSApplication.getInstance().getMachineDetails().getCoreId() == item.getPos_machine_id()){
                ArrayList<AccountReceivableOrdersObject> orders = new ArrayList<>();
                ArrayList<AccountReceivableDiscountsObject> discounts = new ArrayList<>();
                ArrayList<AccountReceivableDiscountOtherInformationsObject> discountOtherInformations = new ArrayList<>();
                ArrayList<AccountReceivableDiscountDetailsObject> discountDetails = new ArrayList<>();
                ArrayList<AccountReceivablePaymentsObject> payments = new ArrayList<>();
                ArrayList<AccountReceivablePaymentOtherInformationsObject> paymentOtherInformations = new ArrayList<>();
                AccountReceivableOfficialReceiptInformationsObject officialReceiptInformations = null;
                //Condition For Official Receipt
                if(!item.getOfficial_receipt_informations().isEmpty()){
                    officialReceiptInformations = new AccountReceivableOfficialReceiptInformationsObject(
                            item.getOfficial_receipt_informations().get(0).getPos_machine_id(),
                            item.getOfficial_receipt_informations().get(0).getBranch_id(),
                            item.getOfficial_receipt_informations().get(0).getCompany_id(),
                            item.getOfficial_receipt_informations().get(0).getTransaction_id(),
                            item.getOfficial_receipt_informations().get(0).getName(),
                            item.getOfficial_receipt_informations().get(0).getAddress(),
                            item.getOfficial_receipt_informations().get(0).getTin(),
                            item.getOfficial_receipt_informations().get(0).getBusiness_style(),
                            item.getOfficial_receipt_informations().get(0).getIs_void(),
                            item.getOfficial_receipt_informations().get(0).getVoid_by(),
                            item.getOfficial_receipt_informations().get(0).getVoid_name(),
                            item.getOfficial_receipt_informations().get(0).getVoid_at(),
                            item.getOfficial_receipt_informations().get(0).getIs_sent_to_server(),
                            item.getOfficial_receipt_informations().get(0).getTreg()
                    );
                }
                //Loop the order information of the transaction
                item.getItems().forEach(order -> {
                    AccountReceivableOrdersObject itemOrders = new AccountReceivableOrdersObject(
                            order.getPos_machine_id(),
                            order.getBranch_id(),
                            order.getCompany_id(),
                            order.getTransaction_id(),
                            order.getProduct_id(),
                            order.getCode(),
                            order.getName(),
                            order.getDescription(),
                            order.getAbbreviation(),
                            order.getCost(),
                            order.getQty(),
                            order.getAmount(),
                            order.getOriginal_amount(),
                            order.getGross(),
                            order.getTotal(),
                            order.getTotal_cost(),
                            order.getIs_vatable(),
                            order.getVat_amount(),
                            order.getVatable_sales(),
                            order.getVat_exempt_sales(),
                            order.getDiscount_amount(),
                            order.getVat_expense(),
                            order.getDepartment_id(),
                            order.getDepartment_name(),
                            order.getCategory_id(),
                            order.getCategory_name(),
                            order.getSubcategory_id(),
                            order.getSubcategory_name(),
                            order.getUnit_id(),
                            order.getUnit_name(),
                            order.getPrice_change_reason_id(),
                            order.getIs_discount_exempt(),
                            order.getIs_open_price(),
                            order.getWith_serial(),
                            order.getIs_void(),
                            order.getVoid_by(),
                            order.getVoid_at(),
                            order.getIs_back_out(),
                            order.getIs_back_out_id(),
                            order.getBack_out_by(),
                            order.getMin_amount_sold(),
                            order.getIs_paid(),
                            order.getIs_sent_to_server(),
                            order.getIs_completed(),
                            order.getCompleted_at(),
                            order.getShift_number(),
                            order.getCut_off_id(),
                            order.getIs_cut_off(),
                            order.getCut_off_at(),
                            order.getDiscount_details_id(),
                            order.getSerial_number(),
                            order.getRemarks(),
                            order.getIs_return(),
                            order.getTotal_zero_rated_amount(),
                            order.getIs_zero_rated(),
                            order.getTreg()
                    );
                    itemOrders.setId(order.getOrder_id());
                    orders.add(itemOrders);
                });
                //Loop The Discounts information of the transaction
                item.getDiscounts().forEach(discount -> {
                    AccountReceivableDiscountsObject itemDiscounts = new AccountReceivableDiscountsObject(
                            discount.getPos_machine_id(),
                            discount.getBranch_id(),
                            discount.getCompany_id(),
                            discount.getTransaction_id(),
                            discount.getCustom_discount_id(),
                            discount.getDiscount_type_id(),
                            discount.getDiscount_name(),
                            discount.getValue(),
                            discount.getGross_amount(),
                            discount.getNet_amount(),
                            discount.getDiscount_amount(),
                            discount.getVat_exempt_amount(),
                            discount.getVat_expense(),
                            discount.getType(),
                            discount.getCashier_id(),
                            discount.getCashier_name(),
                            discount.getAuthorize_id(),
                            discount.getAuthorize_name(),
                            discount.getIs_void(),
                            discount.getVoid_by_id(),
                            discount.getVoid_by(),
                            discount.getVoid_at(),
                            discount.getIs_sent_to_server(),
                            discount.getIs_zero_rated(),
                            discount.getIs_cut_off(),
                            discount.getCut_off_id(),
                            discount.getShift_number(),
                            discount.getTreg()
                    );
                    itemDiscounts.setId(discount.getDiscount_id());
                    discounts.add(itemDiscounts);
                });
                //Loop Discount Other Information
                if(item.getDiscount_other_informations() != null){ //Remove when the variable is available to the api
                    item.getDiscount_other_informations().forEach(discountOtherInformation -> {
                        AccountReceivableDiscountOtherInformationsObject itemDiscountOtherInformation = new AccountReceivableDiscountOtherInformationsObject(
                                discountOtherInformation.getPos_machine_id(),
                                discountOtherInformation.getBranch_id(),
                                discountOtherInformation.getCompany_id(),
                                discountOtherInformation.getTransaction_id(),
                                discountOtherInformation.getDiscount_id(),
                                discountOtherInformation.getName(),
                                discountOtherInformation.getValue(),
                                discountOtherInformation.getIs_cut_off(),
                                discountOtherInformation.getCut_off_id(),
                                discountOtherInformation.getIs_void(),
                                discountOtherInformation.getIs_sent_to_server(),
                                discountOtherInformation.getTreg()
                        );
                        itemDiscountOtherInformation.setId(discountOtherInformation.getDiscount_other_information_id());
                        discountOtherInformations.add(itemDiscountOtherInformation);
                    });
                }
                //Loop Discount Details information
                item.getDiscount_details().forEach(discountDetail -> {
                    AccountReceivableDiscountDetailsObject itemDiscountDetails = new AccountReceivableDiscountDetailsObject(
                            discountDetail.getDiscount_id(),
                            discountDetail.getPos_machine_id(),
                            discountDetail.getBranch_id(),
                            discountDetail.getCompany_id(),
                            discountDetail.getCustom_discount_id(),
                            discountDetail.getTransaction_id(),
                            discountDetail.getOrder_id(),
                            discountDetail.getDiscount_type_id(),
                            discountDetail.getValue(),
                            discountDetail.getDiscount_amount(),
                            discountDetail.getVat_exempt_amount(),
                            discountDetail.getVat_expense(),
                            discountDetail.getType(),
                            discountDetail.getIs_void(),
                            discountDetail.getVoid_by_id(),
                            discountDetail.getVoid_by(),
                            discountDetail.getVoid_at(),
                            discountDetail.getIs_sent_to_server(),
                            discountDetail.getIs_cut_off(),
                            discountDetail.getCut_off_id(),
                            discountDetail.getIs_vat_exempt() == 1 ? true : false,
                            discountDetail.getIs_zero_rated() == 1 ? true : false,
                            discountDetail.getShift_number(),
                            discountDetail.getTreg()
                    );
                    itemDiscountDetails.setId(discountDetail.getDiscount_details_id());
                    discountDetails.add(itemDiscountDetails);
                });
                //Loop Payment Information
                item.getPayments().forEach(payment -> {
                    AccountReceivablePaymentsObject itemPayments = new AccountReceivablePaymentsObject(
                            payment.getPos_machine_id(),
                            payment.getBranch_id(),
                            payment.getCompany_id(),
                            payment.getTransaction_id(),
                            payment.getPayment_type_id(),
                            payment.getPayment_type_name(),
                            payment.getAmount(),
                            payment.getIs_advance_payment(),
                            payment.getIs_account_receivable(),
                            payment.getShift_number(),
                            payment.getIs_void(),
                            payment.getVoid_by_id(),
                            payment.getVoid_by(),
                            payment.getVoid_at(),
                            payment.getIs_sent_to_server(),
                            payment.getCut_off_id(),
                            payment.getIs_cut_off(),
                            payment.getCut_off_at(),
                            payment.getTreg()
                    );
                    itemPayments.setId(payment.getPayment_id());
                    payments.add(itemPayments);
                });
                //Loop Payment Other Information
                item.getPayment_other_informations().forEach(paymentOtherInformation -> {
                    AccountReceivablePaymentOtherInformationsObject itemPaymentOtherInformation = new AccountReceivablePaymentOtherInformationsObject(
                            paymentOtherInformation.getPos_machine_id(),
                            paymentOtherInformation.getBranch_id(),
                            paymentOtherInformation.getCompany_id(),
                            paymentOtherInformation.getTransaction_id(),
                            paymentOtherInformation.getPayment_id(),
                            paymentOtherInformation.getName(),
                            paymentOtherInformation.getValue(),
                            paymentOtherInformation.getIs_cut_off(),
                            paymentOtherInformation.getCut_off_id(),
                            paymentOtherInformation.getIs_void(),
                            paymentOtherInformation.getIs_sent_to_server(),
                            paymentOtherInformation.getTreg()
                    );
                    itemPaymentOtherInformation.setId(paymentOtherInformation.getPayment_other_information_id());
                    paymentOtherInformations.add(itemPaymentOtherInformation);
                });
                //Process the transaction info
                AccountReceivableTransactionsObject itemTransaction = new AccountReceivableTransactionsObject(
                        item.getPos_machine_id(),
                        item.getBranch_id(),
                        item.getCompany_id(),
                        item.getControl_number(),
                        item.getReceipt_number(),
                        item.getGross_sales(),
                        item.getNet_sales(),
                        item.getVatable_sales(),
                        item.getVat_exempt_sales(),
                        item.getVat_amount(),
                        item.getDiscount_amount(),
                        item.getVat_expense(),
                        item.getTender_amount(),
                        item.getTotal_cash_amount(),
                        item.getChange(),
                        item.getService_charge(),
                        item.getType(),
                        item.getCashier_id(),
                        item.getCashier_name(),
                        item.getTake_order_id(),
                        item.getTake_order_name(),
                        item.getTotal_quantity(),
                        item.getTotal_unit_cost(),
                        item.getTotal_void_qty(),
                        item.getTotal_void_amount(),
                        item.getShift_number(),
                        item.getIs_void(),
                        item.getVoid_by_id(),
                        item.getVoid_by(),
                        item.getVoid_at(),
                        item.getVoid_remarks(),
                        item.getVoid_counter(),
                        item.getIs_back_out(),
                        item.getIs_back_out_id(),
                        item.getBack_out_by(),
                        item.getBackout_at(),
                        item.getCharge_account_id(),
                        item.getCharge_account_name(),
                        item.getIs_account_receivable(),
                        item.getIs_account_receivable_redeem(),
                        item.getAccount_receivable_redeem_at(),
                        item.getIs_sent_to_server(),
                        item.getIs_complete(),
                        item.getCompleted_at(),
                        item.getIs_cut_off(),
                        item.getCut_off_id(),
                        item.getCut_off_at(),
                        item.getGuest_name(),
                        item.getIs_resume_printed(),
                        item.getIs_return(),
                        item.getTotal_return_amount(),
                        item.getTotal_zero_rated_amount(),
                        item.getCustomer_name(),
                        item.getVoid_remarks(),
                        item.getTreg(),
                        orders,
                        discounts,
                        discountOtherInformations,
                        discountDetails,
                        payments,
                        paymentOtherInformations,
                        officialReceiptInformations
                );
                itemTransaction.setId(item.getTransaction_id());
                transactions.add(itemTransaction);
            }
        });
        return transactions;
    }

    public void closeDialog(){
        dismiss();
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }
}
