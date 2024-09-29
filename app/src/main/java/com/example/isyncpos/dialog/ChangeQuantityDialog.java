package com.example.isyncpos.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.viewmodel.OrdersViewModel;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChangeQuantityDialog extends DialogFragment {

    private TextView txtDialogTitle;
    private EditText txtDialogQuantity;
    private MaterialButton btnNegative, btnPositive;
    private AlertDialog alertDialog;
    private View view;
    private Orders orders;
    private TransactionsViewModel transactionsViewModel;
    private OrdersViewModel ordersViewModel;
    private POSProcess posProcess;
    private Generate generate;
    private Compute compute;
    private Activity activity;
    private UserAuthentication authorizeUser;

    public static ChangeQuantityDialog newChangeQuantityDialog(){
        return new ChangeQuantityDialog();
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
        txtDialogQuantity = view.findViewById(R.id.txtDialogQuantity);
        btnNegative = view.findViewById(R.id.btnNegative);
        btnPositive = view.findViewById(R.id.btnPositive);
        generate = new Generate();
        compute = new Compute();
    }

    private void initViewModels(){
        transactionsViewModel = POSApplication.getInstance().getTransactionsViewModel();
        ordersViewModel = POSApplication.getInstance().getOrdersViewModel();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.dialog_order_update_qty,null);
        builder.setView(view);

        initialize();

        setOrderInformation();

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
        if(txtDialogQuantity.getText().toString().isEmpty() || txtDialogQuantity.getText().toString().equals("0")) {
            Toast.makeText(getContext(), "Please input a quantity.", Toast.LENGTH_LONG).show();
            txtDialogQuantity.requestFocus();
        } else{
            double orderQty = orders.getIsReturn() == 1 ? Double.parseDouble(txtDialogQuantity.getText().toString()) * -1 : Double.parseDouble(txtDialogQuantity.getText().toString());
            String description = getDescription(orderQty);
            //Save Audit Trail
            AuditTrail.getInstance().save(
                    POSApplication.getInstance().getUserAuthentication().getId(),
                    POSApplication.getInstance().getUserAuthentication().getUsername(),
                    0,
                    "ITEM CHANGE QUANTITY",
                    description,
                    authorizeUser != null ? authorizeUser.getId() : 0,
                    authorizeUser != null ? authorizeUser.getName() : "",
                    orders.getId(),
                    0
            );
            orders.setQty(orderQty);
            orders.setGross(generate.toFourDecimal(compute.gross(orders.getQty(), orders.getAmount())));
            orders.setTotal(generate.toFourDecimal(compute.total(0, orders.getQty(), orders.getAmount())));
            orders.setVatableSales(generate.toFourDecimal(compute.vatableSales(orders.getGross())));
            orders.setVatAmount(generate.toFourDecimal(compute.vatAmount(compute.vatableSales(orders.getGross()))));
            orders.setTotalCost(generate.toFourDecimal(compute.total(orders.getTotalCost(), orders.getQty(), orders.getCost())));
            updateOrderInformation(orders);
            dismiss();
        }
    }

    private @NonNull String getDescription(double orderQty) {
        String description = "";
        if(authorizeUser != null){
            description = "Item quantity change from " + orders.getQty() + " to " + orderQty + " cashier is " + POSApplication.getInstance().getUserAuthentication().getName() + " authorize by " + authorizeUser.getName() + ".";
        }
        else{
            description = "Item quantity change from " + orders.getQty() + " to " + orderQty + " cashier is " + POSApplication.getInstance().getUserAuthentication().getName() + ".";
        }
        return description;
    }

    private void setOrderInformation(){
        txtDialogTitle.setText(orders.getName());
        txtDialogQuantity.setText(orders.getIsReturn() == 1 ? String.valueOf(orders.getQty() * -1) : String.valueOf(orders.getQty()));
        txtDialogQuantity.setSelectAllOnFocus(true);
        txtDialogQuantity.requestFocus();
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
