package com.example.isyncpos.dialog;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.adapters.AccountReceivablePaymentsAdapter;
import com.example.isyncpos.adapters.PaymentsAdapter;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.Font;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.entity.ChargeAccount;
import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.entity.OfficialReceiptInformation;
import com.example.isyncpos.entity.PaymentOtherInformations;
import com.example.isyncpos.entity.PaymentTypeFields;
import com.example.isyncpos.entity.PaymentTypes;
import com.example.isyncpos.entity.Payments;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.preferences.Cache;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.viewmodel.ChargeAccountViewModel;
import com.example.isyncpos.viewmodel.OfficialReceiptInformationViewModel;
import com.example.isyncpos.viewmodel.POSApplicationViewModel;
import com.example.isyncpos.viewmodel.PaymentTypeFieldOptionsViewModel;
import com.example.isyncpos.viewmodel.PaymentTypeFieldsViewModel;
import com.example.isyncpos.viewmodel.PaymentTypesViewModel;
import com.example.isyncpos.viewmodel.PaymentsViewModel;
import com.example.isyncpos.viewmodel.SafekeepingViewModel;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ARPaymentDialog extends DialogFragment {

    private RecyclerView recyclerPayments;
    private AccountReceivablePaymentsAdapter accountReceivablePaymentsAdapter;
    private TextView labelTransactionTenderedValue, labelTransactionAmountDueValue, labelTransactionChangeValue, labelPaymentType, labelTransactionRemarks;
    private EditText txtPaymentAmount, txtORName, txtORAddress, txtORTIN, txtORBusinessStyle;
    private MaterialButton btnPaymentsBack, btnAddPayment, btnPaymentComplete;
    private CheckBox checkboxPaymentAdvance;
    private LinearLayout linearPaymentTypes;
    private AlertDialog alertDialog;
    private TransactionsViewModel transactionsViewModel;
    private PaymentsViewModel paymentsViewModel;
    private PaymentTypesViewModel paymentTypesViewModel;
    private SafekeepingViewModel safekeepingViewModel;
    private PaymentTypeFieldsViewModel paymentTypeFieldsViewModel;
    private PaymentTypeFieldOptionsViewModel paymentTypeFieldOptionsViewModel;
    private OfficialReceiptInformationViewModel officialReceiptInformationViewModel;
    private OfficialReceiptInformation officialReceiptInformationData;
    private ChargeAccountViewModel chargeAccountViewModel;
    private POSProcess posProcess;
    private POSApplicationViewModel posApplicationViewModel;
    private View view;
    private Generate generate;
    private Dates date;
    private LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private int selectedPaymentTypeId = 0;
    private boolean transactionProcess = false;
    private LinearLayout layoutORForm, layoutAddPaymentForm;
    private ViewAccountReceivableDialog viewAccountReceivableDialog;
    private boolean viewAccountReceivableDialogDismiss = false;

    public static ARPaymentDialog newPaymentDialog(){
        return new ARPaymentDialog();
    }

    public void setArgs(ViewAccountReceivableDialog viewAccountReceivableDialog){
        this.viewAccountReceivableDialog = viewAccountReceivableDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.dialog_payment,null);
        builder.setView(view);
        alertDialog = builder.create();

        initialize();

        posApplicationViewModel.getCurrentARTransactionId().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer transactionId) {
                try {
                    transactionsViewModel.setARCurrentTransaction(transactionsViewModel.fetchTransaction(Integer.parseInt(transactionId.toString())));
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        transactionsViewModel.getARCurrentTransaction().observe(getActivity(), new Observer<Transactions>() {
            @Override
            public void onChanged(Transactions transactions) {
                if(transactions != null){
                    try {
                        labelTransactionTenderedValue.setText(generate.toTwoDecimalWithComma(transactions.getTenderAmount()));
                        labelTransactionAmountDueValue.setText(transactions.getNetSales() > 0 ? generate.toTwoDecimalWithComma(transactions.getNetSales()) : "0.00");
                        labelTransactionChangeValue.setText(generate.toTwoDecimalWithComma(transactions.getChange()));
                        if(transactions.getRemarks() != null){
                            labelTransactionRemarks.setText("Remarks: " + transactions.getRemarks());
                        }
                        else{
                            labelTransactionRemarks.setText("Remarks: ");
                        }
                        paymentsViewModel.setCurrentARTransactionPayments(paymentsViewModel.fetchARTransactionPayments(transactions.getId()));
                        setPayableAmount(transactions);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    labelTransactionTenderedValue.setText("0.00");
                    labelTransactionAmountDueValue.setText("0.00");
                    labelTransactionChangeValue.setText("0.00");
                    labelTransactionRemarks.setText("Remarks: ");
                    focusPayableAmount();
                }
            }
        });

        paymentTypesViewModel.fetchAllWithOutAR().observe(getActivity(), new Observer<List<PaymentTypes>>() {
            @Override
            public void onChanged(List<PaymentTypes> paymentTypes) {
                linearPaymentTypes.removeAllViews();
//                layoutParams.setMargins(65, 10, 0, 10);
                try {
                    PaymentTypes paymentTypeCash = paymentTypesViewModel.fetchById(1);
                    if(paymentTypeCash != null){
                        //This will set payment type cash
                        MaterialButton materialCashButton = new MaterialButton(getContext());
                        materialCashButton.setTypeface(Font.getInstance().getSanFranciscoBoldFont(view.getResources()));
                        materialCashButton.setText(paymentTypeCash.getName());
                        materialCashButton.setTextSize(12);
                        materialCashButton.setId(paymentTypeCash.getCoreId());
                        materialCashButton.setCornerRadius(5);
                        materialCashButton.setTextColor(view.getResources().getColor(R.color.white, null));
                        materialCashButton.setIconTintResource(R.color.white);
                        materialCashButton.setLayoutParams(layoutParams);
                        materialCashButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setSelectedPayment(v.getId());
                            }
                        });
                        linearPaymentTypes.addView(materialCashButton);
                        selectedPaymentTypeId = paymentTypeCash.getCoreId();
                        //Done setting the payment type cash
                    }
                    else{
                        Toast.makeText(getContext(), "There is no payment type cash on the data.", Toast.LENGTH_LONG).show();
                    }
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //This will set all of the payment type except for cash
                for(PaymentTypes item : paymentTypes){
                    MaterialButton materialButton = new MaterialButton(getContext());
                    materialButton.setTypeface(Font.getInstance().getSanFranciscoBoldFont(view.getResources()));
                    materialButton.setText(item.getName());
                    materialButton.setTextSize(12);
                    materialButton.setId(item.getCoreId());
                    materialButton.setCornerRadius(5);
                    materialButton.setTextColor(view.getResources().getColor(R.color.white, null));
                    materialButton.setIconTintResource(R.color.white);
                    materialButton.setLayoutParams(layoutParams);
                    materialButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setSelectedPayment(v.getId());
                        }
                    });
                    linearPaymentTypes.addView(materialButton);
                }
            }
        });

        paymentsViewModel.getCurrentARTransactionPayments().observe(getActivity(), new Observer<List<Payments>>() {
            @Override
            public void onChanged(List<Payments> payments) {
                accountReceivablePaymentsAdapter.submitList(payments);
            }
        });

        btnPaymentsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POSApplication.getInstance().setCurrentARTransactionId(0);
                posApplicationViewModel.setARCurrentTransaction(0);
                dismiss();
            }
        });

        btnAddPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPayment();
            }
        });

        btnPaymentComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeTransaction();
            }
        });

        return alertDialog;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        paymentTypesViewModel.fetchAllWithOutAR().removeObservers(getActivity());
        posApplicationViewModel.getCurrentARTransactionId().removeObservers(getActivity());
        transactionsViewModel.getARCurrentTransaction().removeObservers(getActivity());
        paymentsViewModel.getCurrentARTransactionPayments().removeObservers(getActivity());
    }

    private void initialize(){
        initViewModels();
        initPOSProcess();
        btnPaymentsBack = view.findViewById(R.id.btnPaymentsBack);
        labelTransactionTenderedValue = view.findViewById(R.id.labelTransactionTenderedValue);
        labelTransactionAmountDueValue = view.findViewById(R.id.labelTransactionAmountDueValue);
        labelTransactionChangeValue = view.findViewById(R.id.labelTransactionChangeValue);
        labelTransactionRemarks = view.findViewById(R.id.labelTransactionRemarks);
        btnAddPayment = view.findViewById(R.id.btnAddPayment);
        txtPaymentAmount = view.findViewById(R.id.txtPaymentAmount);
        recyclerPayments = view.findViewById(R.id.recyclerPayments);
        btnPaymentComplete = view.findViewById(R.id.btnPaymentComplete);
        checkboxPaymentAdvance = view.findViewById(R.id.checkboxPaymentAdvance);
        linearPaymentTypes = view.findViewById(R.id.linearPaymentTypes);
        labelPaymentType = view.findViewById(R.id.labelPaymentType);
        layoutAddPaymentForm = view.findViewById(R.id.layoutAddPaymentForm);
        layoutORForm = view.findViewById(R.id.layoutORForm);
        txtORName = view.findViewById(R.id.txtORName);
        txtORAddress = view.findViewById(R.id.txtORAddress);
        txtORTIN = view.findViewById(R.id.txtORTIN);
        txtORBusinessStyle = view.findViewById(R.id.txtORBusinessStyle);
        generate = new Generate();
        date = new Dates();
        initRecyclerView();
    }

    private void initViewModels(){
        transactionsViewModel = POSApplication.getInstance().getTransactionsViewModel();
        paymentsViewModel = POSApplication.getInstance().getPaymentsViewModel();
        posApplicationViewModel = POSApplication.getInstance().getPosApplicationViewModel();
        paymentTypesViewModel = POSApplication.getInstance().getPaymentTypesViewModel();
        safekeepingViewModel = POSApplication.getInstance().getSafekeepingViewModel();
        paymentTypeFieldsViewModel = POSApplication.getInstance().getPaymentTypeFieldsViewModel();
        paymentTypeFieldOptionsViewModel = POSApplication.getInstance().getPaymentTypeFieldOptionsViewModel();
        officialReceiptInformationViewModel = POSApplication.getInstance().getOfficialReceiptInformationViewModel();
        chargeAccountViewModel = POSApplication.getInstance().getChargeAccountViewModel();
    }

    private void initPOSProcess(){
        posProcess = POSApplication.getInstance().getPosProcess();
    }

    private void initRecyclerView(){
        recyclerPayments = view.findViewById(R.id.recyclerPayments);
        recyclerPayments.setHasFixedSize(true);
        recyclerPayments.setLayoutManager(new LinearLayoutManager(getContext()));
        accountReceivablePaymentsAdapter = new AccountReceivablePaymentsAdapter(getActivity());
        recyclerPayments.setAdapter(accountReceivablePaymentsAdapter);
    }

    private void setPayableAmount(Transactions transactions){
        try {
            Double totalPayment = paymentsViewModel.fetchARTransactionPaymentsSum(transactions.getId());
            if(totalPayment != null){
                Double total = transactions.getNetSales() - totalPayment;
                if(total > 0){
                    txtPaymentAmount.setText(generate.toTwoDecimalWithComma(total));
                }
                else{
                    txtPaymentAmount.setText(generate.toTwoDecimalWithComma(0.00));
                }
            }
            else{
                if(transactions.getNetSales() < 0){
                    txtPaymentAmount.setText(generate.toTwoDecimalWithComma(0.00));
                }
                else{
                    txtPaymentAmount.setText(generate.toTwoDecimalWithComma(transactions.getNetSales()));
                }

            }
            focusPayableAmount();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void focusPayableAmount(){
        //Request Focus
        if(txtPaymentAmount.isFocused()){
            txtPaymentAmount.clearFocus();
            txtPaymentAmount.requestFocus();
        }
        else{
            txtPaymentAmount.requestFocus();
        }
    }

    private void setSelectedPayment(int id){
        try {
            selectedPaymentTypeId = id;
            PaymentTypes paymentTypes = paymentTypesViewModel.fetchById(id);
            if(paymentTypes != null){
                layoutAddPaymentForm.setVisibility(View.VISIBLE);
                layoutORForm.setVisibility(View.GONE);
                labelPaymentType.setText(paymentTypes.getName());
                txtPaymentAmount.setSelectAllOnFocus(true);
                txtPaymentAmount.requestFocus();
                btnAddPayment.setText("ADD " + paymentTypes.getName().toUpperCase() + " PAYMENT");
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void addPayment(){
        if(!txtPaymentAmount.getText().toString().isEmpty() && generate.toFourDecimal(Double.parseDouble(txtPaymentAmount.getText().toString().replace(",", ""))) > 0){
            try {
                PaymentTypes paymentTypes = paymentTypesViewModel.fetchById(selectedPaymentTypeId);
                if(paymentTypes != null){
                    //Validation For Payment
                    Transactions transactions = posProcess.getCurrentARTransaction();
                    Double totalPayment = paymentsViewModel.fetchARTransactionPaymentsSum(transactions.getId());
                    if(totalPayment != null){
                        Double total = transactions.getNetSales() - totalPayment;
                        if(total > 0){
                            if(paymentTypes.getCoreId() != 1){
                                if(generate.toTwoDecimal(Double.parseDouble(txtPaymentAmount.getText().toString().replace(",", ""))) <= generate.toTwoDecimal(total)){
                                    processAddPayment(paymentTypes);
                                }
                                else{
                                    Toast.makeText(getContext(), "Please input exact amount for this payment type.", Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                processAddPayment(paymentTypes);
                            }
                        }
                        else{
                            Toast.makeText(getContext(), "Already complete the payment cannot add anymore.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        if(paymentTypes.getCoreId() != 1){
                            if(generate.toTwoDecimal(Double.parseDouble(txtPaymentAmount.getText().toString().replace(",", ""))) <= generate.toTwoDecimal(transactions.getNetSales())){
                                processAddPayment(paymentTypes);
                            }
                            else{
                                Toast.makeText(getContext(), "Please input exact amount for this payment type.", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            processAddPayment(paymentTypes);
                        }
                    }
                }
                else{
                    Toast.makeText(getContext(), "There is no payment type selected.", Toast.LENGTH_LONG).show();
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            txtPaymentAmount.setText("0.00");
            Toast.makeText(getContext(), "Please input a valid amount.", Toast.LENGTH_LONG).show();
        }
    }

    private void processAddPayment(PaymentTypes paymentTypes){
        try {
            List<PaymentTypeFields> paymentTypeFields = paymentTypeFieldsViewModel.fetchByPaymentTypeId(selectedPaymentTypeId);
            if(paymentTypeFields != null && paymentTypeFields.size() != 0){
                //Validation For Checking On Has A Payment Type With Other
                Transactions valTransactions = posProcess.getCurrentARTransaction();
                if(valTransactions.getIsAccountReceivable() != 1){
                    if(paymentTypes.getIsAR() == 1){
                        List<Payments> payments = paymentsViewModel.fetchOtherPaymentsForNotAR(POSApplication.getInstance().getCurrentTransaction());
                        if(payments.isEmpty()){
                            CustomDialog customDialog = new CustomDialog(getActivity(), "Payment") {
                                @Override
                                public void confirmPayment(List<PaymentOtherInformations> paymentOtherInformations, ChargeAccount chargeAccount) {
                                    completeProcessAddPayment(paymentTypes, paymentOtherInformations);
                                }

                                @Override
                                public void confirmDiscount(List<DiscountOtherInformations> discountOtherInformations) {
                                    //Do Nothing
                                }
                            };
                            customDialog.setPaymentTypeFields(paymentTypeFields);
                            customDialog.setPaymentTypeFieldOptionsViewModel(paymentTypeFieldOptionsViewModel);
                            customDialog.setChargeAccountViewModel(chargeAccountViewModel);
                            customDialog.setIsAccountReceivable(paymentTypes.getIsAR() == 1 ? true : false);
                            customDialog.setCancelable(false);
                            customDialog.show();
                        }
                        else{
                            Toast.makeText(getContext(), "Cannot add "+ paymentTypes.getName() +" payment please remove all the other payment type before adding this.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        CustomDialog customDialog = new CustomDialog(getActivity(), "Payment") {
                            @Override
                            public void confirmPayment(List<PaymentOtherInformations> paymentOtherInformations, ChargeAccount chargeAccount) {
                                completeProcessAddPayment(paymentTypes, paymentOtherInformations);
                            }

                            @Override
                            public void confirmDiscount(List<DiscountOtherInformations> discountOtherInformations) {
                                //Do Nothing
                            }
                        };
                        customDialog.setPaymentTypeFields(paymentTypeFields);
                        customDialog.setPaymentTypeFieldOptionsViewModel(paymentTypeFieldOptionsViewModel);
                        customDialog.setChargeAccountViewModel(chargeAccountViewModel);
                        customDialog.setIsAccountReceivable(paymentTypes.getIsAR() == 1 ? true : false);
                        customDialog.setCancelable(false);
                        customDialog.show();
                    }
                }
                else{
                    List<Payments> payments = paymentsViewModel.fetchOtherPaymentsForAR(POSApplication.getInstance().getCurrentTransaction());
                    if(payments.isEmpty()){
                        CustomDialog customDialog = new CustomDialog(getActivity(), "Payment") {
                            @Override
                            public void confirmPayment(List<PaymentOtherInformations> paymentOtherInformations, ChargeAccount chargeAccount) {
                                completeProcessAddPayment(paymentTypes, paymentOtherInformations);
                            }

                            @Override
                            public void confirmDiscount(List<DiscountOtherInformations> discountOtherInformations) {
                                //Do Nothing
                            }
                        };
                        customDialog.setPaymentTypeFields(paymentTypeFields);
                        customDialog.setPaymentTypeFieldOptionsViewModel(paymentTypeFieldOptionsViewModel);
                        customDialog.setChargeAccountViewModel(chargeAccountViewModel);
                        customDialog.setIsAccountReceivable(paymentTypes.getIsAR() == 1 ? true : false);
                        customDialog.setCancelable(false);
                        customDialog.show();
                    }
                    else{
                        if(paymentTypes.getIsAR() == 1){
                            Toast.makeText(getContext(), "You cannot add another "+ paymentTypes.getName() +" please remove the current account receivable payment type before adding again.", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getContext(), "Cannot add this "+ paymentTypes.getName() +" please remove all the account receivable payment type before adding this.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
            else{
                //Validation For Checking On Has A Payment Type With Other
                Transactions valTransactions = posProcess.getCurrentARTransaction();
                if(valTransactions.getIsAccountReceivable() != 1){
                    if(paymentTypes.getIsAR() == 1) {
                        List<Payments> payments = paymentsViewModel.fetchOtherPaymentsForNotAR(POSApplication.getInstance().getCurrentTransaction());
                        if(payments.isEmpty()) {
                            completeProcessAddPayment(paymentTypes, null);
                        }
                        else{
                            Toast.makeText(getContext(), "Cannot add "+ paymentTypes.getName() +" payment please remove all the other payment type before adding this.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        completeProcessAddPayment(paymentTypes, null);
                    }
                }
                else{
                    List<Payments> payments = paymentsViewModel.fetchOtherPaymentsForAR(POSApplication.getInstance().getCurrentTransaction());
                    if(payments.isEmpty()) {
                        completeProcessAddPayment(paymentTypes, null);
                    }
                    else{
                        if(paymentTypes.getIsAR() == 1){
                            Toast.makeText(getContext(), "You cannot add another "+ paymentTypes.getName() +" please remove the current account receivable payment type before adding again.", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getContext(), "Cannot add this "+ paymentTypes.getName() +" please remove all the account receivable payment type before adding this.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void completeProcessAddPayment(PaymentTypes paymentTypes, @Nullable List<PaymentOtherInformations> paymentOtherInformations){
        Long paymentTypeId = posProcess.saveTransactionPayment(new Payments(
                POSApplication.getInstance().getMachineDetails().getCoreId(),
                POSApplication.getInstance().getBranch().getCoreId(),
                POSApplication.getInstance().getCurrentARTransactionId(),
                paymentTypes.getCoreId(),
                paymentTypes.getName(),
                generate.toFourDecimal(Double.parseDouble(txtPaymentAmount.getText().toString().replace(",", ""))),
                checkboxPaymentAdvance.isChecked() ? 1 : 0,
                1,
                0,
                0,
                date.now(),
                0,
                "",
                0,
                "",
                "",
                0,
                POSApplication.getInstance().getCompany().getCoreId(),
                paymentTypes.getIsAR()
        ));
        if(paymentOtherInformations != null){
            paymentOtherInformations.forEach(item -> {
                item.setPaymentId(paymentTypeId.intValue());
                posProcess.saveTransactionPaymentOthers(item);
            });
        }
        Transactions transactions = posProcess.recomputeCurrentARTransaction();
        if(paymentTypes.getIsAR() == 1){
            transactions.setIsAccountReceivable(paymentTypes.getIsAR());
            transactionsViewModel.updateTransactionAR(transactions.getId(), 1);
        }
        transactionsViewModel.setARCurrentTransaction(transactions);
        setPayableAmount(transactions);
        checkboxPaymentAdvance.setChecked(false);
        Toast.makeText(getContext(), "Payment information has been save.", Toast.LENGTH_LONG).show();
    }

    private void completeTransaction(){
        if(!posProcess.checkForEndOfDayProcess()){
            if(!transactionProcess){
                transactionProcess = true;
                LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Redeeming transaction in progress please wait...");
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {

                        viewAccountReceivableDialogDismiss = posProcess.completeCurrentARTransaction(getActivity(), POSApplication.getInstance().getCurrentARTransactionId());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                POSApplication.getInstance().setCurrentARTransactionId(0);
                                posApplicationViewModel.setARCurrentTransaction(0);
                                LoadingDialog.getInstance().closeLoadingDialog();
                                checkForSafeKeeping();
                                if(viewAccountReceivableDialogDismiss) viewAccountReceivableDialog.closeDialog();
                                dismiss();
                            }
                        });

                    }
                });
            }
        }
        else{
            Toast.makeText(getContext(), "Please process the end of day of the previous cutoff before you can complete a transaction for this day.", Toast.LENGTH_LONG).show();
        }
    }

    private void checkForSafeKeeping(){
        if(generate.toFourDecimal(POSApplication.getInstance().getMachineDetails().getCashRegisterLimitAmount()) != generate.toFourDecimal(0)){
            try {
                Double sumSafekeepingAmount = safekeepingViewModel.sumAllSafekeeping();
                Double sumTenderAmount = transactionsViewModel.sumAllCashTransaction();
                double totalAmount = 0.00;
                if(sumSafekeepingAmount != null){
                    totalAmount = sumTenderAmount.doubleValue() - sumSafekeepingAmount.doubleValue();
                }
                else{
                    if(sumTenderAmount != null){
                        totalAmount = sumTenderAmount.doubleValue();
                    }
                }
                if(generate.toFourDecimal(totalAmount) >= generate.toFourDecimal(POSApplication.getInstance().getMachineDetails().getCashRegisterLimitAmount())){
                    SafeKeepingAuto();
                }
                Log.d("checkForSafeKeeping", "checkForSafeKeeping: " + generate.toFourDecimal(totalAmount) + " >= " + generate.toFourDecimal(POSApplication.getInstance().getMachineDetails().getCashRegisterLimitAmount()));
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void SafeKeepingAuto(){
        SafekeepingDialog safekeepingDialog = SafekeepingDialog.newSafekeepingDialog();
        safekeepingDialog.setCancelable(false);
        safekeepingDialog.setIsAuto(1);
        safekeepingDialog.show(getActivity().getSupportFragmentManager(), "Safekeeping Dialog");
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }

}
