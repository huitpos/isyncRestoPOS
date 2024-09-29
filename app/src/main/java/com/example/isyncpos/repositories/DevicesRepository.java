package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.DevicesDAO;
import com.example.isyncpos.entity.Devices;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DevicesRepository {

    private DevicesDAO devicesDAO;

    private LiveData<List<Devices>> devicesLiveData;

    public DevicesRepository(Application application) {
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        devicesDAO = posDatabase.devicesDAO();
        devicesLiveData = devicesDAO.fetchAll();
    }

    public void insert(Devices devices){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                devicesDAO.insert(devices);
            }
        });
    }

    public void update(Devices devices){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                devicesDAO.update(devices);
            }
        });
    }


    public Devices fetchDeviceSerialNumber(String serialNumber) throws ExecutionException, InterruptedException {
        Callable<Devices> callable = new Callable<Devices>() {
            @Override
            public Devices call() throws Exception {
                return devicesDAO.fetchDeviceSerialNumber(serialNumber);
            }
        };
        Future<Devices> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void removeDevice(int id){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                devicesDAO.removeDevice(id);
            }
        });
    }

    public Devices validateDevice(String name, String serialNumber) throws ExecutionException, InterruptedException {
        Callable<Devices> callable = new Callable<Devices>() {
            @Override
            public Devices call() throws Exception {
                return devicesDAO.validateDevice(name, serialNumber);
            }
        };
        Future<Devices> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Devices fetchDeviceID(int deviceId) throws ExecutionException, InterruptedException {
        Callable<Devices> callable = new Callable<Devices>() {
            @Override
            public Devices call() throws Exception {
                return devicesDAO.fetchDeviceID(deviceId);
            }
        };
        Future<Devices> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<Devices>> fetchAll(){
        return devicesLiveData;
    }

}
