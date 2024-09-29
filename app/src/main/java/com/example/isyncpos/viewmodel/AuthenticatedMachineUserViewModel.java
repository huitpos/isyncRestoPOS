package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.AuthenticatedMachineUser;
import com.example.isyncpos.repositories.AuthenticatedMachineUserRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AuthenticatedMachineUserViewModel extends AndroidViewModel {

    private AuthenticatedMachineUserRepository authenticatedMachineUserRepository;

    public AuthenticatedMachineUserViewModel(Application application){
        super(application);
        authenticatedMachineUserRepository = new AuthenticatedMachineUserRepository(application);
    }

    public AuthenticatedMachineUser fetch() throws ExecutionException, InterruptedException {
        return authenticatedMachineUserRepository.fetch();
    }

    public Long insert(AuthenticatedMachineUser authenticatedMachineUser) throws ExecutionException, InterruptedException {
        return authenticatedMachineUserRepository.insert(authenticatedMachineUser);
    }

    public void update(AuthenticatedMachineUser authenticatedMachineUser){
        authenticatedMachineUserRepository.update(authenticatedMachineUser);
    }

}
