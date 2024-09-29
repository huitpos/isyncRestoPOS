package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.EndOfDayDiscounts;
import com.example.isyncpos.repositories.EndOfDayDiscountsRepository;

import java.util.List;

public class EndOfDayDiscountsViewModel extends AndroidViewModel {

    private EndOfDayDiscountsRepository endOfDayDiscountsRepository;
    private LiveData<List<EndOfDayDiscounts>> toSyncListEndOfDayDiscountsLiveData;

    public EndOfDayDiscountsViewModel(Application application){
        super(application);
        endOfDayDiscountsRepository = new EndOfDayDiscountsRepository(application);
        toSyncListEndOfDayDiscountsLiveData = endOfDayDiscountsRepository.fetchCompleteEndOfDayDiscountsToSync();
    }

    public void insert(EndOfDayDiscounts endOfDayDiscounts){
        endOfDayDiscountsRepository.insert(endOfDayDiscounts);
    }

    public LiveData<List<EndOfDayDiscounts>> fetchCompleteEndOfDayDiscountsToSync(){
        return toSyncListEndOfDayDiscountsLiveData;
    }

    public void updateEndOfDayDiscountsSentToServer(int endOfDayDiscountId){
        endOfDayDiscountsRepository.updateEndOfDayDiscountsSentToServer(endOfDayDiscountId);
    }

}
