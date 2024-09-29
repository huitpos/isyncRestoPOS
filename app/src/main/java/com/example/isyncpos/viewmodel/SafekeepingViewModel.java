package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.isyncpos.entity.Safekeeping;
import com.example.isyncpos.repositories.SafekeepingRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SafekeepingViewModel extends AndroidViewModel {

    private SafekeepingRepository safekeepingRepository;

    private MutableLiveData<Double> safekeepingTotalLiveData = new MutableLiveData<>();

    private LiveData<List<Safekeeping>> safekeepingLiveData;

    public SafekeepingViewModel(Application application) {
        super(application);
        safekeepingRepository = new SafekeepingRepository(application);
        safekeepingLiveData = safekeepingRepository.fetchCompleteSafekeepingToSync();
    }

    public Long insert(Safekeeping safekeeping) throws ExecutionException, InterruptedException {
        return safekeepingRepository.insert(safekeeping);
    }

    public void update(Safekeeping safekeeping){
        safekeepingRepository.update(safekeeping);
    }

    public void updateSafekeepingSentToServer(int safeKeepingId){
        safekeepingRepository.updateSafekeepingSentToServer(safeKeepingId);
    }

    public Safekeeping fetchById(int safekeepingId) throws ExecutionException, InterruptedException {
        return safekeepingRepository.fetchById(safekeepingId);
    }

    public Double sumAllSafekeeping() throws ExecutionException, InterruptedException {
        return safekeepingRepository.sumAllSafekeeping();
    }

    public List<Safekeeping> fetchSafekeepingToCutOff() throws ExecutionException, InterruptedException {
        return safekeepingRepository.fetchSafekeepingToCutOff();
    }

    public List<Safekeeping> fetchSafekeepingToEndOfDay() throws ExecutionException, InterruptedException {
        return safekeepingRepository.fetchSafekeepingToEndOfDay();
    }

    public LiveData<List<Safekeeping>> fetchCompleteSafekeepingToSync() {
        return safekeepingLiveData;
    }

    public void setSafekeepingTotalLiveData(double value){
        safekeepingTotalLiveData.setValue(value);
    }

    public MutableLiveData<Double> getSafekeepingTotalLiveData(){
        if(safekeepingTotalLiveData == null){
            safekeepingTotalLiveData.setValue(0.00);
        }
        return safekeepingTotalLiveData;
    }

}
