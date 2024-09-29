package com.example.isyncpos.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.DepartmentsDAO;
import com.example.isyncpos.entity.Departments;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DepartmentsRepository {

    private DepartmentsDAO departmentsDAO;

    private LiveData<List<Departments>> allDepartments;
    public DepartmentsRepository(Application application) {
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        departmentsDAO = posDatabase.departmentsDAO();
        allDepartments = departmentsDAO.fetchAll();
    }

    public void insert(Departments departments){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                departmentsDAO.insert(departments);
            }
        });
    }

    public LiveData<List<Departments>> fetchAll() {
        return allDepartments;
    }

    public Departments fetchDepartment(int departmentId) throws ExecutionException, InterruptedException {
        Callable<Departments> callable = new Callable<Departments>() {
            @Override
            public Departments call() throws Exception {
                return departmentsDAO.fetchDepartment(departmentId);
            }
        };
        Future<Departments> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void update(Departments departments){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                departmentsDAO.update(departments);
            }
        });
    }

    public List<Departments> fetchAllDepartmentInformation() throws ExecutionException, InterruptedException {
        Callable<List<Departments>> callable = new Callable<List<Departments>>() {
            @Override
            public List<Departments> call() throws Exception {
                return departmentsDAO.fetchAllDepartmentInformation();
            }
        };
        Future<List<Departments>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
