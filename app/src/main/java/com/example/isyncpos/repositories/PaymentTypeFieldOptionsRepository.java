package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.PaymentTypeFieldOptionsDAO;
import com.example.isyncpos.entity.PaymentTypeFieldOptions;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PaymentTypeFieldOptionsRepository {

    private PaymentTypeFieldOptionsDAO paymentTypeFieldOptionsDAO;

    public PaymentTypeFieldOptionsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        paymentTypeFieldOptionsDAO = posDatabase.paymentTypeFieldOptionsDAO();
    }

    public void insert(PaymentTypeFieldOptions paymentTypeFieldOptions){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                paymentTypeFieldOptionsDAO.insert(paymentTypeFieldOptions);
            }
        });
    }

    public Void delete(int paymentTypeId) throws ExecutionException, InterruptedException {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return paymentTypeFieldOptionsDAO.delete(paymentTypeId);
            }
        };
        Future<Void> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<PaymentTypeFieldOptions> fetchByPaymentTypeFieldId(int paymentTypeFieldId) throws ExecutionException, InterruptedException {
        Callable<List<PaymentTypeFieldOptions>> callable = new Callable<List<PaymentTypeFieldOptions>>() {
            @Override
            public List<PaymentTypeFieldOptions> call() throws Exception {
                return paymentTypeFieldOptionsDAO.fetchByPaymentTypeFieldId(paymentTypeFieldId);
            }
        };
        Future<List<PaymentTypeFieldOptions>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
