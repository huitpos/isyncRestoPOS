package com.example.isyncpos.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.isyncpos.POSApplication;
import com.example.isyncpos.dao.ProductsDAO;
import com.example.isyncpos.entity.Products;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProductsRepository {

    private ProductsDAO productsDAO;

    public ProductsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        productsDAO = posDatabase.productsDAO();
    }

    public void insert(Products products){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                productsDAO.insert(products);
            }
        });
    }

    public void update(Products products){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                productsDAO.update(products);
            }
        });
    }

    public List<Products> fetchAllByDepartment(int departmentId) throws ExecutionException, InterruptedException {
        Callable<List<Products>> callable = new Callable<List<Products>>() {
            @Override
            public List<Products> call() throws Exception {
                return productsDAO.fetchAllByDepartment(departmentId);
            }
        };
        Future<List<Products>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<Products> search(String search) throws ExecutionException, InterruptedException {
        Callable<List<Products>> callable = new Callable<List<Products>>() {
            @Override
            public List<Products> call() throws Exception {
                return productsDAO.search(search);
            }
        };
        Future<List<Products>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Products barcode(String barcode) throws ExecutionException, InterruptedException {
        Callable<Products> callable = new Callable<Products>() {
            @Override
            public Products call() throws Exception {
                return productsDAO.barcode(barcode);
            }
        };
        Future<Products> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Products fetchById(int id) throws ExecutionException, InterruptedException {
        Callable<Products> callable = new Callable<Products>() {
            @Override
            public Products call() throws Exception {
                return productsDAO.fetchById(id);
            }
        };
        Future<Products> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Long fetchProductCount() throws ExecutionException, InterruptedException {
        Callable<Long> callable = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return productsDAO.fetchProductCount();
            }
        };
        Future<Long> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
