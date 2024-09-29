package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.PriceChangeReasonDAO;
import com.example.isyncpos.entity.PriceChangeReason;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PriceChangeReasonRepository {

    private PriceChangeReasonDAO priceChangeReasonDAO;

    public PriceChangeReasonRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        priceChangeReasonDAO = posDatabase.priceChangeReasonDAO();
    }

    public void insert(PriceChangeReason priceChangeReason){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                priceChangeReasonDAO.insert(priceChangeReason);
            }
        });
    }

    public void update(PriceChangeReason priceChangeReason){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                priceChangeReasonDAO.update(priceChangeReason);
            }
        });
    }

    public PriceChangeReason fetchByCoreId(int coreId) throws ExecutionException, InterruptedException {
        Callable<PriceChangeReason> callable = new Callable<PriceChangeReason>() {
            @Override
            public PriceChangeReason call() throws Exception {
                return priceChangeReasonDAO.fetchByCoreId(coreId);
            }
        };
        Future<PriceChangeReason> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<PriceChangeReason> fetchAll() throws ExecutionException, InterruptedException {
        Callable<List<PriceChangeReason>> callable = new Callable<List<PriceChangeReason>>() {
            @Override
            public List<PriceChangeReason> call() throws Exception {
                return priceChangeReasonDAO.fetchAll();
            }
        };
        Future<List<PriceChangeReason>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
