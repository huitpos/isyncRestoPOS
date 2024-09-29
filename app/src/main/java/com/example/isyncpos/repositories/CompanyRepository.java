package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.CompanyDAO;
import com.example.isyncpos.entity.Company;
import com.example.isyncpos.handler.POSDatabase;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompanyRepository {

    private CompanyDAO companyDAO;
    private LiveData<Company> companyLiveData;

    public CompanyRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        companyDAO = posDatabase.companyDAO();
        companyLiveData = companyDAO.fetchCompanyInformation();
    }

    public void insert(Company company){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                companyDAO.insert(company);
            }
        });
    }

    public Company fetch() throws ExecutionException, InterruptedException {
        Callable<Company> callable = new Callable<Company>() {
            @Override
            public Company call() throws Exception {
                return companyDAO.fetch();
            }
        };
        Future<Company> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void remove(Company company){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                companyDAO.remove(company);
            }
        });
    }

    public void update(Company company){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                companyDAO.update(company);
            }
        });
    }

    public LiveData<Company> fetchCompanyInformation(){
        return companyLiveData;
    }

}
