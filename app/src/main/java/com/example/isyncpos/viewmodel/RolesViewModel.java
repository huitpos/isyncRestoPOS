package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.Roles;
import com.example.isyncpos.repositories.RolesRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RolesViewModel extends AndroidViewModel {

    private RolesRepository rolesRepository;

    public RolesViewModel(Application application){
        super(application);
        rolesRepository = new RolesRepository(application);
    }

    public void insert(Roles roles){
        rolesRepository.insert(roles);
    }

    public List<Roles> fetchByUserId(int userId) throws ExecutionException, InterruptedException {
        return rolesRepository.fetchByUserId(userId);
    }

    public Void removeRoles() throws ExecutionException, InterruptedException {
        return rolesRepository.removeRoles();
    }

}
