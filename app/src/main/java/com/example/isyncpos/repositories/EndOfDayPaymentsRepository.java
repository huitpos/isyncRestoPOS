package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.EndOfDayPaymentsDAO;
import com.example.isyncpos.entity.EndOfDayPayments;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EndOfDayPaymentsRepository {

    private EndOfDayPaymentsDAO endOfDayPaymentsDAO;
    private LiveData<List<EndOfDayPayments>> toSyncListEndOfDayPaymentsLiveData;

    public EndOfDayPaymentsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        endOfDayPaymentsDAO = posDatabase.endOfDayPaymentsDAO();
        toSyncListEndOfDayPaymentsLiveData = endOfDayPaymentsDAO.fetchCompleteEndOfDayPaymentsToSync();
    }

    public void insert(EndOfDayPayments endOfDayPayments){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                endOfDayPaymentsDAO.insert(endOfDayPayments);
            }
        });
    }

    public LiveData<List<EndOfDayPayments>> fetchCompleteEndOfDayPaymentsToSync() {
        return toSyncListEndOfDayPaymentsLiveData;
    }

    public void updateEndOfDayPaymentsSentToServer(int endOfDayPaymentId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                endOfDayPaymentsDAO.updateEndOfDayPaymentsSentToServer(endOfDayPaymentId);
            }
        });
    }

}
