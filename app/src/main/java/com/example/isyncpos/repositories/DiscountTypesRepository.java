package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.DiscountTypesDAO;
import com.example.isyncpos.entity.DiscountTypes;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DiscountTypesRepository {

    private DiscountTypesDAO discountTypesDAO;
    private LiveData<List<DiscountTypes>> discountTypesLiveData;

    public DiscountTypesRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        discountTypesDAO = posDatabase.discountTypesDAO();
        discountTypesLiveData = discountTypesDAO.fetchAll();
    }

    public void insert(DiscountTypes discountTypes){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                discountTypesDAO.insert(discountTypes);
            }
        });
    }

    public void update(DiscountTypes discountTypes){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                discountTypesDAO.update(discountTypes);
            }
        });
    }

    public DiscountTypes fetchById(int coreId) throws ExecutionException, InterruptedException {
        Callable<DiscountTypes> callable = new Callable<DiscountTypes>() {
            @Override
            public DiscountTypes call() throws Exception {
                return discountTypesDAO.fetchById(coreId);
            }
        };
        Future<DiscountTypes> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<DiscountTypes>> fetchAll(){
        return discountTypesLiveData;
    }

    public List<DiscountTypes> fetchAllDiscountTypeInformation() throws ExecutionException, InterruptedException {
        Callable<List<DiscountTypes>> callable = new Callable<List<DiscountTypes>>() {
            @Override
            public List<DiscountTypes> call() throws Exception {
                return discountTypesDAO.fetchAllDiscountTypeInformation();
            }
        };
        Future<List<DiscountTypes>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
