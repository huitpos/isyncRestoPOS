package com.example.isyncpos.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.util.ObjectsCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.common.APIRequest;
import com.example.isyncpos.common.EncryptDecrypt;
import com.example.isyncpos.entity.Sync;
import com.example.isyncpos.response.entity.BranchInformationResponse;
import com.example.isyncpos.response.entity.CashDenominationResponse;
import com.example.isyncpos.response.entity.CategoriesResponse;
import com.example.isyncpos.response.entity.ChargeAccountResponse;
import com.example.isyncpos.response.entity.CompanyInformationResponse;
import com.example.isyncpos.response.entity.DepartmentsResponse;
import com.example.isyncpos.response.entity.DiscountTypesResponse;
import com.example.isyncpos.response.entity.ErrorBody;
import com.example.isyncpos.response.entity.PaymentTypesResponse;
import com.example.isyncpos.response.entity.PriceChangeReasonResponse;
import com.example.isyncpos.response.entity.ProductsResponse;
import com.example.isyncpos.response.entity.SubCategoriesResponse;
import com.example.isyncpos.response.entity.UsersResponse;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class SyncAdapter extends ListAdapter<Sync, SyncAdapter.ViewHolder> {

    private SyncListener syncListener;
    private Context context;
    private Gson gson;
    private boolean cashDenominationRequest = false;
    private boolean departmentRequest = false;
    private boolean categoriesRequest = false;
    private boolean subCategoriesRequest = false;
    private boolean userRequest = false;
    private boolean paymentTypeRequest = false;
    private boolean discountTypeRequest = false;
    private boolean chargeAccountRequest = false;
    private boolean productsRequest = false;
    private boolean branchInformationRequest = false;
    private boolean companyInformationRequest = false;
    private boolean priceChangeReasonRequest = false;

    interface RequestAPI {
        @Headers({
            "accept: application/json"
        })
        @GET("/api/cash-denominations")
        Call<CashDenominationResponse> Denomination(@Header("Authorization") String authorization, @Query("device_id") int deviceId);

        @Headers({
            "accept: application/json"
        })
        @GET("/api/departments/{id}")
        Call<DepartmentsResponse> Departments(@Header("Authorization") String authorization, @Path("id") int id, @Query("device_id") int deviceId);

        @Headers({
            "accept: application/json"
        })
        @GET("/api/categories/{id}")
        Call<CategoriesResponse> Categories(@Header("Authorization") String authorization, @Path("id") int id, @Query("device_id") int deviceId);

        @Headers({
            "accept: application/json"
        })
        @GET("/api/sub-categories/{id}")
        Call<SubCategoriesResponse> SubCategories(@Header("Authorization") String authorization, @Path("id") int id, @Query("device_id") int deviceId);

        @Headers({
            "accept: application/json"
        })
        @GET("/api/branch-users/{id}")
        Call<UsersResponse> Users(@Header("Authorization") String authorization, @Path("id") int id, @Query("device_id") int deviceId);

        @Headers({
            "accept: application/json"
        })
        @GET("/api/payment-types/{id}")
        Call<PaymentTypesResponse> PaymentTypes(@Header("Authorization") String authorization, @Path("id") int id, @Query("device_id") int deviceId);

        @Headers({
            "accept: application/json"
        })
        @GET("/api/discount-types/{id}")
        Call<DiscountTypesResponse> DiscountTypes(@Header("Authorization") String authorization, @Path("id") int id, @Query("device_id") int deviceId);

        @Headers({
            "accept: application/json"
        })
        @GET("/api/charge-accounts/{id}")
        Call<ChargeAccountResponse> ChargeAccount(@Header("Authorization") String authorization, @Path("id") int id, @Query("device_id") int deviceId);

        @Headers({
            "accept: application/json"
        })
        @GET("/api/branch-products/{id}")
        Call<ProductsResponse> Products(@Header("Authorization") String authorization, @Path("id") int id, @Query("device_id") int deviceId, @Query("from_date") String fromDate);

        @Headers({
            "accept: application/json"
        })
        @GET("/api/branches/{id}")
        Call<BranchInformationResponse> Branch(@Header("Authorization") String authorization, @Path("id") int id, @Query("device_id") int deviceId);

        @Headers({
            "accept: application/json"
        })
        @GET("/api/companies/{id}")
        Call<CompanyInformationResponse> Company(@Header("Authorization") String authorization, @Path("id") int id, @Query("device_id") int deviceId);

        @Headers({
            "accept: application/json"
        })
        @GET("/api/price-change-reasons/{id}")
        Call<PriceChangeReasonResponse> PriceChangeReason(@Header("Authorization") String authorization, @Path("id") int id, @Query("device_id") int deviceId);

    }

    public interface SyncListener {

        void CashDenominationSync(CashDenominationResponse cashDenomination, Sync sync, boolean success);

        void DepartmentsSync(DepartmentsResponse departmentsResponse, Sync sync, boolean success);

        void CategoriesSync(CategoriesResponse categoriesResponse, Sync sync, boolean success);

        void SubCategoriesSync(SubCategoriesResponse subCategoriesResponse, Sync sync, boolean success);

        void UsersSync(UsersResponse usersResponse, Sync sync, boolean success);

        void PaymentTypeSync(PaymentTypesResponse paymentTypesResponse, Sync sync, boolean success);

        void DiscountTypeSync(DiscountTypesResponse discountTypesResponse, Sync sync, boolean success);

        void ChargeAccountSync(ChargeAccountResponse chargeAccountResponse, Sync sync, boolean success);

        void ProductsSync(ProductsResponse productsResponse, Sync sync, boolean success);

        void BranchSync(BranchInformationResponse branchInformationResponse, Sync sync, boolean success);

        void CompanySync(CompanyInformationResponse companyInformationResponse, Sync sync, boolean success);

        void PriceChangeReasonSync(PriceChangeReasonResponse priceChangeReasonResponse, Sync sync, boolean success);

    }

    public SyncAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
        gson = new Gson();
    }

    private static final DiffUtil.ItemCallback<Sync> DIFF_CALLBACK = new DiffUtil.ItemCallback<Sync>() {
        @Override
        public boolean areItemsTheSame(Sync oldItem, Sync newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Sync oldItem, Sync newItem) {
            return ObjectsCompat.equals(oldItem, newItem);
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_sync, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Sync syncList = getItem(position);
        holder.txtSyncName.setText(syncList.getName());
        boolean isFirstSync = syncList.getIsFirstSync() == 0 ? true : false;
        if(isFirstSync){
            syncList.setIsFirstSync(1);
            holder.btnSyncInformation.performClick();
        }
        if(syncList.getIsSync() == 0){
            holder.btnSyncInformation.setVisibility(View.GONE);
            holder.syncProgressBar.setVisibility(View.VISIBLE);
        }
        else{
            holder.btnSyncInformation.setVisibility(View.VISIBLE);
            holder.syncProgressBar.setVisibility(View.GONE);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtSyncName;
        private ProgressBar syncProgressBar;
        private MaterialButton btnSyncInformation;
        private boolean hasRequest = false;

        public ViewHolder(View itemView) {
            super(itemView);
            txtSyncName = itemView.findViewById(R.id.txtSyncName);
            syncProgressBar = itemView.findViewById(R.id.syncProgressBar);
            btnSyncInformation = itemView.findViewById(R.id.btnSyncInformation);

            btnSyncInformation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    syncInformation(ViewHolder.this, getItem(getAbsoluteAdapterPosition()));
                }
            });

        }

    }

    private void syncInformation(ViewHolder holder, Sync sync){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        switch (holder.txtSyncName.getText().toString()) {
            case "Cash Denominations":
                if(!cashDenominationRequest){
                    cashDenominationRequest = true;
                    requestAPI.Denomination("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<CashDenominationResponse>() {
                        @Override
                        public void onResponse(Call<CashDenominationResponse> call, Response<CashDenominationResponse> response) {
                            if(response.isSuccessful()){
                                syncListener.CashDenominationSync(response.body(), sync, response.body().isSuccess());
                                cashDenominationRequest = false;
                            }
                            else{
                                ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                Toast.makeText(context, errorBody.getMessage() + " - " + POSApplication.getInstance().getDeviceDetails().getCoreId(), Toast.LENGTH_LONG).show();
                                syncListener.CashDenominationSync(response.body(), sync, errorBody.isSuccess());
                                cashDenominationRequest = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<CashDenominationResponse> call, Throwable t) {
                            Log.d("cashDenominationSync", "onFailure: " + t.getMessage());
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                            cashDenominationRequest = false;
                        }
                    });
                }
                break;
            case "Departments":
                if(!departmentRequest){
                    departmentRequest = true;
                    requestAPI.Departments("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<DepartmentsResponse>() {
                        @Override
                        public void onResponse(Call<DepartmentsResponse> call, Response<DepartmentsResponse> response) {
                            if(response.isSuccessful()){
                                syncListener.DepartmentsSync(response.body(), sync, response.body().isSuccess());
                                departmentRequest = false;
                            }
                            else{
                                ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                Toast.makeText(context, errorBody.getMessage() + " - " + POSApplication.getInstance().getDeviceDetails().getCoreId(), Toast.LENGTH_LONG).show();
                                syncListener.DepartmentsSync(response.body(), sync, errorBody.isSuccess());
                                departmentRequest = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<DepartmentsResponse> call, Throwable t) {
                            Log.d("departmentsSync", "onFailure: " + t.getMessage());
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                            departmentRequest = false;
                        }
                    });
                }
                break;
            case "Categories":
                if(!categoriesRequest){
                    categoriesRequest = true;
                    requestAPI.Categories("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<CategoriesResponse>() {
                        @Override
                        public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                            if(response.isSuccessful()){
                                syncListener.CategoriesSync(response.body(), sync, response.body().isSuccess());
                                categoriesRequest = false;
                            }
                            else{
                                ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                Toast.makeText(context, errorBody.getMessage() + " - " + POSApplication.getInstance().getDeviceDetails().getCoreId(), Toast.LENGTH_LONG).show();
                                syncListener.CategoriesSync(response.body(), sync, errorBody.isSuccess());
                                categoriesRequest = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                            Log.d("categoriesSync", "onFailure: " + t.getMessage());
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                            categoriesRequest = false;
                        }
                    });
                }
                break;
            case "Sub Categories":
                if(!subCategoriesRequest){
                    subCategoriesRequest = true;
                    requestAPI.SubCategories("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<SubCategoriesResponse>() {
                        @Override
                        public void onResponse(Call<SubCategoriesResponse> call, Response<SubCategoriesResponse> response) {
                            if(response.isSuccessful()){
                                syncListener.SubCategoriesSync(response.body(), sync, response.body().isSuccess());
                                subCategoriesRequest = false;
                            }
                            else{
                                ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                Toast.makeText(context, errorBody.getMessage() + " - " + POSApplication.getInstance().getDeviceDetails().getCoreId(), Toast.LENGTH_LONG).show();
                                syncListener.SubCategoriesSync(response.body(), sync, errorBody.isSuccess());
                                subCategoriesRequest = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<SubCategoriesResponse> call, Throwable t) {
                            Log.d("subCategoriesSync", "onFailure: " + t.getMessage());
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                            subCategoriesRequest = false;
                        }
                    });
                }
                break;
            case "Users":
                if(!userRequest){
                    userRequest = true;
                    requestAPI.Users("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<UsersResponse>() {
                        @Override
                        public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                            if(response.isSuccessful()){
                                syncListener.UsersSync(response.body(), sync, response.body().isSuccess());
                                userRequest = false;
                            }
                            else{
                                ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                Toast.makeText(context, errorBody.getMessage() + " - " + POSApplication.getInstance().getDeviceDetails().getCoreId(), Toast.LENGTH_LONG).show();
                                syncListener.UsersSync(response.body(), sync, errorBody.isSuccess());
                                userRequest = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<UsersResponse> call, Throwable t) {
                            Log.d("usersSync", "onFailure: " + t.getMessage());
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                            userRequest = false;
                        }
                    });
                }
                break;
            case "Payment Type":
                if(!paymentTypeRequest){
                    paymentTypeRequest = true;
                    requestAPI.PaymentTypes("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<PaymentTypesResponse>() {
                        @Override
                        public void onResponse(Call<PaymentTypesResponse> call, Response<PaymentTypesResponse> response) {
                            if(response.isSuccessful()){
                                syncListener.PaymentTypeSync(response.body(), sync, response.body().isSuccess());
                                paymentTypeRequest = false;
                            }
                            else{
                                ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                Toast.makeText(context, errorBody.getMessage() + " - " + POSApplication.getInstance().getDeviceDetails().getCoreId(), Toast.LENGTH_LONG).show();
                                syncListener.PaymentTypeSync(response.body(), sync, errorBody.isSuccess());
                                paymentTypeRequest = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<PaymentTypesResponse> call, Throwable t) {
                            Log.d("paymentTypesSync", "onFailure: " + t.getMessage());
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                            paymentTypeRequest = false;
                        }
                    });
                }
                break;
            case "Discount Type":
                if(!discountTypeRequest){
                    discountTypeRequest = true;
                    requestAPI.DiscountTypes("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<DiscountTypesResponse>() {
                        @Override
                        public void onResponse(Call<DiscountTypesResponse> call, Response<DiscountTypesResponse> response) {
                            if(response.isSuccessful()){
                                syncListener.DiscountTypeSync(response.body(), sync, response.body().isSuccess());
                                discountTypeRequest = false;
                            }
                            else{
                                ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                Toast.makeText(context, errorBody.getMessage() + " - " + POSApplication.getInstance().getDeviceDetails().getCoreId(), Toast.LENGTH_LONG).show();
                                syncListener.DiscountTypeSync(response.body(), sync, errorBody.isSuccess());
                                discountTypeRequest = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<DiscountTypesResponse> call, Throwable t) {
                            Log.d("discountTypesSync", "onFailure: " + t.getMessage());
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                            discountTypeRequest = false;
                        }
                    });
                }
                break;
            case "Charge Account":
                if(!chargeAccountRequest){
                    chargeAccountRequest = true;
                    requestAPI.ChargeAccount("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<ChargeAccountResponse>() {
                        @Override
                        public void onResponse(Call<ChargeAccountResponse> call, Response<ChargeAccountResponse> response) {
                            if(response.isSuccessful()){
                                syncListener.ChargeAccountSync(response.body(), sync, response.body().isSuccess());
                                chargeAccountRequest = false;
                            }
                            else{
                                ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                Toast.makeText(context, errorBody.getMessage() + " - " + POSApplication.getInstance().getDeviceDetails().getCoreId(), Toast.LENGTH_LONG).show();
                                syncListener.ChargeAccountSync(response.body(), sync, errorBody.isSuccess());
                                chargeAccountRequest = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<ChargeAccountResponse> call, Throwable t) {
                            Log.d("chargeAccountsSync", "onFailure: " + t.getMessage());
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                            chargeAccountRequest = false;
                        }
                    });
                }
                break;
            case "Products":
                if(!productsRequest){
                    productsRequest = true;
                    requestAPI.Products("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getDeviceDetails().getCoreId(), sync.getUpdatedAt()).enqueue(new Callback<ProductsResponse>() {
                        @Override
                        public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                            if(response.isSuccessful()){
                                syncListener.ProductsSync(response.body(), sync, response.body().isSuccess());
                                productsRequest = false;
                            }
                            else{
                                ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                Toast.makeText(context, errorBody.getMessage() + " - " + POSApplication.getInstance().getDeviceDetails().getCoreId(), Toast.LENGTH_LONG).show();
                                syncListener.ProductsSync(response.body(), sync, errorBody.isSuccess());
                                productsRequest = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<ProductsResponse> call, Throwable t) {
                            Log.d("productsSync", "onFailure: " + t.getMessage());
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                            productsRequest = false;
                        }
                    });
                }
                break;
            case "Branch Information":
                if(!branchInformationRequest){
                    branchInformationRequest = true;
                    requestAPI.Branch("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchInformationResponse>() {
                        @Override
                        public void onResponse(Call<BranchInformationResponse> call, Response<BranchInformationResponse> response) {
                            if(response.isSuccessful()){
                                syncListener.BranchSync(response.body(), sync, response.body().isSuccess());
                                branchInformationRequest = false;
                            }
                            else{
                                ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                Toast.makeText(context, errorBody.getMessage() + " - " + POSApplication.getInstance().getDeviceDetails().getCoreId(), Toast.LENGTH_LONG).show();
                                syncListener.BranchSync(response.body(), sync, errorBody.isSuccess());
                                branchInformationRequest = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<BranchInformationResponse> call, Throwable t) {
                            Log.d("BranchSync", "onFailure: " + t.getMessage());
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                            branchInformationRequest = false;
                        }
                    });
                }
                break;
            case "Company Information":
                if(!companyInformationRequest){
                    companyInformationRequest = true;
                    requestAPI.Company("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getCompany().getCoreId(), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<CompanyInformationResponse>() {
                        @Override
                        public void onResponse(Call<CompanyInformationResponse> call, Response<CompanyInformationResponse> response) {
                            if(response.isSuccessful()){
                                syncListener.CompanySync(response.body(), sync, response.body().isSuccess());
                                companyInformationRequest = false;
                            }
                            else{
                                ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                Toast.makeText(context, errorBody.getMessage() + " - " + POSApplication.getInstance().getDeviceDetails().getCoreId(), Toast.LENGTH_LONG).show();
                                syncListener.CompanySync(response.body(), sync, errorBody.isSuccess());
                                companyInformationRequest = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<CompanyInformationResponse> call, Throwable t) {
                            Log.d("CompanySync", "onFailure: " + t.getMessage());
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                            companyInformationRequest = false;
                        }
                    });
                }
                break;
            case "Price Change Reason":
                if(!priceChangeReasonRequest){
                    priceChangeReasonRequest = true;
                    requestAPI.PriceChangeReason("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<PriceChangeReasonResponse>() {
                        @Override
                        public void onResponse(Call<PriceChangeReasonResponse> call, Response<PriceChangeReasonResponse> response) {
                            if(response.isSuccessful()){
                                syncListener.PriceChangeReasonSync(response.body(), sync, response.body().isSuccess());
                                priceChangeReasonRequest = false;
                            }
                            else{
                                ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                Toast.makeText(context, errorBody.getMessage() + " - " + POSApplication.getInstance().getDeviceDetails().getCoreId(), Toast.LENGTH_LONG).show();
                                syncListener.PriceChangeReasonSync(response.body(), sync, errorBody.isSuccess());
                                priceChangeReasonRequest = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<PriceChangeReasonResponse> call, Throwable t) {
                            Log.d("PriceChangeSync", "onFailure: " + t.getMessage());
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                            priceChangeReasonRequest = false;
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if(recyclerView.getContext() instanceof SyncAdapter.SyncListener){
            syncListener = (SyncListener) recyclerView.getContext();
        }
        else{
            throw new RuntimeException(recyclerView.toString() + "must implement SyncListener.");
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        syncListener = null;
    }

}
