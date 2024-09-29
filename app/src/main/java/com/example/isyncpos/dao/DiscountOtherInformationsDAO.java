package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.entity.Discounts;
import com.example.isyncpos.entity.PaymentOtherInformations;

import java.util.List;

@Dao
public interface DiscountOtherInformationsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DiscountOtherInformations discountOtherInformations);

    @Update
    void update(DiscountOtherInformations discountOtherInformations);

    @Query("SELECT * FROM discountOtherInformations WHERE discountId = :discountId")
    List<DiscountOtherInformations> fetchByTransactionDiscountId(int discountId);

    @Query("SELECT * FROM discountOtherInformations WHERE isSentToServer = 0")
    LiveData<List<DiscountOtherInformations>> fetchCompleteDiscountOtherInformationsToSync();

    @Query("SELECT * FROM discountOtherInformations WHERE isCutOff = 0")
    List<DiscountOtherInformations> fetchDiscountOtherInformationToCutOff();

    @Query("UPDATE discountOtherInformations SET isSentToServer = 1 WHERE id = :discountOtherInformationId")
    void updateDiscountOtherInformationSentToServer(int discountOtherInformationId);

    @Query("SELECt * FROM discountOtherInformations WHERE transactionId = :transactionId")
    List<DiscountOtherInformations> fetchDiscountOtherInformationByTransactionId(int transactionId);

}
