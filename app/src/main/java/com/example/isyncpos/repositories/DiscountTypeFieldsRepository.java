package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.DiscountTypeFieldsDAO;
import com.example.isyncpos.entity.DiscountTypeFields;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DiscountTypeFieldsRepository {

    private DiscountTypeFieldsDAO discountTypeFieldsDAO;

    public DiscountTypeFieldsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        discountTypeFieldsDAO = posDatabase.discountTypeFieldsDAO();
    }

    public void insert(DiscountTypeFields discountTypeFields){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                discountTypeFieldsDAO.insert(discountTypeFields);
            }
        });
    }

    public Void delete(int discountTypeId) throws ExecutionException, InterruptedException {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return discountTypeFieldsDAO.delete(discountTypeId);
            }
        };
        Future<Void> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<DiscountTypeFields> fetchByDiscountTypeId(int discountTypeId) throws ExecutionException, InterruptedException {
        Callable<List<DiscountTypeFields>> callable = new Callable<List<DiscountTypeFields>>() {
            @Override
            public List<DiscountTypeFields> call() throws Exception {
                return discountTypeFieldsDAO.fetchByDiscountTypeId(discountTypeId);
            }
        };
        Future<List<DiscountTypeFields>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
