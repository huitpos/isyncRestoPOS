package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.isyncpos.entity.Orders;
import com.example.isyncpos.repositories.OrdersRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class OrdersViewModel extends AndroidViewModel {

    private MutableLiveData<List<Orders>> currentTransactionOrders = new MutableLiveData<>();
    private OrdersRepository ordersRepository;
    private LiveData<List<Orders>> toSyncOrderLiveData;

    public OrdersViewModel(Application application){
        super(application);
        ordersRepository = new OrdersRepository(application);
        toSyncOrderLiveData = ordersRepository.fetchCompleteOrderToSync();
    }

    public Void insert(Orders orders) throws ExecutionException, InterruptedException {
        return ordersRepository.insert(orders);
    }

    public Long insertTakeOrder(Orders orders) throws ExecutionException, InterruptedException {
        return ordersRepository.insertTakeOrder(orders);
    }

    public Void update(Orders orders) throws ExecutionException, InterruptedException {
        return ordersRepository.update(orders);
    }

    public List<Orders> fetchTransactionOrders(int transactionId) throws ExecutionException, InterruptedException {
        return ordersRepository.fetchTransactionOrders(transactionId);
    }

    public List<Orders> fetchTransactionOrdersBackOut(int transactionId) throws ExecutionException, InterruptedException {
        return ordersRepository.fetchTransactionOrdersBackOut(transactionId);
    }

    public List<Orders> fetchCompleteOrderToUpload() throws ExecutionException, InterruptedException {
        return ordersRepository.fetchCompleteOrderToUpload();
    }

    public List<Orders> fetchTransactionOrdersWithVoid(int transactionId) throws ExecutionException, InterruptedException {
        return ordersRepository.fetchTransactionOrdersWithVoid(transactionId);
    }

    public void clearTransactionOrders(int transactionId){
        ordersRepository.clearTransactionOrders(transactionId);
    }

    public Orders fetchTransactionOrder(int transactionId, int productId) throws ExecutionException, InterruptedException {
        return ordersRepository.fetchTransactionOrder(transactionId, productId);
    }

    public Orders fetchTransactionOrderWithReturn(int transactionId, int productId) throws ExecutionException, InterruptedException {
        return ordersRepository.fetchTransactionOrderWithReturn(transactionId, productId);
    }

    public Orders fetchOrder(int orderId) throws ExecutionException, InterruptedException {
        return ordersRepository.fetchOrder(orderId);
    }

    public void setCurrentTransactionOrders(List<Orders> orders){
        this.currentTransactionOrders.setValue(orders);
    }

    public MutableLiveData<List<Orders>> getCurrentTransactionOrders(){
        if(currentTransactionOrders == null){
            currentTransactionOrders.setValue(null);
        }
        return currentTransactionOrders;
    }

    public LiveData<List<Orders>> fetchCompleteOrderToSync(){
        return toSyncOrderLiveData;
    }

    public void updateOrderSentToServer(int orderId){
        ordersRepository.updateOrderSentToServer(orderId);
    }

}
