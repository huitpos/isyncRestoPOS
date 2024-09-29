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

public abstract class OpenPriceDialog extends Dialog {

    private EditText txtDialogPrice;
    private MaterialButton btnPositive, btnNegative;
    private Products products;
    private Generate generate;

    public abstract void confirm(Products products);

    public OpenPriceDialog(Activity activity, Products products){
        super(activity);
        this.products = products;
        generate = new Generate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_open_price);
        setCancelable(false);
        initialize();

        //Set
        txtDialogPrice.setText(String.valueOf(products.getPrice()));
        txtDialogPrice.setSelectAllOnFocus(true);
        txtDialogPrice.requestFocus();

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
        if(txtDialogPrice.getText().toString().equals("") || txtDialogPrice.getText().toString().equals(null)){
            Toast.makeText(getContext(), "Please input a price.", Toast.LENGTH_LONG).show();
        }
        else{
            products.setPrice(generate.toFourDecimal(Double.parseDouble(txtDialogPrice.getText().toString())));
            confirm(products);
            dismiss();
        }
    }

    private void initialize(){
        btnPositive = findViewById(R.id.btnPositive);
        txtDialogPrice = findViewById(R.id.txtDialogPrice);
        btnNegative = findViewById(R.id.btnNegative);
    }

}
