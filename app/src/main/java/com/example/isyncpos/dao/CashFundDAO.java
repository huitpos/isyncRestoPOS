package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.CashFund;

import java.util.List;

@Dao
public interface CashFundDAO {

    @Insert
    Long insert(CashFund cashFund);

    @Update
    void update(CashFund cashFund);

    @Query("SELECT * FROM cashFund WHERE id = :cashFundId")
    CashFund fetchById(int cashFundId);

    @Query("SELECT * FROM cashFund WHERE isSentToServer = 0")
    LiveData<List<CashFund>> fetchCompleteCashFundToSync();

    @Query("UPDATE cashFund SET isSentToServer = 1 WHERE id = :cashFundId")
    void updateCashFundSentToServer(int cashFundId);

    @Query("SELECT * FROM cashFund WHERE isCutOff = 0")
    List<CashFund> fetchCashFundToCutOff();

    @Query("SELECT * FROM cashFund WHERE isCutOff = 1 AND endOfDayId = 0")
    List<CashFund> fetchCasFundToEndOfDay();

    @Query("SELECT * FROM cashFund WHERE isCutOff = 0")
    CashFund checkForCashFund();

}
