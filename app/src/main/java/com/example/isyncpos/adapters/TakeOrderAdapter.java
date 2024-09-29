package com.example.isyncpos.adapters;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.util.ObjectsCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.common.EncryptDecrypt;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.common.APIRequest;
import com.example.isyncpos.dialog.OrdersDialog;
import com.example.isyncpos.entity.DiscountDetails;
import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.entity.Discounts;
import com.example.isyncpos.entity.Orders;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.response.entity.DiscountDetailsResponse;
import com.example.isyncpos.response.entity.DiscountOtherInformationResponse;
import com.example.isyncpos.response.entity.DiscountsResponse;
import com.example.isyncpos.response.entity.ErrorBody;
import com.example.isyncpos.response.entity.OrdersResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class TakeOrderAdapter extends ListAdapter<Transactions, TakeOrderAdapter.ViewHolder> {

    private TakeOrderItemListener takeOrderItemListener;
    private Activity activity;
    private Gson gson;
    private OrdersDialog ordersDialog;
    private POSProcess posProcess;
    ArrayList<Orders> orders = new ArrayList<>();
    ArrayList<Discounts> discounts = new ArrayList<>();
    ArrayList<DiscountDetails> discountDetails = new ArrayList<>();
    ArrayList<DiscountOtherInformations> discountOtherInformations = new ArrayList<>();
    private int branchId = POSApplication.getInstance().getBranch().getCoreId();
    private int deviceDetailsId = POSApplication.getInstance().getDeviceDetails().getCoreId();
    private boolean processTransactionOrders = false;
    private boolean processTransactionDiscounts = false;
    private boolean processTransactionDiscountDetails;
    private boolean processTransactionDiscountOtherInformations = false;
    private Handler transactionSyncHandler = new Handler();
    private Runnable transactionSyncRunnable;
    private int tryCounter = 0;

    public interface TakeOrderItemListener{
        void onTakeOrderItemClick(Transactions transactions, List<Orders> orders, List<Discounts> discounts, List<DiscountDetails> discountDetails, List<DiscountOtherInformations> discountOtherInformations);
    }

    interface RequestAPI {
        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/branch-take-order-orders")
        Call<OrdersResponse> BranchTakeOrderTransactionOrdersFetch(@Header("Authorization") String authorization, @Query("branch_id") int branchId, @Query("device_id") int deviceId, @Query("transaction_id") int transactionId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/take-order-discounts")
        Call<DiscountsResponse> BranchTakeOrderTransactionDiscountsFetch(@Header("Authorization") String authorization, @Query("branch_id") int branchId, @Query("device_id") int deviceId, @Query("transaction_id") int transactionId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/take-order-discount-details")
        Call<DiscountDetailsResponse> BranchTakeOrderTransactionDiscountDetailsFetch(@Header("Authorization") String authorization, @Query("branch_id") int branchId, @Query("device_id") int deviceId, @Query("transaction_id") int transactionId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/take-order-discount-other-informations")
        Call<DiscountOtherInformationResponse> BranchTakeOrderTransactionDiscountOtherInformationFetch(@Header("Authorization") String authorization, @Query("branch_id") int branchId, @Query("device_id") int deviceId, @Query("transaction_id") int transactionId);
    }

    public TakeOrderAdapter(Activity activity, OrdersDialog ordersDialog){
        super(DIFF_CALLBACK);
        gson = new Gson();
        this.activity = activity;
        this.ordersDialog = ordersDialog;
        posProcess = POSApplication.getInstance().getPosProcess();
    }

    private static final DiffUtil.ItemCallback<Transactions> DIFF_CALLBACK = new DiffUtil.ItemCallback<Transactions>() {
        @Override
        public boolean areItemsTheSame(Transactions oldItem, Transactions newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Transactions oldItem, Transactions newItem) {
            return ObjectsCompat.equals(oldItem, newItem);
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_take_order, parent, false);
        return new TakeOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Transactions transactionItemList = getItem(position);
        String info = "";
        if(transactionItemList.getCustomerName() == null){
            info = transactionItemList.getControlNumber();
        } else if (transactionItemList.getCustomerName().equals("")) {
            info = transactionItemList.getControlNumber();
        } else{
            info = transactionItemList.getCustomerName() + " - " + transactionItemList.getControlNumber();
        }
        holder.txtTakeOrderDeviceNumber.setText("Device #: " + transactionItemList.getTakeOrderId());
        holder.txtTakeOrderControlNumber.setText(info);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTakeOrderControlNumber, txtTakeOrderDeviceNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTakeOrderControlNumber = itemView.findViewById(R.id.txtTakeOrderControlNumber);
            txtTakeOrderDeviceNumber = itemView.findViewById(R.id.txtTakeOrderDeviceNumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    processSelectedItem(getItem(getAbsoluteAdapterPosition()));
                }
            });

        }

    }

    public void getScanItem(String controlNumber){
        boolean success = false;
        List<Transactions> transactions = getCurrentList();
        for(Transactions item : transactions){
            if(item.getControlNumber().equals(controlNumber)){
                processSelectedItem(item);
                success = true;
                break;
            }
        }
        if(!success){
            Toast.makeText(activity, "No data found.", Toast.LENGTH_LONG).show();
        }
    }

    private void processSelectedItem(Transactions transactions){
        if(!POSApplication.getInstance().checkCurrentTransaction()){
            //Set Request API
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            //Reset The Variables
            orders = new ArrayList<>();
            discounts = new ArrayList<>();
            discountDetails = new ArrayList<>();
            discountOtherInformations = new ArrayList<>();
            processTransactionOrders = false;
            processTransactionDiscounts = false;
            processTransactionDiscountDetails = false;
            processTransactionDiscountOtherInformations = false;
            //Set Timer Checker
            LoadingDialog.getInstance().startLoadingDialog(activity, "Processing order information.");
            transactionSyncRunnable = new Runnable() {
                @Override
                public void run() {
                    Log.d("processTransactionOrders", "run: " + processTransactionOrders);
                    Log.d("processTransactionDiscounts", "run: " + processTransactionDiscounts);
                    Log.d("processTransactionDiscountDetails", "run: " + processTransactionDiscountDetails);
                    Log.d("processTransactionDiscountOtherInformations", "run: " + processTransactionDiscountOtherInformations);
                    if(processTransactionOrders && processTransactionDiscounts && processTransactionDiscountDetails && processTransactionDiscountOtherInformations){
                        transactionSyncHandler.removeCallbacks(transactionSyncRunnable);
                        CheckForDoneProcess(transactions);
                    }
                    else{
                        if(tryCounter != BuildConfig.POS_API_TIMER_TRY_COUNTER){
                            tryCounter += 1;
                            transactionSyncHandler.postDelayed(transactionSyncRunnable, 1500);
                        }
                        else{
                            tryCounter = 0;
                            transactionSyncHandler.removeCallbacks(transactionSyncRunnable);
                            Toast.makeText(activity, "The request takes to long please try again.", Toast.LENGTH_LONG).show();
                            LoadingDialog.getInstance().closeLoadingDialog();
                        }
                    }
                }
            };
            transactionSyncHandler.post(transactionSyncRunnable);
            //This Will Get The Transaction Orders
            requestAPI.BranchTakeOrderTransactionOrdersFetch("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), branchId, deviceDetailsId, transactions.getId())
                    .enqueue(new Callback<OrdersResponse>() {
                        @Override
                        public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                            if(response.isSuccessful()){
                                if(response.body().isSuccess()){
                                    processTransactionOrders(response);
                                    Log.d("ToFetchTakeOrder", "onResponseSuccess: " + response.body().getMessage());
                                }
                                else{
                                    Log.d("ToFetchTakeOrder", "onResponseError: " + response.body().getMessage());
                                }
                            }
                            else{
                                ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                Log.d("ToFetchTakeOrder", "onResponseError: " + errorBody.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<OrdersResponse> call, Throwable t) {
                            Log.d("ToFetchTakeOrder", "onFailure: " + t.getMessage());
                        }
                    });
            //This Will Get The Transaction Discounts
            requestAPI.BranchTakeOrderTransactionDiscountsFetch("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), branchId, deviceDetailsId, transactions.getId())
                    .enqueue(new Callback<DiscountsResponse>() {
                        @Override
                        public void onResponse(Call<DiscountsResponse> call, Response<DiscountsResponse> response) {
                            if(response.isSuccessful()){
                                if(response.body().isSuccess()){
                                    processTransactionsDiscount(response);
                                    Log.d("ToFetchTakeOrderDiscounts", "onResponseSuccess: " + response.body().getMessage());
                                }
                                else{
                                    Log.d("ToFetchTakeOrderDiscounts", "onResponseError: " + response.body().getMessage());
                                }
                            }
                            else{
                                ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                Log.d("ToFetchTakeOrderDiscounts", "onResponseError: " + errorBody.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<DiscountsResponse> call, Throwable t) {
                            Log.d("ToFetchTakeOrderDiscounts", "onFailure: " + t.getMessage());
                        }
                    });
            //This Will Get The Transaction Discount Details
            requestAPI.BranchTakeOrderTransactionDiscountDetailsFetch("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), branchId, deviceDetailsId, transactions.getId())
                    .enqueue(new Callback<DiscountDetailsResponse>() {
                        @Override
                        public void onResponse(Call<DiscountDetailsResponse> call, Response<DiscountDetailsResponse> response) {
                            if(response.isSuccessful()){
                                if(response.body().isSuccess()){
                                    processTransactionDiscountDetails(response);
                                    Log.d("ToFetchTakeOrderDiscountDetails", "onResponseSuccess: " + response.body().getMessage());
                                }
                                else{
                                    Log.d("ToFetchTakeOrderDiscountDetails", "onResponseError: " + response.body().getMessage());
                                }
                            }
                            else{
                                ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                Log.d("ToFetchTakeOrderDiscountDetails", "onResponseError: " + errorBody.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<DiscountDetailsResponse> call, Throwable t) {
                            Log.d("ToFetchTakeOrderDiscountDetails", "onFailure: " + t.getMessage());
                        }
                    });
            //This Will Get The Transaction Discount Other Informations
            requestAPI.BranchTakeOrderTransactionDiscountOtherInformationFetch("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), branchId, deviceDetailsId, transactions.getId())
                    .enqueue(new Callback<DiscountOtherInformationResponse>() {
                        @Override
                        public void onResponse(Call<DiscountOtherInformationResponse> call, Response<DiscountOtherInformationResponse> response) {
                            if(response.isSuccessful()){
                                if(response.body().isSuccess()){
                                    processTransactionDiscountOtherInformations(response);
                                    Log.d("ToFetchTakeOrderDiscountOtherInformations", "onResponseSuccess: " + response.body().getMessage());
                                }
                                else{
                                    Log.d("ToFetchTakeOrderDiscountOtherInformations", "onResponseError: " + response.body().getMessage());
                                }
                            }
                            else{
                                ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                Log.d("ToFetchTakeOrderDiscountOtherInformations", "onResponseError: " + errorBody.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<DiscountOtherInformationResponse> call, Throwable t) {
                            Log.d("ToFetchTakeOrderDiscountOtherInformations", "onFailure: " + t.getMessage());
                        }
                    });
        }
        else{
            Toast.makeText(activity, "You have a current transaction please pause or backout the transaction before selecting this item again.", Toast.LENGTH_LONG).show();
        }
    }

    private void processTransactionOrders(Response<OrdersResponse> response){
        response.body().getData().forEach(item -> {
            Orders addOrders = new Orders(
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
                    item.getIs_sent_to_server(),
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
            addOrders.setId(item.getOrder_id());
            Log.d("onResponse", "onResponse: " + addOrders.getName());
            orders.add(addOrders);
        });
        processTransactionOrders = true;
    }

    private void processTransactionsDiscount(Response<DiscountsResponse> response){
        response.body().getData().forEach(item -> {
            Discounts addDiscounts = new Discounts(
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
                    item.getIs_sent_to_server(),
                    item.getIs_cut_off(),
                    item.getCut_off_id(),
                    item.getShift_number(),
                    item.getTreg(),
                    item.getIs_zero_rated(),
                    item.getGross_amount(),
                    item.getNet_amount(),
                    item.getCompany_id()
            );
            addDiscounts.setId(item.getDiscount_id());
            Log.d("onResponse", "onResponse: " + addDiscounts.getDiscountName());
            discounts.add(addDiscounts);
        });
        processTransactionDiscounts = true;
    }

    private void processTransactionDiscountDetails(Response<DiscountDetailsResponse> response){
        response.body().getData().forEach(item -> {
            DiscountDetails addDiscountDetails = new DiscountDetails(
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
                  item.getIs_sent_to_server(),
                  item.getIs_cut_off(),
                  item.getCut_off_id(),
                  item.getIs_vat_exempt() == 1 ? true : false,
                  item.getShift_number(),
                  item.getTreg(),
                  item.getIs_zero_rated() == 1 ? true : false,
                  item.getCompany_id()
            );
            Log.d("onResponse", "onResponse: " + addDiscountDetails.getTransactionId());
            discountDetails.add(addDiscountDetails);
        });
        processTransactionDiscountDetails = true;
    }

    private void processTransactionDiscountOtherInformations(Response<DiscountOtherInformationResponse> response){
        response.body().getData().forEach(item -> {
            DiscountOtherInformations addDiscountOtherInformations = new DiscountOtherInformations(
                    item.getPos_machine_id(),
                    item.getBranch_id(),
                    item.getTransaction_id(),
                    item.getDiscount_id(),
                    item.getName(),
                    item.getValue(),
                    item.getIs_cut_off(),
                    item.getCut_off_id(),
                    item.getIs_void(),
                    item.getIs_sent_to_server(),
                    item.getTreg(),
                    item.getCompany_id()
            );
            Log.d("onResponse", "onResponse: " + addDiscountOtherInformations.getName());
            discountOtherInformations.add(addDiscountOtherInformations);
        });
        processTransactionDiscountOtherInformations = true;
    }

    private void CheckForDoneProcess(Transactions transactions){
        LoadingDialog.getInstance().closeLoadingDialog();
        transactions.setIsComplete(1);
        takeOrderItemListener.onTakeOrderItemClick(transactions, orders, discounts, discountDetails, discountOtherInformations);
        posProcess.SyncTakeOrderTransactionCompleteToServer(transactions);
        ordersDialog.closeDialog();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if(recyclerView.getContext() instanceof TakeOrderItemListener){
            takeOrderItemListener = (TakeOrderItemListener) recyclerView.getContext();
        }
        else{
            throw new RuntimeException(recyclerView.toString() + "must implement MenuItemListener.");
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        takeOrderItemListener = null;
    }

}
