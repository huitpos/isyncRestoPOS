package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.PaymentTypesDAO;
import com.example.isyncpos.entity.PaymentTypes;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PaymentTypesRepository {

    private PaymentTypesDAO paymentTypesDAO;
    private LiveData<List<PaymentTypes>> paymentTypesLiveData;
    private LiveData<List<PaymentTypes>> paymentTypesWithOutARLiveData;

    public PaymentTypesRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        paymentTypesDAO = posDatabase.paymentTypesDAO();
        paymentTypesLiveData = paymentTypesDAO.fetchAll();
        paymentTypesWithOutARLiveData = paymentTypesDAO.fetchAllWithOutAR();
    }

    public void insert(PaymentTypes paymentTypes){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                paymentTypesDAO.insert(paymentTypes);
            }
        });
    }

    public void update(PaymentTypes paymentTypes){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                paymentTypesDAO.update(paymentTypes);
            }
        });
    }

    public LiveData<List<PaymentTypes>> fetchAll(){
        return paymentTypesLiveData;
    }

    public LiveData<List<PaymentTypes>> fetchAllWithOutAR(){
        return paymentTypesWithOutARLiveData;
    }

    public PaymentTypes fetchById(int id) throws ExecutionException, InterruptedException {
        Callable<PaymentTypes> callable = new Callable<PaymentTypes>() {
            @Override
            public PaymentTypes call() throws Exception {
                return paymentTypesDAO.fetchById(id);
            }
        };
        Future<PaymentTypes> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<PaymentTypes> fetchAllPaymentType() throws ExecutionException, InterruptedException {
        Callable<List<PaymentTypes>> callable = new Callable<List<PaymentTypes>>() {
            @Override
            public List<PaymentTypes> call() throws Exception {
                return paymentTypesDAO.fetchAllPaymentType();
            }
        };
        Future<List<PaymentTypes>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
