package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.PaymentTypeFields;
import com.example.isyncpos.repositories.PaymentTypeFieldsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PaymentTypeFieldsViewModel extends AndroidViewModel {

    private PaymentTypeFieldsRepository paymentTypeFieldsRepository;

    public PaymentTypeFieldsViewModel(Application application){
        super(application);
        paymentTypeFieldsRepository = new PaymentTypeFieldsRepository(application);
    }

    public void insert(PaymentTypeFields paymentTypeFields){
        paymentTypeFieldsRepository.insert(paymentTypeFields);
    }

    public Void delete(int paymentTypeId) throws ExecutionException, InterruptedException {
        return paymentTypeFieldsRepository.delete(paymentTypeId);
    }

    public List<PaymentTypeFields> fetchByPaymentTypeId(int paymentTypeId) throws ExecutionException, InterruptedException {
        return paymentTypeFieldsRepository.fetchByPaymentTypeId(paymentTypeId);
    }

}
