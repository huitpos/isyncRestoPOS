package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.CashFundDenomination;

import java.util.List;

@Dao
public interface CashFundDenominationDAO {

    @Insert
    void insert(CashFundDenomination cashFundDenomination);

    @Update
    void update(CashFundDenomination cashFundDenomination);

    @Query("SELECT * FROM cashFundDenomination WHERE isSentToServer = 0")
    LiveData<List<CashFundDenomination>> fetchCompleteCashFundDenominationToSync();

    @Query("UPDATE cashFundDenomination SET isSentToServer = 1 WHERE id = :cashFundDenominationId")
    void updateCashFundDenominationSentToServer(int cashFundDenominationId);

    @Query("SELECT * FROM cashFundDenomination WHERE cashFundId = :cashFundId")
    List<CashFundDenomination> fetchByCashFundId(int cashFundId);

}
