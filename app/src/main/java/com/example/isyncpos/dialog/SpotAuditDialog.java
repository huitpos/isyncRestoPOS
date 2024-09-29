package com.example.isyncpos.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.adapters.CashDenominationAdapter;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.entity.CashDenomination;
import com.example.isyncpos.entity.CashFundDenomination;
import com.example.isyncpos.entity.SpotAuditDenomination;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.viewmodel.CashDenominationViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupDevicesViewModel;
import com.example.isyncpos.viewmodel.SafekeepingViewModel;
import com.example.isyncpos.viewmodel.SpotAuditDenominationViewModel;
import com.example.isyncpos.viewmodel.SpotAuditViewModel;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpotAuditDialog extends DialogFragment {

    private MaterialButton btnSpotAuditNegative, btnSpotAuditPositive;
    private TextView labelSpotAuditTotal;
    private RecyclerView recyclerSpotAudit;
    private CashDenominationAdapter cashDenominationAdapter;
    private CashDenominationViewModel cashDenominationViewModel;
    private SpotAuditViewModel spotAuditViewModel;
    private SpotAuditDenominationViewModel spotAuditDenominationViewModel;
    private PrinterSetupDevicesViewModel printerSetupDevicesViewModel;
    private TransactionsViewModel transactionsViewModel;
    private SafekeepingViewModel safekeepingViewModel;
    private List<Transactions> transactionsList;
    private Dates date;
    private Generate generate;
    private View view;
    private double spotAuditTotal;
    private double safekeepingAmount = 0.00;
    double totalAmount = 0.00;
    private POSProcess posProcess;

    public static SpotAuditDialog newSpotAuditDialog() {
        return new SpotAuditDialog();
    }

    public void setTransactionsList(List<Transactions> transactionsList) {
        this.transactionsList = transactionsList;
    }

    private void initialize(){
        initViewModels();
        btnSpotAuditNegative = view.findViewById(R.id.btnSpotAuditNegative);
        btnSpotAuditPositive = view.findViewById(R.id.btnSpotAuditPositive);
        labelSpotAuditTotal = view.findViewById(R.id.labelSpotAuditTotal);
        initRecyclerView();
        date = new Dates();
        generate = new Generate();
        posProcess = POSApplication.getInstance().getPosProcess();
    }

    private void initViewModels(){
        cashDenominationViewModel = POSApplication.getInstance().getCashDenominationViewModel();
        spotAuditViewModel = POSApplication.getInstance().getSpotAuditViewModel();
        spotAuditDenominationViewModel = POSApplication.getInstance().getSpotAuditDenominationViewModel();
        printerSetupDevicesViewModel = POSApplication.getInstance().getPrinterSetupDevicesViewModel();
        transactionsViewModel = POSApplication.getInstance().getTransactionsViewModel();
        safekeepingViewModel = POSApplication.getInstance().getSafekeepingViewModel();
    }

    private void initRecyclerView(){
        recyclerSpotAudit = view.findViewById(R.id.recyclerSpotAudit);
        recyclerSpotAudit.setHasFixedSize(true);
        recyclerSpotAudit.setLayoutManager(new LinearLayoutManager(getContext()));
        cashDenominationAdapter = new CashDenominationAdapter();
        cashDenominationAdapter.setMethod("SPOT AUDIT");
        recyclerSpotAudit.setAdapter(cashDenominationAdapter);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.dialog_spotaudit,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        //This will set the focus of the touch keyboard of the android to this dialog order to use on the recycler view edittext
        alertDialog.setOnShowListener(dialogInterface -> alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM));
        //Initialize
        initialize();

        cashDenominationViewModel.fetchCashDenomination().observe(getActivity(), new Observer<List<CashDenomination>>() {
            @Override
            public void onChanged(List<CashDenomination> cashDenominations) {
                cashDenominationAdapter.submitList(cashDenominations);
            }
        });

        spotAuditViewModel.setSpotAuditTotalLiveData(0.00);
        spotAuditViewModel.getSpotAuditTotalLiveData().observe(getActivity(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                labelSpotAuditTotal.setText(generate.toTwoDecimalWithComma(value));
            }
        });

        btnSpotAuditNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnSpotAuditPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process();
            }
        });

        return alertDialog;
    }

    private void process(){
        List<SpotAuditDenomination> spotAuditDenominations = cashDenominationAdapter.fetchSpotAuditList();
        if(toProcess(spotAuditDenominations)){
            LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Spot audit on progress please wait...");
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        Double sumSafekeepingAmount = safekeepingViewModel.sumAllSafekeeping();
                        Double sumTenderAmount = transactionsViewModel.sumAllCashTransaction();
                        if(sumSafekeepingAmount != null){
                            totalAmount = generate.toFourDecimal(sumTenderAmount.doubleValue() - sumSafekeepingAmount.doubleValue());
                        }
                        else{
                            if(sumTenderAmount != null){
                                totalAmount = generate.toFourDecimal(sumTenderAmount.doubleValue());
                            }
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        posProcess.processSpotAudit(getActivity(), transactionsList, computeTotal(spotAuditDenominations), totalAmount - computeTotal(spotAuditDenominations), spotAuditDenominations);
                                        Toast.makeText(getContext(), "Spot audit has been process.", Toast.LENGTH_LONG).show();
                                        LoadingDialog.getInstance().closeLoadingDialog();
                                        dismiss();
                                    }
                                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
                            }
                        });

                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
        }
        else{
            Toast.makeText(getContext(), "Please input the quantity of any cash denomination in order to process.", Toast.LENGTH_LONG).show();
        }
    }

    private double computeTotal(List<SpotAuditDenomination> spotAuditDenominations){
        spotAuditTotal = 0;
        for(SpotAuditDenomination item : spotAuditDenominations){
            spotAuditTotal += item.getTotal();
        }
        return spotAuditTotal;
    }

    private boolean toProcess(List<SpotAuditDenomination> spotAuditDenominations){
        for(SpotAuditDenomination item : spotAuditDenominations){
            if(item.getTotal() != 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cashDenominationViewModel.fetchCashDenomination().removeObservers(this);
        spotAuditViewModel.getSpotAuditTotalLiveData().removeObservers(this);
    }
}
