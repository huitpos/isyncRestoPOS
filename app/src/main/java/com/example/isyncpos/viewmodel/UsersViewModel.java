package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.Users;
import com.example.isyncpos.repositories.UsersRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UsersViewModel extends AndroidViewModel {

    private UsersRepository usersRepository;

    public UsersViewModel(Application application) {
        super(application);
        usersRepository = new UsersRepository(application);
    }

    public void insert(Users users){
        usersRepository.insert(users);
    }

    public void update(Users users){
        usersRepository.update(users);
    }

    public Users findByUsernameOrEmail(String username) throws ExecutionException, InterruptedException {
        return usersRepository.findByUsernameOrEmail(username);
    }

    public Users authenticate(String credential) throws ExecutionException, InterruptedException {
        return usersRepository.authenticate(credential);
    }

    public Users fetchById(int id) throws ExecutionException, InterruptedException {
        return usersRepository.fetchById(id);
    }

    public List<Users> fetchAll() throws ExecutionException, InterruptedException {
        return usersRepository.fetchAll();
    }


}
