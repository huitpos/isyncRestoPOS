package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.Branch;
import com.example.isyncpos.repositories.BranchRepository;

import java.util.concurrent.ExecutionException;

public class BranchViewModel extends AndroidViewModel {

    private BranchRepository branchRepository;
    private LiveData<Branch> branchLiveData;

    public BranchViewModel(Application application){
        super(application);
        branchRepository = new BranchRepository(application);
        branchLiveData = branchRepository.fetchBranchInformation();
    }

    public void insert(Branch branch){
        branchRepository.insert(branch);
    }

    public Branch fetch() throws ExecutionException, InterruptedException {
        return branchRepository.fetch();
    }

    public void remove(Branch branch){
        branchRepository.remove(branch);
    }

    public void update(Branch branch){
        branchRepository.update(branch);
    }

    public LiveData<Branch> fetchBranchInformation(){
        return branchLiveData;
    }

}
