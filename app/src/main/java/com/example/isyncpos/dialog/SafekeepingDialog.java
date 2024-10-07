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
import com.example.isyncpos.authentication.UserAuthentication;
import com.example.isyncpos.common.AuditTrail;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.common.Printer;
import com.example.isyncpos.entity.CashDenomination;
import com.example.isyncpos.entity.Safekeeping;
import com.example.isyncpos.entity.SafekeepingDenomination;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.fragment.Navigation;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.viewmodel.CashDenominationViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupDevicesViewModel;
import com.example.isyncpos.viewmodel.SafekeepingDenominationViewModel;
import com.example.isyncpos.viewmodel.SafekeepingViewModel;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SafekeepingDialog extends DialogFragment {

    private MaterialButton btnSafekeepingNegative, btnSafekeepingPositive;
    private TextView labelSafekeepingTotal, txtCardViewTitle;
    private RecyclerView cashDenominationRecycler;
    private CashDenominationAdapter cashDenominationAdapter;
    private CashDenominationViewModel cashDenominationViewModel;
    private SafekeepingViewModel safekeepingViewModel;
    private SafekeepingDenominationViewModel safekeepingDenominationViewModel;
    private TransactionsViewModel transactionsViewModel;
    private PrinterSetupDevicesViewModel printerSetupDevicesViewModel;
    private Dates date;
    private Generate generate;
    private View view;
    private double safekeepingTotal;
    private int isAuto = 0;
    private Long safekeepingId;
    private boolean isCutOff = false;
    private List<Transactions> transactions;
    private CutoffDialog cutoffDialog;
    private POSProcess posProcess;
    private UserAuthentication authorizeUser;

    public static SafekeepingDialog newSafekeepingDialog(){
        return new SafekeepingDialog();
    }

    public void setIsAuto(int value){
        isAuto = value;
    }

    public void setIsCutOff(boolean value){
        isCutOff = value;
    }

    public void setTransactions(List<Transactions> transactions){
        this.transactions = transactions;
    }

    public void setAuthorizeUser(UserAuthentication authorizeUser){
        this.authorizeUser = authorizeUser;
    }

    public void setCutoffDialog(CutoffDialog cutoffDialog){
        this.cutoffDialog = cutoffDialog;
    }

    private void initialize(){
        initViewModels();
        btnSafekeepingNegative = view.findViewById(R.id.btnSafekeepingNegative);
        btnSafekeepingPositive = view.findViewById(R.id.btnSafekeepingPositive);
        labelSafekeepingTotal = view.findViewById(R.id.labelSafekeepingTotal);
        txtCardViewTitle = view.findViewById(R.id.txtCardViewTitle);
        initRecyclerView();
        date = new Dates();
        generate = new Generate();
    }

    private void initViewModels(){
        cashDenominationViewModel = POSApplication.getInstance().getCashDenominationViewModel();
        safekeepingViewModel = POSApplication.getInstance().getSafekeepingViewModel();
        safekeepingDenominationViewModel = POSApplication.getInstance().getSafekeepingDenominationViewModel();
        transactionsViewModel = POSApplication.getInstance().getTransactionsViewModel();
        printerSetupDevicesViewModel = POSApplication.getInstance().getPrinterSetupDevicesViewModel();
        posProcess = POSApplication.getInstance().getPosProcess();
    }

    private void initRecyclerView(){
        cashDenominationRecycler = view.findViewById(R.id.recyclerSafekeeping);
        cashDenominationRecycler.setHasFixedSize(true);
        cashDenominationRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        cashDenominationAdapter = new CashDenominationAdapter();
        cashDenominationAdapter.setMethod("SAFEKEEPING");
        cashDenominationRecycler.setAdapter(cashDenominationAdapter);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.dialog_safekeeping,null);
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

        safekeepingViewModel.setSafekeepingTotalLiveData(0.00);
        safekeepingViewModel.getSafekeepingTotalLiveData().observe(getActivity(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                labelSafekeepingTotal.setText(generate.toTwoDecimalWithComma(value));
            }
        });

        btnSafekeepingNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCutOff){
                    cutoffDialog.getDialog().show();
                }
                dismiss();
            }
        });

        btnSafekeepingPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process();
            }
        });

        if(isCutOff){
            txtCardViewTitle.setText("CASH DENOMINATION");
        }
        else{
            txtCardViewTitle.setText("SAFEKEEPING");
        }

        return alertDialog;
    }

    private void process(){
        List<SafekeepingDenomination> safekeepingDenomination = cashDenominationAdapter.fetchSafekeepingList();
        if(toProcess(safekeepingDenomination)){
            LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Safekeeping on progress please wait...");
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        String treg = date.now();
                        Double sumSafekeepingAmount = safekeepingViewModel.sumAllSafekeeping();
                        Double sumTotalCash = transactionsViewModel.sumAllTotalCashTransaction();
                        double totalAmount = 0.00;
                        if(sumSafekeepingAmount != null){
                            totalAmount = generate.toFourDecimal(sumTotalCash.doubleValue() - sumSafekeepingAmount.doubleValue());
                        }
                        else{
                            if(sumTotalCash != null){
                                totalAmount = generate.toFourDecimal(sumTotalCash.doubleValue());
                            }
                        }
                        safekeepingId = safekeepingViewModel.insert(new Safekeeping(
                                POSApplication.getInstance().getMachineDetails().getCoreId(),
                                POSApplication.getInstance().getBranch().getCoreId(),
                                generate.toFourDecimal(computeTotal(safekeepingDenomination)),
                                POSApplication.getInstance().getUserAuthentication().getId(),
                                POSApplication.getInstance().getUserAuthentication().getName(),
                                0,
                                "",
                                0,
                                0,
                                0,
                                1,
                                treg,
                                0,
                                isAuto,
                                totalAmount < 0 ? generate.toFourDecimal(computeTotal(safekeepingDenomination) + totalAmount) : generate.toFourDecimal(computeTotal(safekeepingDenomination) - totalAmount),
                                POSApplication.getInstance().getCompany().getCoreId()
                        ));
                        for(SafekeepingDenomination item : safekeepingDenomination){
                            item.setCutOffId(0);
                            item.setMachineNumber(POSApplication.getInstance().getMachineDetails().getCoreId());
                            item.setBranchId(POSApplication.getInstance().getBranch().getCoreId());
                            item.setSafekeepingId(Integer.parseInt(safekeepingId.toString()));
                            safekeepingDenominationViewModel.insert(item);
                        }
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(isCutOff){
                                getDialog().hide();
                                cutOffProcess();
                            }
                            else{
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Safekeeping safekeeping = safekeepingViewModel.fetchById(safekeepingId.intValue());
                                            Printer.getInstance().print(getActivity(), printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_SAFEKEEPING), Printer.getInstance().safeKeeping(safekeeping, safekeepingDenomination));
                                            Toast.makeText(getContext(), "Safekeeping has been process.", Toast.LENGTH_LONG).show();
                                            LoadingDialog.getInstance().closeLoadingDialog();
                                            dismiss();
                                        } catch (ExecutionException e) {
                                            throw new RuntimeException(e);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
                            }
                        }
                    });

                }
            });
        }
        else{
            Toast.makeText(getContext(), "Please input the quantity of any cash denomination in order to process.", Toast.LENGTH_LONG).show();
        }
    }

    private void cutOffProcess(){
        LoadingDialog.getInstance().changeLoadingMessage("Cutoff in progress please wait...");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                posProcess.cutOffTransactions(getActivity(), transactions);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String description = "";
                        if(authorizeUser != null){
                            description = "Cutoff process by cashier " + POSApplication.getInstance().getUserAuthentication().getName() + " authorize by " + authorizeUser.getName() + ".";
                        }
                        else {
                            description = "Cutoff process by cashier " + POSApplication.getInstance().getUserAuthentication().getName() + ".";
                        }
                        AuditTrail.getInstance().save(
                                POSApplication.getInstance().getUserAuthentication().getId(),
                                POSApplication.getInstance().getUserAuthentication().getUsername(),
                                0,
                                "CUTOFF",
                                description,
                                authorizeUser != null ? authorizeUser.getId() : 0,
                                authorizeUser != null ? authorizeUser.getName() : "",
                                0,
                                0
                        );
                        cutoffDialog.dismiss();
                        dismiss();
                    }
                });

            }
        });
    }

    private double computeTotal(List<SafekeepingDenomination> safekeepingDenomination){
        safekeepingTotal = 0;
        for(SafekeepingDenomination item : safekeepingDenomination){
            safekeepingTotal += item.getTotal();
        }
        return safekeepingTotal;
    }

    private boolean toProcess(List<SafekeepingDenomination> safekeepingDenomination){
        for(SafekeepingDenomination item : safekeepingDenomination){
            if(item.getTotal() != 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cashDenominationViewModel.fetchCashDenomination().removeObservers(getActivity());
        safekeepingViewModel.getSafekeepingTotalLiveData().removeObservers(getActivity());
    }
}
