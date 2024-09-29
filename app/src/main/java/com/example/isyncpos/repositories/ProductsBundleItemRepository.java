package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.ProductsBundleItemDAO;
import com.example.isyncpos.entity.ProductsBundleItem;
import com.example.isyncpos.handler.POSDatabase;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProductsBundleItemRepository {

    private ProductsBundleItemDAO productsBundleItemDAO;

    public ProductsBundleItemRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        productsBundleItemDAO = posDatabase.productsBundleItemDAO();
    }

    public void insert(ProductsBundleItem productsBundleItem){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                productsBundleItemDAO.insert(productsBundleItem);
            }
        });
    }

    public void update(ProductsBundleItem productsBundleItem){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                productsBundleItemDAO.update(productsBundleItem);
            }
        });
    }

    public ProductsBundleItem fetchByProductBundle(int productId, int productBundleId) throws ExecutionException, InterruptedException {
        Callable<ProductsBundleItem> callable = new Callable<ProductsBundleItem>() {
            @Override
            public ProductsBundleItem call() throws Exception {
                return productsBundleItemDAO.fetchByProductBundle(productId, productBundleId);
            }
        };
        Future<ProductsBundleItem> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Void removeAllByProductId(int productId) throws ExecutionException, InterruptedException {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return productsBundleItemDAO.removeAllByProductId(productId);
            }
        };
        Future<Void> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
