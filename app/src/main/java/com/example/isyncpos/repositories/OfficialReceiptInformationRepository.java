package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.OfficialReceiptInformationDAO;
import com.example.isyncpos.entity.OfficialReceiptInformation;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OfficialReceiptInformationRepository {

    private OfficialReceiptInformationDAO officialReceiptInformationDAO;
    private LiveData<List<OfficialReceiptInformation>> toSyncOfficialReceiptInformationLiveData;

    public OfficialReceiptInformationRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        officialReceiptInformationDAO = posDatabase.officialReceiptInformationDAO();
        toSyncOfficialReceiptInformationLiveData = officialReceiptInformationDAO.fetchCompleteOfficialReceiptInformationToSync();
    }

    public void insert(OfficialReceiptInformation officialReceiptInformation){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                officialReceiptInformationDAO.insert(officialReceiptInformation);
            }
        });
    }

    public void update(OfficialReceiptInformation officialReceiptInformation){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                officialReceiptInformationDAO.update(officialReceiptInformation);
            }
        });
    }

    public OfficialReceiptInformation fetchByTransactionId(int transactionId) throws ExecutionException, InterruptedException {
        Callable<OfficialReceiptInformation> callable = new Callable<OfficialReceiptInformation>() {
            @Override
            public OfficialReceiptInformation call() throws Exception {
                return officialReceiptInformationDAO.fetchByTransactionId(transactionId);
            }
        };
        Future<OfficialReceiptInformation> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<OfficialReceiptInformation>> fetchCompleteOfficialReceiptInformationToSync(){
        return toSyncOfficialReceiptInformationLiveData;
    }

    public void updateOfficialReceiptInformationSentToServer(int officialReceiptId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                officialReceiptInformationDAO.updateOfficialReceiptInformationSentToServer(officialReceiptId);
            }
        });
    }

}
