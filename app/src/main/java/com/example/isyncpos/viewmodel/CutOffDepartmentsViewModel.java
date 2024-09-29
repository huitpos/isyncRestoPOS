package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.CutOffDepartments;
import com.example.isyncpos.repositories.CutOffDepartmentsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CutOffDepartmentsViewModel extends AndroidViewModel {

    private CutOffDepartmentsRepository cutOffDepartmentsRepository;
    private LiveData<List<CutOffDepartments>> toSyncListCutOffDepartmentsLiveData;

    public CutOffDepartmentsViewModel(Application application){
        super(application);
        cutOffDepartmentsRepository = new CutOffDepartmentsRepository(application);
        toSyncListCutOffDepartmentsLiveData = cutOffDepartmentsRepository.fetchCompleteCutOffDepartmentsToSync();
    }

    public void insert(CutOffDepartments cutOffDepartments){
        cutOffDepartmentsRepository.insert(cutOffDepartments);
    }

    public void update(CutOffDepartments cutOffDepartments){
        cutOffDepartmentsRepository.update(cutOffDepartments);
    }

    public List<CutOffDepartments> fetchByCutOffId(int cutOffId) throws ExecutionException, InterruptedException {
        return cutOffDepartmentsRepository.fetchByCutOffId(cutOffId);
    }

    public LiveData<List<CutOffDepartments>> fetchCompleteCutOffDepartmentsToSync(){
        return toSyncListCutOffDepartmentsLiveData;
    }

    public void updateCutOffDepartmentsSentToServer(int cutOffDepartmentId){
        cutOffDepartmentsRepository.updateCutOffDepartmentsSentToServer(cutOffDepartmentId);
    }

}
