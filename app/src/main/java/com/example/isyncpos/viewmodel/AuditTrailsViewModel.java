package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.AuditTrails;
import com.example.isyncpos.repositories.AuditTrailsRepository;

import java.util.List;

public class AuditTrailsViewModel extends AndroidViewModel {

    private AuditTrailsRepository auditTrailsRepository;
    private LiveData<List<AuditTrails>> toSyncAuditTrailsLiveData;

    public AuditTrailsViewModel(Application application) {
        super(application);
        auditTrailsRepository = new AuditTrailsRepository(application);
        toSyncAuditTrailsLiveData = auditTrailsRepository.fetchCompleteAuditTrailsToSync();
    }

    public void insert(AuditTrails auditTrails){
        auditTrailsRepository.insert(auditTrails);
    }

    public void updateAuditTrailSentToServer(int auditTrailId){
        auditTrailsRepository.updateAuditTrailSentToServer(auditTrailId);
    }


    public LiveData<List<AuditTrails>> fetchCompleteAuditTrailsToSync(){
        return toSyncAuditTrailsLiveData;
    }

}
