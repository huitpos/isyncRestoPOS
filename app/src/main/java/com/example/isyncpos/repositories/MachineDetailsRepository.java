package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.MachineDetailsDAO;
import com.example.isyncpos.entity.MachineDetails;
import com.example.isyncpos.handler.POSDatabase;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MachineDetailsRepository {

    private MachineDetailsDAO machineDetailsDAO;
    private LiveData<MachineDetails> machineDetailsLiveData;

    public MachineDetailsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        machineDetailsDAO = posDatabase.machineDetailsDAO();
        machineDetailsLiveData = machineDetailsDAO.fetchMachineDetailsToSync();
    }

    public void insert(MachineDetails machineDetails){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                machineDetailsDAO.insert(machineDetails);
            }
        });
    }

    public Void update(MachineDetails machineDetails) throws ExecutionException, InterruptedException {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return machineDetailsDAO.update(machineDetails);
            }
        };
        Future<Void> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public MachineDetails fetchMachineDetails() throws ExecutionException, InterruptedException {
        Callable<MachineDetails> callable = new Callable<MachineDetails>() {
            @Override
            public MachineDetails call() throws Exception {
                return machineDetailsDAO.fetchMachineDetails();
            }
        };
        Future<MachineDetails> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public MachineDetails fetchMachineDetailsToUpload() throws ExecutionException, InterruptedException {
        Callable<MachineDetails> callable = new Callable<MachineDetails>() {
            @Override
            public MachineDetails call() throws Exception {
                return machineDetailsDAO.fetchMachineDetailsToUpload();
            }
        };
        Future<MachineDetails> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void remove(MachineDetails machineDetails){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                machineDetailsDAO.remove(machineDetails);
            }
        });
    }


    public void updateMachineDetailsSentToServer(int machineDetailsId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                machineDetailsDAO.updateMachineDetailsSentToServer(machineDetailsId);
            }
        });
    }

    public LiveData<MachineDetails> fetchMachineDetailsToSync(){
        return machineDetailsLiveData;
    }

}
