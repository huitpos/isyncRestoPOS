package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.Sync;
import com.example.isyncpos.repositories.SyncRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SyncViewModel extends AndroidViewModel {

    private SyncRepository syncRepository;
    private LiveData<List<Sync>> syncLiveData;

    public SyncViewModel(Application application){
        super(application);
        syncRepository = new SyncRepository(application);
        syncLiveData = syncRepository.fetchAll();
    }

    public void insert(Sync sync){
        syncRepository.insert(sync);
    }

    public void update(Sync sync){
        syncRepository.update(sync);
    }

    public LiveData<List<Sync>> fetchAll() {
        return syncLiveData;
    }

    public List<Sync> fetchUnfinish() throws ExecutionException, InterruptedException {
        return syncRepository.fetchUnfinish();
    }

    public Sync fetchById(int id) throws ExecutionException, InterruptedException {
        return syncRepository.fetchById(id);
    }

    public List<Sync> fetchSyncList() throws ExecutionException, InterruptedException {
        return syncRepository.fetchSyncList();
    }

}
