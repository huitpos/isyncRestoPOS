package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.CutOffProducts;
import com.example.isyncpos.repositories.CutOffProductsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CutOffProductsViewModel extends AndroidViewModel {

    private CutOffProductsRepository cutOffProductsRepository;
    private LiveData<List<CutOffProducts>> toSyncListCutOffProductsLiveData;

    public CutOffProductsViewModel(Application application){
        super(application);
        cutOffProductsRepository = new CutOffProductsRepository(application);
        toSyncListCutOffProductsLiveData = cutOffProductsRepository.fetchCompleteCutOffProductsToSync();
    }

    public void insert(CutOffProducts cutOffProducts){
        cutOffProductsRepository.insert(cutOffProducts);
    }

    public void update(CutOffProducts cutOffProducts){
        cutOffProductsRepository.update(cutOffProducts);
    }

    public List<CutOffProducts> fetchCutOffProductsToEndOfDay() throws ExecutionException, InterruptedException {
        return cutOffProductsRepository.fetchCutOffProductsToEndOfDay();
    }

    public LiveData<List<CutOffProducts>> fetchCompleteCutOffProductsToSync() {
        return toSyncListCutOffProductsLiveData;
    }

    public void updateCutOffProductsSentToServer(int cutOffProductId){
        cutOffProductsRepository.updateCutOffProductsSentToServer(cutOffProductId);
    }

}
