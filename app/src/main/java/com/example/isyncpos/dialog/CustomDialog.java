package com.example.isyncpos.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.entity.ChargeAccount;
import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.entity.DiscountTypeFieldOptions;
import com.example.isyncpos.entity.DiscountTypeFields;
import com.example.isyncpos.entity.PaymentOtherInformations;
import com.example.isyncpos.entity.PaymentTypeFieldOptions;
import com.example.isyncpos.entity.PaymentTypeFields;
import com.example.isyncpos.objects.FieldTypeObject;
import com.example.isyncpos.viewmodel.ChargeAccountViewModel;
import com.example.isyncpos.viewmodel.DiscountTypeFieldOptionsViewModel;
import com.example.isyncpos.viewmodel.PaymentTypeFieldOptionsViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public abstract class CustomDialog extends Dialog {

    private LinearLayout container;
    private List<PaymentTypeFields> paymentTypeFields;
    private List<DiscountTypeFields> discountTypeFields;
    private PaymentTypeFieldOptionsViewModel paymentTypeFieldOptionsViewModel;
    private DiscountTypeFieldOptionsViewModel discountTypeFieldOptionsViewModel;
    private MaterialButton btnCustomDialogNegative, btnCustomDialogPositive;
    private ChargeAccountViewModel chargeAccountViewModel;
    private String method;
    private Dates date;
    private boolean isAccountReceivable = false;
    // Data source for suggestions
    private List<String> chargeAccountSuggestions = new ArrayList<>();
    private ChargeAccount chargeAccount;
    private Timer timer;
    private Activity activity;

    public abstract void confirmPayment(List<PaymentOtherInformations> paymentOtherInformations, @Nullable ChargeAccount chargeAccount);
    public abstract void confirmDiscount(List<DiscountOtherInformations> discountOtherInformations);


    public CustomDialog(Activity activity, String method){
        super(activity);
        this.activity = activity;
        this.method = method;
    }

    public void setPaymentTypeFields(List<PaymentTypeFields> paymentTypeFields){
        this.paymentTypeFields = paymentTypeFields;
    }

    public void setPaymentTypeFieldOptionsViewModel(PaymentTypeFieldOptionsViewModel paymentTypeFieldOptionsViewModel){
        this.paymentTypeFieldOptionsViewModel = paymentTypeFieldOptionsViewModel;
    }

    public void setDiscountTypeFields(List<DiscountTypeFields> discountTypeFields){
        this.discountTypeFields = discountTypeFields;
    }

    public void setDiscountTypeFieldOptionsViewModel(DiscountTypeFieldOptionsViewModel discountTypeFieldOptionsViewModel){
        this.discountTypeFieldOptionsViewModel = discountTypeFieldOptionsViewModel;
    }

    public void setIsAccountReceivable(boolean isAccountReceivable){
        this.isAccountReceivable = isAccountReceivable;
    }

    public void setChargeAccountViewModel(ChargeAccountViewModel chargeAccountViewModel){
        this.chargeAccountViewModel = chargeAccountViewModel;
    }

    public void initializeCustom(){
        if(method.equals("Payment")){
            paymentTypeFields.forEach(item -> {
                if(item.getFieldType() != null){
                    addCustomTextLabel(item.getName());
                    if(item.getFieldType().equals("textbox")){
                        if(isAccountReceivable && item.getName().equals("CUSTOMER'S NAME:")){
                            addCustomAutoCompleteTextField(item.getName(), 0);
                        }
                        else{
                            addCustomTextField(item.getName(), 0);
                        }
                    } else if (item.getFieldType().equals("select")) {
                        try {
                            List<String> options = new ArrayList<>();
                            List<PaymentTypeFieldOptions> paymentTypeFieldOptions = paymentTypeFieldOptionsViewModel.fetchByPaymentTypeFieldId(item.getCoreId());
                            paymentTypeFieldOptions.forEach(itemOption -> {
                                options.add(itemOption.getName());
                            });
                            addCustomSpinner(options, item.getName(), 0);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }
        else{
            discountTypeFields.forEach(item -> {
                if(item.getFieldType() != null){
                    addCustomTextLabel(item.getName());
                    if(item.getFieldType().equals("textbox")){
                        addCustomTextField(item.getName(), item.getIsRequired());
                    } else if (item.getFieldType().equals("select")) {
                        try {
                            List<String> options = new ArrayList<>();
                            List<DiscountTypeFieldOptions> discountTypeFieldOptions = discountTypeFieldOptionsViewModel.fetchByDiscountTypeFieldId(item.getCoreId());
                            discountTypeFieldOptions.forEach(itemOption -> {
                                options.add(itemOption.getName());
                            });
                            addCustomSpinner(options, item.getName(), item.getIsRequired());
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }
    }

    private void addCustomTextLabel(String label){
        TextView textView = new TextView(getContext());
        textView.setText(label);
        container.addView(textView);
    }

    private void addCustomTextField(String tag, int isRequired){
        EditText editText = new EditText(getContext());
        editText.setTag(new FieldTypeObject(tag, isRequired));
        container.addView(editText);
    }

    private void addCustomAutoCompleteTextField(String tag, int isRequired){
        AutoCompleteTextView autoCompleteTextView = new AutoCompleteTextView(getContext());
        autoCompleteTextView.setTag(new FieldTypeObject(tag, isRequired));
        // Set up the adapter with an empty list initially
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, chargeAccountSuggestions);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("onTextChanged", "onTextChanged: " + editable.toString());
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            chargeAccount = chargeAccountViewModel.fetchByName(editable.toString());
                            LoopARCustomField(editable.toString());
                        } catch (ExecutionException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, 500);
            }
        });
        container.addView(autoCompleteTextView);
    }

    private void addCustomSpinner(List<String> options, String tag, int isRequired){
        Spinner spinner = new Spinner(getContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setTag(new FieldTypeObject(tag, isRequired));
        container.addView(spinner);
    }

    private void LoopARCustomField(String name){
        if(chargeAccount != null){
            int childCount = container.getChildCount();
            if(method.equals("Payment")){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < childCount; i++) {
                            View view = container.getChildAt(i);
                            if (view instanceof EditText) {
                                EditText editText = (EditText) view;
                                FieldTypeObject fieldTypeObject = (FieldTypeObject) editText.getTag();
                                if(fieldTypeObject.getName().equals("ADDRESS:")){
                                    editText.setText(chargeAccount.getAddress());
                                }
                                if(fieldTypeObject.getName().equals("MOBILE NO.:")){
                                    editText.setText(chargeAccount.getContactNumber());
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    private void LoopCustomField(){
        int childCount = container.getChildCount();
        boolean processData = true;
        if(method.equals("Payment")){
            List<PaymentOtherInformations> paymentOtherInformations = new ArrayList<>();
            String treg = date.now();
            for (int i = 0; i < childCount; i++) {
                View view = container.getChildAt(i);
                if (view instanceof EditText) {
                    EditText editText = (EditText) view;
                    FieldTypeObject fieldTypeObject = (FieldTypeObject) editText.getTag();
                    if(fieldTypeObject.getIsRequired() == 1){
                        if(!editText.getText().toString().isEmpty()){
                            paymentOtherInformations.add(new PaymentOtherInformations(
                                POSApplication.getInstance().getMachineDetails().getCoreId(),
                                POSApplication.getInstance().getBranch().getCoreId(),
                                POSApplication.getInstance().getCurrentTransaction(),
                                0,
                                fieldTypeObject.getName(),
                                editText.getText().toString(),
                                0,
                                0,
                                0,
                                0,
                                treg,
                                POSApplication.getInstance().getCompany().getCoreId()
                            ));
                        }
                        else{
                            processData = false;
                            break;
                        }
                    }
                    else{
                        paymentOtherInformations.add(new PaymentOtherInformations(
                            POSApplication.getInstance().getMachineDetails().getCoreId(),
                            POSApplication.getInstance().getBranch().getCoreId(),
                            POSApplication.getInstance().getCurrentTransaction(),
                            0,
                            fieldTypeObject.getName(),
                            editText.getText().toString(),
                            0,
                            0,
                            0,
                            0,
                            treg,
                            POSApplication.getInstance().getCompany().getCoreId()
                        ));
                    }
                }
                if (view instanceof Spinner) {
                    Spinner spinner = (Spinner) view;
                    FieldTypeObject fieldTypeObject = (FieldTypeObject) spinner.getTag();
                    if(fieldTypeObject.getIsRequired() == 1){
                        if(!spinner.getSelectedItem().toString().isEmpty()){
                            paymentOtherInformations.add(new PaymentOtherInformations(
                                POSApplication.getInstance().getMachineDetails().getCoreId(),
                                POSApplication.getInstance().getBranch().getCoreId(),
                                POSApplication.getInstance().getCurrentTransaction(),
                                0,
                                fieldTypeObject.getName(),
                                spinner.getSelectedItem().toString(),
                                0,
                                0,
                                0,
                                0,
                                treg,
                                POSApplication.getInstance().getCompany().getCoreId()
                            ));
                        }
                        else{
                            processData = false;
                            break;
                        }
                    }
                    else{
                        paymentOtherInformations.add(new PaymentOtherInformations(
                            POSApplication.getInstance().getMachineDetails().getCoreId(),
                            POSApplication.getInstance().getBranch().getCoreId(),
                            POSApplication.getInstance().getCurrentTransaction(),
                            0,
                            fieldTypeObject.getName(),
                            spinner.getSelectedItem().toString(),
                            0,
                            0,
                            0,
                            0,
                            treg,
                            POSApplication.getInstance().getCompany().getCoreId()
                        ));
                    }
                }
            }
            if(processData){
                confirmPayment(paymentOtherInformations, chargeAccount);
                dismiss();
            }
            else{
                Toast.makeText(getContext(), "Please dont leave any blank information if there is value please input N/A.", Toast.LENGTH_LONG).show();
            }
        }
        else{
            List<DiscountOtherInformations> discountOtherInformations = new ArrayList<>();
            String treg = date.now();
            for (int i = 0; i < childCount; i++) {
                View view = container.getChildAt(i);
                if (view instanceof EditText) {
                    EditText editText = (EditText) view;
                    FieldTypeObject fieldTypeObject = (FieldTypeObject) editText.getTag();
                    if(fieldTypeObject.getIsRequired() == 1){
                        if(!editText.getText().toString().isEmpty()){
                            discountOtherInformations.add(new DiscountOtherInformations(
                                POSApplication.getInstance().getMachineDetails().getCoreId(),
                                POSApplication.getInstance().getBranch().getCoreId(),
                                POSApplication.getInstance().getCurrentTransaction(),
                                0,
                                fieldTypeObject.getName(),
                                editText.getText().toString(),
                                0,
                                0,
                                0,
                                0,
                                treg,
                                POSApplication.getInstance().getCompany().getCoreId()
                            ));
                        }
                        else{
                            processData = false;
                            break;
                        }
                    }
                    else{
                        discountOtherInformations.add(new DiscountOtherInformations(
                            POSApplication.getInstance().getMachineDetails().getCoreId(),
                            POSApplication.getInstance().getBranch().getCoreId(),
                            POSApplication.getInstance().getCurrentTransaction(),
                            0,
                            fieldTypeObject.getName(),
                            editText.getText().toString(),
                            0,
                            0,
                            0,
                            0,
                            treg,
                            POSApplication.getInstance().getCompany().getCoreId()
                        ));
                    }
                }
                if (view instanceof Spinner) {
                    Spinner spinner = (Spinner) view;
                    FieldTypeObject fieldTypeObject = (FieldTypeObject) spinner.getTag();
                    if(fieldTypeObject.getIsRequired() == 1){
                        if(!spinner.getSelectedItem().toString().isEmpty()){
                            discountOtherInformations.add(new DiscountOtherInformations(
                                POSApplication.getInstance().getMachineDetails().getCoreId(),
                                POSApplication.getInstance().getBranch().getCoreId(),
                                POSApplication.getInstance().getCurrentTransaction(),
                                0,
                                fieldTypeObject.getName(),
                                spinner.getSelectedItem().toString(),
                                0,
                                0,
                                0,
                                0,
                                treg,
                                POSApplication.getInstance().getCompany().getCoreId()
                            ));
                        }
                        else{
                            processData = false;
                            break;
                        }
                    }
                    else{
                        discountOtherInformations.add(new DiscountOtherInformations(
                            POSApplication.getInstance().getMachineDetails().getCoreId(),
                            POSApplication.getInstance().getBranch().getCoreId(),
                            POSApplication.getInstance().getCurrentTransaction(),
                            0,
                            fieldTypeObject.getName(),
                            spinner.getSelectedItem().toString(),
                            0,
                            0,
                            0,
                            0,
                            treg,
                            POSApplication.getInstance().getCompany().getCoreId()
                        ));
                    }
                }
            }
            if(processData){
                confirmDiscount(discountOtherInformations);
                dismiss();
            }
            else{
                Toast.makeText(getContext(), "Please dont leave any blank information if there is value please input N/A.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);
        setCancelable(false);
        initialize();
        initializeCustom();
        if(isAccountReceivable) initChargeAccounts();
        btnCustomDialogNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnCustomDialogPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoopCustomField();
            }
        });
    }

    private void initialize(){
        btnCustomDialogNegative = findViewById(R.id.btnCustomDialogNegative);
        btnCustomDialogPositive = findViewById(R.id.btnCustomDialogPositive);
        container = findViewById(R.id.linearCustomForm);
        date = new Dates();
    }

    private void initChargeAccounts(){
        if(chargeAccountViewModel != null){
            try {
                List<ChargeAccount> chargeAccounts = chargeAccountViewModel.fetchAll();
                chargeAccounts.forEach(item -> {
                    chargeAccountSuggestions.add(item.getName());
                });
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
