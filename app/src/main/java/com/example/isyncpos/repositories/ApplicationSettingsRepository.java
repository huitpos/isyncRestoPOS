package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.ApplicationSettingsDAO;
import com.example.isyncpos.entity.ApplicationSettings;
import com.example.isyncpos.handler.POSDatabase;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ApplicationSettingsRepository {

    private ApplicationSettingsDAO applicationSettingsDAO;

    public ApplicationSettingsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        applicationSettingsDAO = posDatabase.applicationSettingsDAO();
    }

    public ApplicationSettings fetch() throws ExecutionException, InterruptedException {
        Callable<ApplicationSettings> callable = new Callable<ApplicationSettings>() {
            @Override
            public ApplicationSettings call() throws Exception {
                return applicationSettingsDAO.fetch();
            }
        };
        Future<ApplicationSettings> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void insert(ApplicationSettings applicationSettings){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                applicationSettingsDAO.insert(applicationSettings);
            }
        });
    }

    public void update(ApplicationSettings applicationSettings){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                applicationSettingsDAO.update(applicationSettings);
            }
        });
    }

}
