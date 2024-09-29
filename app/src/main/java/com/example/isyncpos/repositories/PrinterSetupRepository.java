package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.PrinterSetupDAO;
import com.example.isyncpos.entity.PrinterSetup;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PrinterSetupRepository {

    private PrinterSetupDAO printerSetupDAO;
    private LiveData<List<PrinterSetup>> printerSetupCashierLiveData;
    private LiveData<List<PrinterSetup>> printerSetupTakeOrderLiveData;

    public PrinterSetupRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        printerSetupDAO = posDatabase.printerSetupDAO();
        printerSetupCashierLiveData = printerSetupDAO.fetchAllCashierType();
        printerSetupTakeOrderLiveData = printerSetupDAO.fetchAllTakeOrderType();
    }

    public void insert(PrinterSetup printerSetup){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                printerSetupDAO.insert(printerSetup);
            }
        });
    }

    public void update(PrinterSetup printerSetup){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                printerSetupDAO.update(printerSetup);
            }
        });
    }

    public void clearDevices(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                printerSetupDAO.clearDevices();
            }
        });
    }

    public PrinterSetup fetchPrinterSetup(int printerSetupId) throws ExecutionException, InterruptedException {
        Callable<PrinterSetup> callable = new Callable<PrinterSetup>() {
            @Override
            public PrinterSetup call() throws Exception {
                return printerSetupDAO.fetchPrinterSetup(printerSetupId);
            }
        };
        Future<PrinterSetup> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<PrinterSetup> fetchPrinsterSetupList() throws ExecutionException, InterruptedException {
        Callable<List<PrinterSetup>> callable = new Callable<List<PrinterSetup>>() {
            @Override
            public List<PrinterSetup> call() throws Exception {
                return printerSetupDAO.fetchPrinsterSetupList();
            }
        };
        Future<List<PrinterSetup>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<PrinterSetup>> fetchAllCashierType(){
        return printerSetupCashierLiveData;
    }

    public LiveData<List<PrinterSetup>> fetchAllTakeOrderType(){
        return printerSetupTakeOrderLiveData;
    }

}
