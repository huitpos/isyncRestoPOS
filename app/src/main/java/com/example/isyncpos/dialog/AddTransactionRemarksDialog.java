package com.example.isyncpos.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AddTransactionRemarksDialog extends Dialog {

    private EditText txtDialogRemarks;
    private MaterialButton btnNegative, btnPositive;
    private Activity activity;
    private TransactionsViewModel transactionsViewModel;
    private Transactions transactions;

    public abstract void process(boolean success);

    public AddTransactionRemarksDialog(Activity activity){
        super(activity);
        this.activity = activity;
    }

    private void initialize(){
        txtDialogRemarks = findViewById(R.id.txtDialogRemarks);
        btnNegative = findViewById(R.id.btnNegative);
        btnPositive = findViewById(R.id.btnPositive);
    }

    private void initViewModels(){
        transactionsViewModel = POSApplication.getInstance().getTransactionsViewModel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_transaction_remarks);
        setCancelable(false);
        initialize();
        initViewModels();

        try {
            transactions = transactionsViewModel.fetchTransaction(POSApplication.getInstance().getCurrentTransaction());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Set
        if(transactions.getRemarks() != null){
            txtDialogRemarks.setText(transactions.getRemarks() == "" ? "" : transactions.getRemarks());
        }
        else{
            txtDialogRemarks.setText("");
        }
        txtDialogRemarks.setSelectAllOnFocus(true);
        txtDialogRemarks.requestFocus();

        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {

                        transactionsViewModel.updateTransactionRemarks(transactions.getId(), txtDialogRemarks.getText().toString());

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                process(true);
                            }
                        });

                    }
                });
                dismiss();
            }
        });
    }

}
