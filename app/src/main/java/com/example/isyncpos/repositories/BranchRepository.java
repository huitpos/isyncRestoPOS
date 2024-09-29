package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.BranchDAO;
import com.example.isyncpos.entity.Branch;
import com.example.isyncpos.handler.POSDatabase;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BranchRepository {

    private BranchDAO branchDAO;
    private LiveData<Branch> branchLiveData;

    public BranchRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        branchDAO = posDatabase.branchDAO();
        branchLiveData = branchDAO.fetchBranchInformation();
    }

    public void insert(Branch branch){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                branchDAO.insert(branch);
            }
        });
    }

    public Branch fetch() throws ExecutionException, InterruptedException {
        Callable<Branch> callable = new Callable<Branch>() {
            @Override
            public Branch call() throws Exception {
                return branchDAO.fetch();
            }
        };
        Future<Branch> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void remove(Branch branch){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                branchDAO.remove(branch);
            }
        });
    }

    public void update(Branch branch){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                branchDAO.update(branch);
            }
        });
    }

    public LiveData<Branch> fetchBranchInformation(){
        return branchLiveData;
    }

}
