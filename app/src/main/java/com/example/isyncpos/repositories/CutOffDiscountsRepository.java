package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.CutOffDiscountsDAO;
import com.example.isyncpos.entity.CutOffDiscounts;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CutOffDiscountsRepository {

    private CutOffDiscountsDAO cutOffDiscountsDAO;
    private LiveData<List<CutOffDiscounts>> toSyncListCutOffDiscountsLiveData;

    public CutOffDiscountsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        cutOffDiscountsDAO = posDatabase.cutOffDiscountsDAO();
        toSyncListCutOffDiscountsLiveData = cutOffDiscountsDAO.fetchCompleteCutOffDiscountsToSync();
    }

    public void insert(CutOffDiscounts cutOffDiscounts){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cutOffDiscountsDAO.insert(cutOffDiscounts);
            }
        });
    }

    public void update(CutOffDiscounts cutOffDiscounts){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cutOffDiscountsDAO.update(cutOffDiscounts);
            }
        });
    }

    public List<CutOffDiscounts> fetchByCutOffId(int cutOffId) throws ExecutionException, InterruptedException {
        Callable<List<CutOffDiscounts>> callable = new Callable<List<CutOffDiscounts>>() {
            @Override
            public List<CutOffDiscounts> call() throws Exception {
                return cutOffDiscountsDAO.fetchByCutOffId(cutOffId);
            }
        };
        Future<List<CutOffDiscounts>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<CutOffDiscounts>> fetchCompleteCutOffDiscountsToSync(){
        return toSyncListCutOffDiscountsLiveData;
    }

    public void updateCutOffDiscountsSentToServer(int cutOffDiscountId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cutOffDiscountsDAO.updateCutOffDiscountsSentToServer(cutOffDiscountId);
            }
        });
    }

}
