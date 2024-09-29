package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.PrinterSetupDevices;
import com.example.isyncpos.repositories.PrinterSetupDevicesRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PrinterSetupDevicesViewModel extends AndroidViewModel {

    private PrinterSetupDevicesRepository printerSetupDevicesRepository;

    public PrinterSetupDevicesViewModel(Application application){
        super(application);
        printerSetupDevicesRepository = new PrinterSetupDevicesRepository(application);
    }

    public void insert(PrinterSetupDevices printerSetupDevices){
        printerSetupDevicesRepository.insert(printerSetupDevices);
    }

    public void removeAll(){
        printerSetupDevicesRepository.removeAll();
    }

    public void remove(PrinterSetupDevices printerSetupDevices){
        printerSetupDevicesRepository.remove(printerSetupDevices);
    }

    public PrinterSetupDevices validatePrinterSetupDevices(int printerSetupId, int deviceId) throws ExecutionException, InterruptedException {
        return printerSetupDevicesRepository.validatePrinterSetupDevices(printerSetupId, deviceId);
    }

    public List<PrinterSetupDevices> fetchPrinterSetupDevicesPrinterSetupID(int printerSetupId) throws ExecutionException, InterruptedException {
        return printerSetupDevicesRepository.fetchPrinterSetupDevicesPrinterSetupID(printerSetupId);
    }

    public PrinterSetupDevices fetchPrinterSetupDevice(int printerSetupDeviceId) throws ExecutionException, InterruptedException {
        return printerSetupDevicesRepository.fetchPrinterSetupDevice(printerSetupDeviceId);
    }

    public List<PrinterSetupDevices> fetchPrinterSetupDevicesDeviceID(int deviceId) throws ExecutionException, InterruptedException {
        return printerSetupDevicesRepository.fetchPrinterSetupDevicesDeviceID(deviceId);
    }

}
