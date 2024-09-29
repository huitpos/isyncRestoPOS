package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.SpotAuditDenomination;
import com.example.isyncpos.repositories.SpotAuditDenominationRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SpotAuditDenominationViewModel extends AndroidViewModel {

    private SpotAuditDenominationRepository spotAuditDenominationRepository;
    private LiveData<List<SpotAuditDenomination>> toSyncSpotAuditDenominationLiveData;

    public SpotAuditDenominationViewModel(Application application){
        super(application);
        spotAuditDenominationRepository = new SpotAuditDenominationRepository(application);
        toSyncSpotAuditDenominationLiveData = spotAuditDenominationRepository.fetchCompleteSpotAuditDenominationToSync();
    }

    public void insert(SpotAuditDenomination spotAuditDenomination){
        spotAuditDenominationRepository.insert(spotAuditDenomination);
    }

    public List<SpotAuditDenomination> fetchBySpotAuditId(int spotAuditId) throws ExecutionException, InterruptedException {
        return spotAuditDenominationRepository.fetchBySpotAuditId(spotAuditId);
    }

    public LiveData<List<SpotAuditDenomination>> fetchCompleteSpotAuditDenominationToSync(){
        return toSyncSpotAuditDenominationLiveData;
    }

    public void updateSpotAuditDenominationSentToServer(int spotAuditDenominationId){
        spotAuditDenominationRepository.updateSpotAuditDenominationSentToServer(spotAuditDenominationId);
    }

}
