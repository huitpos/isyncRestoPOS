package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.PaymentsDAO;
import com.example.isyncpos.entity.Payments;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PaymentsRepository {

    private PaymentsDAO paymentsDAO;
    private LiveData<List<Payments>> toSyncPaymentLiveData;

    public PaymentsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        paymentsDAO = posDatabase.paymentsDAO();
        toSyncPaymentLiveData = paymentsDAO.fetchCompletePaymentToSync();
    }

    public Long insert(Payments payments) throws ExecutionException, InterruptedException {
        Callable<Long> callable = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return paymentsDAO.insert(payments);
            }
        };
        Future<Long> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void update(Payments payments){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                paymentsDAO.update(payments);
            }
        });
    }

    public void remove(Payments payments){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                paymentsDAO.remove(payments);
            }
        });
    }

    public Double fetchTransactionPaymentsSum(int transactionId) throws ExecutionException, InterruptedException {
        Callable<Double> callable = new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return paymentsDAO.fetchTransactionPaymentsSum(transactionId);
            }
        };
        Future<Double> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Double fetchARTransactionPaymentsSum(int transactionId) throws ExecutionException, InterruptedException {
        Callable<Double> callable = new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return paymentsDAO.fetchARTransactionPaymentsSum(transactionId);
            }
        };
        Future<Double> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<Payments> fetchTransactionPayments(int transactionId) throws ExecutionException, InterruptedException {
        Callable<List<Payments>> callable = new Callable<List<Payments>>() {
            @Override
            public List<Payments> call() throws Exception {
                return paymentsDAO.fetchTransactionPayments(transactionId);
            }
        };
        Future<List<Payments>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<Payments> fetchARTransactionPayments(int transactionId) throws ExecutionException, InterruptedException {
        Callable<List<Payments>> callable = new Callable<List<Payments>>() {
            @Override
            public List<Payments> call() throws Exception {
                return paymentsDAO.fetchARTransactionPayments(transactionId);
            }
        };
        Future<List<Payments>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<Payments> fetchCompletePaymentToUpload() throws ExecutionException, InterruptedException {
        Callable<List<Payments>> callable = new Callable<List<Payments>>() {
            @Override
            public List<Payments> call() throws Exception {
                return paymentsDAO.fetchCompletePaymentToUpload();
            }
        };
        Future<List<Payments>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Double fetchTransactionPaymentCashSum(int transactionId) throws ExecutionException, InterruptedException {
        Callable<Double> callable = new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return paymentsDAO.fetchTransactionPaymentCashSum(transactionId);
            }
        };
        Future<Double> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Double fetchTransactionOthersSum(int transactionId) throws ExecutionException, InterruptedException {
        Callable<Double> callable = new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return paymentsDAO.fetchTransactionOthersSum(transactionId);
            }
        };
        Future<Double> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Double fetchARTransactionOthersSum(int transactionId) throws ExecutionException, InterruptedException {
        Callable<Double> callable = new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return paymentsDAO.fetchARTransactionOthersSum(transactionId);
            }
        };
        Future<Double> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<Payments>> fetchCompletePaymentToSync(){
        return toSyncPaymentLiveData;
    }

    public Payments fetchPaymentById(int paymentId) throws ExecutionException, InterruptedException {
        Callable<Payments> callable = new Callable<Payments>() {
            @Override
            public Payments call() throws Exception {
                return paymentsDAO.fetchPaymentById(paymentId);
            }
        };
        Future<Payments> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void updatePaymentSentToServer(int paymentId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                paymentsDAO.updatePaymentSentToServer(paymentId);
            }
        });
    }

    public List<Payments> fetchOtherPaymentsForAR(int transactionId) throws ExecutionException, InterruptedException {
        Callable<List<Payments>> callable = new Callable<List<Payments>>() {
            @Override
            public List<Payments> call() throws Exception {
                return paymentsDAO.fetchOtherPaymentsForAR(transactionId);
            }
        };
        Future<List<Payments>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<Payments> fetchOtherPaymentsForNotAR(int transactionId) throws ExecutionException, InterruptedException {
        Callable<List<Payments>> callable = new Callable<List<Payments>>() {
            @Override
            public List<Payments> call() throws Exception {
                return paymentsDAO.fetchOtherPaymentsForNotAR(transactionId);
            }
        };
        Future<List<Payments>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
