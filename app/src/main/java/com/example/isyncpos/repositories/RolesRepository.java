package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.RolesDAO;
import com.example.isyncpos.entity.Roles;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RolesRepository {

    private RolesDAO rolesDAO;

    public RolesRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        rolesDAO = posDatabase.rolesDAO();
    }

    public void insert(Roles roles){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                rolesDAO.insert(roles);
            }
        });
    }

    public List<Roles> fetchByUserId(int userId) throws ExecutionException, InterruptedException {
        Callable<List<Roles>> callable = new Callable<List<Roles>>() {
            @Override
            public List<Roles> call() throws Exception {
                return rolesDAO.fetchByUserId(userId);
            }
        };
        Future<List<Roles>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Void removeRoles() throws ExecutionException, InterruptedException {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return rolesDAO.removeRoles();
            }
        };
        Future<Void> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
