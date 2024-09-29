package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.CutOffDAO;
import com.example.isyncpos.entity.CutOff;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CutOffRepository {

    private POSDatabase posDatabase;
    private CutOffDAO cutOffDAO;
    private LiveData<List<CutOff>> toSyncCutOffLiveData;
    private LiveData<List<CutOff>> listCutOffReprintLiveData;

    public CutOffRepository(Application application) {
        posDatabase = POSDatabase.getInstance(application);
        cutOffDAO = posDatabase.cutOffDAO();
        toSyncCutOffLiveData = cutOffDAO.fetchCompleteCutOffToSync();
        listCutOffReprintLiveData = cutOffDAO.fetchCutOffForTodayToReprint();
    }

    public Long insert(CutOff cutOff) throws ExecutionException, InterruptedException {
        Callable<Long> callable = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return cutOffDAO.insert(cutOff);
            }
        };
        Future<Long> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void update(CutOff cutOff){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cutOffDAO.update(cutOff);
            }
        });
    }

    public void updateCutOffSentToServer(int cutOffId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cutOffDAO.updateCutOffSentToServer(cutOffId);
            }
        });
    }

    public List<CutOff> fetchCutOffToEndOfDay() throws ExecutionException, InterruptedException {
        Callable<List<CutOff>> callable = new Callable<List<CutOff>>() {
            @Override
            public List<CutOff> call() throws Exception {
                return cutOffDAO.fetchCutOffToEndOfDay();
            }
        };
        Future<List<CutOff>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }


    public List<CutOff> fetchCompleteCutOffToUpload() throws ExecutionException, InterruptedException {
        Callable<List<CutOff>> callable = new Callable<List<CutOff>>() {
            @Override
            public List<CutOff> call() throws Exception {
                return cutOffDAO.fetchCompleteCutOffToUpload();
            }
        };
        Future<List<CutOff>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public CutOff fetchLastCutOffInformation() throws ExecutionException, InterruptedException {
        Callable<CutOff> callable = new Callable<CutOff>() {
            @Override
            public CutOff call() throws Exception {
                return cutOffDAO.fetchLastCutOffInformation();
            }
        };
        Future<CutOff> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public CutOff fetchLastCutOffForEndOfDayInformation() throws ExecutionException, InterruptedException {
        Callable<CutOff> callable = new Callable<CutOff>() {
            @Override
            public CutOff call() throws Exception {
                return cutOffDAO.fetchLastCutOffForEndOfDayInformation();
            }
        };
        Future<CutOff> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public CutOff fetchCutOffInformationById(int cutOffId) throws ExecutionException, InterruptedException {
        Callable<CutOff> callable = new Callable<CutOff>() {
            @Override
            public CutOff call() throws Exception {
                return cutOffDAO.fetchCutOffInformationById(cutOffId);
            }
        };
        Future<CutOff> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<String> checkForEndOfDayProcess() throws ExecutionException, InterruptedException {
        Callable<List<String>> callable = new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                return cutOffDAO.checkForEndOfDayProcess();
            }
        };
        Future<List<String>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<CutOff> fetchCutOffToEndOfDayByDate(String today) throws ExecutionException, InterruptedException {
        Callable<List<CutOff>> callable = new Callable<List<CutOff>>() {
            @Override
            public List<CutOff> call() throws Exception {
                return cutOffDAO.fetchCutOffToEndOfDayByDate(today);
            }
        };
        Future<List<CutOff>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<CutOff>> fetchCompleteCutOffToSync(){
        return toSyncCutOffLiveData;
    }

    public LiveData<List<CutOff>> fetchCutOffForTodayToReprint(){
        return listCutOffReprintLiveData;
    }

}
