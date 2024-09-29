package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.DeviceDetailsDAO;
import com.example.isyncpos.entity.DeviceDetails;
import com.example.isyncpos.handler.POSDatabase;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DeviceDetailsRepository {

    private DeviceDetailsDAO deviceDetailsDAO;

    public DeviceDetailsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        deviceDetailsDAO = posDatabase.deviceDetailsDAO();
    }

    public void insert(DeviceDetails deviceDetails){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                deviceDetailsDAO.insert(deviceDetails);
            }
        });
    }

    public void update(DeviceDetails deviceDetails){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                deviceDetailsDAO.update(deviceDetails);
            }
        });
    }

    public DeviceDetails fetch() throws ExecutionException, InterruptedException {
        Callable<DeviceDetails> callable = new Callable<DeviceDetails>() {
            @Override
            public DeviceDetails call() throws Exception {
                return deviceDetailsDAO.fetch();
            }
        };
        Future<DeviceDetails> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
