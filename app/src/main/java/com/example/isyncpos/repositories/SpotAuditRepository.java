package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.SpotAuditDAO;
import com.example.isyncpos.entity.SpotAudit;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SpotAuditRepository {

    private SpotAuditDAO spotAuditDAO;
    private LiveData<List<SpotAudit>> toSyncSpotAuditLiveData;

    public SpotAuditRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        spotAuditDAO = posDatabase.spotAuditDAO();
        toSyncSpotAuditLiveData = spotAuditDAO.fetchCompleteSpotAuditToSync();
    }

    public Long insert(SpotAudit spotAudit) throws ExecutionException, InterruptedException {
        Callable<Long> callable = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return spotAuditDAO.insert(spotAudit);
            }
        };
        Future<Long> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void update(SpotAudit spotAudit){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                spotAuditDAO.update(spotAudit);
            }
        });
    }

    public SpotAudit fetchById(int spotAuditId) throws ExecutionException, InterruptedException {
        Callable<SpotAudit> callable = new Callable<SpotAudit>() {
            @Override
            public SpotAudit call() throws Exception {
                return spotAuditDAO.fetchById(spotAuditId);
            }
        };
        Future<SpotAudit> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<SpotAudit> fetchSpotAuditToCutOff() throws ExecutionException, InterruptedException {
        Callable<List<SpotAudit>> callable = new Callable<List<SpotAudit>>() {
            @Override
            public List<SpotAudit> call() throws Exception {
                return spotAuditDAO.fetchSpotAuditToCutOff();
            }
        };
        Future<List<SpotAudit>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<SpotAudit>> fetchCompleteSpotAuditToSync(){
        return toSyncSpotAuditLiveData;
    }

    public void updateSpotAuditSentToServer(int spotAuditId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                spotAuditDAO.updateSpotAuditSentToServer(spotAuditId);
            }
        });
    }

}
