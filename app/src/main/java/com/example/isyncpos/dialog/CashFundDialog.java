package com.example.isyncpos.dialog;

import android.app.Dialog;
import android.os.Bundle;
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
import com.example.isyncpos.common.Printer;
import com.example.isyncpos.entity.CashDenomination;
import com.example.isyncpos.entity.CashFund;
import com.example.isyncpos.entity.CashFundDenomination;
import com.example.isyncpos.viewmodel.CashDenominationViewModel;
import com.example.isyncpos.viewmodel.CashFundDenominationViewModel;
import com.example.isyncpos.viewmodel.CashFundViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupDevicesViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CashFundDialog extends DialogFragment {

    private MaterialButton btnCashFundNegative, btnCashFundPositive;
    private TextView labelCashFundTotal;
    private RecyclerView cashDenominationRecycler;
    private CashDenominationAdapter cashDenominationAdapter;
    private CashDenominationViewModel cashDenominationViewModel;
    private CashFundViewModel cashFundViewModel;
    private CashFundDenominationViewModel cashFundDenominationViewModel;
    private PrinterSetupDevicesViewModel printerSetupDevicesViewModel;
    private Dates date;
    private Generate generate;
    private View view;
    private double cashFundTotal;
    private Long cashFundId;

    public static CashFundDialog newCashFundDialog(){
        return new CashFundDialog();
    }

    private void initialize(){
        initViewModels();
        btnCashFundNegative = view.findViewById(R.id.btnCashFundNegative);
        btnCashFundPositive = view.findViewById(R.id.btnCashFundPositive);
        labelCashFundTotal = view.findViewById(R.id.labelCashFundTotal);
        initRecyclerView();
        date = new Dates();
        generate = new Generate();
    }

    private void initViewModels(){
        cashDenominationViewModel = POSApplication.getInstance().getCashDenominationViewModel();
        cashFundViewModel = POSApplication.getInstance().getCashFundViewModel();
        cashFundDenominationViewModel = POSApplication.getInstance().getCashFundDenominationViewModel();
        printerSetupDevicesViewModel = POSApplication.getInstance().getPrinterSetupDevicesViewModel();
    }

    private void initRecyclerView(){
        cashDenominationRecycler = view.findViewById(R.id.recyclerCashFund);
        cashDenominationRecycler.setHasFixedSize(true);
        cashDenominationRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        cashDenominationAdapter = new CashDenominationAdapter();
        cashDenominationAdapter.setMethod("CASH FUND");
        cashDenominationRecycler.setAdapter(cashDenominationAdapter);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.dialog_cashfund,null);
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

        cashFundViewModel.setCashFundTotalLiveData(0.00);
        cashFundViewModel.getCashFundTotalLiveData().observe(getActivity(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                labelCashFundTotal.setText(generate.toTwoDecimalWithComma(value));
            }
        });

        btnCashFundNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnCashFundPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process();
            }
        });

        return alertDialog;
    }

    private void process(){
        List<CashFundDenomination> cashFundDenominations = cashDenominationAdapter.fetchCashFundList();
        if(toProcess(cashFundDenominations)){
            LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Cash fund on progress please wait...");
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        String treg = date.now();
                        double totalAmount = 0.00;
                        cashFundId = cashFundViewModel.insert(new CashFund(
                                POSApplication.getInstance().getMachineDetails().getCoreId(),
                                POSApplication.getInstance().getBranch().getCoreId(),
                                generate.toFourDecimal(computeTotal(cashFundDenominations)),
                                POSApplication.getInstance().getUserAuthentication().getId(),
                                POSApplication.getInstance().getUserAuthentication().getName(),
                                0,
                                0,
                                0,
                                0,
                                1,
                                treg,
                                POSApplication.getInstance().getCompany().getCoreId()
                        ));
                        for(CashFundDenomination item : cashFundDenominations){
                            item.setCutOffId(0);
                            item.setMachineNumber(POSApplication.getInstance().getMachineDetails().getCoreId());
                            item.setBranchId(POSApplication.getInstance().getBranch().getCoreId());
                            item.setCashFundId(Integer.parseInt(cashFundId.toString()));
                            item.setTreg(treg);
                            cashFundDenominationViewModel.insert(item);
                        }
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                CashFund cashFund = cashFundViewModel.fetchById(cashFundId.intValue());
                                Printer.getInstance().print(getActivity(), printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_CASH_FUND), Printer.getInstance().cashFund(cashFund, cashFundDenominations));
                                Toast.makeText(getContext(), "Cash fund has been process.", Toast.LENGTH_LONG).show();
                                LoadingDialog.getInstance().closeLoadingDialog();
                                dismiss();
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
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

    private double computeTotal(List<CashFundDenomination> cashFundDenominations){
        cashFundTotal = 0;
        for(CashFundDenomination item : cashFundDenominations){
            cashFundTotal += item.getTotal();
        }
        return cashFundTotal;
    }

    private boolean toProcess(List<CashFundDenomination> cashFundDenominations){
        for(CashFundDenomination item : cashFundDenominations){
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
        cashFundViewModel.getCashFundTotalLiveData().removeObservers(getActivity());
    }
}
