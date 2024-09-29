package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.PaymentTypes;
import com.example.isyncpos.repositories.PaymentTypesRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PaymentTypesViewModel extends AndroidViewModel {

    private PaymentTypesRepository paymentTypesRepository;
    private LiveData<List<PaymentTypes>> paymentTypesLiveData;
    private LiveData<List<PaymentTypes>> paymentTypesWithOutARLiveData;

    public PaymentTypesViewModel(Application application){
        super(application);
        paymentTypesRepository = new PaymentTypesRepository(application);
        paymentTypesLiveData = paymentTypesRepository.fetchAll();
        paymentTypesWithOutARLiveData = paymentTypesRepository.fetchAllWithOutAR();
    }

    public void insert(PaymentTypes paymentTypes){
        paymentTypesRepository.insert(paymentTypes);
    }

    public void update(PaymentTypes paymentTypes){
        paymentTypesRepository.update(paymentTypes);
    }

    public LiveData<List<PaymentTypes>> fetchAll(){
        return paymentTypesLiveData;
    }

    public LiveData<List<PaymentTypes>> fetchAllWithOutAR(){
        return paymentTypesWithOutARLiveData;
    }

    public PaymentTypes fetchById(int id) throws ExecutionException, InterruptedException {
        return paymentTypesRepository.fetchById(id);
    }

    public List<PaymentTypes> fetchAllPaymentType() throws ExecutionException, InterruptedException {
        return paymentTypesRepository.fetchAllPaymentType();
    }

}
