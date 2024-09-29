package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.PaymentOtherInformations;
import com.example.isyncpos.repositories.PaymentOtherInformationsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PaymentOtherInformationsViewModel extends AndroidViewModel {

    private PaymentOtherInformationsRepository paymentOtherInformationsRepository;
    private LiveData<List<PaymentOtherInformations>> paymentOtherInformationsLiveData;

    public PaymentOtherInformationsViewModel(Application application){
        super(application);
        paymentOtherInformationsRepository = new PaymentOtherInformationsRepository(application);
        paymentOtherInformationsLiveData = paymentOtherInformationsRepository.fetchCompletePaymentOtherInformationsToSync();
    }

    public void insert(PaymentOtherInformations paymentOtherInformations){
        paymentOtherInformationsRepository.insert(paymentOtherInformations);
    }

    public void update(PaymentOtherInformations paymentOtherInformations){
        paymentOtherInformationsRepository.update(paymentOtherInformations);
    }

    public void updatePaymentOtherInformationSentToServer(int paymentOtherInformationId){
        paymentOtherInformationsRepository.updatePaymentOtherInformationSentToServer(paymentOtherInformationId);
    }

    public List<PaymentOtherInformations> fetchByPaymentId(int paymentId) throws ExecutionException, InterruptedException {
        return paymentOtherInformationsRepository.fetchByPaymentId(paymentId);
    }

    public LiveData<List<PaymentOtherInformations>> fetchCompletePaymentOtherInformationsToSync(){
        return paymentOtherInformationsLiveData;
    }

    public List<PaymentOtherInformations> fetchPaymentOtherInformationToCutOff() throws ExecutionException, InterruptedException {
        return paymentOtherInformationsRepository.fetchPaymentOtherInformationToCutOff();
    }

    public List<PaymentOtherInformations> fetchPaymentOtherInformationByTransactionId(int transactionId) throws ExecutionException, InterruptedException {
        return paymentOtherInformationsRepository.fetchPaymentOtherInformationByTransactionId(transactionId);
    }

}
