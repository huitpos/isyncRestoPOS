package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.CategoriesDAO;
import com.example.isyncpos.entity.Categories;
import com.example.isyncpos.handler.POSDatabase;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CategoriesRepository {

    private CategoriesDAO categoriesDAO;

    public CategoriesRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        categoriesDAO = posDatabase.categoriesDAO();
    }

    public void insert(Categories categories){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                categoriesDAO.insert(categories);
            }
        });
    }

    public void update(Categories categories){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                categoriesDAO.update(categories);
            }
        });
    }

    public Categories fetchCategory(int categoryId) throws ExecutionException, InterruptedException {
        Callable<Categories> callable = new Callable<Categories>() {
            @Override
            public Categories call() throws Exception {
                return categoriesDAO.fetchCategory(categoryId);
            }
        };
        Future<Categories> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
