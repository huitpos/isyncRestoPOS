package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.EndOfDay;
import com.example.isyncpos.repositories.EndOfDayRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class EndOfDayViewModel extends AndroidViewModel {

    private EndOfDayRepository endOfDayRepository;
    private LiveData<List<EndOfDay>> toSyncEndOfDayLiveData;
    private LiveData<List<EndOfDay>> listEndOfDayReprintLiveData;

    public EndOfDayViewModel(Application application){
        super(application);
        endOfDayRepository = new EndOfDayRepository(application);
        toSyncEndOfDayLiveData = endOfDayRepository.fetchCompleteEndOfDayToSync();
        listEndOfDayReprintLiveData = endOfDayRepository.fetchEndOfDayForTodayToReprint();
    }

    public Long insert(EndOfDay endOfDay) throws ExecutionException, InterruptedException {
        return endOfDayRepository.insert(endOfDay);
    }

    public void update(EndOfDay endOfDay){
        endOfDayRepository.update(endOfDay);
    }

    public void updateEndOfDaySentToServer(int endOfDayId){
        endOfDayRepository.updateEndOfDaySentToServer(endOfDayId);
    }

    public EndOfDay fetchEndOfDayInformationById(int endOfDayId) throws ExecutionException, InterruptedException {
        return endOfDayRepository.fetchEndOfDayInformationById(endOfDayId);
    }

    public EndOfDay fetchEndOfDayNow(String date) throws ExecutionException, InterruptedException {
        return endOfDayRepository.fetchEndOfDayNow(date);
    }

    public List<EndOfDay> fetchCompleteEndOfDayToUpload() throws ExecutionException, InterruptedException {
        return endOfDayRepository.fetchCompleteEndOfDayToUpload();
    }

    public LiveData<List<EndOfDay>> fetchCompleteEndOfDayToSync(){
        return toSyncEndOfDayLiveData;
    }

    public LiveData<List<EndOfDay>> fetchEndOfDayForTodayToReprint(){
        return listEndOfDayReprintLiveData;
    }

}
