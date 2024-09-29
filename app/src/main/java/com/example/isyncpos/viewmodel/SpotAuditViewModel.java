package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.isyncpos.entity.SpotAudit;
import com.example.isyncpos.repositories.SpotAuditRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SpotAuditViewModel extends AndroidViewModel {

    private SpotAuditRepository spotAuditRepository;
    private MutableLiveData<Double> spotAuditTotalLiveData = new MutableLiveData<>();
    private LiveData<List<SpotAudit>> toSyncSpotAuditLiveData;

    public SpotAuditViewModel(Application application){
        super(application);
        spotAuditRepository = new SpotAuditRepository(application);
        toSyncSpotAuditLiveData = spotAuditRepository.fetchCompleteSpotAuditToSync();
    }

    public Long insert(SpotAudit spotAudit) throws ExecutionException, InterruptedException {
        return spotAuditRepository.insert(spotAudit);
    }

    public void update(SpotAudit spotAudit){
        spotAuditRepository.update(spotAudit);
    }

    public SpotAudit fetchById(int spotAuditId) throws ExecutionException, InterruptedException {
        return spotAuditRepository.fetchById(spotAuditId);
    }

    public List<SpotAudit> fetchSpotAuditToCutOff() throws ExecutionException, InterruptedException {
        return spotAuditRepository.fetchSpotAuditToCutOff();
    }

    public void setSpotAuditTotalLiveData(double value){
        spotAuditTotalLiveData.setValue(value);
    }

    public MutableLiveData<Double> getSpotAuditTotalLiveData(){
        if(spotAuditTotalLiveData == null){
            spotAuditTotalLiveData.setValue(0.00);
        }
        return spotAuditTotalLiveData;
    }

    public LiveData<List<SpotAudit>> fetchCompleteSpotAuditToSync(){
        return toSyncSpotAuditLiveData;
    }

    public void updateSpotAuditSentToServer(int spotAuditId){
        spotAuditRepository.updateSpotAuditSentToServer(spotAuditId);
    }

}
