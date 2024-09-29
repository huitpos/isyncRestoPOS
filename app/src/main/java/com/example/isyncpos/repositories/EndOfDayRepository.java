package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.EndOfDayDAO;
import com.example.isyncpos.entity.EndOfDay;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EndOfDayRepository {

    private POSDatabase posDatabase;
    private EndOfDayDAO endOfDayDAO;
    private LiveData<List<EndOfDay>> toSyncEndOfDayLiveData;
    private LiveData<List<EndOfDay>> listEndOfDayReprintLiveData;

    public EndOfDayRepository(Application application){
        posDatabase = POSDatabase.getInstance(application);
        endOfDayDAO = posDatabase.endOfDayDAO();
        toSyncEndOfDayLiveData = endOfDayDAO.fetchCompleteEndOfDayToSync();
        listEndOfDayReprintLiveData = endOfDayDAO.fetchEndOfDayForTodayToReprint();
    }

    public Long insert(EndOfDay endOfDay) throws ExecutionException, InterruptedException {
        Callable<Long> callable = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return endOfDayDAO.insert(endOfDay);
            }
        };
        Future<Long> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void update(EndOfDay endOfDay){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                endOfDayDAO.update(endOfDay);
            }
        });
    }

    public void updateEndOfDaySentToServer(int endOfDayId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                endOfDayDAO.updateEndOfDaySentToServer(endOfDayId);
            }
        });
    }

    public EndOfDay fetchEndOfDayInformationById(int endOfDayId) throws ExecutionException, InterruptedException {
        Callable<EndOfDay> callable = new Callable<EndOfDay>() {
            @Override
            public EndOfDay call() throws Exception {
                return endOfDayDAO.fetchEndOfDayInformationById(endOfDayId);
            }
        };
        Future<EndOfDay> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public EndOfDay fetchEndOfDayNow(String date) throws ExecutionException, InterruptedException {
        Callable<EndOfDay> callable = new Callable<EndOfDay>() {
            @Override
            public EndOfDay call() throws Exception {
                return endOfDayDAO.fetchEndOfDayNow(date);
            }
        };
        Future<EndOfDay> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<EndOfDay> fetchCompleteEndOfDayToUpload() throws ExecutionException, InterruptedException {
        Callable<List<EndOfDay>> callable = new Callable<List<EndOfDay>>() {
            @Override
            public List<EndOfDay> call() throws Exception {
                return endOfDayDAO.fetchCompleteEndOfDayToUpload();
            }
        };
        Future<List<EndOfDay>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<EndOfDay>> fetchCompleteEndOfDayToSync(){
        return toSyncEndOfDayLiveData;
    }

    public LiveData<List<EndOfDay>> fetchEndOfDayForTodayToReprint(){
        return listEndOfDayReprintLiveData;
    }

}
