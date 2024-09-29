package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.EndOfDayDepartments;
import com.example.isyncpos.repositories.EndOfDayDepartmentsRepository;

import java.util.List;

public class EndOfDayDepartmentsViewModel extends AndroidViewModel {

    private EndOfDayDepartmentsRepository endOfDayDepartmentsRepository;
    private LiveData<List<EndOfDayDepartments>> toSyncListEndOfDayDepartmentsLiveData;

    public EndOfDayDepartmentsViewModel(Application application){
        super(application);
        endOfDayDepartmentsRepository = new EndOfDayDepartmentsRepository(application);
        toSyncListEndOfDayDepartmentsLiveData = endOfDayDepartmentsRepository.fetchCompleteEndOfDayDepartmentsToSync();
    }

    public void insert(EndOfDayDepartments endOfDayDepartments){
        endOfDayDepartmentsRepository.insert(endOfDayDepartments);
    }

    public LiveData<List<EndOfDayDepartments>> fetchCompleteEndOfDayDepartmentsToSync(){
        return toSyncListEndOfDayDepartmentsLiveData;
    }

    public void updateEndOfDayDepartmentsSentToServer(int endOfDayDepartmentId){
        endOfDayDepartmentsRepository.updateEndOfDayDepartmentsSentToServer(endOfDayDepartmentId);
    }

}
