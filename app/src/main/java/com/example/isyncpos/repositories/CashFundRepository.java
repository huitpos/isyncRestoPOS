package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.CashFundDAO;
import com.example.isyncpos.entity.CashFund;
import com.example.isyncpos.handler.POSDatabase;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CashFundRepository {

    private CashFundDAO cashFundDAO;
    private LiveData<List<CashFund>> toSyncCashFundLiveData;

    public CashFundRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        cashFundDAO = posDatabase.cashFundDAO();
        toSyncCashFundLiveData = cashFundDAO.fetchCompleteCashFundToSync();
    }

    public Long insert(CashFund cashFund) throws ExecutionException, InterruptedException {
        Callable<Long> callable = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return cashFundDAO.insert(cashFund);
            }
        };
        Future<Long> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void update(CashFund cashFund){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cashFundDAO.update(cashFund);
            }
        });
    }

    public CashFund fetchById(int cashFundId) throws ExecutionException, InterruptedException {
        Callable<CashFund> callable = new Callable<CashFund>() {
            @Override
            public CashFund call() throws Exception {
                return cashFundDAO.fetchById(cashFundId);
            }
        };
        Future<CashFund> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<CashFund>> fetchCompleteCashFundToSync(){
        return toSyncCashFundLiveData;
    }

    public void updateCashFundSentToServer(int cashFundId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cashFundDAO.updateCashFundSentToServer(cashFundId);
            }
        });
    }

    public List<CashFund> fetchCashFundToCutOff() throws ExecutionException, InterruptedException {
        Callable<List<CashFund>> callable = new Callable<List<CashFund>>() {
            @Override
            public List<CashFund> call() throws Exception {
                return cashFundDAO.fetchCashFundToCutOff();
            }
        };
        Future<List<CashFund>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<CashFund> fetchCasFundToEndOfDay() throws ExecutionException, InterruptedException {
        Callable<List<CashFund>> callable = new Callable<List<CashFund>>() {
            @Override
            public List<CashFund> call() throws Exception {
                return cashFundDAO.fetchCasFundToEndOfDay();
            }
        };
        Future<List<CashFund>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public CashFund checkForCashFund() throws ExecutionException, InterruptedException {
        Callable<CashFund> callable = new Callable<CashFund>() {
            @Override
            public CashFund call() throws Exception {
                return cashFundDAO.checkForCashFund();
            }
        };
        Future<CashFund> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
