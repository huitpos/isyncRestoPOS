package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.PermissionsDAO;
import com.example.isyncpos.entity.Permissions;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PermissionRepository {

    private PermissionsDAO permissionsDAO;

    public PermissionRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        permissionsDAO = posDatabase.permissionsDAO();
    }

    public void insert(Permissions permissions){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                permissionsDAO.insert(permissions);
            }
        });
    }

    public List<Permissions> fetchByRoleUserId(int roleId, int userId) throws ExecutionException, InterruptedException {
        Callable<List<Permissions>> callable = new Callable<List<Permissions>>() {
            @Override
            public List<Permissions> call() throws Exception {
                return permissionsDAO.fetchByRoleUserId(roleId, userId);
            }
        };
        Future<List<Permissions>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Permissions fetchByRoleUserIdPermissionId(int roleId, int userId, int permissionId) throws ExecutionException, InterruptedException {
        Callable<Permissions> callable = new Callable<Permissions>() {
            @Override
            public Permissions call() throws Exception {
                return permissionsDAO.fetchByRoleUserIdPermissionId(roleId, userId, permissionId);
            }
        };
        Future<Permissions> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Void removePermission() throws ExecutionException, InterruptedException {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return permissionsDAO.removePermission();
            }
        };
        Future<Void> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
