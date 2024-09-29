package com.example.isyncpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.adapters.SyncAdapter;
import com.example.isyncpos.common.APIRequest;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.EncryptDecrypt;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.entity.Payouts;
import com.example.isyncpos.entity.ProductLocations;
import com.example.isyncpos.entity.Products;
import com.example.isyncpos.listener.DoneOnEditorActionListener;
import com.example.isyncpos.response.entity.BranchProductSOHResponse;
import com.example.isyncpos.response.entity.CashDenominationResponse;
import com.example.isyncpos.response.entity.ErrorBody;
import com.example.isyncpos.viewmodel.PayoutsViewModel;
import com.example.isyncpos.viewmodel.ProductLocationsViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class ItemMenuInformationDialog extends DialogFragment {

    private MaterialButton btnNegative;
    private TextView labelDescriptionValue, labelBarcodeValue, labelSRPValue, labelCostValue, labelUOMValue, labelStockOnHandValue, labelItemLocationValue;
    private Products products;
    private AlertDialog alertDialog;
    private View view;
    private Generate generate;
    private ProductLocationsViewModel productLocationsViewModel;
    private String itemLocationContent;
    private Context context;
    private Gson gson;

    interface RequestAPI {

        @Headers({
            "accept: application/json"
        })
        @GET("/api/branch-product-soh")
        Call<BranchProductSOHResponse> BranchProductSOH(@Header("Authorization") String authorization, @Query("branch_id") int branchId, @Query("product_id") int productId, @Query("device_id") int deviceId);

    }

    public static ItemMenuInformationDialog newItemMenuInformationDialog(){
        return new ItemMenuInformationDialog();
    }

    public void setArgs(Products products, Context context){
        this.products = products;
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.dialog_menu_item_information,null);
        builder.setView(view);
        alertDialog = builder.create();

        initialize();
        initViewModels();
        initValues();

        btnNegative.setOnFocusChangeListener(new DoneOnEditorActionListener());
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return alertDialog;
    }

    private void initialize(){
        btnNegative = view.findViewById(R.id.btnNegative);
        labelDescriptionValue = view.findViewById(R.id.labelDescriptionValue);
        labelBarcodeValue = view.findViewById(R.id.labelBarcodeValue);
        labelSRPValue = view.findViewById(R.id.labelSRPValue);
        labelCostValue = view.findViewById(R.id.labelCostValue);
        labelUOMValue = view.findViewById(R.id.labelUOMValue);
        labelStockOnHandValue = view.findViewById(R.id.labelStockOnHandValue);
        labelItemLocationValue = view.findViewById(R.id.labelItemLocationValue);
        generate = new Generate();
        gson = new Gson();
    }

    private void initViewModels(){
        productLocationsViewModel = POSApplication.getInstance().getProductLocationsViewModel();
    }

    private void initValues(){
        try {
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            List<ProductLocations> productLocations = productLocationsViewModel.fetchProductLocations(products.getCoreId());
            labelDescriptionValue.setText(products.getDescription());
            labelBarcodeValue.setText(products.getBarcode());
            labelSRPValue.setText(generate.toTwoDecimalWithComma(products.getPrice()));
            labelCostValue.setText(generate.toTwoDecimalWithComma(products.getCost()));
            labelUOMValue.setText(products.getUnitDescription());
            labelStockOnHandValue.setText("Loading....");
            requestAPI.BranchProductSOH("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getBranch().getCoreId(), products.getCoreId(), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchProductSOHResponse>() {
                @Override
                public void onResponse(Call<BranchProductSOHResponse> call, Response<BranchProductSOHResponse> response) {
                    if(response.isSuccessful()){
                        labelStockOnHandValue.setText(generate.toTwoDecimalWithComma(response.body().getData().getSoh()));
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Toast.makeText(context, errorBody.getMessage() + " - " + POSApplication.getInstance().getDeviceDetails().getCoreId(), Toast.LENGTH_LONG).show();
                        labelStockOnHandValue.setText("0.00");
                    }
                }

                @Override
                public void onFailure(Call<BranchProductSOHResponse> call, Throwable t) {
                    labelStockOnHandValue.setText("0.00");
                }
            });
            if(!productLocations.isEmpty()){
                //Reset
                itemLocationContent = "";
                productLocations.forEach(item -> {
                    itemLocationContent += item.getName() + ", ";
                });
                //Remove the last string on the content
                labelItemLocationValue.setText(itemLocationContent.substring(0, itemLocationContent.length() - 2));
            }
            else{
                labelItemLocationValue.setText("--");
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
