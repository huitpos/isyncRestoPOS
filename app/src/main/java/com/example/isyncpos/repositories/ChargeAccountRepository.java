package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.ChargeAccountDAO;
import com.example.isyncpos.entity.ChargeAccount;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ChargeAccountRepository {

    private ChargeAccountDAO chargeAccountDAO;

    public ChargeAccountRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        chargeAccountDAO = posDatabase.chargeAccountDAO();
    }

    public void insert(ChargeAccount chargeAccount){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                chargeAccountDAO.insert(chargeAccount);
            }
        });
    }

    public void update(ChargeAccount chargeAccount){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                chargeAccountDAO.update(chargeAccount);
            }
        });
    }

    public ChargeAccount fetchById(int id) throws ExecutionException, InterruptedException {
        Callable<ChargeAccount> callable = new Callable<ChargeAccount>() {
            @Override
            public ChargeAccount call() throws Exception {
                return chargeAccountDAO.fetchById(id);
            }
        };
        Future<ChargeAccount> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<ChargeAccount> fetchAll() throws ExecutionException, InterruptedException {
        Callable<List<ChargeAccount>> callable = new Callable<List<ChargeAccount>>() {
            @Override
            public List<ChargeAccount> call() throws Exception {
                return chargeAccountDAO.fetchAll();
            }
        };
        Future<List<ChargeAccount>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public ChargeAccount fetchByName(String name) throws ExecutionException, InterruptedException {
        Callable<ChargeAccount> callable = new Callable<ChargeAccount>() {
            @Override
            public ChargeAccount call() throws Exception {
                return chargeAccountDAO.fetchByName(name);
            }
        };
        Future<ChargeAccount> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
