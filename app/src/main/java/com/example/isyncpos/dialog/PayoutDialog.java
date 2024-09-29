package com.example.isyncpos.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.entity.Payouts;
import com.example.isyncpos.listener.DoneOnEditorActionListener;
import com.example.isyncpos.viewmodel.PayoutsViewModel;
import com.google.android.material.button.MaterialButton;

public class PayoutDialog extends DialogFragment {

    private MaterialButton btnPayoutNegative, btnPayoutPositive;
    private EditText txtPayoutAmount, txtPayoutReason;
    private PayoutsViewModel payoutsViewModel;
    private AlertDialog alertDialog;
    private View view;
    private Generate generate;
    private Dates date;

    public static PayoutDialog newPayoutDialog(){
        return new PayoutDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.dialog_payouts,null);
        builder.setView(view);
        alertDialog = builder.create();

        initialize();

        btnPayoutNegative.setOnFocusChangeListener(new DoneOnEditorActionListener());
        btnPayoutNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnPayoutPositive.setOnFocusChangeListener(new DoneOnEditorActionListener());
        btnPayoutPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtPayoutAmount.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please input the amount.", Toast.LENGTH_LONG).show();
                }
                else if(txtPayoutReason.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please input the reason for the payout.", Toast.LENGTH_LONG).show();
                }
                else{
                    confirm();
                }
            }
        });

        return alertDialog;
    }

    private void initialize(){
        initViewModels();
        btnPayoutNegative = view.findViewById(R.id.btnPayoutNegative);
        btnPayoutPositive = view.findViewById(R.id.btnPayoutPositive);
        txtPayoutAmount = view.findViewById(R.id.txtPayoutAmount);
        txtPayoutReason = view.findViewById(R.id.txtPayoutReason);
        generate = new Generate();
        date = new Dates();
    }

    private void initViewModels(){
        payoutsViewModel = POSApplication.getInstance().getPayoutsViewModel();
    }

    private void confirm(){
        getDialog().hide();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to save this payout with amount of " + txtPayoutAmount.getText() + " with a reason '" + txtPayoutReason.getText() + "'?");
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Saving payout please wait....");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        payoutsViewModel.insert(new Payouts(
                                POSApplication.getInstance().getMachineDetails().getCoreId(),
                                generate.controlNumber(),
                                Double.parseDouble(txtPayoutAmount.getText().toString()),
                                txtPayoutReason.getText().toString(),
                                POSApplication.getInstance().getUserAuthentication().getId(),
                                POSApplication.getInstance().getUserAuthentication().getName(),
                                0,
                                "",
                                POSApplication.getInstance().getBranch().getCoreId(),
                                0,
                                0,
                                0,
                                "",
                                date.now(),
                                POSApplication.getInstance().getCompany().getCoreId()
                        ));
                        LoadingDialog.getInstance().closeLoadingDialog();
                        dismiss();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getDialog().show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
