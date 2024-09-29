package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.CutOffPayments;
import com.example.isyncpos.repositories.CutOffPaymentsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CutOffPaymentsViewModel extends AndroidViewModel {

    private CutOffPaymentsRepository cutOffPaymentsRepository;
    private LiveData<List<CutOffPayments>> toSyncListCutOffPaymentsLiveData;

    public CutOffPaymentsViewModel(Application application){
        super(application);
        cutOffPaymentsRepository = new CutOffPaymentsRepository(application);
        toSyncListCutOffPaymentsLiveData = cutOffPaymentsRepository.fetchCompleteCutOffPaymentsToSync();
    }

    public void insert(CutOffPayments cutOffPayments){
        cutOffPaymentsRepository.insert(cutOffPayments);
    }

    public void update(CutOffPayments cutOffPayments){
        cutOffPaymentsRepository.update(cutOffPayments);
    }

    public List<CutOffPayments> fetchByCutOffId(int cutOffId) throws ExecutionException, InterruptedException {
        return cutOffPaymentsRepository.fetchByCutOffId(cutOffId);
    }

    public LiveData<List<CutOffPayments>> fetchCompleteCutOffPaymentsToSync(){
        return toSyncListCutOffPaymentsLiveData;
    }

    public void updateCutOffPaymentsSentToServer(int cutOffPaymentId){
        cutOffPaymentsRepository.updateCutOffPaymentsSentToServer(cutOffPaymentId);
    }

}
