package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.SyncDAO;
import com.example.isyncpos.entity.Sync;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SyncRepository {

    private SyncDAO syncDAO;
    private LiveData<List<Sync>> syncLiveData;

    public SyncRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        syncDAO = posDatabase.syncDAO();
        syncLiveData = syncDAO.fetchAll();
    }

    public void insert(Sync sync){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                syncDAO.insert(sync);
            }
        });
    }

    public void update(Sync sync){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                syncDAO.update(sync);
            }
        });
    }

    public List<Sync> fetchUnfinish() throws ExecutionException, InterruptedException {
        Callable<List<Sync>> callable = new Callable<List<Sync>>() {
            @Override
            public List<Sync> call() throws Exception {
                return syncDAO.fetchUnfinish();
            }
        };
        Future<List<Sync>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<Sync>> fetchAll(){
        return syncLiveData;
    }

    public Sync fetchById(int id) throws ExecutionException, InterruptedException {
        Callable<Sync> callable = new Callable<Sync>() {
            @Override
            public Sync call() throws Exception {
                return syncDAO.fetchById(id);
            }
        };
        Future<Sync> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<Sync> fetchSyncList() throws ExecutionException, InterruptedException {
        Callable<List<Sync>> callable = new Callable<List<Sync>>() {
            @Override
            public List<Sync> call() throws Exception {
                return syncDAO.fetchSyncList();
            }
        };
        Future<List<Sync>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
