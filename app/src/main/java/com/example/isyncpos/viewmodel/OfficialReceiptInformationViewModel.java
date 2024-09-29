package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.OfficialReceiptInformation;
import com.example.isyncpos.repositories.OfficialReceiptInformationRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class OfficialReceiptInformationViewModel extends AndroidViewModel {

    private OfficialReceiptInformationRepository officialReceiptInformationRepository;
    private LiveData<List<OfficialReceiptInformation>> toSyncOfficialReceiptInformationLiveData;

    public OfficialReceiptInformationViewModel(Application application){
        super(application);
        officialReceiptInformationRepository = new OfficialReceiptInformationRepository(application);
        toSyncOfficialReceiptInformationLiveData = officialReceiptInformationRepository.fetchCompleteOfficialReceiptInformationToSync();
    }

    public void insert(OfficialReceiptInformation officialReceiptInformation){
        officialReceiptInformationRepository.insert(officialReceiptInformation);
    }

    public void update(OfficialReceiptInformation officialReceiptInformation){
        officialReceiptInformationRepository.update(officialReceiptInformation);
    }

    public OfficialReceiptInformation fetchByTransactionId(int transactionId) throws ExecutionException, InterruptedException {
        return officialReceiptInformationRepository.fetchByTransactionId(transactionId);
    }

    public LiveData<List<OfficialReceiptInformation>> fetchCompleteOfficialReceiptInformationToSync(){
        return toSyncOfficialReceiptInformationLiveData;
    }

    public void updateOfficialReceiptInformationSentToServer(int officialReceiptId){
        officialReceiptInformationRepository.updateOfficialReceiptInformationSentToServer(officialReceiptId);
    }

}
