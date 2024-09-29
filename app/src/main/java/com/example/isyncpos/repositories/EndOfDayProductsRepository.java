package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.EndOfDayProductsDAO;
import com.example.isyncpos.entity.EndOffDayProducts;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EndOfDayProductsRepository {

    private EndOfDayProductsDAO endOfDayProductsDAO;

    public EndOfDayProductsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        endOfDayProductsDAO = posDatabase.endOfDayProductsDAO();
    }

    public void insert(EndOffDayProducts endOffDayProducts){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                endOfDayProductsDAO.insert(endOffDayProducts);
            }
        });
    }

    public void update(EndOffDayProducts endOffDayProducts){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                endOfDayProductsDAO.update(endOffDayProducts);
            }
        });
    }

    public List<EndOffDayProducts> fetchByEndOfDayId(int endOfDayId) throws ExecutionException, InterruptedException {
        Callable<List<EndOffDayProducts>> callable = new Callable<List<EndOffDayProducts>>() {
            @Override
            public List<EndOffDayProducts> call() throws Exception {
                return endOfDayProductsDAO.fetchByEndOfDayId(endOfDayId);
            }
        };
        Future<List<EndOffDayProducts>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void updateEndOffDayProductsSentToServer(int endOfDayProductId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                endOfDayProductsDAO.updateEndOffDayProductsSentToServer(endOfDayProductId);
            }
        });
    }

}
