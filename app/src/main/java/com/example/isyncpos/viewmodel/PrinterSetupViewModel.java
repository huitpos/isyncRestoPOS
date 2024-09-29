package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.PrinterSetup;
import com.example.isyncpos.repositories.PrinterSetupRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PrinterSetupViewModel extends AndroidViewModel {

    private PrinterSetupRepository printerSetupRepository;
    private LiveData<List<PrinterSetup>> printerSetupCashierLiveData;
    private LiveData<List<PrinterSetup>> printerSetupTakeOrderLiveData;


    public PrinterSetupViewModel(Application application){
        super(application);
        printerSetupRepository = new PrinterSetupRepository(application);
        printerSetupCashierLiveData = printerSetupRepository.fetchAllCashierType();
        printerSetupTakeOrderLiveData = printerSetupRepository.fetchAllTakeOrderType();
    }

    public void insert(PrinterSetup printerSetup){
        printerSetupRepository.insert(printerSetup);
    }

    public void update(PrinterSetup printerSetup){
        printerSetupRepository.update(printerSetup);
    }

    public void clearDevices(){
        printerSetupRepository.clearDevices();
    }

    public PrinterSetup fetchPrinterSetup(int printerSetupId) throws ExecutionException, InterruptedException {
        return printerSetupRepository.fetchPrinterSetup(printerSetupId);
    }

    public List<PrinterSetup> fetchPrinsterSetupList() throws ExecutionException, InterruptedException {
        return printerSetupRepository.fetchPrinsterSetupList();
    }

    public LiveData<List<PrinterSetup>> fetchAllCashierType(){
        return printerSetupCashierLiveData;
    }

    public LiveData<List<PrinterSetup>> fetchAllTakeOrderType(){
        return printerSetupTakeOrderLiveData;
    }

}
