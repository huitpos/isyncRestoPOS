package com.example.isyncpos.repositories;

import android.app.Application;

import com.example.isyncpos.dao.PayoutsDAO;
import com.example.isyncpos.entity.Payouts;
import com.example.isyncpos.handler.POSDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PayoutsRepository {

    private PayoutsDAO payoutsDAO;

    public PayoutsRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        payoutsDAO = posDatabase.payoutsDAO();
    }

    public void insert(Payouts payouts){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                payoutsDAO.insert(payouts);
            }
        });
    }

}
