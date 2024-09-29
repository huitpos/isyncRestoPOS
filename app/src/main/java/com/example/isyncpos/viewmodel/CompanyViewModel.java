package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.Company;
import com.example.isyncpos.repositories.CompanyRepository;

import java.util.concurrent.ExecutionException;

public class CompanyViewModel extends AndroidViewModel {

    private CompanyRepository companyRepository;
    private LiveData<Company> companyLiveData;

    public CompanyViewModel(Application application){
        super(application);
        companyRepository = new CompanyRepository(application);
        companyLiveData = companyRepository.fetchCompanyInformation();
    }

    public void insert(Company company){
        companyRepository.insert(company);
    }

    public Company fetch() throws ExecutionException, InterruptedException {
        return companyRepository.fetch();
    }

    public void remove(Company company){
        companyRepository.remove(company);
    }

    public void update(Company company){
        companyRepository.update(company);
    }

    public LiveData<Company> fetchCompanyInformation(){
        return companyLiveData;
    }

}
