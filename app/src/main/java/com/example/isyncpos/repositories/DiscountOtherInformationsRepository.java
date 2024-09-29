package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.DiscountOtherInformationsDAO;
import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DiscountOtherInformationsRepository {

    private DiscountOtherInformationsDAO discountOtherInformationsDAO;
    private LiveData<List<DiscountOtherInformations>> discountOtherInformationsLiveData;

    public DiscountOtherInformationsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        discountOtherInformationsDAO = posDatabase.discountOtherInformationsDAO();
        discountOtherInformationsLiveData = discountOtherInformationsDAO.fetchCompleteDiscountOtherInformationsToSync();
    }

    public void insert(DiscountOtherInformations discountOtherInformations){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                discountOtherInformationsDAO.insert(discountOtherInformations);
            }
        });
    }

    public void update(DiscountOtherInformations discountOtherInformations){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                discountOtherInformationsDAO.update(discountOtherInformations);
            }
        });
    }

    public void updateDiscountOtherInformationSentToServer(int discountOtherInformationId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                discountOtherInformationsDAO.updateDiscountOtherInformationSentToServer(discountOtherInformationId);
            }
        });
    }

    public List<DiscountOtherInformations> fetchByTransactionDiscountId(int discountId) throws ExecutionException, InterruptedException {
        Callable<List<DiscountOtherInformations>> callable = new Callable<List<DiscountOtherInformations>>() {
            @Override
            public List<DiscountOtherInformations> call() throws Exception {
                return discountOtherInformationsDAO.fetchByTransactionDiscountId(discountId);
            }
        };
        Future<List<DiscountOtherInformations>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<DiscountOtherInformations>> fetchCompleteDiscountOtherInformationsToSync(){
        return discountOtherInformationsLiveData;
    }

    public List<DiscountOtherInformations> fetchDiscountOtherInformationToCutOff() throws ExecutionException, InterruptedException {
        Callable<List<DiscountOtherInformations>> callable = new Callable<List<DiscountOtherInformations>>() {
            @Override
            public List<DiscountOtherInformations> call() throws Exception {
                return discountOtherInformationsDAO.fetchDiscountOtherInformationToCutOff();
            }
        };
        Future<List<DiscountOtherInformations>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<DiscountOtherInformations> fetchDiscountOtherInformationByTransactionId(int transactionId) throws ExecutionException, InterruptedException {
        Callable<List<DiscountOtherInformations>> callable = new Callable<List<DiscountOtherInformations>>() {
            @Override
            public List<DiscountOtherInformations> call() throws Exception {
                return discountOtherInformationsDAO.fetchDiscountOtherInformationByTransactionId(transactionId);
            }
        };
        Future<List<DiscountOtherInformations>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
