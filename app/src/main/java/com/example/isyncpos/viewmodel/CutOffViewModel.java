package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.CutOff;
import com.example.isyncpos.repositories.CutOffRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CutOffViewModel extends AndroidViewModel {

    private CutOffRepository cutOffRepository;
    private LiveData<List<CutOff>> toSyncCutOffLiveData;
    private LiveData<List<CutOff>> listCutOffReprintLiveData;

    public CutOffViewModel(Application application){
        super(application);
        cutOffRepository = new CutOffRepository(application);
        toSyncCutOffLiveData = cutOffRepository.fetchCompleteCutOffToSync();
        listCutOffReprintLiveData = cutOffRepository.fetchCutOffForTodayToReprint();
    }

    public Long insert(CutOff cutOff) throws ExecutionException, InterruptedException {
        return cutOffRepository.insert(cutOff);
    }

    public void update(CutOff cutOff){
        cutOffRepository.update(cutOff);
    }

    public void updateCutOffSentToServer(int cutOffId){
        cutOffRepository.updateCutOffSentToServer(cutOffId);
    }

    public List<CutOff> fetchCutOffToEndOfDay() throws ExecutionException, InterruptedException {
        return cutOffRepository.fetchCutOffToEndOfDay();
    }

    public List<CutOff> fetchCompleteCutOffToUpload() throws ExecutionException, InterruptedException {
        return cutOffRepository.fetchCompleteCutOffToUpload();
    }

    public CutOff fetchLastCutOffInformation() throws ExecutionException, InterruptedException {
        return cutOffRepository.fetchLastCutOffInformation();
    }

    public CutOff fetchLastCutOffForEndOfDayInformation() throws ExecutionException, InterruptedException {
        return cutOffRepository.fetchLastCutOffForEndOfDayInformation();
    }

    public CutOff fetchCutOffInformationById(int cutOffId) throws ExecutionException, InterruptedException {
        return cutOffRepository.fetchCutOffInformationById(cutOffId);
    }

    public List<String> checkForEndOfDayProcess() throws ExecutionException, InterruptedException {
        return cutOffRepository.checkForEndOfDayProcess();
    }

    public List<CutOff> fetchCutOffToEndOfDayByDate(String today) throws ExecutionException, InterruptedException {
        return cutOffRepository.fetchCutOffToEndOfDayByDate(today);
    }

    public LiveData<List<CutOff>> fetchCompleteCutOffToSync(){
        return toSyncCutOffLiveData;
    }

    public LiveData<List<CutOff>> fetchCutOffForTodayToReprint(){
        return listCutOffReprintLiveData;
    }

}
