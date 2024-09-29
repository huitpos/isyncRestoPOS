package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.ApplicationSettings;
import com.example.isyncpos.repositories.ApplicationSettingsRepository;

import java.util.concurrent.ExecutionException;

public class ApplicationSettingsViewModel extends AndroidViewModel {

    private ApplicationSettingsRepository applicationSettingsRepository;

    public ApplicationSettingsViewModel(Application application){
        super(application);
        applicationSettingsRepository = new ApplicationSettingsRepository(application);
    }

    public ApplicationSettings fetch() throws ExecutionException, InterruptedException {
        return applicationSettingsRepository.fetch();
    }

    public void update(ApplicationSettings applicationSettings){
        applicationSettingsRepository.update(applicationSettings);
    }

    public void insert(ApplicationSettings applicationSettings){
        applicationSettingsRepository.insert(applicationSettings);
    }

}
