package com.example.isyncpos.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.authentication.UserAuthentication;
import com.example.isyncpos.common.AuditTrail;
import com.example.isyncpos.common.Compute;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.entity.Orders;
import com.example.isyncpos.entity.PriceChangeReason;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.viewmodel.OrdersViewModel;
import com.example.isyncpos.viewmodel.PriceChangeReasonViewModel;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChangePriceDialog extends DialogFragment {

    private TextView txtDialogTitle;
    private EditText txtDialogPrice;
    private MaterialButton btnNegative, btnPositive;
    private AlertDialog alertDialog;
    private View view;
    private TransactionsViewModel transactionsViewModel;
    private OrdersViewModel ordersViewModel;
    private PriceChangeReasonViewModel priceChangeReasonViewModel;
    private POSProcess posProcess;
    private Orders orders;
    private Generate generate;
    private Compute compute;
    private Activity activity;
    private Spinner spinnerPriceChangeReason;
    private List<Integer> priceChangeIds = new ArrayList<>();
    private List<String> priceChangeNames = new ArrayList<>();
    private ArrayAdapter<String> arrayPriceChangeReasonAdapter;
    private PriceChangeReason selectedPriceChangeReason;
    private UserAuthentication authorizeUser;

    public static ChangePriceDialog newChangePriceDialog(){
        return new ChangePriceDialog();
    }

    public void setArgs(Orders orders, Activity activity, UserAuthentication authorizeUser){
        this.orders = orders;
        this.activity = activity;
        this.authorizeUser = authorizeUser;
    }

    private void initialize(){
        initViewModels();
        posProcess = POSApplication.getInstance().getPosProcess();
        txtDialogTitle = view.findViewById(R.id.txtDialogTitle);
        txtDialogPrice = view.findViewById(R.id.txtDialogPrice);
        btnNegative = view.findViewById(R.id.btnNegative);
        btnPositive = view.findViewById(R.id.btnPositive);
        spinnerPriceChangeReason = view.findViewById(R.id.spinnerPriceChangeReason);
        generate = new Generate();
        compute = new Compute();
    }

    private void initViewModels(){
        transactionsViewModel = POSApplication.getInstance().getTransactionsViewModel();
        ordersViewModel = POSApplication.getInstance().getOrdersViewModel();
        priceChangeReasonViewModel = POSApplication.getInstance().getPriceChangeReasonViewModel();
    }

    private void initPriceChangeReason(){
        try {
            List<PriceChangeReason> priceChangeReasons = priceChangeReasonViewModel.fetchAll();
            priceChangeReasons.forEach(item -> {
                priceChangeIds.add(item.getCoreId());
                priceChangeNames.add(item.getName());
            });
            arrayPriceChangeReasonAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, priceChangeNames);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.dialog_order_update_price,null);
        builder.setView(view);

        initialize();
        initPriceChangeReason();

        setOrderInformation();

        spinnerPriceChangeReason.setAdapter(arrayPriceChangeReasonAdapter);
        spinnerPriceChangeReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                try {
                    int coreId = priceChangeIds.get(position);
                    selectedPriceChangeReason = priceChangeReasonViewModel.fetchByCoreId(coreId);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process();
            }
        });

        alertDialog = builder.create();
        return alertDialog;
    }

    public void process(){
        if(txtDialogPrice.getText().toString().isEmpty() || txtDialogPrice.getText().toString().equals("0")) {
            Toast.makeText(getContext(), "Please input a price.", Toast.LENGTH_LONG).show();
            txtDialogPrice.requestFocus();
        } else{
            //This will validate the cost of the product will not be update less than the cost
            if(Double.parseDouble(txtDialogPrice.getText().toString()) < orders.getCost()){
                Toast.makeText(getContext(), "You cannot update the price below the cost amount of the product.", Toast.LENGTH_LONG).show();
            } else if (selectedPriceChangeReason == null) {
                Toast.makeText(getContext(), "Please select a reason for a price change.", Toast.LENGTH_LONG).show();
            } else{
                Transactions transactions = posProcess.getCurrentTransaction();
                String description = getDescription(transactions);
                //Save Audit Trail
                AuditTrail.getInstance().save(
                        POSApplication.getInstance().getUserAuthentication().getId(),
                        POSApplication.getInstance().getUserAuthentication().getName(),
                        transactions.getId(),
                        "ITEM CHANGE PRICE",
                        description,
                        authorizeUser != null ? authorizeUser.getId() : 0,
                        authorizeUser != null ? authorizeUser.getName() : "",
                        orders.getId(),
                        selectedPriceChangeReason.getCoreId()
                );
                orders.setAmount(Double.parseDouble(txtDialogPrice.getText().toString()));
                orders.setGross(generate.toFourDecimal(compute.gross(orders.getQty(), orders.getAmount())));
                orders.setTotal(generate.toFourDecimal(compute.total(0, orders.getQty(), orders.getAmount())));
                orders.setVatableSales(generate.toFourDecimal(compute.vatableSales(orders.getGross())));
                orders.setVatAmount(generate.toFourDecimal(compute.vatAmount(compute.vatableSales(orders.getGross()))));
                orders.setTotalCost(generate.toFourDecimal(compute.total(orders.getTotalCost(), orders.getQty(), orders.getCost())));
                orders.setPriceChangeReasonId(selectedPriceChangeReason.getCoreId());
                updateOrderInformation(orders);
                dismiss();
            }
        }
    }

    private @NonNull String getDescription(Transactions transactions) {
        String description = "";
        if(authorizeUser != null){
            description = "Transaction control number of " + transactions.getControlNumber() + " update the price of " + orders.getName() + " from " + generate.toFourDecimal(orders.getAmount()) + " to " + generate.toFourDecimal(Double.parseDouble(txtDialogPrice.getText().toString())) + " and the reason is " + selectedPriceChangeReason.getName() + " cashier is " + POSApplication.getInstance().getUserAuthentication().getName() + " authorize by " + authorizeUser.getName() + ".";
        }
        else{
            description = "Transaction control number of " + transactions.getControlNumber() + " update the price of " + orders.getName() + " from " + generate.toFourDecimal(orders.getAmount()) + " to " + generate.toFourDecimal(Double.parseDouble(txtDialogPrice.getText().toString())) + " and the reason is " + selectedPriceChangeReason.getName() + " cashier is " + POSApplication.getInstance().getUserAuthentication().getName() + ".";
        }
        return description;
    }

    private void setOrderInformation(){
        txtDialogTitle.setText(orders.getName());
        txtDialogPrice.setText(String.valueOf(orders.getAmount()));
        txtDialogPrice.setSelectAllOnFocus(true);
        txtDialogPrice.requestFocus();
    }

    private void updateOrderInformation(Orders orders){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Updating please wait...");
                    }
                });

                try {
                    ordersViewModel.update(orders);
                    posProcess.recomputeCurrentTransaction();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        transactionsViewModel.setCurrentTransaction(posProcess.getCurrentTransaction());
                        ordersViewModel.setCurrentTransactionOrders(posProcess.getCurrentTransactionOrders());
                        LoadingDialog.getInstance().closeLoadingDialog();
                    }
                });

            }
        });
    }

}
