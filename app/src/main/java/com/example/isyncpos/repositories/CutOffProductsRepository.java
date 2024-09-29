package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.CutOffProductsDAO;
import com.example.isyncpos.entity.CutOffProducts;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CutOffProductsRepository {

    private CutOffProductsDAO cutOffProductsDAO;
    private LiveData<List<CutOffProducts>> toSyncListCutOffProductsLiveData;

    public CutOffProductsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        cutOffProductsDAO = posDatabase.cutOffProductsDAO();
        toSyncListCutOffProductsLiveData = cutOffProductsDAO.fetchCompleteCutOffProductsToSync();
    }

    public void insert(CutOffProducts cutOffProducts){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cutOffProductsDAO.insert(cutOffProducts);
            }
        });
    }

    public void update(CutOffProducts cutOffProducts){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cutOffProductsDAO.update(cutOffProducts);
            }
        });
    }

    public List<CutOffProducts> fetchCutOffProductsToEndOfDay() throws ExecutionException, InterruptedException {
        Callable<List<CutOffProducts>> callable = new Callable<List<CutOffProducts>>() {
            @Override
            public List<CutOffProducts> call() throws Exception {
                return cutOffProductsDAO.fetchCutOffProductsToEndOfDay();
            }
        };
        Future<List<CutOffProducts>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<CutOffProducts>> fetchCompleteCutOffProductsToSync(){
        return toSyncListCutOffProductsLiveData;
    }

    public void updateCutOffProductsSentToServer(int cutOffProductId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cutOffProductsDAO.updateCutOffProductsSentToServer(cutOffProductId);
            }
        });
    }

}
