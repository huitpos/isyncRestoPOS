package com.example.isyncpos.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.common.APIRequest;
import com.example.isyncpos.common.EncryptDecrypt;
import com.example.isyncpos.entity.ApplicationSettings;
import com.example.isyncpos.entity.AuditTrails;
import com.example.isyncpos.entity.CutOff;
import com.example.isyncpos.entity.CutOffDepartments;
import com.example.isyncpos.entity.CutOffDiscounts;
import com.example.isyncpos.entity.CutOffPayments;
import com.example.isyncpos.entity.CutOffProducts;
import com.example.isyncpos.entity.DiscountDetails;
import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.entity.Discounts;
import com.example.isyncpos.entity.EndOfDay;
import com.example.isyncpos.entity.EndOfDayDepartments;
import com.example.isyncpos.entity.EndOfDayDiscounts;
import com.example.isyncpos.entity.EndOfDayPayments;
import com.example.isyncpos.entity.EndOffDayProducts;
import com.example.isyncpos.entity.OfficialReceiptInformation;
import com.example.isyncpos.entity.Orders;
import com.example.isyncpos.entity.PaymentOtherInformations;
import com.example.isyncpos.entity.Payments;
import com.example.isyncpos.entity.Safekeeping;
import com.example.isyncpos.entity.SafekeepingDenomination;
import com.example.isyncpos.entity.SpotAudit;
import com.example.isyncpos.entity.SpotAuditDenomination;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.response.entity.AuditTrailResponse;
import com.example.isyncpos.response.entity.CutOffDepartmentsResponse;
import com.example.isyncpos.response.entity.CutOffDiscountsResponse;
import com.example.isyncpos.response.entity.CutOffPaymentsResponse;
import com.example.isyncpos.response.entity.CutOffProductsResponse;
import com.example.isyncpos.response.entity.CutOffResponse;
import com.example.isyncpos.response.entity.DiscountDetailsResponse;
import com.example.isyncpos.response.entity.DiscountOtherInformationResponse;
import com.example.isyncpos.response.entity.DiscountsResponse;
import com.example.isyncpos.response.entity.EndOfDayProductsResponse;
import com.example.isyncpos.response.entity.EndOfDayResponse;
import com.example.isyncpos.response.entity.ErrorBody;
import com.example.isyncpos.response.entity.OfficialReceiptInformationResponse;
import com.example.isyncpos.response.entity.OrdersResponse;
import com.example.isyncpos.response.entity.PaymentOtherInformationResponse;
import com.example.isyncpos.response.entity.PaymentsResponse;
import com.example.isyncpos.response.entity.SafekeepingDenominationResponse;
import com.example.isyncpos.response.entity.SafekeepingResponse;
import com.example.isyncpos.response.entity.SpotAuditDenominationResponse;
import com.example.isyncpos.response.entity.SpotAuditResponse;
import com.example.isyncpos.response.entity.TransactionsResponse;
import com.example.isyncpos.viewmodel.ApplicationSettingsViewModel;
import com.example.isyncpos.viewmodel.AuditTrailsViewModel;
import com.example.isyncpos.viewmodel.CutOffDepartmentsViewModel;
import com.example.isyncpos.viewmodel.CutOffDiscountsViewModel;
import com.example.isyncpos.viewmodel.CutOffPaymentsViewModel;
import com.example.isyncpos.viewmodel.CutOffProductsViewModel;
import com.example.isyncpos.viewmodel.CutOffViewModel;
import com.example.isyncpos.viewmodel.DiscountDetailsViewModel;
import com.example.isyncpos.viewmodel.DiscountOtherInformationsViewModel;
import com.example.isyncpos.viewmodel.DiscountsViewModel;
import com.example.isyncpos.viewmodel.EndOfDayDepartmentsViewModel;
import com.example.isyncpos.viewmodel.EndOfDayDiscountsViewModel;
import com.example.isyncpos.viewmodel.EndOfDayPaymentsViewModel;
import com.example.isyncpos.viewmodel.EndOfDayProductsViewModel;
import com.example.isyncpos.viewmodel.EndOfDayViewModel;
import com.example.isyncpos.viewmodel.OfficialReceiptInformationViewModel;
import com.example.isyncpos.viewmodel.OrdersViewModel;
import com.example.isyncpos.viewmodel.PaymentOtherInformationsViewModel;
import com.example.isyncpos.viewmodel.PaymentsViewModel;
import com.example.isyncpos.viewmodel.SafekeepingDenominationViewModel;
import com.example.isyncpos.viewmodel.SafekeepingViewModel;
import com.example.isyncpos.viewmodel.SpotAuditDenominationViewModel;
import com.example.isyncpos.viewmodel.SpotAuditViewModel;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public class TransactionSyncDialog extends DialogFragment {

    private AlertDialog alertDialog;
    private View view;
    private Gson gson;
    private TransactionsViewModel transactionsViewModel;
    private OrdersViewModel ordersViewModel;
    private PaymentsViewModel paymentsViewModel;
    private DiscountsViewModel discountsViewModel;
    private DiscountDetailsViewModel discountDetailsViewModel;
    private CutOffViewModel cutOffViewModel;
    private EndOfDayViewModel endOfDayViewModel;
    private ApplicationSettingsViewModel applicationSettingsViewModel;
    private SafekeepingViewModel safekeepingViewModel;
    private SafekeepingDenominationViewModel safekeepingDenominationViewModel;
    private PaymentOtherInformationsViewModel paymentOtherInformationsViewModel;
    private DiscountOtherInformationsViewModel discountOtherInformationsViewModel;
    private AuditTrailsViewModel auditTrailsViewModel;
    private OfficialReceiptInformationViewModel officialReceiptInformationViewModel;
    private SpotAuditViewModel spotAuditViewModel;
    private SpotAuditDenominationViewModel spotAuditDenominationViewModel;
    private CutOffDepartmentsViewModel cutOffDepartmentsViewModel;
    private CutOffDiscountsViewModel cutOffDiscountsViewModel;
    private CutOffPaymentsViewModel cutOffPaymentsViewModel;
    private CutOffProductsViewModel cutOffProductsViewModel;
    private EndOfDayDepartmentsViewModel endOfDayDepartmentsViewModel;
    private EndOfDayDiscountsViewModel endOfDayDiscountsViewModel;
    private EndOfDayPaymentsViewModel endOfDayPaymentsViewModel;
    private EndOfDayProductsViewModel endOfDayProductsViewModel;

    interface RequestAPI {
        //Transaction Sync
        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/branch-transactions")
        Call<TransactionsResponse> TransactionSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/branch-orders")
        Call<OrdersResponse> OrdersSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/branch-payments")
        Call<PaymentsResponse> PaymentsSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/discounts")
        Call<DiscountsResponse> DiscountSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/discount-details")
        Call<DiscountDetailsResponse> DiscountDetailsSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/cut-offs")
        Call<CutOffResponse> CutOffSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/end-of-days")
        Call<EndOfDayResponse> EndOfDaySync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/safekeeping")
        Call<SafekeepingResponse> SafekeepingSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/safekeeping-denominations")
        Call<SafekeepingDenominationResponse> SafekeepingDenominationSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/payment-other-informations")
        Call<PaymentOtherInformationResponse> PaymentOtherInformationSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/discount-other-informations")
        Call<DiscountOtherInformationResponse> DiscountOtherInformationSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/audit-trails")
        Call<AuditTrailResponse> AuditTrailsSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/official-receipt-informations")
        Call<OfficialReceiptInformationResponse> OfficialReceiptInformationSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/spot-audits")
        Call<SpotAuditResponse> SpotAuditSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/spot-audit-denominations")
        Call<SpotAuditDenominationResponse> SpotAuditDenominationSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/cut-off-departments")
        Call<CutOffDepartmentsResponse> CutOffDepartmentsSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/cut-off-discounts")
        Call<CutOffDiscountsResponse> CutOffDiscountsSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/cut-off-payments")
        Call<CutOffPaymentsResponse> CutOffPaymentsSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/cut-off-products")
        Call<CutOffProductsResponse> CutOffProductsSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/end-of-day-products")
        Call<EndOfDayProductsResponse> EndOfDayProductsSync(@Header("Authorization") String authorization, @Query("device_id") int deviceId, @Query("branch_id") int branchId, @Query("pos_machine_id") int posMachineId);

    }

    public static TransactionSyncDialog newTransactionSyncDialog(){
        return new TransactionSyncDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_transaction_sync, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        initViewModels();
        initialize();

        alertDialog = builder.create();
        return alertDialog;
    }

    private void initialize(){
        gson = new Gson();
        Transaction();
    }

    private void initViewModels(){
        transactionsViewModel = POSApplication.getInstance().getTransactionsViewModel();
        ordersViewModel = POSApplication.getInstance().getOrdersViewModel();
        paymentsViewModel = POSApplication.getInstance().getPaymentsViewModel();
        discountsViewModel = POSApplication.getInstance().getDiscountsViewModel();
        discountDetailsViewModel = POSApplication.getInstance().getDiscountDetailsViewModel();
        cutOffViewModel = POSApplication.getInstance().getCutOffViewModel();
        endOfDayViewModel = POSApplication.getInstance().getEndOfDayViewModel();
        applicationSettingsViewModel = POSApplication.getInstance().getApplicationSettingsViewModel();
        safekeepingViewModel = POSApplication.getInstance().getSafekeepingViewModel();
        safekeepingDenominationViewModel = POSApplication.getInstance().getSafekeepingDenominationViewModel();
        paymentOtherInformationsViewModel = POSApplication.getInstance().getPaymentOtherInformationsViewModel();
        discountOtherInformationsViewModel = POSApplication.getInstance().getDiscountOtherInformationsViewModel();
        auditTrailsViewModel = POSApplication.getInstance().getAuditTrailsViewModel();
        officialReceiptInformationViewModel = POSApplication.getInstance().getOfficialReceiptInformationViewModel();
        spotAuditViewModel = POSApplication.getInstance().getSpotAuditViewModel();
        spotAuditDenominationViewModel = POSApplication.getInstance().getSpotAuditDenominationViewModel();
        cutOffDepartmentsViewModel = POSApplication.getInstance().getCutOffDepartmentsViewModel();
        cutOffDiscountsViewModel = POSApplication.getInstance().getCutOffDiscountsViewModel();
        cutOffPaymentsViewModel = POSApplication.getInstance().getCutOffPaymentsViewModel();
        cutOffProductsViewModel = POSApplication.getInstance().getCutOffProductsViewModel();
        endOfDayDepartmentsViewModel = POSApplication.getInstance().getEndOfDayDepartmentsViewModel();
        endOfDayDiscountsViewModel = POSApplication.getInstance().getEndOfDayDiscountsViewModel();
        endOfDayPaymentsViewModel = POSApplication.getInstance().getEndOfDayPaymentsViewModel();
        endOfDayProductsViewModel = POSApplication.getInstance().getEndOfDayProductsViewModel();
    }

    private void Transaction(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.TransactionSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId())
        .enqueue(new Callback<TransactionsResponse>() {
            @Override
            public void onResponse(Call<TransactionsResponse> call, Response<TransactionsResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getData().size() != 0){
                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                        response.body().getData().forEach(item -> {
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Transactions transactions = new Transactions(
                                                item.getPos_machine_id(),
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
                                                item.getChange(),
                                                item.getService_charge(),
                                                item.getType(),
                                                item.getCashier_id(),
                                                item.getCashier_name(),
                                                item.getTake_order_id(),
                                                item.getTake_order_name(),
                                                item.getTotal_quantity(),
                                                item.getTotal_unit_cost(),
                                                item.getTotal_void_amount(),
                                                item.getShift_number(),
                                                item.getIs_void(),
                                                item.getVoid_by_id(),
                                                item.getVoid_by(),
                                                item.getVoid_at(),
                                                item.getIs_back_out(),
                                                item.getIs_back_out_id(),
                                                item.getBack_out_by(),
                                                item.getBackout_at(),
                                                item.getCharge_account_id(),
                                                item.getCharge_account_name(),
                                                item.getIs_account_receivable(),
                                               1,
                                                item.getIs_complete(),
                                                item.getCompleted_at(),
                                                item.getIs_cut_off(),
                                                item.getCut_off_id(),
                                                item.getCut_off_at(),
                                                item.getBranch_id(),
                                                item.getGuest_name(),
                                                item.getIs_resume_printed(),
                                                item.getTreg(),
                                                item.getTotal_void_qty(),
                                                item.getIs_return(),
                                                item.getTotal_return_amount(),
                                                item.getTotal_cash_amount(),
                                                item.getVoid_remarks(),
                                                item.getVoid_counter(),
                                                item.getTotal_zero_rated_amount(),
                                                item.getCustomer_name(),
                                                item.getRemarks(),
                                                item.getCompany_id(),
                                                item.getIs_account_receivable_redeem(),
                                                item.getAccount_receivable_redeem_at()
                                        );
                                        transactions.setId(item.getTransaction_id());
                                        transactionsViewModel.insert(transactions);
                                    } catch (ExecutionException e) {
                                        throw new RuntimeException(e);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }

                                }
                            });
                        });
                        orders();
                    }
                    else{
                        orders();
                    }
                }
                else{
                    orders();
                    ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                    Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TransactionsResponse> call, Throwable t) {
                orders();
                Log.d("TransactionSync", "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "Failed To Sync Transaction.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void orders(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.OrdersSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId())
            .enqueue(new Callback<OrdersResponse>() {
                @Override
                public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().getData().size() != 0){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            response.body().getData().forEach(item -> {
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Orders orders = new Orders(
                                                    item.getPos_machine_id(),
                                                    item.getTransaction_id(),
                                                    item.getProduct_id(),
                                                    item.getCode(),
                                                    item.getName(),
                                                    item.getDescription(),
                                                    item.getAbbreviation(),
                                                    item.getCost(),
                                                    item.getQty(),
                                                    item.getAmount(),
                                                    item.getOriginal_amount(),
                                                    item.getGross(),
                                                    item.getTotal(),
                                                    item.getTotal_cost(),
                                                    item.getIs_vatable(),
                                                    item.getVat_amount(),
                                                    item.getVatable_sales(),
                                                    item.getVat_exempt_sales(),
                                                    item.getDiscount_amount(),
                                                    item.getVat_expense(),
                                                    item.getDepartment_id(),
                                                    item.getDepartment_name(),
                                                    item.getCategory_id(),
                                                    item.getCategory_name(),
                                                    item.getSubcategory_id(),
                                                    item.getSubcategory_name(),
                                                    item.getUnit_id(),
                                                    item.getUnit_name(),
                                                    item.getIs_discount_exempt(),
                                                    item.getIs_open_price(),
                                                    item.getWith_serial(),
                                                    item.getIs_void(),
                                                    item.getVoid_by(),
                                                    item.getVoid_at(),
                                                    item.getIs_back_out(),
                                                    item.getIs_back_out_id(),
                                                    item.getBack_out_by(),
                                                    item.getMin_amount_sold(),
                                                    item.getIs_paid(),
                                                    1,
                                                    item.getIs_completed(),
                                                    item.getCompleted_at(),
                                                    item.getBranch_id(),
                                                    item.getShift_number(),
                                                    item.getCut_off_id(),
                                                    item.getTreg(),
                                                    item.getIs_cut_off(),
                                                    item.getCut_off_at(),
                                                    item.getDiscount_details_id(),
                                                    item.getRemarks(),
                                                    item.getIs_return(),
                                                    item.getSerial_number(),
                                                    item.getIs_zero_rated(),
                                                    item.getTotal_zero_rated_amount(),
                                                    item.getCompany_id(),
                                                    item.getPrice_change_reason_id()
                                            );
                                            orders.setId(item.getOrder_id());
                                            ordersViewModel.insert(orders);
                                        } catch (ExecutionException e) {
                                            throw new RuntimeException(e);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }

                                    }
                                });
                            });
                            payments();
                        }
                        else{
                            payments();
                        }
                    }
                    else{
                        payments();
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<OrdersResponse> call, Throwable t) {
                    payments();
                    Log.d("OrdersSync", "onFailure: " + t.getMessage());
                    Toast.makeText(getContext(), "Failed To Sync Orders.", Toast.LENGTH_LONG).show();
                }
            });
    }

    private void payments(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.PaymentsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId())
            .enqueue(new Callback<PaymentsResponse>() {
                @Override
                public void onResponse(Call<PaymentsResponse> call, Response<PaymentsResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().getData().size() != 0){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            response.body().getData().forEach(item -> {
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Payments payments = new Payments(
                                                    item.getPos_machine_id(),
                                                    item.getBranch_id(),
                                                    item.getTransaction_id(),
                                                    item.getPayment_type_id(),
                                                    item.getPayment_type_name(),
                                                    item.getAmount(),
                                                    item.getIs_advance_payment(),
                                                    item.getShift_number(),
                                                    1,
                                                    item.getCut_off_id(),
                                                    item.getTreg(),
                                                    item.getIs_cut_off(),
                                                    item.getCut_off_at(),
                                                    item.getIs_void(),
                                                    item.getVoid_by(),
                                                    item.getVoid_at(),
                                                    item.getVoid_by_id(),
                                                    item.getCompany_id(),
                                                    item.getIs_account_receivable()
                                            );
                                            payments.setId(item.getPayment_id());
                                            paymentsViewModel.insert(payments);
                                        } catch (ExecutionException e) {
                                            throw new RuntimeException(e);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }

                                    }
                                });
                            });
                            discounts();
                        }
                        else{
                            discounts();
                        }
                    }
                    else{
                        discounts();
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<PaymentsResponse> call, Throwable t) {
                    discounts();
                    Log.d("PaymentsSync", "onFailure: " + t.getMessage());
                    Toast.makeText(getContext(), "Failed To Sync Payments.", Toast.LENGTH_LONG).show();
                }
            });
    }

    private void discounts(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.DiscountSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId())
            .enqueue(new Callback<DiscountsResponse>() {
                @Override
                public void onResponse(Call<DiscountsResponse> call, Response<DiscountsResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().getData().size() != 0){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            response.body().getData().forEach(item -> {
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Discounts discounts = new Discounts(
                                                    item.getPos_machine_id(),
                                                    item.getBranch_id(),
                                                    item.getTransaction_id(),
                                                    item.getCustom_discount_id(),
                                                    item.getDiscount_type_id(),
                                                    item.getDiscount_name(),
                                                    item.getValue(),
                                                    item.getDiscount_amount(),
                                                    item.getVat_exempt_amount(),
                                                    item.getVat_expense(),
                                                    item.getType(),
                                                    item.getCashier_id(),
                                                    item.getCashier_name(),
                                                    item.getAuthorize_id(),
                                                    item.getAuthorize_name(),
                                                    item.getIs_void(),
                                                    item.getVoid_by_id(),
                                                    item.getVoid_by(),
                                                    item.getVoid_at(),
                                                    1,
                                                    item.getIs_cut_off(),
                                                    item.getCut_off_id(),
                                                    item.getShift_number(),
                                                    item.getTreg(),
                                                    item.getIs_zero_rated(),
                                                    item.getGross_amount(),
                                                    item.getNet_amount(),
                                                    item.getCompany_id()
                                            );
                                            discounts.setId(item.getDiscount_id());
                                            discountsViewModel.insert(discounts);
                                        } catch (ExecutionException e) {
                                            throw new RuntimeException(e);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }

                                    }
                                });
                            });
                            discountDetails();
                        }
                        else{
                            discountDetails();
                        }
                    }
                    else{
                        discountDetails();
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<DiscountsResponse> call, Throwable t) {
                    discountDetails();
                    Log.d("DiscountsSync", "onFailure: " + t.getMessage());
                    Toast.makeText(getContext(), "Failed To Sync Discounts.", Toast.LENGTH_LONG).show();
                }
            });
    }

    private void discountDetails(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.DiscountDetailsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId())
            .enqueue(new Callback<DiscountDetailsResponse>() {
                @Override
                public void onResponse(Call<DiscountDetailsResponse> call, Response<DiscountDetailsResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().getData().size() != 0){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            response.body().getData().forEach(item -> {
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        DiscountDetails discountDetails = new DiscountDetails(
                                                item.getDiscount_id(),
                                                item.getPos_machine_id(),
                                                item.getBranch_id(),
                                                item.getCustom_discount_id(),
                                                item.getTransaction_id(),
                                                item.getOrder_id(),
                                                item.getDiscount_type_id(),
                                                item.getValue(),
                                                item.getDiscount_amount(),
                                                item.getVat_exempt_amount(),
                                                item.getVat_expense(),
                                                item.getType(),
                                                item.getIs_void(),
                                                item.getVoid_by_id(),
                                                item.getVoid_by(),
                                                item.getVoid_at(),
                                                1,
                                                item.getIs_cut_off(),
                                                item.getCut_off_id(),
                                                item.getIs_vat_exempt() == 1 ? true : false,
                                                item.getShift_number(),
                                                item.getTreg(),
                                                item.getIs_zero_rated() == 1 ? true : false,
                                                item.getCompany_id()
                                        );
                                        discountDetails.setId(item.getDiscount_details_id());
                                        discountDetailsViewModel.insert(discountDetails);

                                    }
                                });
                            });
                            cutOff();
                        }
                        else{
                            cutOff();
                        }
                    }
                    else{
                        cutOff();
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<DiscountDetailsResponse> call, Throwable t) {
                    cutOff();
                    Log.d("DiscountDetailsSync", "onFailure: " + t.getMessage());
                    Toast.makeText(getContext(), "Failed To Sync Discount Details.", Toast.LENGTH_LONG).show();
                }
            });
    }

    private void cutOff(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.CutOffSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId())
            .enqueue(new Callback<CutOffResponse>() {
                @Override
                public void onResponse(Call<CutOffResponse> call, Response<CutOffResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().getData().size() != 0){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            response.body().getData().forEach(item -> {
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {

                                        try {
                                            CutOff cutOff = new CutOff(
                                                    item.getPos_machine_id(),
                                                    item.getBranch_id(),
                                                    item.getReading_number(),
                                                    item.getBeginning_or(),
                                                    item.getEnding_or(),
                                                    item.getBeginning_amount(),
                                                    item.getEnding_amount(),
                                                    item.getTotal_transactions(),
                                                    item.getGross_sales(),
                                                    item.getNet_sales(),
                                                    item.getVatable_sales(),
                                                    item.getVat_exempt_sales(),
                                                    item.getVat_amount(),
                                                    item.getVat_expense(),
                                                    item.getVoid_qty(),
                                                    item.getVoid_amount(),
                                                    item.getTotal_change(),
                                                    item.getTotal_payout(),
                                                    item.getTotal_service_charge(),
                                                    item.getTotal_discount_amount(),
                                                    item.getTotal_cost(),
                                                    item.getTotal_sk(),
                                                    item.getCashier_id(),
                                                    item.getCashier_name(),
                                                    item.getAdmin_id(),
                                                    item.getAdmin_name(),
                                                    item.getShift_number(),
                                                    item.getEnd_of_day_id(),
                                                    1,
                                                    item.getTreg(),
                                                    item.getTotal_short_over(),
                                                    item.getTotal_zero_rated_amount(),
                                                    item.getPrint_string(),
                                                    item.getIs_complete(),
                                                    item.getCompany_id(),
                                                    item.getTotal_return()
                                            );
                                            cutOff.setId(item.getCut_off_id());
                                            cutOffViewModel.insert(cutOff);
                                        } catch (ExecutionException e) {
                                            throw new RuntimeException(e);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }

                                    }
                                });
                            });
                            endOfDay();
                        }
                        else{
                            endOfDay();
                        }
                    }
                    else{
                        endOfDay();
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<CutOffResponse> call, Throwable t) {
                    endOfDay();
                    Log.d("CutOffSync", "onFailure: " + t.getMessage());
                    Toast.makeText(getContext(), "Failed To Sync CutOff.", Toast.LENGTH_LONG).show();
                }
            });
    }

    private void endOfDay(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.EndOfDaySync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId())
            .enqueue(new Callback<EndOfDayResponse>() {
                @Override
                public void onResponse(Call<EndOfDayResponse> call, Response<EndOfDayResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().getData().size() != 0){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            response.body().getData().forEach(item -> {
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {

                                        try {
                                            EndOfDay endOfDay = new EndOfDay(
                                                    item.getPos_machine_id(),
                                                    item.getBranch_id(),
                                                    item.getReading_number(),
                                                    item.getBeginning_or(),
                                                    item.getEnding_or(),
                                                    item.getBeginning_amount(),
                                                    item.getEnding_amount(),
                                                    item.getTotal_transactions(),
                                                    item.getGross_sales(),
                                                    item.getNet_sales(),
                                                    item.getVatable_sales(),
                                                    item.getVat_exempt_sales(),
                                                    item.getVat_amount(),
                                                    item.getVat_expense(),
                                                    item.getVoid_qty(),
                                                    item.getVoid_amount(),
                                                    item.getTotal_change(),
                                                    item.getTotal_payout(),
                                                    item.getTotal_service_charge(),
                                                    item.getTotal_discount_amount(),
                                                    item.getTotal_cost(),
                                                    item.getTotal_sk(),
                                                    item.getCashier_id(),
                                                    item.getCashier_name(),
                                                    item.getAdmin_id(),
                                                    item.getAdmin_name(),
                                                    item.getShift_number(),
                                                    1,
                                                    item.getTreg(),
                                                    item.getTotal_short_over(),
                                                    1,
                                                    item.getGenerated_date(),
                                                    item.getTotal_zero_rated_amount(),
                                                    item.getBeg_reading_number(),
                                                    item.getEnd_reading_number(),
                                                    item.getPrint_string(),
                                                    item.getCompany_id(),
                                                    item.getTotal_return()
                                            );
                                            endOfDay.setId(item.getEnd_of_day_id());

                                            //Departments
                                            item.getDepartments().forEach(dep -> {

                                                EndOfDayDepartments endOfDayDepartments = new EndOfDayDepartments(
                                                        dep.getPos_machine_id(),
                                                        dep.getBranch_id(),
                                                        dep.getEnd_of_day_id(),
                                                        dep.getDepartment_id(),
                                                        dep.getName(),
                                                        dep.getTransaction_count(),
                                                        dep.getAmount(),
                                                        1,
                                                        dep.getTreg(),
                                                        dep.getCompany_id()
                                                );
                                                endOfDayDepartments.setId(dep.getEnd_of_day_department_id());
                                                endOfDayDepartmentsViewModel.insert(endOfDayDepartments);

                                            });
                                            //Discounts
                                            item.getDiscounts().forEach(disc -> {

                                                EndOfDayDiscounts endOfDayDiscounts = new EndOfDayDiscounts(
                                                        disc.getPos_machine_id(),
                                                        disc.getBranch_id(),
                                                        disc.getEnd_of_day_id(),
                                                        disc.getDiscount_type_id(),
                                                        disc.getName(),
                                                        disc.getTransaction_count(),
                                                        disc.getAmount(),
                                                        1,
                                                        disc.getTreg(),
                                                        disc.getIs_zero_rated(),
                                                        disc.getCompany_id()
                                                );
                                                endOfDayDiscounts.setId(disc.getEnd_of_day_discount_id());
                                                endOfDayDiscountsViewModel.insert(endOfDayDiscounts);

                                            });

                                            //Payments
                                            item.getPayments().forEach(pay -> {

                                                EndOfDayPayments endOfDayPayments = new EndOfDayPayments(
                                                        pay.getPos_machine_id(),
                                                        pay.getBranch_id(),
                                                        pay.getEnd_of_day_id(),
                                                        pay.getPayment_type_id(),
                                                        pay.getName(),
                                                        pay.getTransaction_count(),
                                                        pay.getAmount(),
                                                        1,
                                                        pay.getTreg(),
                                                        pay.getCompany_id()
                                                );
                                                endOfDayPayments.setId(pay.getEnd_of_day_payment_id());
                                                endOfDayPaymentsViewModel.insert(endOfDayPayments);

                                            });

                                            endOfDayViewModel.insert(endOfDay);
                                        } catch (ExecutionException e) {
                                            throw new RuntimeException(e);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }

                                    }
                                });
                            });
                            safeKeeping();
                        }
                        else{
                            safeKeeping();
                        }
                    }
                    else{
                        safeKeeping();
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<EndOfDayResponse> call, Throwable t) {
                    safeKeeping();
                    Log.d("EndOfDaySync", "onFailure: " + t.getMessage());
                    Toast.makeText(getContext(), "Failed To Sync End Of Day.", Toast.LENGTH_LONG).show();
                }
            });
    }

    private void safeKeeping(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.SafekeepingSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId())
            .enqueue(new Callback<SafekeepingResponse>() {
                @Override
                public void onResponse(Call<SafekeepingResponse> call, Response<SafekeepingResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().getData().size() != 0){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            response.body().getData().forEach(item -> {
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {

                                        Safekeeping safekeeping = new Safekeeping(
                                            item.getPos_machine_id(),
                                            item.getBranch_id(),
                                            item.getAmount(),
                                            item.getCashier_id(),
                                            item.getCashier_name(),
                                            item.getAuthorize_id(),
                                            item.getAuthorize_name(),
                                            item.getIs_cut_off(),
                                            item.getCut_off_id(),
                                            1,
                                            item.getShift_number(),
                                            item.getTreg(),
                                            item.getEnd_of_day_id(),
                                            item.getIs_auto(),
                                            item.getShort_over(),
                                            item.getCompany_id()
                                        );
                                        safekeeping.setId(item.getSafekeeping_id());
                                        try {
                                            safekeepingViewModel.insert(safekeeping);
                                        } catch (ExecutionException e) {
                                            throw new RuntimeException(e);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }

                                    }
                                });
                            });
                            safeKeepingDenomination();
                        }
                        else{
                            safeKeepingDenomination();
                        }
                    }
                    else{
                        safeKeepingDenomination();
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<SafekeepingResponse> call, Throwable t) {
                    safeKeepingDenomination();
                    Log.d("SafekeepingSync", "onFailure: " + t.getMessage());
                    Toast.makeText(getContext(), "Failed To Sync Safekeeping." + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
    }

    private void safeKeepingDenomination(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.SafekeepingDenominationSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId())
            .enqueue(new Callback<SafekeepingDenominationResponse>() {
                @Override
                public void onResponse(Call<SafekeepingDenominationResponse> call, Response<SafekeepingDenominationResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().getData().size() != 0){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            response.body().getData().forEach(item -> {
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {

                                        SafekeepingDenomination safekeepingDenomination = new SafekeepingDenomination(
                                            item.getPos_machine_id(),
                                            item.getBranch_id(),
                                            item.getSafekeeping_id(),
                                            item.getCash_denomination_id(),
                                            item.getName(),
                                            item.getAmount(),
                                            item.getQty(),
                                            item.getTotal(),
                                            item.getShift_number(),
                                            item.getCut_off_id(),
                                            item.getTreg(),
                                            item.getIs_cut_off(),
                                            item.getEnd_of_day_id(),
                                            1,
                                            item.getCompany_id()
                                        );
                                        safekeepingDenomination.setId(item.getSafekeeping_denomination_id());
                                        safekeepingDenominationViewModel.insert(safekeepingDenomination);

                                    }
                                });
                            });
                            paymentOtherInformation();
                        }
                        else{
                            paymentOtherInformation();
                        }
                    }
                    else{
                        paymentOtherInformation();
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<SafekeepingDenominationResponse> call, Throwable t) {
                    paymentOtherInformation();
                    Log.d("SafekeepingDenominationSync", "onFailure: " + t.getMessage());
                    Toast.makeText(getContext(), "Failed To Sync Safekeeping Denomination.", Toast.LENGTH_LONG).show();
                }
            });
    }

    private void paymentOtherInformation(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.PaymentOtherInformationSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId())
                .enqueue(new Callback<PaymentOtherInformationResponse>() {
                    @Override
                    public void onResponse(Call<PaymentOtherInformationResponse> call, Response<PaymentOtherInformationResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body().getData().size() != 0){
                                ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                                response.body().getData().forEach(item -> {
                                    executorService.execute(new Runnable() {
                                        @Override
                                        public void run() {

                                            PaymentOtherInformations paymentOtherInformations = new PaymentOtherInformations(
                                                item.getPos_machine_id(),
                                                item.getBranch_id(),
                                                item.getTransaction_id(),
                                                item.getPayment_id(),
                                                item.getName(),
                                                item.getValue(),
                                                item.getIs_cut_off(),
                                                item.getCut_off_id(),
                                                item.getIs_void(),
                                                1,
                                                item.getTreg(),
                                                item.getCompany_id()
                                            );
                                            paymentOtherInformations.setId(item.getPayment_other_information_id());
                                            paymentOtherInformationsViewModel.insert(paymentOtherInformations);

                                        }
                                    });
                                });
                                discountOtherInformation();
                            }
                            else{
                                discountOtherInformation();
                            }
                        }
                        else{
                            discountOtherInformation();
                            ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                            Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PaymentOtherInformationResponse> call, Throwable t) {
                        discountOtherInformation();
                        Log.d("PaymentOtherInformationSync", "onFailure: " + t.getMessage());
                        Toast.makeText(getContext(), "Failed To Sync Payment Other Information.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void discountOtherInformation(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.DiscountOtherInformationSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId())
                .enqueue(new Callback<DiscountOtherInformationResponse>() {
                    @Override
                    public void onResponse(Call<DiscountOtherInformationResponse> call, Response<DiscountOtherInformationResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body().getData().size() != 0){
                                ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                                response.body().getData().forEach(item -> {
                                    executorService.execute(new Runnable() {
                                        @Override
                                        public void run() {

                                            DiscountOtherInformations discountOtherInformations = new DiscountOtherInformations(
                                                    item.getPos_machine_id(),
                                                    item.getBranch_id(),
                                                    item.getTransaction_id(),
                                                    item.getDiscount_id(),
                                                    item.getName(),
                                                    item.getValue(),
                                                    item.getIs_cut_off(),
                                                    item.getCut_off_id(),
                                                    item.getIs_void(),
                                                    1,
                                                    item.getTreg(),
                                                    item.getCompany_id()
                                            );
                                            discountOtherInformations.setId(item.getDiscount_other_information_id());
                                            discountOtherInformationsViewModel.insert(discountOtherInformations);

                                        }
                                    });
                                });
                                auditTrails();
                            }
                            else{
                                auditTrails();
                            }
                        }
                        else{
                            auditTrails();
                            ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                            Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DiscountOtherInformationResponse> call, Throwable t) {
                        auditTrails();
                        Log.d("DiscountOtherInformationSync", "onFailure: " + t.getMessage());
                        Toast.makeText(getContext(), "Failed To Sync Discount Other Information.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void auditTrails(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.AuditTrailsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId())
                .enqueue(new Callback<AuditTrailResponse>() {
                    @Override
                    public void onResponse(Call<AuditTrailResponse> call, Response<AuditTrailResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body().getData().size() != 0) {
                                ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                                response.body().getData().forEach(item -> {
                                    executorService.execute(new Runnable() {
                                        @Override
                                        public void run() {

                                            AuditTrails auditTrails = new AuditTrails(
                                                    item.getPos_machine_id(),
                                                    item.getBranch_id(),
                                                    item.getUser_id(),
                                                    item.getUser_name(),
                                                    item.getTransaction_id(),
                                                    item.getAction(),
                                                    item.getDescription(),
                                                    item.getAuthorize_id(),
                                                    item.getAuthorize_name(),
                                                    1,
                                                    item.getTreg(),
                                                    item.getOrder_id(),
                                                    item.getPrice_change_reason_id(),
                                                    item.getCompany_id()
                                            );
                                            auditTrails.setId(item.getAudit_trail_id());
                                            auditTrailsViewModel.insert(auditTrails);

                                        }
                                    });
                                });
                                officialReceiptInformation();
                            }
                            else{
                                officialReceiptInformation();
                            }
                        }
                        else{
                            officialReceiptInformation();
                            ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                            Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuditTrailResponse> call, Throwable t) {
                        officialReceiptInformation();
                        Log.d("AuditTrailsSync", "onFailure: " + t.getMessage());
                        Toast.makeText(getContext(), "Failed To Sync Audit Trails.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void officialReceiptInformation(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.OfficialReceiptInformationSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId()).enqueue(new Callback<OfficialReceiptInformationResponse>() {
            @Override
            public void onResponse(Call<OfficialReceiptInformationResponse> call, Response<OfficialReceiptInformationResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getData().size() != 0){
                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                        response.body().getData().forEach(item -> {
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {

                                    OfficialReceiptInformation officialReceiptInformation = new OfficialReceiptInformation(
                                            item.getTransaction_id(),
                                            item.getName(),
                                            item.getAddress(),
                                            item.getTin(),
                                            item.getBusiness_style(),
                                            item.getIs_void(),
                                            item.getVoid_by(),
                                            item.getVoid_name(),
                                            item.getVoid_at(),
                                            1,
                                            item.getTreg(),
                                            item.getPos_machine_id(),
                                            item.getBranch_id(),
                                            item.getCompany_id()
                                    );
                                    officialReceiptInformation.setId(item.getOfficial_receipt_information_id());
                                    officialReceiptInformationViewModel.insert(officialReceiptInformation);

                                }
                            });
                        });
                        spotAudit();
                    }
                    else{
                        spotAudit();
                    }
                }
                else{
                    spotAudit();
                    ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                    Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OfficialReceiptInformationResponse> call, Throwable t) {
                spotAudit();
                Log.d("OfficialReceiptInformationSync", "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "Failed To Sync Official Receipt Informations.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void spotAudit(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.SpotAuditSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId()).enqueue(new Callback<SpotAuditResponse>() {
            @Override
            public void onResponse(Call<SpotAuditResponse> call, Response<SpotAuditResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().getData().size() != 0) {
                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                        response.body().getData().forEach(item -> {
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {

                                    SpotAudit spotAudit = new SpotAudit(
                                            item.getPos_machine_id(),
                                            item.getBranch_id(),
                                            item.getBeginning_or(),
                                            item.getEnding_or(),
                                            item.getBeginning_amount(),
                                            item.getEnding_amount(),
                                            item.getTotal_transactions(),
                                            item.getGross_sales(),
                                            item.getNet_sales(),
                                            item.getVatable_sales(),
                                            item.getVat_exempt_sales(),
                                            item.getVat_amount(),
                                            item.getVat_expense(),
                                            item.getVoid_qty(),
                                            item.getVoid_amount(),
                                            item.getTotal_change(),
                                            item.getTotal_payout(),
                                            item.getTotal_service_charge(),
                                            item.getTotal_discount_amount(),
                                            item.getTotal_cost(),
                                            item.getSafekeeping_amount(),
                                            item.getTotal_sk(),
                                            item.getTotal_short_over(),
                                            item.getCashier_id(),
                                            item.getCashier_name(),
                                            item.getAdmin_id(),
                                            item.getAdmin_name(),
                                            item.getShift_number(),
                                            item.getIs_cut_off(),
                                            item.getCut_off_id(),
                                            1,
                                            item.getTreg(),
                                            item.getSafekeeping_short_over(),
                                            item.getCompany_id()
                                    );
                                    spotAudit.setId(item.getSpot_audit_id());
                                    try {
                                        spotAuditViewModel.insert(spotAudit);
                                    } catch (ExecutionException e) {
                                        throw new RuntimeException(e);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }

                                }
                            });
                        });
                        spotAuditDenomination();
                    }
                    else{
                        spotAuditDenomination();
                    }
                }
                else{
                    spotAuditDenomination();
                    ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                    Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SpotAuditResponse> call, Throwable t) {
                spotAuditDenomination();
                Log.d("SpotAuditSync", "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "Failed To Sync Spot Audit.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void spotAuditDenomination(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.SpotAuditDenominationSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId()).enqueue(new Callback<SpotAuditDenominationResponse>() {
            @Override
            public void onResponse(Call<SpotAuditDenominationResponse> call, Response<SpotAuditDenominationResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().getData().size() != 0) {
                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                        response.body().getData().forEach(item -> {
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {

                                    SpotAuditDenomination spotAuditDenomination = new SpotAuditDenomination(
                                            item.getPos_machine_id(),
                                            item.getBranch_id(),
                                            item.getSpot_audit_id(),
                                            item.getCash_denomination_id(),
                                            item.getName(),
                                            item.getAmount(),
                                            item.getQty(),
                                            item.getTotal(),
                                            item.getIs_cut_off(),
                                            item.getCut_off_id(),
                                            1,
                                            item.getShift_number(),
                                            item.getTreg(),
                                            item.getCompany_id()
                                    );
                                    spotAuditDenomination.setId(item.getSpot_audit_denomination_id());
                                    spotAuditDenominationViewModel.insert(spotAuditDenomination);

                                }
                            });
                        });
                        cutOffDepartments();
                    }
                    else{
                        cutOffDepartments();
                    }
                }
                else{
                    cutOffDepartments();
                    ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                    Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SpotAuditDenominationResponse> call, Throwable t) {
                cutOffDepartments();
                Log.d("SpotAuditDenominationSync", "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "Failed To Sync Spot Audit Denomination.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void cutOffDepartments(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.CutOffDepartmentsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId()).enqueue(new Callback<CutOffDepartmentsResponse>() {
            @Override
            public void onResponse(Call<CutOffDepartmentsResponse> call, Response<CutOffDepartmentsResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().getData().size() != 0) {
                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                        response.body().getData().forEach(item -> {
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {

                                    CutOffDepartments cutOffDepartments = new CutOffDepartments(
                                        item.getPos_machine_id(),
                                            item.getBranch_id(),
                                            item.getCut_off_id(),
                                            item.getDepartment_id(),
                                            item.getName(),
                                            item.getTransaction_count(),
                                            item.getAmount(),
                                            item.getEnd_of_day_id(),
                                            1,
                                            item.getTreg(),
                                            item.getCompany_id()
                                    );
                                    cutOffDepartments.setId(item.getCut_off_department_id());
                                    cutOffDepartmentsViewModel.insert(cutOffDepartments);

                                }
                            });
                        });
                        cutOffDiscounts();
                    }
                    else{
                        cutOffDiscounts();
                    }
                }
                else{
                    cutOffDiscounts();
                    ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                    Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CutOffDepartmentsResponse> call, Throwable t) {
                cutOffDiscounts();
                Log.d("CutOffDepartmentsSync", "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "Failed To Sync Cut Off Departments.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void cutOffDiscounts(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.CutOffDiscountsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId()).enqueue(new Callback<CutOffDiscountsResponse>() {
            @Override
            public void onResponse(Call<CutOffDiscountsResponse> call, Response<CutOffDiscountsResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().getData().size() != 0) {
                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                        response.body().getData().forEach(item -> {
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {

                                    CutOffDiscounts cutOffDiscounts = new CutOffDiscounts(
                                            item.getPos_machine_id(),
                                            item.getBranch_id(),
                                            item.getCut_off_id(),
                                            item.getDiscount_type_id(),
                                            item.getName(),
                                            item.getTransaction_count(),
                                            item.getAmount(),
                                            item.getEnd_of_day_id(),
                                            1,
                                            item.getTreg(),
                                            item.getIs_zero_rated(),
                                            item.getCompany_id()
                                    );
                                    cutOffDiscounts.setId(item.getCut_off_discount_id());
                                    cutOffDiscountsViewModel.insert(cutOffDiscounts);

                                }
                            });
                        });
                        cutOffPayments();
                    }
                    else{
                        cutOffPayments();
                    }
                }
                else{
                    cutOffPayments();
                    ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                    Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CutOffDiscountsResponse> call, Throwable t) {
                cutOffPayments();
                Log.d("CutOffPaymentsSync", "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "Failed To Sync Cut Off Payments.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void cutOffPayments(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.CutOffPaymentsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId()).enqueue(new Callback<CutOffPaymentsResponse>() {
            @Override
            public void onResponse(Call<CutOffPaymentsResponse> call, Response<CutOffPaymentsResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().getData().size() != 0) {
                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                        response.body().getData().forEach(item -> {
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {

                                    CutOffPayments cutOffPayments = new CutOffPayments(
                                            item.getPos_machine_id(),
                                            item.getBranch_id(),
                                            item.getCut_off_id(),
                                            item.getPayment_type_id(),
                                            item.getName(),
                                            item.getTransaction_count(),
                                            item.getAmount(),
                                            item.getEnd_of_day_id(),
                                            1,
                                            item.getTreg(),
                                            item.getCompany_id()
                                    );
                                    cutOffPayments.setId(item.getCut_off_payment_id());
                                    cutOffPaymentsViewModel.insert(cutOffPayments);

                                }
                            });
                        });
                        cutOffProducts();
                    }
                    else{
                        cutOffProducts();
                    }
                }
                else{
                    cutOffProducts();
                    ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                    Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CutOffPaymentsResponse> call, Throwable t) {
                cutOffProducts();
                Log.d("CutOffPaymentsSync", "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "Failed To Sync Cut Off Payments.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void cutOffProducts(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.CutOffProductsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId()).enqueue(new Callback<CutOffProductsResponse>() {
            @Override
            public void onResponse(Call<CutOffProductsResponse> call, Response<CutOffProductsResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().getData().size() != 0) {
                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                        response.body().getData().forEach(item -> {
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {

                                    CutOffProducts cutOffProducts = new CutOffProducts(
                                            item.getCut_off_id(),
                                            item.getProduct_id(),
                                            item.getQty(),
                                            item.getIs_cut_off(),
                                            item.getCut_off_at(),
                                            item.getEnd_of_day_id(),
                                            1,
                                            item.getTreg(),
                                            item.getPos_machine_id(),
                                            item.getBranch_id(),
                                            item.getCompany_id()
                                    );
                                    cutOffProducts.setId(item.getCut_off_product_id());
                                    cutOffProductsViewModel.insert(cutOffProducts);

                                }
                            });
                        });
                        endOfDayProducts();
                    }
                    else{
                        endOfDayProducts();
                    }
                }
                else{
                    endOfDayProducts();
                    ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                    Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CutOffProductsResponse> call, Throwable t) {
                endOfDayProducts();
                Log.d("CutOffProductsSync", "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "Failed To Sync Cut Off Products.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void endOfDayProducts(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.EndOfDayProductsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getMachineDetails().getCoreId()).enqueue(new Callback<EndOfDayProductsResponse>() {
            @Override
            public void onResponse(Call<EndOfDayProductsResponse> call, Response<EndOfDayProductsResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().getData().size() != 0) {
                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                        response.body().getData().forEach(item -> {
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {

                                    EndOffDayProducts endOffDayProducts = new EndOffDayProducts(
                                            item.getEnd_of_day_id(),
                                            item.getProduct_id(),
                                            item.getQty(),
                                            1,
                                            item.getTreg(),
                                            item.getPos_machine_id(),
                                            item.getBranch_id(),
                                            item.getCompany_id()
                                    );
                                    endOffDayProducts.setId(item.getEnd_of_day_product_id());
                                    endOfDayProductsViewModel.insert(endOffDayProducts);

                                }
                            });
                        });
                        try {
                            ApplicationSettings applicationSettings = applicationSettingsViewModel.fetch();
                            applicationSettings.setFirstInstall(0);
                            applicationSettingsViewModel.update(applicationSettings);
                            dismiss();
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else{
                        try {
                            ApplicationSettings applicationSettings = applicationSettingsViewModel.fetch();
                            applicationSettings.setFirstInstall(0);
                            applicationSettingsViewModel.update(applicationSettings);
                            dismiss();
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                else{
                    dismiss();
                    ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                    Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<EndOfDayProductsResponse> call, Throwable t) {
                dismiss();
                Log.d("CutOffProductsSync", "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "Failed To Sync Cut Off Products.", Toast.LENGTH_LONG).show();
            }
        });
    }

}
