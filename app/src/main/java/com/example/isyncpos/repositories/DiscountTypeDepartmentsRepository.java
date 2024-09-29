package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.DiscountTypeDepartmentsDAO;
import com.example.isyncpos.entity.DiscountTypeDepartments;
import com.example.isyncpos.handler.POSDatabase;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DiscountTypeDepartmentsRepository {

    private DiscountTypeDepartmentsDAO discountTypeDepartmentsDAO;

    public DiscountTypeDepartmentsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        discountTypeDepartmentsDAO = posDatabase.discountTypeDepartmentsDAO();
    }

    public void insert(DiscountTypeDepartments discountTypeDepartments){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                discountTypeDepartmentsDAO.insert(discountTypeDepartments);
            }
        });
    }

    public Void delete(int discountTypeId) throws ExecutionException, InterruptedException {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return discountTypeDepartmentsDAO.delete(discountTypeId);
            }
        };
        Future<Void> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public DiscountTypeDepartments fetchByDepartmentDiscountId(int departmentId, int discountTypeId) throws ExecutionException, InterruptedException {
        Callable<DiscountTypeDepartments> callable = new Callable<DiscountTypeDepartments>() {
            @Override
            public DiscountTypeDepartments call() throws Exception {
                return discountTypeDepartmentsDAO.fetchByDepartmentDiscountId(departmentId, discountTypeId);
            }
        };
        Future<DiscountTypeDepartments> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Long fetchDiscountTypeDepartmentCount(int discountTypeId) throws ExecutionException, InterruptedException {
        Callable<Long> callable = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return discountTypeDepartmentsDAO.fetchDiscountTypeDepartmentCount(discountTypeId);
            }
        };
        Future<Long> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
