package com.example.isyncpos.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.isyncpos.R;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.entity.Products;
import com.google.android.material.button.MaterialButton;

public abstract class SerialNumberDialog extends Dialog {

    private EditText txtDialogSerialNumber;
    private MaterialButton btnPositive, btnNegative;
    private Products products;

    public abstract void confirm(Products withSerialProducts, String serialNumber);

    public SerialNumberDialog(Activity activity, Products products){
        super(activity);
        this.products = products;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_serial_number);
        setCancelable(false);
        initialize();

        //Set
        txtDialogSerialNumber.requestFocus();

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process();
            }
        });

        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void process(){
        if(txtDialogSerialNumber.getText().toString().equals("") || txtDialogSerialNumber.getText().toString().equals(null)){
            Toast.makeText(getContext(), "Please input a serial number.", Toast.LENGTH_LONG).show();
        }
        else{
            confirm(products, txtDialogSerialNumber.getText().toString());
            dismiss();
        }
    }

    private void initialize(){
        btnPositive = findViewById(R.id.btnPositive);
        txtDialogSerialNumber = findViewById(R.id.txtDialogSerialNumber);
        btnNegative = findViewById(R.id.btnNegative);
    }

}
