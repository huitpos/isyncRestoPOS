package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.Orders;

import java.util.List;

@Dao
public interface OrdersDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Void insert(Orders orders);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertTakeOrder(Orders orders);

    @Update
    Void update(Orders orders);

    @Query("SELECT * FROM orders WHERE transactionId = :transactionId AND isVoid = 0 AND isBackout = 0 ORDER BY id DESC")
    List<Orders> fetchTransactionOrders(int transactionId);

    @Query("SELECT * FROM orders WHERE transactionId = :transactionId AND isBackout = 1")
    List<Orders> fetchTransactionOrdersBackOut(int transactionId);

    @Query("SELECT * FROM orders WHERE transactionId = :transactionId AND isBackout = 0 ORDER BY id DESC")
    List<Orders> fetchTransactionOrdersWithVoid(int transactionId);

    @Query("DELETE FROM orders WHERE transactionId = :transactionId")
    void clearTransactionOrders(int transactionId);

    @Query("SELECT * FROM orders WHERE transactionId = :transactionId AND productId = :productId AND isVoid = 0 AND isBackout = 0 AND isReturn = 0")
    Orders fetchTransactionOrder(int transactionId, int productId);

    @Query("SELECT * FROM orders WHERE transactionId = :transactionId AND productId = :productId AND isVoid = 0 AND isBackout = 0")
    Orders fetchTransactionOrderWithReturn(int transactionId, int productId);

    @Query("SELECT * FROM orders WHERE id = :orderId")
    Orders fetchOrder(int orderId);

    @Query("SELECT * FROM orders WHERE isSentToServer = 0")
    LiveData<List<Orders>> fetchCompleteOrderToSync();

    @Query("SELECT * FROM orders WHERE isSentToServer = 0")
    List<Orders> fetchCompleteOrderToUpload();

    @Query("UPDATE orders SET isSentToServer = 1 WHERE id = :orderId")
    void updateOrderSentToServer(int orderId);

}
