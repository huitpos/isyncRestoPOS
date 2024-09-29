package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.isyncpos.entity.Departments;
import com.example.isyncpos.repositories.DepartmentsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DepartmentsViewModel extends AndroidViewModel {

    private DepartmentsRepository departmentsRepository;

    private LiveData<List<Departments>> allDepartments;

    public DepartmentsViewModel(Application application) {
        super(application);
        departmentsRepository = new DepartmentsRepository(application);
        allDepartments = departmentsRepository.fetchAll();
    }

    public void insert(Departments departments){
        departmentsRepository.insert(departments);
    }

    public LiveData<List<Departments>> fetchAll() {
        return allDepartments;
    }

    public Departments fetchDepartment(int departmentId) throws ExecutionException, InterruptedException {
        return departmentsRepository.fetchDepartment(departmentId);
    }

    public void update(Departments departments){
        departmentsRepository.update(departments);
    }

    public List<Departments> fetchAllDepartmentInformation() throws ExecutionException, InterruptedException {
        return departmentsRepository.fetchAllDepartmentInformation();
    }

}
