package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.PaymentTypeFieldsDAO;
import com.example.isyncpos.entity.PaymentTypeFields;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PaymentTypeFieldsRepository {

    private PaymentTypeFieldsDAO paymentTypeFieldsDAO;

    public PaymentTypeFieldsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        paymentTypeFieldsDAO = posDatabase.paymentTypeFieldsDAO();
    }

    public void insert(PaymentTypeFields paymentTypeFields){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                paymentTypeFieldsDAO.insert(paymentTypeFields);
            }
        });
    }

    public Void delete(int paymentTypeId) throws ExecutionException, InterruptedException {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return paymentTypeFieldsDAO.delete(paymentTypeId);
            }
        };
        Future<Void> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<PaymentTypeFields> fetchByPaymentTypeId(int paymentTypeId) throws ExecutionException, InterruptedException {
        Callable<List<PaymentTypeFields>> callable = new Callable<List<PaymentTypeFields>>() {
            @Override
            public List<PaymentTypeFields> call() throws Exception {
                return paymentTypeFieldsDAO.fetchByPaymentTypeId(paymentTypeId);
            }
        };
        Future<List<PaymentTypeFields>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
