package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.EndOffDayProducts;

import java.util.List;

@Dao
public interface EndOfDayProductsDAO {

    @Insert
    void insert(EndOffDayProducts endOffDayProducts);

    @Update
    void update(EndOffDayProducts endOffDayProducts);

    @Query("SELECT * FROM endOffDayProducts WHERE endOfDayId = :endOfDayId AND isSentToServer = 0")
    List<EndOffDayProducts> fetchByEndOfDayId(int endOfDayId);

    @Query("UPDATE endOffDayProducts SET isSentToServer = 1 WHERE id = :endOfDayProductId")
    void updateEndOffDayProductsSentToServer(int endOfDayProductId);

}
