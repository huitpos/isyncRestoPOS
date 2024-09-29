package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.Payouts;
import com.example.isyncpos.repositories.PayoutsRepository;

public class PayoutsViewModel extends AndroidViewModel {

    private PayoutsRepository payoutsRepository;

    public PayoutsViewModel(Application application) {
        super(application);
        payoutsRepository = new PayoutsRepository(application);
    }

    public void insert(Payouts payouts){
        payoutsRepository.insert(payouts);
    }

}
