package com.example.isyncpos.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.isyncpos.R;
import com.google.android.material.button.MaterialButton;

public abstract class VoidRemarksDialog extends Dialog {

    private EditText txtRemarks;
    private MaterialButton btnNegative, btnPositive;

    public abstract void confirm(String remarks);

    public VoidRemarksDialog(Activity activity){
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_void_remarks);
        setCancelable(false);
        initialize();
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
    }

    private void process(){
        if(txtRemarks.getText().toString().equals("") || txtRemarks.getText().toString().equals(null)){
            Toast.makeText(getContext(), "Please input a remarks.", Toast.LENGTH_LONG).show();
        }
        else{
            confirm(txtRemarks.getText().toString());
            dismiss();
        }
    }

    private void initialize(){
        btnNegative = findViewById(R.id.btnNegative);
        btnPositive = findViewById(R.id.btnPositive);
        txtRemarks = findViewById(R.id.txtRemarks);
    }

}
