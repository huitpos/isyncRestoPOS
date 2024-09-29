package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.EndOffDayProducts;
import com.example.isyncpos.repositories.EndOfDayProductsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class EndOfDayProductsViewModel extends AndroidViewModel {

    private EndOfDayProductsRepository endOfDayProductsRepository;

    public EndOfDayProductsViewModel(Application application){
        super(application);
        endOfDayProductsRepository = new EndOfDayProductsRepository(application);
    }

    public void insert(EndOffDayProducts endOffDayProducts){
        endOfDayProductsRepository.insert(endOffDayProducts);
    }

    public void update(EndOffDayProducts endOffDayProducts){
        endOfDayProductsRepository.update(endOffDayProducts);
    }

    public List<EndOffDayProducts> fetchByEndOfDayId(int endOfDayId) throws ExecutionException, InterruptedException {
        return endOfDayProductsRepository.fetchByEndOfDayId(endOfDayId);
    }

    public void updateEndOffDayProductsSentToServer(int endOfDayProductId){
        endOfDayProductsRepository.updateEndOffDayProductsSentToServer(endOfDayProductId);
    }

}
