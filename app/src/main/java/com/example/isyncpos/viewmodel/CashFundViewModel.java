package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.isyncpos.entity.CashFund;
import com.example.isyncpos.repositories.CashFundRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CashFundViewModel extends AndroidViewModel {

    private CashFundRepository cashFundRepository;
    private MutableLiveData<Double> cashFundTotalLiveData = new MutableLiveData<>();
    private LiveData<List<CashFund>> toSyncCashFundLiveData;

    public CashFundViewModel(Application application){
        super(application);
        cashFundRepository = new CashFundRepository(application);
        toSyncCashFundLiveData = cashFundRepository.fetchCompleteCashFundToSync();
    }

    public Long insert(CashFund cashFund) throws ExecutionException, InterruptedException {
       return cashFundRepository.insert(cashFund);
    }

    public void update(CashFund cashFund){
        cashFundRepository.update(cashFund);
    }

    public CashFund fetchById(int cashFundId) throws ExecutionException, InterruptedException {
        return cashFundRepository.fetchById(cashFundId);
    }

    public LiveData<List<CashFund>> fetchCompleteCashFundToSync(){
        return toSyncCashFundLiveData;
    }

    public void updateCashFundSentToServer(int cashFundId){
        cashFundRepository.updateCashFundSentToServer(cashFundId);
    }

    public List<CashFund> fetchCashFundToCutOff() throws ExecutionException, InterruptedException {
        return cashFundRepository.fetchCashFundToCutOff();
    }

    public List<CashFund> fetchCasFundToEndOfDay() throws ExecutionException, InterruptedException {
        return cashFundRepository.fetchCasFundToEndOfDay();
    }

    public CashFund checkForCashFund() throws ExecutionException, InterruptedException {
        return cashFundRepository.checkForCashFund();
    }

    public void setCashFundTotalLiveData(double value){
        cashFundTotalLiveData.setValue(value);
    }

    public MutableLiveData<Double> getCashFundTotalLiveData(){
        if(cashFundTotalLiveData == null){
            cashFundTotalLiveData.setValue(0.00);
        }
        return cashFundTotalLiveData;
    }

}
