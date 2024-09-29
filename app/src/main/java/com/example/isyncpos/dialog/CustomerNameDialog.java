package com.example.isyncpos.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.fragment.Menu;
import com.example.isyncpos.preferences.Cache;
import com.example.isyncpos.viewmodel.AuthenticatedMachineUserViewModel;
import com.example.isyncpos.viewmodel.UsersViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

public abstract class CustomerNameDialog extends Dialog {

    private EditText txtDialogCustomerName;
    private MaterialButton btnNegative, btnPositive;
    private Transactions transactions;
    private Activity activity;

    public abstract void process(boolean success, Transactions transactions);

    public CustomerNameDialog(Activity activity, Transactions transactions){
        super(activity);
        this.activity = activity;
        this.transactions = transactions;
    }

    private void initialize(){
        txtDialogCustomerName = findViewById(R.id.txtDialogCustomerName);
        btnNegative = findViewById(R.id.btnNegative);
        btnPositive = findViewById(R.id.btnPositive);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_customer_name);
        setCancelable(false);
        initialize();

        //Set
        txtDialogCustomerName.setText(transactions.getCustomerName());
        txtDialogCustomerName.requestFocus();

        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtDialogCustomerName.getText().equals("")){
                    transactions.setCustomerName("");
                    process(true, transactions);
                }
                else{
                    transactions.setCustomerName(txtDialogCustomerName.getText().toString());
                    process(true, transactions);
                }
                dismiss();
            }
        });
    }

}
