package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.Discounts;

import java.util.List;

@Dao
public interface DiscountsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Discounts discounts);

    @Update
    void update(Discounts discounts);

    @Query("SELECT * FROM discounts WHERE transactionId = :transactionId AND isVoid = 0")
    List<Discounts> fetchDiscountsByTransactionId(int transactionId);

    @Query("SELECT * FROM discounts WHERE id = :discountId")
    Discounts fetchDiscountByDiscountId(int discountId);

    @Query("SELECT * FROM discounts WHERE transactionId = :transactionId")
    List<Discounts> fetchDiscountsByTransactionIdWithVoid(int transactionId);

    @Query("SELECT * FROM discounts WHERE isSentToServer = 0")
    LiveData<List<Discounts>> fetchCompleteDiscountsToSync();

    @Query("SELECT * FROM discounts WHERE isSentToServer = 0")
    List<Discounts> fetchCompleteDiscountsToUpload();

    @Query("UPDATE discounts SET isSentToServer = 1 WHERE id = :discountId")
    void updateDiscountSentToServer(int discountId);

}
