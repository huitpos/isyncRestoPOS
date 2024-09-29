package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.DiscountDetailsDAO;
import com.example.isyncpos.entity.DiscountDetails;
import com.example.isyncpos.entity.Discounts;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DiscountDetailsRepository {

    private DiscountDetailsDAO discountDetailsDAO;
    private LiveData<List<DiscountDetails>> toSyncDiscountDetailsLiveData;

    public DiscountDetailsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        discountDetailsDAO = posDatabase.discountDetailsDAO();
        toSyncDiscountDetailsLiveData = discountDetailsDAO.fetchCompleteDiscountDetailsToSync();
    }

    public void insert(DiscountDetails discountDetails){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                discountDetailsDAO.insert(discountDetails);
            }
        });
    }

    public Long insertTakeOrderDiscountDetails(DiscountDetails discountDetails) throws ExecutionException, InterruptedException {
        Callable<Long> callable = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return discountDetailsDAO.insertTakeOrderDiscountDetails(discountDetails);
            }
        };
        Future<Long> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void updateDiscountDetailsSentToServer(int discountDetailsId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                discountDetailsDAO.updateDiscountDetailsSentToServer(discountDetailsId);
            }
        });
    }

    public void update(DiscountDetails discountDetails){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                discountDetailsDAO.update(discountDetails);
            }
        });
    }

    public List<DiscountDetails> fetchDiscountDetailsByTransactionId(int transactionId) throws ExecutionException, InterruptedException {
        Callable<List<DiscountDetails>> callable = new Callable<List<DiscountDetails>>() {
            @Override
            public List<DiscountDetails> call() throws Exception {
                return discountDetailsDAO.fetchDiscountDetailsByTransactionId(transactionId);
            }
        };
        Future<List<DiscountDetails>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<DiscountDetails> fetchCompleteDiscountDetailsToUpload() throws ExecutionException, InterruptedException {
        Callable<List<DiscountDetails>> callable = new Callable<List<DiscountDetails>>() {
            @Override
            public List<DiscountDetails> call() throws Exception {
                return discountDetailsDAO.fetchCompleteDiscountDetailsToUpload();
            }
        };
        Future<List<DiscountDetails>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<DiscountDetails> fetchDiscountDetailsByTransactionIdWithVoid(int transactionId) throws ExecutionException, InterruptedException {
        Callable<List<DiscountDetails>> callable = new Callable<List<DiscountDetails>>() {
            @Override
            public List<DiscountDetails> call() throws Exception {
                return discountDetailsDAO.fetchDiscountDetailsByTransactionIdWithVoid(transactionId);
            }
        };
        Future<List<DiscountDetails>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<DiscountDetails> fetchDiscountDetailsByDiscountIdWithVoid(int discountId) throws ExecutionException, InterruptedException {
        Callable<List<DiscountDetails>> callable = new Callable<List<DiscountDetails>>() {
            @Override
            public List<DiscountDetails> call() throws Exception {
                return discountDetailsDAO.fetchDiscountDetailsByDiscountIdWithVoid(discountId);
            }
        };
        Future<List<DiscountDetails>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<DiscountDetails> fetchDiscountDetailsByDiscountId(int discountId) throws ExecutionException, InterruptedException {
        Callable<List<DiscountDetails>> callable = new Callable<List<DiscountDetails>>() {
            @Override
            public List<DiscountDetails> call() throws Exception {
                return discountDetailsDAO.fetchDiscountDetailsByDiscountId(discountId);
            }
        };
        Future<List<DiscountDetails>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<DiscountDetails>> fetchCompleteDiscountDetailsToSync(){
        return toSyncDiscountDetailsLiveData;
    }

}
