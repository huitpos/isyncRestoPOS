package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.DiscountTypeFieldOptionsDAO;
import com.example.isyncpos.entity.DiscountTypeFieldOptions;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DiscountTypeFieldOptionsRepository {

    private DiscountTypeFieldOptionsDAO discountTypeFieldOptionsDAO;

    public DiscountTypeFieldOptionsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        discountTypeFieldOptionsDAO = posDatabase.discountTypeFieldOptionsDAO();
    }

    public void insert(DiscountTypeFieldOptions discountTypeFieldOptions){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                discountTypeFieldOptionsDAO.insert(discountTypeFieldOptions);
            }
        });
    }

    public Void delete(int discountTypeId) throws ExecutionException, InterruptedException {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return discountTypeFieldOptionsDAO.delete(discountTypeId);
            }
        };
        Future<Void> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<DiscountTypeFieldOptions> fetchByDiscountTypeFieldId(int discountTypeFieldId) throws ExecutionException, InterruptedException {
        Callable<List<DiscountTypeFieldOptions>> callable = new Callable<List<DiscountTypeFieldOptions>>() {
            @Override
            public List<DiscountTypeFieldOptions> call() throws Exception {
                return discountTypeFieldOptionsDAO.fetchByDiscountTypeFieldId(discountTypeFieldId);
            }
        };
        Future<List<DiscountTypeFieldOptions>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
