package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.CutOffDiscounts;
import com.example.isyncpos.repositories.CutOffDiscountsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CutOffDiscountsViewModel extends AndroidViewModel {

    private CutOffDiscountsRepository cutOffDiscountsRepository;
    private LiveData<List<CutOffDiscounts>> toSyncListCutOffDiscountsLiveData;

    public CutOffDiscountsViewModel(Application application){
        super(application);
        cutOffDiscountsRepository = new CutOffDiscountsRepository(application);
        toSyncListCutOffDiscountsLiveData = cutOffDiscountsRepository.fetchCompleteCutOffDiscountsToSync();
    }

    public void insert(CutOffDiscounts cutOffDiscounts){
        cutOffDiscountsRepository.insert(cutOffDiscounts);
    }

    public void update(CutOffDiscounts cutOffDiscounts){
        cutOffDiscountsRepository.update(cutOffDiscounts);
    }

    public List<CutOffDiscounts> fetchByCutOffId(int cutOffId) throws ExecutionException, InterruptedException {
        return cutOffDiscountsRepository.fetchByCutOffId(cutOffId);
    }

    public LiveData<List<CutOffDiscounts>> fetchCompleteCutOffDiscountsToSync(){
        return toSyncListCutOffDiscountsLiveData;
    }

    public void updateCutOffDiscountsSentToServer(int cutOffDiscountId){
        cutOffDiscountsRepository.updateCutOffDiscountsSentToServer(cutOffDiscountId);
    }

}
