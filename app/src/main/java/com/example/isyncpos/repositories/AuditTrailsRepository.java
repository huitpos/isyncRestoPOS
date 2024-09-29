package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.AuditTrailsDAO;
import com.example.isyncpos.entity.AuditTrails;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuditTrailsRepository {

    private AuditTrailsDAO auditTrailsDAO;
    private LiveData<List<AuditTrails>> toSyncAuditTrailsLiveData;

    public AuditTrailsRepository(Application application) {
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        auditTrailsDAO = posDatabase.auditTrailsDAO();
        toSyncAuditTrailsLiveData = auditTrailsDAO.fetchCompleteAuditTrailsToSync();
    }

    public void insert(AuditTrails auditTrails){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                auditTrailsDAO.insert(auditTrails);
            }
        });
    }

    public void updateAuditTrailSentToServer(int auditTrailId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                auditTrailsDAO.updateAuditTrailSentToServer(auditTrailId);
            }
        });
    }


    public LiveData<List<AuditTrails>> fetchCompleteAuditTrailsToSync(){
        return toSyncAuditTrailsLiveData;
    }

}
