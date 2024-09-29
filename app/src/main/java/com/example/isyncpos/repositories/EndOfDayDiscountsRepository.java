package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.EndOfDayDiscountsDAO;
import com.example.isyncpos.entity.EndOfDayDiscounts;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EndOfDayDiscountsRepository {

    private EndOfDayDiscountsDAO endOfDayDiscountsDAO;
    private LiveData<List<EndOfDayDiscounts>> toSyncListEndOfDayDiscountsLiveData;

    public EndOfDayDiscountsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        endOfDayDiscountsDAO = posDatabase.endOfDayDiscountsDAO();
        toSyncListEndOfDayDiscountsLiveData = endOfDayDiscountsDAO.fetchCompleteEndOfDayDiscountsToSync();
    }

    public void insert(EndOfDayDiscounts endOfDayDiscounts){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                endOfDayDiscountsDAO.insert(endOfDayDiscounts);
            }
        });
    }

    public LiveData<List<EndOfDayDiscounts>> fetchCompleteEndOfDayDiscountsToSync(){
        return toSyncListEndOfDayDiscountsLiveData;
    }

    public void updateEndOfDayDiscountsSentToServer(int endOfDayDiscountId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                endOfDayDiscountsDAO.updateEndOfDayDiscountsSentToServer(endOfDayDiscountId);
            }
        });
    }

}
