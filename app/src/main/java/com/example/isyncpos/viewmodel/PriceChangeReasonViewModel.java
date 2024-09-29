package com.example.isyncpos.viewmodel;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.PriceChangeReason;
import com.example.isyncpos.repositories.PriceChangeReasonRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PriceChangeReasonViewModel extends AndroidViewModel {

    private PriceChangeReasonRepository priceChangeReasonRepository;

    public PriceChangeReasonViewModel(Application application){
        super(application);
        priceChangeReasonRepository = new PriceChangeReasonRepository(application);
    }

    public void insert(PriceChangeReason priceChangeReason){
        priceChangeReasonRepository.insert(priceChangeReason);
    }

    public void update(PriceChangeReason priceChangeReason){
        priceChangeReasonRepository.update(priceChangeReason);
    }

    public PriceChangeReason fetchByCoreId(int coreId) throws ExecutionException, InterruptedException {
        return priceChangeReasonRepository.fetchByCoreId(coreId);
    }

    public List<PriceChangeReason> fetchAll() throws ExecutionException, InterruptedException {
        return priceChangeReasonRepository.fetchAll();
    }

}
