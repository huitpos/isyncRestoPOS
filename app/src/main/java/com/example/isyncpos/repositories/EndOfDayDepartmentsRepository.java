package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.EndOfDayDepartmentsDAO;
import com.example.isyncpos.entity.EndOfDayDepartments;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EndOfDayDepartmentsRepository {

    private EndOfDayDepartmentsDAO endOfDayDepartmentsDAO;
    private LiveData<List<EndOfDayDepartments>> toSyncListEndOfDayDepartmentsLiveData;

    public EndOfDayDepartmentsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        endOfDayDepartmentsDAO = posDatabase.endOfDayDepartmentsDAO();
        toSyncListEndOfDayDepartmentsLiveData = endOfDayDepartmentsDAO.fetchCompleteEndOfDayDepartmentsToSync();
    }

    public void insert(EndOfDayDepartments endOfDayDepartments){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                endOfDayDepartmentsDAO.insert(endOfDayDepartments);
            }
        });
    }

    public LiveData<List<EndOfDayDepartments>> fetchCompleteEndOfDayDepartmentsToSync(){
        return toSyncListEndOfDayDepartmentsLiveData;
    }

    public void updateEndOfDayDepartmentsSentToServer(int endOfDayDepartmentId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                endOfDayDepartmentsDAO.updateEndOfDayDepartmentsSentToServer(endOfDayDepartmentId);
            }
        });
    }

}
