package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.UploadDAO;
import com.example.isyncpos.entity.Upload;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UploadRepository {

    private UploadDAO uploadDAO;

    public UploadRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        uploadDAO = posDatabase.uploadDAO();
    }

    public void insert(Upload upload){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                uploadDAO.insert(upload);
            }
        });
    }

    public List<Upload> fetchAll() throws ExecutionException, InterruptedException {
        Callable<List<Upload>> callable = new Callable<List<Upload>>() {
            @Override
            public List<Upload> call() throws Exception {
                return uploadDAO.fetchAll();
            }
        };
        Future<List<Upload>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
