package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.DeviceDetails;
import com.example.isyncpos.repositories.DeviceDetailsRepository;

import java.util.concurrent.ExecutionException;

public class DeviceDetailsViewModel extends AndroidViewModel {

    private DeviceDetailsRepository deviceDetailsRepository;

    public DeviceDetailsViewModel(Application application){
        super(application);
        deviceDetailsRepository = new DeviceDetailsRepository(application);
    }

    public void insert(DeviceDetails deviceDetails){
        deviceDetailsRepository.insert(deviceDetails);
    }

    public void update(DeviceDetails deviceDetails){
        deviceDetailsRepository.update(deviceDetails);
    }

    public DeviceDetails fetch() throws ExecutionException, InterruptedException {
        return deviceDetailsRepository.fetch();
    }

}
