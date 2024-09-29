package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.UsersDAO;
import com.example.isyncpos.entity.Users;
import com.example.isyncpos.handler.POSDatabase;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UsersRepository {

    private UsersDAO usersDAO;

    public UsersRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        usersDAO = posDatabase.usersDAO();
    }

    public void insert(Users users){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                usersDAO.insert(users);
            }
        });
    }

    public void update(Users users){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                usersDAO.update(users);
            }
        });
    }

    public Users findByUsernameOrEmail(String username) throws ExecutionException, InterruptedException {
        Callable<Users> callable = new Callable<Users>() {
            @Override
            public Users call() throws Exception {
                return usersDAO.findByUsernameOrEmail(username);
            }
        };
        Future<Users> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }


    public Users authenticate(String credential) throws ExecutionException, InterruptedException {
        Callable<Users> callable = new Callable<Users>() {
            @Override
            public Users call() throws Exception {
                return usersDAO.authenticate(credential);
            }
        };
        Future<Users> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Users fetchById(int id) throws ExecutionException, InterruptedException {
        Callable<Users> callable = new Callable<Users>() {
            @Override
            public Users call() throws Exception {
                return usersDAO.fetchById(id);
            }
        };
        Future<Users> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<Users> fetchAll() throws ExecutionException, InterruptedException {
        Callable<List<Users>> callable = new Callable<List<Users>>() {
            @Override
            public List<Users> call() throws Exception {
                return usersDAO.fetchAll();
            }
        };
        Future<List<Users>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
