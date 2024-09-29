package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.isyncpos.dao.DiscountsDAO;
import com.example.isyncpos.entity.Discounts;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DiscountsRepository {

    private DiscountsDAO discountsDAO;
    private LiveData<List<Discounts>> toSyncDiscountsLivaData;

    public DiscountsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        discountsDAO = posDatabase.discountsDAO();
        toSyncDiscountsLivaData = discountsDAO.fetchCompleteDiscountsToSync();
    }

    public Long insert(Discounts discounts) throws ExecutionException, InterruptedException {
        Callable<Long> callable = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return discountsDAO.insert(discounts);
            }
        };
        Future<Long> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void update(Discounts discounts){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                discountsDAO.update(discounts);
            }
        });
    }

    public List<Discounts> fetchDiscountsByTransactionId(int transactionId) throws ExecutionException, InterruptedException {
        Callable<List<Discounts>> callable = new Callable<List<Discounts>>() {
            @Override
            public List<Discounts> call() throws Exception {
                return discountsDAO.fetchDiscountsByTransactionId(transactionId);
            }
        };
        Future<List<Discounts>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<Discounts> fetchCompleteDiscountsToUpload() throws ExecutionException, InterruptedException {
        Callable<List<Discounts>> callable = new Callable<List<Discounts>>() {
            @Override
            public List<Discounts> call() throws Exception {
                return discountsDAO.fetchCompleteDiscountsToUpload();
            }
        };
        Future<List<Discounts>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Discounts fetchDiscountByDiscountId(int discountId) throws ExecutionException, InterruptedException {
        Callable<Discounts> callable = new Callable<Discounts>() {
            @Override
            public Discounts call() throws Exception {
                return discountsDAO.fetchDiscountByDiscountId(discountId);
            }
        };
        Future<Discounts> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<Discounts> fetchDiscountsByTransactionIdWithVoid(int transactionId) throws ExecutionException, InterruptedException {
        Callable<List<Discounts>> callable = new Callable<List<Discounts>>() {
            @Override
            public List<Discounts> call() throws Exception {
                return discountsDAO.fetchDiscountsByTransactionIdWithVoid(transactionId);
            }
        };
        Future<List<Discounts>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void updateDiscountSentToServer(int discountId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                discountsDAO.updateDiscountSentToServer(discountId);
            }
        });
    }

    public LiveData<List<Discounts>> fetchCompleteDiscountsToSync(){
        return toSyncDiscountsLivaData;
    }

}
