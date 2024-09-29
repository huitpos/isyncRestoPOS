package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.Devices;
import com.example.isyncpos.repositories.DevicesRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DevicesViewModel extends AndroidViewModel {

    private DevicesRepository devicesRepository;
    private LiveData<List<Devices>> devicesLiveData;

    public DevicesViewModel(Application application) {
        super(application);
        devicesRepository = new DevicesRepository(application);
        devicesLiveData = devicesRepository.fetchAll();
    }

    public void insert(Devices devices){
        devicesRepository.insert(devices);
    }

    public void update(Devices devices){
        devicesRepository.update(devices);
    }

    public Devices fetchDeviceSerialNumber(String serialNumber) throws ExecutionException, InterruptedException {
        return devicesRepository.fetchDeviceSerialNumber(serialNumber);
    }

    public void removeDevice(int id){
        devicesRepository.removeDevice(id);
    }

    public Devices validateDevice(String name, String serialNumber) throws ExecutionException, InterruptedException {
        return devicesRepository.validateDevice(name, serialNumber);
    }

    public Devices fetchDeviceID(int deviceId) throws ExecutionException, InterruptedException {
        return devicesRepository.fetchDeviceID(deviceId);
    }

    public LiveData<List<Devices>> fetchAll(){
        return devicesLiveData;
    }

}
