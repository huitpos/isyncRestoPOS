package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.MachineDetails;
import com.example.isyncpos.repositories.MachineDetailsRepository;

import java.util.concurrent.ExecutionException;

public class MachineDetailsViewModel extends AndroidViewModel {

    private MachineDetailsRepository machineDetailsRepository;
    private LiveData<MachineDetails> machineDetailsLiveData;

    public MachineDetailsViewModel(Application application){
        super(application);
        machineDetailsRepository = new MachineDetailsRepository(application);
        machineDetailsLiveData = machineDetailsRepository.fetchMachineDetailsToSync();
    }

    public void insert(MachineDetails machineDetails){
        machineDetailsRepository.insert(machineDetails);
    }

    public Void update(MachineDetails machineDetails) throws ExecutionException, InterruptedException {
        return machineDetailsRepository.update(machineDetails);
    }

    public MachineDetails fetchMachineDetails() throws ExecutionException, InterruptedException {
        return machineDetailsRepository.fetchMachineDetails();
    }

    public MachineDetails fetchMachineDetailsToUpload() throws ExecutionException, InterruptedException {
        return machineDetailsRepository.fetchMachineDetailsToUpload();
    }

    public void remove(MachineDetails machineDetails){
        machineDetailsRepository.remove(machineDetails);
    }

    public void updateMachineDetailsSentToServer(int machineDetailsId){
        machineDetailsRepository.updateMachineDetailsSentToServer(machineDetailsId);
    }

    public LiveData<MachineDetails> fetchMachineDetailsToSync(){
        return machineDetailsLiveData;
    }

}
