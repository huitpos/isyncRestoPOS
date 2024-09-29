package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.AuthenticatedMachineUserDAO;
import com.example.isyncpos.entity.AuthenticatedMachineUser;
import com.example.isyncpos.handler.POSDatabase;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AuthenticatedMachineUserRepository {
    private AuthenticatedMachineUserDAO authenticatedMachineUserDAO;

    public AuthenticatedMachineUserRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        authenticatedMachineUserDAO = posDatabase.authenticatedMachineUserDAO();
    }

    public AuthenticatedMachineUser fetch() throws ExecutionException, InterruptedException {
        Callable<AuthenticatedMachineUser> callable = new Callable<AuthenticatedMachineUser>() {
            @Override
            public AuthenticatedMachineUser call() throws Exception {
                return authenticatedMachineUserDAO.fetch();
            }
        };
        Future<AuthenticatedMachineUser> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Long insert(AuthenticatedMachineUser authenticatedMachineUser) throws ExecutionException, InterruptedException {
        Callable<Long> callable = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return authenticatedMachineUserDAO.insert(authenticatedMachineUser);
            }
        };
        Future<Long> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }


    public void update(AuthenticatedMachineUser authenticatedMachineUser){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                authenticatedMachineUserDAO.update(authenticatedMachineUser);
            }
        });
    }

}
