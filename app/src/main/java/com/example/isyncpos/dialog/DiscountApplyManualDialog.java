package com.example.isyncpos.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.isyncpos.R;
import com.example.isyncpos.entity.DiscountTypes;
import com.example.isyncpos.entity.Transactions;
import com.google.android.material.button.MaterialButton;

public abstract class DiscountApplyManualDialog extends Dialog {

    private EditText txtDialogManualValue;
    private MaterialButton btnNegative, btnPositive;
    private Activity activity;
    private DiscountTypes discountTypes;

    public abstract void process(boolean success, DiscountTypes discountTypes);

    public DiscountApplyManualDialog(Activity activity, DiscountTypes discountTypes){
        super(activity);
        this.activity = activity;
        this.discountTypes = discountTypes;
    }

    private void initialize(){
        txtDialogManualValue = findViewById(R.id.txtDialogManualValue);
        btnNegative = findViewById(R.id.btnNegative);
        btnPositive = findViewById(R.id.btnPositive);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_discount_manual_amount);
        setCancelable(false);
        initialize();

        //Set
        txtDialogManualValue.setText(String.valueOf(discountTypes.getDiscount()));
        txtDialogManualValue.setSelectAllOnFocus(true);
        txtDialogManualValue.requestFocus();

        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process(false, discountTypes);
                dismiss();
            }
        });

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(discountTypes.getType().equals("percentage")){
                    if(Integer.parseInt(txtDialogManualValue.getText().toString()) > 100){
                        Toast.makeText(activity, "Please input a valid range for percentage discount 0 to 100 percent only.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        discountTypes.setDiscount(Integer.parseInt(txtDialogManualValue.getText().toString()));
                        process(true, discountTypes);
                        dismiss();
                    }
                }
                else{
                    discountTypes.setDiscount(Integer.parseInt(txtDialogManualValue.getText().toString()));
                    process(true, discountTypes);
                    dismiss();
                }
            }
        });
    }

}
