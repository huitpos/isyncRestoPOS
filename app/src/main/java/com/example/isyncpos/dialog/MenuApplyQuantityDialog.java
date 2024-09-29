package com.example.isyncpos.dialog;

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
import com.example.isyncpos.common.Compute;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.fragment.Menu;
import com.google.android.material.button.MaterialButton;

public class MenuApplyQuantityDialog extends DialogFragment {

    private EditText txtDialogQuantity;
    private MaterialButton btnNegative, btnPositive;
    private AlertDialog alertDialog;
    private View view;
    private Menu menu;

    public void setArgs(Menu menu){
        this.menu = menu;
    }

    public static MenuApplyQuantityDialog newMenuApplyQuantityDialog(){
        return new MenuApplyQuantityDialog();
    }

    private void initialize(){
        txtDialogQuantity = view.findViewById(R.id.txtDialogQuantity);
        btnNegative = view.findViewById(R.id.btnNegative);
        btnPositive = view.findViewById(R.id.btnPositive);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.dialog_menu_apply_qty,null);
        builder.setView(view);

        initialize();

        //Set Quantity To Default
        txtDialogQuantity.setText(String.valueOf(POSApplication.getInstance().getTotalQuantity()));
        txtDialogQuantity.setSelectAllOnFocus(true);
        txtDialogQuantity.requestFocus();

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

    private void process(){
        if(txtDialogQuantity.getText().equals("") || txtDialogQuantity.getText().equals(0)){
            Toast.makeText(getContext(), "Please input a quantity.", Toast.LENGTH_LONG).show();
        }
        else {
            POSApplication.getInstance().setTotalQuantity(Double.parseDouble(txtDialogQuantity.getText().toString()));
            menu.applyMenuQuantity(txtDialogQuantity.getText().toString());
            dismiss();
        }
    }


}
