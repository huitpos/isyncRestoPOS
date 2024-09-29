package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.CashDenomination;
import com.example.isyncpos.repositories.CashDenominationRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CashDenominationViewModel extends AndroidViewModel {

    private CashDenominationRepository cashDenominationRepository;
    private LiveData<List<CashDenomination>> cashDenominationLiveData;

    public CashDenominationViewModel(Application application){
        super(application);
        cashDenominationRepository = new CashDenominationRepository(application);
        cashDenominationLiveData = cashDenominationRepository.fetchCashDenomination();
    }

    public void insert(CashDenomination cashDenomination){
        cashDenominationRepository.insert(cashDenomination);
    }

    public void update(CashDenomination cashDenomination){
        cashDenominationRepository.update(cashDenomination);
    }

    public LiveData<List<CashDenomination>> fetchCashDenomination(){
        return cashDenominationLiveData;
    }

    public CashDenomination fetchById(int id) throws ExecutionException, InterruptedException {
        return cashDenominationRepository.fetchById(id);
    }

}
