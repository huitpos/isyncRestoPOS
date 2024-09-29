package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.SafekeepingDAO;
import com.example.isyncpos.entity.Safekeeping;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SafekeepingRepository {

    private SafekeepingDAO safekeepingDAO;
    private LiveData<List<Safekeeping>> safekeepingListLiveData;

    public SafekeepingRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        safekeepingDAO = posDatabase.safekeepingDAO();
        safekeepingListLiveData = safekeepingDAO.fetchCompleteSafekeepingToSync();
    }

    public Long insert(Safekeeping safekeeping) throws ExecutionException, InterruptedException {
        Callable<Long> callable = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return safekeepingDAO.insert(safekeeping);
            }
        };
        Future<Long> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void update(Safekeeping safekeeping){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                safekeepingDAO.update(safekeeping);
            }
        });
    }

    public void updateSafekeepingSentToServer(int safeKeepingId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                safekeepingDAO.updateSafekeepingSentToServer(safeKeepingId);
            }
        });
    }

    public Safekeeping fetchById(int safekeepingId) throws ExecutionException, InterruptedException {
        Callable<Safekeeping> callable = new Callable<Safekeeping>() {
            @Override
            public Safekeeping call() throws Exception {
                return safekeepingDAO.fetchById(safekeepingId);
            }
        };
        Future<Safekeeping> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Double sumAllSafekeeping() throws ExecutionException, InterruptedException {
        Callable<Double> callable = new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return safekeepingDAO.sumAllSafekeeping();
            }
        };
        Future<Double> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<Safekeeping> fetchSafekeepingToCutOff() throws ExecutionException, InterruptedException {
        Callable<List<Safekeeping>> callable = new Callable<List<Safekeeping>>() {
            @Override
            public List<Safekeeping> call() throws Exception {
                return safekeepingDAO.fetchSafekeepingToCutOff();
            }
        };
        Future<List<Safekeeping>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<Safekeeping> fetchSafekeepingToEndOfDay() throws ExecutionException, InterruptedException {
        Callable<List<Safekeeping>> callable = new Callable<List<Safekeeping>>() {
            @Override
            public List<Safekeeping> call() throws Exception {
                return safekeepingDAO.fetchSafekeepingToEndOfDay();
            }
        };
        Future<List<Safekeeping>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<Safekeeping>> fetchCompleteSafekeepingToSync(){
        return safekeepingListLiveData;
    }

}
