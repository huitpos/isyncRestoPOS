package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.PaymentTypeFieldOptions;
import com.example.isyncpos.repositories.PaymentTypeFieldOptionsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PaymentTypeFieldOptionsViewModel extends AndroidViewModel {

    private PaymentTypeFieldOptionsRepository paymentTypeFieldOptionsRepository;

    public PaymentTypeFieldOptionsViewModel(Application application){
        super(application);
        paymentTypeFieldOptionsRepository = new PaymentTypeFieldOptionsRepository(application);
    }

    public void insert(PaymentTypeFieldOptions paymentTypeFieldOptions){
        paymentTypeFieldOptionsRepository.insert(paymentTypeFieldOptions);
    }

    public Void delete(int paymentTypeId) throws ExecutionException, InterruptedException {
        return paymentTypeFieldOptionsRepository.delete(paymentTypeId);
    }

    public List<PaymentTypeFieldOptions> fetchByPaymentTypeFieldId(int paymentTypeFieldId) throws ExecutionException, InterruptedException {
        return paymentTypeFieldOptionsRepository.fetchByPaymentTypeFieldId(paymentTypeFieldId);
    }

}
