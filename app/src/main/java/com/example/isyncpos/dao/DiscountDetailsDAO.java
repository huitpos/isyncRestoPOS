package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.DiscountDetails;

import java.util.List;

@Dao
public interface DiscountDetailsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DiscountDetails discountDetails);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertTakeOrderDiscountDetails(DiscountDetails discountDetails);

    @Update
    void update(DiscountDetails discountDetails);

    @Query("SELECT * FROM discountDetails WHERE transactionId = :transactionId AND isVoid = 0")
    List<DiscountDetails> fetchDiscountDetailsByTransactionId(int transactionId);

    @Query("SELECT * FROM discountDetails WHERE transactionId = :transactionId")
    List<DiscountDetails> fetchDiscountDetailsByTransactionIdWithVoid(int transactionId);

    @Query("SELECT * FROM discountDetails WHERE discountId = :discountId")
    List<DiscountDetails> fetchDiscountDetailsByDiscountIdWithVoid(int discountId);

    @Query("SELECT * FROM discountDetails WHERE discountId = :discountId AND isVoid = 0")
    List<DiscountDetails> fetchDiscountDetailsByDiscountId(int discountId);

    @Query("SELECT * FROM discountDetails WHERE isSentToServer = 0")
    LiveData<List<DiscountDetails>> fetchCompleteDiscountDetailsToSync();

    @Query("SELECT * FROM discountDetails WHERE isSentToServer = 0")
    List<DiscountDetails> fetchCompleteDiscountDetailsToUpload();

    @Query("UPDATE discountDetails SET isSentToServer = 1 WHERE id = :discountDetailsId")
    void updateDiscountDetailsSentToServer(int discountDetailsId);

}
