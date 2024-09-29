package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.SafekeepingDenomination;
import com.example.isyncpos.repositories.SafekeepingDenominationRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SafekeepingDenominationViewModel extends AndroidViewModel {

    private SafekeepingDenominationRepository safekeepingDenominationRepository;
    private LiveData<List<SafekeepingDenomination>> safekeepingDenominationLiveData;

    public SafekeepingDenominationViewModel(Application application) {
        super(application);
        safekeepingDenominationRepository = new SafekeepingDenominationRepository(application);
        safekeepingDenominationLiveData = safekeepingDenominationRepository.fetchCompleteSafekeepingDenominationToSync();
    }

    public void insert(SafekeepingDenomination safekeepingDenomination){
        safekeepingDenominationRepository.insert(safekeepingDenomination);
    }

    public void update(SafekeepingDenomination safekeepingDenomination){
        safekeepingDenominationRepository.update(safekeepingDenomination);
    }

    public void updateSafekeepingDenominationSentToServer(int safekeepingDenominationId){
        safekeepingDenominationRepository.updateSafekeepingDenominationSentToServer(safekeepingDenominationId);
    }

    public List<SafekeepingDenomination> fetchSafekeepingDenominationBySafekeepingId(int safekeepingId) throws ExecutionException, InterruptedException {
        return safekeepingDenominationRepository.fetchSafekeepingDenominationBySafekeepingId(safekeepingId);
    }

    public LiveData<List<SafekeepingDenomination>> fetchCompleteSafekeepingDenominationToSync() {
        return safekeepingDenominationLiveData;
    }

}
