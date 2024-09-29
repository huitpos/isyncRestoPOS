package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.CutOffPaymentsDAO;
import com.example.isyncpos.entity.CutOffPayments;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CutOffPaymentsRepository {

    private CutOffPaymentsDAO cutOffPaymentsDAO;
    private LiveData<List<CutOffPayments>> toSyncListCutOffPaymentsLiveData;

    public CutOffPaymentsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        cutOffPaymentsDAO = posDatabase.cutOffPaymentsDAO();
        toSyncListCutOffPaymentsLiveData = cutOffPaymentsDAO.fetchCompleteCutOffPaymentsToSync();
    }

    public void insert(CutOffPayments cutOffPayments){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cutOffPaymentsDAO.insert(cutOffPayments);
            }
        });
    }

    public void update(CutOffPayments cutOffPayments){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cutOffPaymentsDAO.update(cutOffPayments);
            }
        });
    }

    public List<CutOffPayments> fetchByCutOffId(int cutOffId) throws ExecutionException, InterruptedException {
        Callable<List<CutOffPayments>> callable = new Callable<List<CutOffPayments>>() {
            @Override
            public List<CutOffPayments> call() throws Exception {
                return cutOffPaymentsDAO.fetchByCutOffId(cutOffId);
            }
        };
        Future<List<CutOffPayments>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<CutOffPayments>> fetchCompleteCutOffPaymentsToSync(){
        return toSyncListCutOffPaymentsLiveData;
    }

    public void updateCutOffPaymentsSentToServer(int cutOffPaymentId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cutOffPaymentsDAO.updateCutOffPaymentsSentToServer(cutOffPaymentId);
            }
        });
    }

}
