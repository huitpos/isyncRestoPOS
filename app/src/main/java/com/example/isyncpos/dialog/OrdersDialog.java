package com.example.isyncpos.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.adapters.TakeOrderAdapter;
import com.example.isyncpos.common.APIRequest;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.response.entity.ErrorBody;
import com.example.isyncpos.response.entity.TransactionsResponse;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class OrdersDialog extends DialogFragment {

    interface RequestAPI {
        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @GET("/api/branch-take-order-transactions")
        Call<TransactionsResponse> BranchTakeOrderTransactionFetch(@Header("Authorization") String authorization, @Query("branch_id") int branchId, @Query("device_id") int deviceId);
    }

    private RecyclerView recyclerViewOrders;
    private TakeOrderAdapter takeOrderAdapter;
    private MaterialButton btnOrderNegative, btnOrderRefresh;
    private AlertDialog alertDialog;
    private View view;
    private Gson gson;
    private boolean hasRequest = false;
    private String scanValue = "";
    private Dates date;
    private Socket socket;

    public static OrdersDialog newOrdersDialog(){
        return new OrdersDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.dialog_order,null);
        builder.setView(view);
        alertDialog = builder.create();
        //This will set the focus of the touch keyboard of the android to this dialog order to use on the recycler view edittext
//        alertDialog.setOnShowListener(dialogInterface -> alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM));
        //Initialize
        initialize();

        //Get
        getTakeOrderInformation();

        btnOrderRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTakeOrderInformation();
            }
        });

        btnOrderNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //Socket Functions
        socket.on(POSApplication.getInstance().getCompany().getPosType() + "-refresh-takeorder-" + POSApplication.getInstance().getBranch().getCoreId(), new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                getTakeOrderInformation();
            }
        });

        return alertDialog;
    }

    private void initialize(){
        btnOrderNegative = view.findViewById(R.id.btnOrderNegative);
        btnOrderRefresh = view.findViewById(R.id.btnOrderRefresh);
        gson = new Gson();
        date = new Dates();
        socket = POSApplication.getInstance().getSocket();
        initRecyclerView();
    }

    private void initRecyclerView(){
        recyclerViewOrders = view.findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setHasFixedSize(true);
        recyclerViewOrders.setLayoutManager(new GridLayoutManager(getContext(), 6));
        takeOrderAdapter = new TakeOrderAdapter(getActivity(), OrdersDialog.this);
        recyclerViewOrders.setAdapter(takeOrderAdapter);
    }

    public void closeDialog(){
        dismiss();
    }

    private void getTakeOrderInformation(){
        if(!hasRequest){
            hasRequest = true;
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchTakeOrderTransactionFetch("Bearer " + POSApplication.getInstance().getServerUserAuthentication().getToken(), POSApplication.getInstance().getBranch().getCoreId(), POSApplication.getInstance().getDeviceDetails().getCoreId())
            .enqueue(new Callback<TransactionsResponse>() {
                @Override
                public void onResponse(Call<TransactionsResponse> call, Response<TransactionsResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            ArrayList<Transactions> transactions = new ArrayList<>();
                            response.body().getData().forEach(item -> {
                                Transactions addTransactions = new Transactions(
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
                                        item.getIs_sent_to_server(),
                                        item.getIs_complete(),
                                        item.getCompleted_at(),
                                        item.getIs_cut_off(),
                                        item.getCut_off_id(),
                                        item.getCut_off_at(),
                                        item.getBranch_id(),
                                        item.getGuest_name(),
                                        item.getIs_resume_printed(),
                                        date.now(),
                                        item.getTotal_void_qty(),
                                        item.getIs_return(),
                                        item.getTotal_return_amount(),
                                        item.getTotal_cash_amount(),
                                        item.getVoid_remarks(),
                                        item.getVoid_counter(),
                                        item.getTotal_zero_rated_amount(),
                                        item.getCustomer_name() == null ? "" : item.getCustomer_name(),
                                        item.getRemarks(),
                                        item.getCompany_id(),
                                        item.getIs_account_receivable_redeem(),
                                        item.getAccount_receivable_redeem_at()
                                );
                                addTransactions.setId(item.getTransaction_id());
                                transactions.add(addTransactions);
                            });
                            takeOrderAdapter.submitList(transactions);
                            Log.d("ToFetchTakeOrder", "onResponseSuccess: " + response.body().getMessage());
                            hasRequest = false;
                        }
                        else{
                            Log.d("ToFetchTakeOrder", "onResponseError: " + response.body().getMessage());
                            hasRequest = false;
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToFetchTakeOrder", "onResponseError: " + errorBody.getMessage());
                        hasRequest = false;
                    }
                }

                @Override
                public void onFailure(Call<TransactionsResponse> call, Throwable t) {
                    Log.d("ToFetchTakeOrder", "onFailure: " + t.getMessage());
                    hasRequest = false;
                }
            });
        }
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        //Set
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                scanner(event);
                return true;
            }
        });

        super.onResume();
    }

    private void scanner(KeyEvent event){
        //barcode scanner
        int c = event.getUnicodeChar();
        //accept only 0..9 and ENTER
        if ((c>=48 && c<=57) || c==10) {
            if (event.getAction() == 0) {
                if (c >= 48 && c <= 57)
                    scanValue += "" + (char) c;
                else {
                    if (!scanValue.equals("")) {
                        final String b = scanValue;
                        scanValue = "";
                        takeOrderAdapter.getScanItem(b);
                        Log.d("scanner", "scanner: " + b);
                    }
                }
            }
        }
    }

}
