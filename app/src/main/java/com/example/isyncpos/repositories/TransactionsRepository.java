package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.TransactionsDAO;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TransactionsRepository {

    private TransactionsDAO transactionsDAO;
    private LiveData<List<Transactions>> resumeTransactionsLiveData;
    private LiveData<List<Transactions>> completeTransactionsLiveData;
    private LiveData<List<Transactions>> toSyncTransactionsLiveData;
    private LiveData<List<Transactions>> completeTransactionsCutOffLiveData;

    public TransactionsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        transactionsDAO = posDatabase.transactionsDAO();
        resumeTransactionsLiveData = transactionsDAO.fetchResumeTransactions();
        completeTransactionsLiveData = transactionsDAO.fetchCompleteTransactions();
        toSyncTransactionsLiveData = transactionsDAO.fetchCompleteTransactionToSync();
        completeTransactionsCutOffLiveData = transactionsDAO.fetchCompleteTransactionsCutOff();
    }

    public Long insert(Transactions transactions) throws ExecutionException, InterruptedException {
        Callable<Long> callable = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return transactionsDAO.insert(transactions);
            }
        };
        Future<Long> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Void update(Transactions transactions) throws ExecutionException, InterruptedException {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return transactionsDAO.update(transactions);
            }
        };
        Future<Void> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void updateTransactionSentToServer(int transactionId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                transactionsDAO.updateTransactionSentToServer(transactionId);
            }
        });
    }

    public void updateRecomputeTransaction(int transactionId, double change, double grossSales, double netSales, double discountAmount, double vatableSales, double vatExemptSales, double vatAmount, double serviceCharge, double totalUnitCost, double totalVoidAmount, double tenderAmount, double vatExpense, double totalQuantity, double totalVoidQty, double totalReturnAmount, double totalCashAmount, double totalZeroRatedAmount){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                transactionsDAO.updateRecomputeTransaction(transactionId, change, grossSales, netSales, discountAmount, vatableSales, vatExemptSales, vatAmount, serviceCharge, totalUnitCost, totalVoidAmount, tenderAmount, vatExpense,  totalQuantity, totalVoidQty, totalReturnAmount, totalCashAmount, totalZeroRatedAmount);
            }
        });
    }

    public void updateRecomputeARTransaction(int transactionId, double change, double tenderAmount, double totalCashAmount){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                transactionsDAO.updateRecomputeARTransaction(transactionId, change, tenderAmount, totalCashAmount);
            }
        });
    }

    public void completeTransaction(int transactionId, String completedAt, int cashierId, String cashierName, String receiptNumber, int isReturn, double totalCashAmount){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                transactionsDAO.completeTransaction(transactionId, completedAt, cashierId, cashierName, receiptNumber, isReturn, totalCashAmount);
            }
        });
    }

    public void completeARTransaction(int transactionId, int cashierId, String cashierName, int isAccountReceivableRedeem, String accountReceivableRedeemAt, String receiptNumber){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                transactionsDAO.completeARTransaction(transactionId, cashierId, cashierName, isAccountReceivableRedeem, accountReceivableRedeemAt, receiptNumber);
            }
        });
    }

    public void resumeTransaction(int transactionId, String customerName){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                transactionsDAO.resumeTransaction(transactionId, customerName);
            }
        });
    }

    public void pauseTransaction(int transactionId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                transactionsDAO.pauseTransaction(transactionId);
            }
        });
    }

    public void updateTransactionCustomerName(int transactionId, String customerName){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                transactionsDAO.updateTransactionCustomerName(transactionId, customerName);
            }
        });
    }

    public void updateTransactionRemarks(int transactionId, String remarks){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                transactionsDAO.updateTransactionRemarks(transactionId, remarks);
            }
        });
    }

    public void backoutTransaction(int transactionId, int isBackoutId, String backoutBy, String backoutAt){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                transactionsDAO.backoutTransaction(transactionId, isBackoutId, backoutBy, backoutAt);
            }
        });
    }

    public Transactions fetchTransaction(int transactionId) throws ExecutionException, InterruptedException {
        Callable<Transactions> callable = new Callable<Transactions>() {
            @Override
            public Transactions call() throws Exception {
                return transactionsDAO.fetchTransaction(transactionId);
            }
        };
        Future<Transactions> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<Transactions>> fetchResumeTransactions() {
        return resumeTransactionsLiveData;
    }

    public LiveData<List<Transactions>> fetchCompleteTransactions() {
        return completeTransactionsLiveData;
    }

    public LiveData<List<Transactions>> fetchCompleteTransactionToSync() {
        return toSyncTransactionsLiveData;
    }

    public LiveData<List<Transactions>> fetchCompleteTransactionsCutOff(){
        return completeTransactionsCutOffLiveData;
    }

    public List<Transactions> fetchTransactionsToCutOff() throws ExecutionException, InterruptedException {
        Callable<List<Transactions>> callable = new Callable<List<Transactions>>() {
            @Override
            public List<Transactions> call() throws Exception {
                return transactionsDAO.fetchTransactionsToCutOff();
            }
        };
        Future<List<Transactions>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<Transactions> fetchBackoutTransactionsToCutOff() throws ExecutionException, InterruptedException {
        Callable<List<Transactions>> callable = new Callable<List<Transactions>>() {
            @Override
            public List<Transactions> call() throws Exception {
                return transactionsDAO.fetchBackoutTransactionsToCutOff();
            }
        };
        Future<List<Transactions>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<Transactions> fetchResumeTransactionsList() throws ExecutionException, InterruptedException {
        Callable<List<Transactions>> callable = new Callable<List<Transactions>>() {
            @Override
            public List<Transactions> call() throws Exception {
                return transactionsDAO.fetchResumeTransactionsList();
            }
        };
        Future<List<Transactions>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Double sumAllCashTransaction() throws ExecutionException, InterruptedException {
        Callable<Double> callable = new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return transactionsDAO.sumAllCashTransaction();
            }
        };
        Future<Double> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Double sumAllTotalCashTransaction() throws ExecutionException, InterruptedException {
        Callable<Double> callable = new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return transactionsDAO.sumAllTotalCashTransaction();
            }
        };
        Future<Double> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<Transactions> fetchCompleteTransactionToUpload() throws ExecutionException, InterruptedException {
        Callable<List<Transactions>> callable = new Callable<List<Transactions>>() {
            @Override
            public List<Transactions> call() throws Exception {
                return transactionsDAO.fetchCompleteTransactionToUpload();
            }
        };
        Future<List<Transactions>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void updateTransactionAR(int transactionId, int isAccountReceivable){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                transactionsDAO.updateTransactionAR(transactionId, isAccountReceivable);
            }
        });
    }

}
