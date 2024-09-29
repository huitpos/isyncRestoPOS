package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.ProductLocationsDAO;
import com.example.isyncpos.entity.ProductLocations;
import com.example.isyncpos.handler.POSDatabase;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProductLocationsRepository {

    private ProductLocationsDAO productLocationsDAO;

    public ProductLocationsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        productLocationsDAO = posDatabase.productLocationsDAO();
    }

    public void insert(ProductLocations productLocations){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                productLocationsDAO.insert(productLocations);
            }
        });
    }

    public Void delete(int productId) throws ExecutionException, InterruptedException {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return productLocationsDAO.delete(productId);
            }
        };
        Future<Void> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<ProductLocations> fetchProductLocations(int productId) throws ExecutionException, InterruptedException {
        Callable<List<ProductLocations>> callable = new Callable<List<ProductLocations>>() {
            @Override
            public List<ProductLocations> call() throws Exception {
                return productLocationsDAO.fetchProductLocations(productId);
            }
        };
        Future<List<ProductLocations>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
