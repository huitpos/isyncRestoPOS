package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.CashFundDenominationDAO;
import com.example.isyncpos.entity.CashFundDenomination;
import com.example.isyncpos.handler.POSDatabase;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CashFundDenominationRepository {

    private CashFundDenominationDAO cashFundDenominationDAO;
    private LiveData<List<CashFundDenomination>> toSyncCashFundDenominationLiveData;

    public CashFundDenominationRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        cashFundDenominationDAO = posDatabase.cashFundDenominationDAO();
        toSyncCashFundDenominationLiveData = cashFundDenominationDAO.fetchCompleteCashFundDenominationToSync();
    }

    public void insert(CashFundDenomination cashFundDenomination){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cashFundDenominationDAO.insert(cashFundDenomination);
            }
        });
    }

    public void update(CashFundDenomination cashFundDenomination){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cashFundDenominationDAO.update(cashFundDenomination);
            }
        });
    }

    public LiveData<List<CashFundDenomination>> fetchCompleteCashFundDenominationToSync(){
        return toSyncCashFundDenominationLiveData;
    }

    public void updateCashFundDenominationSentToServer(int cashFundDenominationId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cashFundDenominationDAO.updateCashFundDenominationSentToServer(cashFundDenominationId);
            }
        });
    }

    public List<CashFundDenomination> fetchByCashFundId(int cashFundId) throws ExecutionException, InterruptedException {
        Callable<List<CashFundDenomination>> callable = new Callable<List<CashFundDenomination>>() {
            @Override
            public List<CashFundDenomination> call() throws Exception {
                return cashFundDenominationDAO.fetchByCashFundId(cashFundId);
            }
        };
        Future<List<CashFundDenomination>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
