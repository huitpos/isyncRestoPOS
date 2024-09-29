package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.ChargeAccount;
import com.example.isyncpos.repositories.ChargeAccountRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChargeAccountViewModel extends AndroidViewModel {

    private ChargeAccountRepository chargeAccountRepository;

    public ChargeAccountViewModel(Application application){
        super(application);
        chargeAccountRepository = new ChargeAccountRepository(application);
    }

    public void insert(ChargeAccount chargeAccount){
        chargeAccountRepository.insert(chargeAccount);
    }

    public void update(ChargeAccount chargeAccount){
        chargeAccountRepository.update(chargeAccount);
    }

    public ChargeAccount fetchById(int id) throws ExecutionException, InterruptedException {
        return chargeAccountRepository.fetchById(id);
    }

    public List<ChargeAccount> fetchAll() throws ExecutionException, InterruptedException {
        return chargeAccountRepository.fetchAll();
    }

}
