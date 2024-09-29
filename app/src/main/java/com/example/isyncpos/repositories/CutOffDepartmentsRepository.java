package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.CutOffDepartmentsDAO;
import com.example.isyncpos.entity.CutOffDepartments;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CutOffDepartmentsRepository {

    private CutOffDepartmentsDAO cutOffDepartmentsDAO;
    private LiveData<List<CutOffDepartments>> toSyncListCutOffDepartmentsLiveData;

    public CutOffDepartmentsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        cutOffDepartmentsDAO = posDatabase.cutOffDepartmentsDAO();
        toSyncListCutOffDepartmentsLiveData = cutOffDepartmentsDAO.fetchCompleteCutOffDepartmentsToSync();
    }

    public void insert(CutOffDepartments cutOffDepartments){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cutOffDepartmentsDAO.insert(cutOffDepartments);
            }
        });
    }

    public void update(CutOffDepartments cutOffDepartments){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cutOffDepartmentsDAO.update(cutOffDepartments);
            }
        });
    }

    public List<CutOffDepartments> fetchByCutOffId(int cutOffId) throws ExecutionException, InterruptedException {
        Callable<List<CutOffDepartments>> callable = new Callable<List<CutOffDepartments>>() {
            @Override
            public List<CutOffDepartments> call() throws Exception {
                return cutOffDepartmentsDAO.fetchByCutOffId(cutOffId);
            }
        };
        Future<List<CutOffDepartments>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<CutOffDepartments>> fetchCompleteCutOffDepartmentsToSync(){
        return toSyncListCutOffDepartmentsLiveData;
    }

    public void updateCutOffDepartmentsSentToServer(int cutOffDepartmentId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cutOffDepartmentsDAO.updateCutOffDepartmentsSentToServer(cutOffDepartmentId);
            }
        });
    }

}
