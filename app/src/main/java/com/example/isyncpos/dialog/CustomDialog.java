package com.example.isyncpos.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.entity.DiscountTypeFieldOptions;
import com.example.isyncpos.entity.DiscountTypeFields;
import com.example.isyncpos.entity.PaymentOtherInformations;
import com.example.isyncpos.entity.PaymentTypeFieldOptions;
import com.example.isyncpos.entity.PaymentTypeFields;
import com.example.isyncpos.objects.FieldTypeObject;
import com.example.isyncpos.viewmodel.DiscountTypeFieldOptionsViewModel;
import com.example.isyncpos.viewmodel.PaymentTypeFieldOptionsViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public abstract class CustomDialog extends Dialog {

    private LinearLayout container;
    private List<PaymentTypeFields> paymentTypeFields;
    private List<DiscountTypeFields> discountTypeFields;
    private PaymentTypeFieldOptionsViewModel paymentTypeFieldOptionsViewModel;
    private DiscountTypeFieldOptionsViewModel discountTypeFieldOptionsViewModel;
    private MaterialButton btnCustomDialogNegative, btnCustomDialogPositive;
    private String method;
    private Dates date;

    public abstract void confirmPayment(List<PaymentOtherInformations> paymentOtherInformations);
    public abstract void confirmDiscount(List<DiscountOtherInformations> discountOtherInformations);


    public CustomDialog(Activity activity, String method){
        super(activity);
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

    public void initializeCustom(){
        if(method.equals("Payment")){
            paymentTypeFields.forEach(item -> {
                if(item.getFieldType() != null){
                    addCustomTextLabel(item.getName());
                    if(item.getFieldType().equals("textbox")){
                        addCustomTextField(item.getName(), 0);
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

    private void addCustomSpinner(List<String> options, String tag, int isRequired){
        Spinner spinner = new Spinner(getContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setTag(new FieldTypeObject(tag, isRequired));
        container.addView(spinner);
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
                confirmPayment(paymentOtherInformations);
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

}
