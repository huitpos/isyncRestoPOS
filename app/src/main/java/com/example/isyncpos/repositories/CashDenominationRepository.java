package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.CashDenominationDAO;
import com.example.isyncpos.entity.CashDenomination;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CashDenominationRepository {

    private CashDenominationDAO cashDenominationDAO;
    private LiveData<List<CashDenomination>> cashDenominationLiveData;

    public CashDenominationRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        cashDenominationDAO = posDatabase.cashDenominationDAO();
        cashDenominationLiveData = cashDenominationDAO.fetchAll();
    }

    public void insert(CashDenomination cashDenomination){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cashDenominationDAO.insert(cashDenomination);
            }
        });
    }

    public void update(CashDenomination cashDenomination){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cashDenominationDAO.update(cashDenomination);
            }
        });
    }

    public LiveData<List<CashDenomination>> fetchCashDenomination(){
        return cashDenominationLiveData;
    }

    public CashDenomination fetchById(int id) throws ExecutionException, InterruptedException {
        Callable<CashDenomination> callable = new Callable<CashDenomination>() {
            @Override
            public CashDenomination call() throws Exception {
                return cashDenominationDAO.fetchById(id);
            }
        };
        Future<CashDenomination> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
