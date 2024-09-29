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
import com.example.isyncpos.entity.PrinterSetup;
import com.example.isyncpos.viewmodel.PrinterSetupViewModel;
import com.google.android.material.button.MaterialButton;

public class PrinterSetupInformationDialog extends DialogFragment {

    private EditText txtDialogPrintCount;
    private MaterialButton btnNegative, btnPositive;
    private AlertDialog alertDialog;
    private View view;
    private PrinterSetupViewModel printerSetupViewModel;
    private PrinterSetup printerSetup;

    public static PrinterSetupInformationDialog newPrinterSetupInformationDialog(){
        return new PrinterSetupInformationDialog();
    }

    public void setArgs(PrinterSetup printerSetup){
        this.printerSetup = printerSetup;
    }

    private void initialize(){
        txtDialogPrintCount = view.findViewById(R.id.txtDialogPrintCount);
        btnNegative = view.findViewById(R.id.btnNegative);
        btnPositive = view.findViewById(R.id.btnPositive);
        initializeModels();
    }

    private void initializeModels(){
        printerSetupViewModel = POSApplication.getInstance().getPrinterSetupViewModel();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.dialog_printer_setup,null);
        builder.setView(view);

        initialize();

        //Set Quantity To Default
        txtDialogPrintCount.setText(String.valueOf(printerSetup.getPrintCount()));
        txtDialogPrintCount.setSelectAllOnFocus(true);
        txtDialogPrintCount.requestFocus();

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
        if(Integer.parseInt(txtDialogPrintCount.getText().toString()) <= 0){
            Toast.makeText(getContext(), "Please input a valid number.", Toast.LENGTH_LONG).show();
        }
        else{
            printerSetup.setPrintCount(Integer.parseInt(txtDialogPrintCount.getText().toString()));
            printerSetupViewModel.update(printerSetup);
            dismiss();
        }
    }


}
