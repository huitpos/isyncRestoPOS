package com.example.isyncpos.repositories;

import android.app.Application;


import androidx.lifecycle.LiveData;

import com.example.isyncpos.dao.OrdersDAO;
import com.example.isyncpos.entity.Orders;
import com.example.isyncpos.handler.POSDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OrdersRepository {

    private OrdersDAO ordersDAO;
    private LiveData<List<Orders>> toSyncOrderLiveData;

    public OrdersRepository(Application application){
        POSDatabase posDatabase = POSDatabase.getInstance(application);
        ordersDAO = posDatabase.ordersDAO();
        toSyncOrderLiveData = ordersDAO.fetchCompleteOrderToSync();
    }

    public Void insert(Orders orders) throws ExecutionException, InterruptedException {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return ordersDAO.insert(orders);
            }
        };
        Future<Void> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Long insertTakeOrder(Orders orders) throws ExecutionException, InterruptedException {
        Callable<Long> callable = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return ordersDAO.insertTakeOrder(orders);
            }
        };
        Future<Long> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Void update(Orders orders) throws ExecutionException, InterruptedException {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return ordersDAO.update(orders);
            }
        };
        Future<Void> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<Orders> fetchTransactionOrders(int transactionId) throws ExecutionException, InterruptedException {
        Callable<List<Orders>> callable = new Callable<List<Orders>>() {
            @Override
            public List<Orders> call() throws Exception {
                return ordersDAO.fetchTransactionOrders(transactionId);
            }
        };
        Future<List<Orders>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<Orders> fetchTransactionOrdersBackOut(int transactionId) throws ExecutionException, InterruptedException {
        Callable<List<Orders>> callable = new Callable<List<Orders>>() {
            @Override
            public List<Orders> call() throws Exception {
                return ordersDAO.fetchTransactionOrdersBackOut(transactionId);
            }
        };
        Future<List<Orders>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<Orders> fetchCompleteOrderToUpload() throws ExecutionException, InterruptedException {
        Callable<List<Orders>> callable = new Callable<List<Orders>>() {
            @Override
            public List<Orders> call() throws Exception {
                return ordersDAO.fetchCompleteOrderToUpload();
            }
        };
        Future<List<Orders>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<Orders> fetchTransactionOrdersWithVoid(int transactionId) throws ExecutionException, InterruptedException {
        Callable<List<Orders>> callable = new Callable<List<Orders>>() {
            @Override
            public List<Orders> call() throws Exception {
                return ordersDAO.fetchTransactionOrdersWithVoid(transactionId);
            }
        };
        Future<List<Orders>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void clearTransactionOrders(int transactionId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                ordersDAO.clearTransactionOrders(transactionId);
            }
        });
    }

    public Orders fetchTransactionOrder(int transactionId, int productId) throws ExecutionException, InterruptedException {
        Callable<Orders> callable = new Callable<Orders>() {
            @Override
            public Orders call() throws Exception {
                return ordersDAO.fetchTransactionOrder(transactionId, productId);
            }
        };
        Future<Orders> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Orders fetchTransactionOrderWithReturn(int transactionId, int productId) throws ExecutionException, InterruptedException {
        Callable<Orders> callable = new Callable<Orders>() {
            @Override
            public Orders call() throws Exception {
                return ordersDAO.fetchTransactionOrderWithReturn(transactionId, productId);
            }
        };
        Future<Orders> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Orders fetchOrder(int orderId) throws ExecutionException, InterruptedException {
        Callable<Orders> callable = new Callable<Orders>() {
            @Override
            public Orders call() throws Exception {
                return ordersDAO.fetchOrder(orderId);
            }
        };
        Future<Orders> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<Orders>> fetchCompleteOrderToSync(){
        return toSyncOrderLiveData;
    }

    public void updateOrderSentToServer(int orderId){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                ordersDAO.updateOrderSentToServer(orderId);
            }
        });
    }

}
