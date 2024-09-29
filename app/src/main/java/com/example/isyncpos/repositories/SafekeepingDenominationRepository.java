package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.SafekeepingDenominationDAO;
import com.example.isyncpos.entity.SafekeepingDenomination;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SafekeepingDenominationRepository {

    private SafekeepingDenominationDAO safekeepingDenominationDAO;
    private LiveData<List<SafekeepingDenomination>> safekeepingDenominationLiveData;

    public SafekeepingDenominationRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        safekeepingDenominationDAO = posDatabase.safekeepingDenominationDAO();
        safekeepingDenominationLiveData = safekeepingDenominationDAO.fetchCompleteSafekeepingDenominationToSync();
    }

    public void insert(SafekeepingDenomination safekeepingDenomination){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                safekeepingDenominationDAO.insert(safekeepingDenomination);
            }
        });
    }

    public void update(SafekeepingDenomination safekeepingDenomination){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                safekeepingDenominationDAO.update(safekeepingDenomination);
            }
        });
    }

    public void updateSafekeepingDenominationSentToServer(int safekeepingDenominationId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                safekeepingDenominationDAO.updateSafekeepingDenominationSentToServer(safekeepingDenominationId);
            }
        });
    }

    public List<SafekeepingDenomination> fetchSafekeepingDenominationBySafekeepingId(int safekeepingId) throws ExecutionException, InterruptedException {
        Callable<List<SafekeepingDenomination>> callable = new Callable<List<SafekeepingDenomination>>() {
            @Override
            public List<SafekeepingDenomination> call() throws Exception {
                return safekeepingDenominationDAO.fetchSafekeepingDenominationBySafekeepingId(safekeepingId);
            }
        };
        Future<List<SafekeepingDenomination>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<SafekeepingDenomination>> fetchCompleteSafekeepingDenominationToSync(){
        return safekeepingDenominationLiveData;
    }

}
