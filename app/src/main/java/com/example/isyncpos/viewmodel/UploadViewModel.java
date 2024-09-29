package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.Upload;
import com.example.isyncpos.repositories.UploadRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UploadViewModel extends AndroidViewModel {

    private UploadRepository uploadRepository;

    public UploadViewModel(Application application){
        super(application);
        uploadRepository = new UploadRepository(application);
    }

    public void insert(Upload upload){
        uploadRepository.insert(upload);
    }

    public List<Upload> fetchAll() throws ExecutionException, InterruptedException {
        return uploadRepository.fetchAll();
    }

}
