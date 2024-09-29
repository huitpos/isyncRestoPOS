package com.example.isyncpos.viewmodel;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.CashFundDenomination;
import com.example.isyncpos.repositories.CashFundDenominationRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CashFundDenominationViewModel extends AndroidViewModel {

    private CashFundDenominationRepository cashFundDenominationRepository;
    private LiveData<List<CashFundDenomination>> toSyncCashFundDenominationLiveData;

    public CashFundDenominationViewModel(Application application){
        super(application);
        cashFundDenominationRepository = new CashFundDenominationRepository(application);
        toSyncCashFundDenominationLiveData = cashFundDenominationRepository.fetchCompleteCashFundDenominationToSync();
    }

    public void insert(CashFundDenomination cashFundDenomination){
        cashFundDenominationRepository.insert(cashFundDenomination);
    }

    public void update(CashFundDenomination cashFundDenomination){
        cashFundDenominationRepository.update(cashFundDenomination);
    }

    public LiveData<List<CashFundDenomination>> fetchCompleteCashFundDenominationToSync(){
        return toSyncCashFundDenominationLiveData;
    }

    public void updateCashFundDenominationSentToServer(int cashFundDenominationId){
        cashFundDenominationRepository.updateCashFundDenominationSentToServer(cashFundDenominationId);
    }

    public List<CashFundDenomination> fetchByCashFundId(int cashFundId) throws ExecutionException, InterruptedException {
        return cashFundDenominationRepository.fetchByCashFundId(cashFundId);
    }

}
