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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.adapters.PaymentsAdapter;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.Font;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.entity.OfficialReceiptInformation;
import com.example.isyncpos.entity.PaymentOtherInformations;
import com.example.isyncpos.entity.PaymentTypeFields;
import com.example.isyncpos.entity.PaymentTypes;
import com.example.isyncpos.entity.Payments;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.preferences.Cache;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.viewmodel.DiscountTypeFieldOptionsViewModel;
import com.example.isyncpos.viewmodel.DiscountTypeFieldsViewModel;
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

public class PaymentDialog extends DialogFragment {

    private RecyclerView recyclerPayments;
    private PaymentsAdapter paymentsAdapter;
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
    private POSProcess posProcess;
    private POSApplicationViewModel posApplicationViewModel;
    private View view;
    private Generate generate;
    private Dates date;
    private Cache cache;
    private LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private int selectedPaymentTypeId = 0;
    private boolean transactionProcess = false;
    private LinearLayout layoutORForm, layoutAddPaymentForm;


    public static PaymentDialog newPaymentDialog(){
        return new PaymentDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.dialog_payment,null);
        builder.setView(view);
        alertDialog = builder.create();

        initialize();

        //Get The Official Receipt Information
        try {
            officialReceiptInformationData = officialReceiptInformationViewModel.fetchByTransactionId(POSApplication.getInstance().getCurrentTransaction());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        transactionsViewModel.getCurrentTransaction().observe(getActivity(), new Observer<Transactions>() {
            @Override
            public void onChanged(Transactions transactions) {
                if(transactions != null){
                    labelTransactionTenderedValue.setText(generate.toTwoDecimalWithComma(transactions.getTenderAmount()));
                    labelTransactionAmountDueValue.setText(transactions.getNetSales() > 0 ? generate.toTwoDecimalWithComma(transactions.getNetSales()) : "0.00");
                    labelTransactionChangeValue.setText(generate.toTwoDecimalWithComma(transactions.getChange()));
                    if(transactions.getRemarks() != null){
                        labelTransactionRemarks.setText("Remarks: " + transactions.getRemarks());
                    }
                    else{
                        labelTransactionRemarks.setText("Remarks: ");
                    }
                    paymentsViewModel.setCurrentTransactionPayments(posProcess.getCurrentTransactionPayments());
                    setPayableAmount(transactions);
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

        paymentTypesViewModel.fetchAll().observe(getActivity(), new Observer<List<PaymentTypes>>() {
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
//                        materialCashButton.setHeight(200);
                        materialCashButton.setTextColor(view.getResources().getColor(R.color.white, null));
//                        materialCashButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.baseline_lock_24));
                        materialCashButton.setIconTintResource(R.color.white);
//                        materialCashButton.setBackgroundColor(getResources().getColor(R.color.lightblue, null));
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
//                    materialButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.baseline_lock_24));
                    materialButton.setIconTintResource(R.color.white);
//        materialButtonAll.setBackgroundColor(getResources().getColor(R.color.lightblue, null));
                    materialButton.setLayoutParams(layoutParams);
                    materialButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setSelectedPayment(v.getId());
                        }
                    });
                    linearPaymentTypes.addView(materialButton);
                }
                //This will set the OR Information Button
                MaterialButton materialORButton = new MaterialButton(getContext());
                materialORButton.setTypeface(Font.getInstance().getSanFranciscoBoldFont(view.getResources()));
                materialORButton.setText("OR");
                materialORButton.setTextSize(12);
                materialORButton.setId(-1);
                materialORButton.setCornerRadius(5);
                materialORButton.setTextColor(view.getResources().getColor(R.color.white, null));
                materialORButton.setIconTintResource(R.color.white);
                materialORButton.setLayoutParams(layoutParams);
                materialORButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setSelectedPayment(-1);
                    }
                });
                linearPaymentTypes.addView(materialORButton);
                //This will set the Add Remarks Button
                MaterialButton materialRemarksButton = new MaterialButton(getContext());
                materialRemarksButton.setTypeface(Font.getInstance().getSanFranciscoBoldFont(view.getResources()));
                materialRemarksButton.setText("ADD REMARKS");
                materialRemarksButton.setTextSize(12);
                materialRemarksButton.setId(-1);
                materialRemarksButton.setCornerRadius(5);
                materialRemarksButton.setTextColor(view.getResources().getColor(R.color.white, null));
                materialRemarksButton.setIconTintResource(R.color.white);
                materialRemarksButton.setLayoutParams(layoutParams);
                materialRemarksButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setSelectedPayment(-2);
                    }
                });
                linearPaymentTypes.addView(materialRemarksButton);
            }
        });


        paymentsViewModel.getCurrentTransactionPayments().observe(getActivity(), new Observer<List<Payments>>() {
            @Override
            public void onChanged(List<Payments> payments) {
                paymentsAdapter.submitList(payments);
            }
        });

        btnPaymentsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        transactionsViewModel.getCurrentTransaction().removeObservers(this);
        paymentTypesViewModel.fetchAll().removeObservers(this);
        paymentsViewModel.getCurrentTransactionPayments().removeObservers(this);
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
        cache = new Cache(getContext());
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
    }

    private void initPOSProcess(){
        posProcess = POSApplication.getInstance().getPosProcess();
    }

    private void initRecyclerView(){
        recyclerPayments = view.findViewById(R.id.recyclerPayments);
        recyclerPayments.setHasFixedSize(true);
        recyclerPayments.setLayoutManager(new LinearLayoutManager(getContext()));
        paymentsAdapter = new PaymentsAdapter(getActivity());
        recyclerPayments.setAdapter(paymentsAdapter);
    }

    private void setPayableAmount(Transactions transactions){
        try {
            Double totalPayment = paymentsViewModel.fetchTransactionPaymentsSum(transactions.getId());
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
            else {
                if(id == -1){
                    layoutAddPaymentForm.setVisibility(View.GONE);
                    layoutORForm.setVisibility(View.VISIBLE);
                    labelPaymentType.setText("Official Receipt Information");
                    btnAddPayment.setText("Save Information");
                    if(officialReceiptInformationData != null){
                        txtORName.setText(officialReceiptInformationData.getName());
                        txtORAddress.setText(officialReceiptInformationData.getAddress());
                        txtORTIN.setText(officialReceiptInformationData.getTin());
                        txtORBusinessStyle.setText(officialReceiptInformationData.getBusinessStyle());
                    }
                    else{
                        //Request Focus
                        txtORName.requestFocus();
                    }
                }
                else if(id == -2){
                    AddTransactionRemarksDialog addTransactionRemarksDialog = new AddTransactionRemarksDialog(getActivity()) {
                        @Override
                        public void process(boolean success) {
                            if(success){
                                try {
                                    Transactions transactions = transactionsViewModel.fetchTransaction(POSApplication.getInstance().getCurrentTransaction());
                                    if(transactions != null){
                                        labelTransactionRemarks.setText("Remarks: " + transactions.getRemarks());
                                    }
                                    else{
                                        labelTransactionRemarks.setText("Remarks: ");
                                    }
                                    setSelectedPayment(1);
                                } catch (ExecutionException e) {
                                    throw new RuntimeException(e);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    };
                    addTransactionRemarksDialog.setCancelable(false);
                    addTransactionRemarksDialog.show();
                }
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void addPayment(){
        if(selectedPaymentTypeId < 0){
            btnAddPayment.setEnabled(false);
            LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Official receipt information saving please wait..");
            if(officialReceiptInformationData != null){
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        officialReceiptInformationData.setName(txtORName.getText().toString());
                        officialReceiptInformationData.setAddress(txtORAddress.getText().toString());
                        officialReceiptInformationData.setTin(txtORTIN.getText().toString());
                        officialReceiptInformationData.setBusinessStyle(txtORBusinessStyle.getText().toString());
                        officialReceiptInformationData.setIsSentToServer(0);
                        officialReceiptInformationViewModel.update(officialReceiptInformationData);
                        Toast.makeText(getContext(), "OR Information has been update.", Toast.LENGTH_LONG).show();
                        LoadingDialog.getInstance().closeLoadingDialog();
                        btnAddPayment.setEnabled(true);
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            else{
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        officialReceiptInformationViewModel.insert(new OfficialReceiptInformation(
                                POSApplication.getInstance().getCurrentTransaction(),
                                txtORName.getText().toString(),
                                txtORAddress.getText().toString(),
                                txtORTIN.getText().toString(),
                                txtORBusinessStyle.getText().toString(),
                                0,
                                0,
                                "",
                                "",
                                0,
                                date.now(),
                                POSApplication.getInstance().getMachineDetails().getCoreId(),
                                POSApplication.getInstance().getBranch().getCoreId(),
                                POSApplication.getInstance().getCompany().getCoreId()
                        ));
                        //Get The Official Receipt Information
                        try {
                            officialReceiptInformationData = officialReceiptInformationViewModel.fetchByTransactionId(POSApplication.getInstance().getCurrentTransaction());
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(getContext(), "OR Information has been save.", Toast.LENGTH_LONG).show();
                        LoadingDialog.getInstance().closeLoadingDialog();
                        btnAddPayment.setEnabled(true);
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
        }
        else{
            if(!txtPaymentAmount.getText().toString().isEmpty() && generate.toFourDecimal(Double.parseDouble(txtPaymentAmount.getText().toString().replace(",", ""))) > 0){
                try {
                    PaymentTypes paymentTypes = paymentTypesViewModel.fetchById(selectedPaymentTypeId);
                    if(paymentTypes != null){
                        //Validation For Payment
                        Transactions transactions = posProcess.getCurrentTransaction();
                        Double totalPayment = paymentsViewModel.fetchTransactionPaymentsSum(transactions.getId());
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
    }

    private void processAddPayment(PaymentTypes paymentTypes){
        try {
            List<PaymentTypeFields> paymentTypeFields = paymentTypeFieldsViewModel.fetchByPaymentTypeId(selectedPaymentTypeId);
            if(paymentTypeFields != null && paymentTypeFields.size() != 0){
                //Validation For Checking On Has A Payment Type With Other
                Transactions valTransactions = posProcess.getCurrentTransaction();
                if(valTransactions.getIsAccountReceivable() != 1){
                    if(paymentTypes.getIsAR() == 1){
                        List<Payments> payments = paymentsViewModel.fetchOtherPaymentsForNotAR(POSApplication.getInstance().getCurrentTransaction());
                        if(payments.isEmpty()){
                            CustomDialog customDialog = new CustomDialog(getActivity(), "Payment") {
                                @Override
                                public void confirmPayment(List<PaymentOtherInformations> paymentOtherInformations) {
                                    completeProcessAddPayment(paymentTypes, paymentOtherInformations);
                                }

                                @Override
                                public void confirmDiscount(List<DiscountOtherInformations> discountOtherInformations) {
                                    //Do Nothing
                                }
                            };
                            customDialog.setPaymentTypeFields(paymentTypeFields);
                            customDialog.setPaymentTypeFieldOptionsViewModel(paymentTypeFieldOptionsViewModel);
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
                            public void confirmPayment(List<PaymentOtherInformations> paymentOtherInformations) {
                                completeProcessAddPayment(paymentTypes, paymentOtherInformations);
                            }

                            @Override
                            public void confirmDiscount(List<DiscountOtherInformations> discountOtherInformations) {
                                //Do Nothing
                            }
                        };
                        customDialog.setPaymentTypeFields(paymentTypeFields);
                        customDialog.setPaymentTypeFieldOptionsViewModel(paymentTypeFieldOptionsViewModel);
                        customDialog.setCancelable(false);
                        customDialog.show();
                    }
                }
                else{
                    List<Payments> payments = paymentsViewModel.fetchOtherPaymentsForAR(POSApplication.getInstance().getCurrentTransaction());
                    if(payments.isEmpty()){
                        CustomDialog customDialog = new CustomDialog(getActivity(), "Payment") {
                            @Override
                            public void confirmPayment(List<PaymentOtherInformations> paymentOtherInformations) {
                                completeProcessAddPayment(paymentTypes, paymentOtherInformations);
                            }

                            @Override
                            public void confirmDiscount(List<DiscountOtherInformations> discountOtherInformations) {
                                //Do Nothing
                            }
                        };
                        customDialog.setPaymentTypeFields(paymentTypeFields);
                        customDialog.setPaymentTypeFieldOptionsViewModel(paymentTypeFieldOptionsViewModel);
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
                Transactions valTransactions = posProcess.getCurrentTransaction();
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
                POSApplication.getInstance().getCurrentTransaction(),
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
        Transactions transactions = posProcess.recomputeCurrentTransaction();
        if(paymentTypes.getIsAR() == 1){
            transactions.setIsAccountReceivable(paymentTypes.getIsAR());
            transactionsViewModel.updateTransactionAR(transactions.getId(), 1);
        }
        transactionsViewModel.setCurrentTransaction(transactions);
        setPayableAmount(transactions);
        checkboxPaymentAdvance.setChecked(false);
        Toast.makeText(getContext(), "Payment information has been save.", Toast.LENGTH_LONG).show();
    }

    private void completeTransaction(){
        if(!posProcess.checkForEndOfDayProcess()){
            if(posProcess.checkCurrentTransactionPayments()){
                if(!transactionProcess){
                    transactionProcess = true;
                    LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Complete transaction in progress please wait...");
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {

                            posProcess.completeCurrentTransaction(getActivity(), POSApplication.getInstance().getCurrentTransaction());

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    POSApplication.getInstance().setCurrentTransaction(0);
                                    posApplicationViewModel.setCurrentTransaction(0);
                                    cache.clearString("currentTransaction");
                                    LoadingDialog.getInstance().closeLoadingDialog();
                                    checkForSafeKeeping();
                                    dismiss();
                                }
                            });

                        }
                    });
                }
            }
            else{
                Toast.makeText(getContext(), "Insufficient funds.", Toast.LENGTH_LONG).show();
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
