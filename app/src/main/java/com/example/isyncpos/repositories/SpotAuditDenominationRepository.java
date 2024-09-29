package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.SpotAuditDenominationDAO;
import com.example.isyncpos.entity.SpotAuditDenomination;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SpotAuditDenominationRepository {

    private SpotAuditDenominationDAO spotAuditDenominationDAO;
    private LiveData<List<SpotAuditDenomination>> toSyncSpotAuditDenominationLiveData;

    public SpotAuditDenominationRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        spotAuditDenominationDAO = posDatabase.spotAuditDenominationDAO();
        toSyncSpotAuditDenominationLiveData = spotAuditDenominationDAO.fetchCompleteSpotAuditDenominationToSync();
    }

    public void insert(SpotAuditDenomination spotAuditDenomination){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                spotAuditDenominationDAO.insert(spotAuditDenomination);
            }
        });
    }

    public List<SpotAuditDenomination> fetchBySpotAuditId(int spotAuditId) throws ExecutionException, InterruptedException {
        Callable<List<SpotAuditDenomination>> callable = new Callable<List<SpotAuditDenomination>>() {
            @Override
            public List<SpotAuditDenomination> call() throws Exception {
                return spotAuditDenominationDAO.fetchBySpotAuditId(spotAuditId);
            }
        };
        Future<List<SpotAuditDenomination>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<SpotAuditDenomination>> fetchCompleteSpotAuditDenominationToSync(){
        return toSyncSpotAuditDenominationLiveData;
    }

    public void updateSpotAuditDenominationSentToServer(int spotAuditDenominationId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                spotAuditDenominationDAO.updateSpotAuditDenominationSentToServer(spotAuditDenominationId);
            }
        });
    }

}
