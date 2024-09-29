package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.PaymentOtherInformationsDAO;
import com.example.isyncpos.entity.PaymentOtherInformations;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PaymentOtherInformationsRepository {

    private PaymentOtherInformationsDAO paymentOtherInformationsDAO;
    private LiveData<List<PaymentOtherInformations>> paymentOtherInformationsLiveData;

    public PaymentOtherInformationsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        paymentOtherInformationsDAO = posDatabase.paymentOtherInformationsDAO();
        paymentOtherInformationsLiveData = paymentOtherInformationsDAO.fetchCompletePaymentOtherInformationsToSync();
    }

    public void insert(PaymentOtherInformations paymentOtherInformations){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                paymentOtherInformationsDAO.insert(paymentOtherInformations);
            }
        });
    }

    public void update(PaymentOtherInformations paymentOtherInformations){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                paymentOtherInformationsDAO.update(paymentOtherInformations);
            }
        });
    }

    public void updatePaymentOtherInformationSentToServer(int paymentOtherInformationId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                paymentOtherInformationsDAO.updatePaymentOtherInformationSentToServer(paymentOtherInformationId);
            }
        });
    }

    public List<PaymentOtherInformations> fetchByPaymentId(int paymentId) throws ExecutionException, InterruptedException {
        Callable<List<PaymentOtherInformations>> callable = new Callable<List<PaymentOtherInformations>>() {
            @Override
            public List<PaymentOtherInformations> call() throws Exception {
                return paymentOtherInformationsDAO.fetchByPaymentId(paymentId);
            }
        };
        Future<List<PaymentOtherInformations>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<PaymentOtherInformations>> fetchCompletePaymentOtherInformationsToSync(){
        return paymentOtherInformationsLiveData;
    }

    public List<PaymentOtherInformations> fetchPaymentOtherInformationToCutOff() throws ExecutionException, InterruptedException {
        Callable<List<PaymentOtherInformations>> callable = new Callable<List<PaymentOtherInformations>>() {
            @Override
            public List<PaymentOtherInformations> call() throws Exception {
                return paymentOtherInformationsDAO.fetchPaymentOtherInformationToCutOff();
            }
        };
        Future<List<PaymentOtherInformations>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<PaymentOtherInformations> fetchPaymentOtherInformationByTransactionId(int transactionId) throws ExecutionException, InterruptedException {
        Callable<List<PaymentOtherInformations>> callable = new Callable<List<PaymentOtherInformations>>() {
            @Override
            public List<PaymentOtherInformations> call() throws Exception {
                return paymentOtherInformationsDAO.fetchPaymentOtherInformationByTransactionId(transactionId);
            }
        };
        Future<List<PaymentOtherInformations>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
