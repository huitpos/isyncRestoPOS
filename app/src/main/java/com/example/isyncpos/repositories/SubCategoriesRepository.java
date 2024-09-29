package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.SubCategoriesDAO;
import com.example.isyncpos.entity.SubCategories;
import com.example.isyncpos.handler.POSDatabase;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SubCategoriesRepository {

    private SubCategoriesDAO subCategoriesDAO;

    public SubCategoriesRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        subCategoriesDAO = posDatabase.subCategoriesDAO();
    }

    public void insert(SubCategories subCategories){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                subCategoriesDAO.insert(subCategories);
            }
        });
    }

    public void update(SubCategories subCategories){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                subCategoriesDAO.update(subCategories);
            }
        });
    }

    public SubCategories fetchSubCategory(int subCategoryId) throws ExecutionException, InterruptedException {
        Callable<SubCategories> callable = new Callable<SubCategories>() {
            @Override
            public SubCategories call() throws Exception {
                return subCategoriesDAO.fetchSubCategory(subCategoryId);
            }
        };
        Future<SubCategories> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
