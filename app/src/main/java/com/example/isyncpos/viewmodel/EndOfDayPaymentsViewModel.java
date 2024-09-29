package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.EndOfDayPayments;
import com.example.isyncpos.repositories.EndOfDayPaymentsRepository;

import java.util.List;

public class EndOfDayPaymentsViewModel extends AndroidViewModel {

    private EndOfDayPaymentsRepository endOfDayPaymentsRepository;
    private LiveData<List<EndOfDayPayments>> toSyncListEndOfDayPaymentsLiveData;

    public EndOfDayPaymentsViewModel(Application application){
        super(application);
        endOfDayPaymentsRepository = new EndOfDayPaymentsRepository(application);
        toSyncListEndOfDayPaymentsLiveData = endOfDayPaymentsRepository.fetchCompleteEndOfDayPaymentsToSync();
    }

    public void insert(EndOfDayPayments endOfDayPayments){
        endOfDayPaymentsRepository.insert(endOfDayPayments);
    }

    public LiveData<List<EndOfDayPayments>> fetchCompleteEndOfDayPaymentsToSync() {
        return toSyncListEndOfDayPaymentsLiveData;
    }

    public void updateEndOfDayPaymentsSentToServer(int endOfDayPaymentId){
        endOfDayPaymentsRepository.updateEndOfDayPaymentsSentToServer(endOfDayPaymentId);
    }

}
