package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.PrinterSetupDevicesDAO;
import com.example.isyncpos.entity.PrinterSetupDevices;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PrinterSetupDevicesRepository {

    private PrinterSetupDevicesDAO printerSetupDevicesDAO;

    public PrinterSetupDevicesRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        printerSetupDevicesDAO = posDatabase.printerSetupDevicesDAO();
    }

    public void insert(PrinterSetupDevices printerSetupDevices){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                printerSetupDevicesDAO.insert(printerSetupDevices);
            }
        });
    }

    public void removeAll(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                printerSetupDevicesDAO.removeAll();
            }
        });
    }

    public void remove(PrinterSetupDevices printerSetupDevices){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                printerSetupDevicesDAO.remove(printerSetupDevices);
            }
        });
    }

    public List<PrinterSetupDevices> fetchPrinterSetupDevicesPrinterSetupID(int printerSetupId) throws ExecutionException, InterruptedException {
        Callable<List<PrinterSetupDevices>> callable = new Callable<List<PrinterSetupDevices>>() {
            @Override
            public List<PrinterSetupDevices> call() throws Exception {
                return printerSetupDevicesDAO.fetchPrinterSetupDevicesPrinterSetupID(printerSetupId);
            }
        };
        Future<List<PrinterSetupDevices>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public PrinterSetupDevices validatePrinterSetupDevices(int printerSetupId, int deviceId) throws ExecutionException, InterruptedException {
        Callable<PrinterSetupDevices> callable = new Callable<PrinterSetupDevices>() {
            @Override
            public PrinterSetupDevices call() throws Exception {
                return printerSetupDevicesDAO.validatePrinterSetupDevices(printerSetupId, deviceId);
            }
        };
        Future<PrinterSetupDevices> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public PrinterSetupDevices fetchPrinterSetupDevice(int printerSetupDeviceId) throws ExecutionException, InterruptedException {
        Callable<PrinterSetupDevices> callable = new Callable<PrinterSetupDevices>() {
            @Override
            public PrinterSetupDevices call() throws Exception {
                return printerSetupDevicesDAO.fetchPrinterSetupDevice(printerSetupDeviceId);
            }
        };
        Future<PrinterSetupDevices> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<PrinterSetupDevices> fetchPrinterSetupDevicesDeviceID(int deviceId) throws ExecutionException, InterruptedException {
        Callable<List<PrinterSetupDevices>> callable = new Callable<List<PrinterSetupDevices>>() {
            @Override
            public List<PrinterSetupDevices> call() throws Exception {
                return printerSetupDevicesDAO.fetchPrinterSetupDevicesDeviceID(deviceId);
            }
        };
        Future<List<PrinterSetupDevices>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
