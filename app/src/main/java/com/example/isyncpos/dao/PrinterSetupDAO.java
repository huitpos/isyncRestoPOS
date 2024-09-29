package com.example.isyncpos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.isyncpos.entity.PrinterSetup;

import java.util.List;

@Dao
public interface PrinterSetupDAO {

    @Insert
    void insert(PrinterSetup printerSetup);

    @Update
    void update(PrinterSetup printerSetup);

    @Query("SELECT * FROM printerSetup WHERE type = 'cashier' OR type = 'both'")
    LiveData<List<PrinterSetup>> fetchAllCashierType();

    @Query("SELECT * FROM printerSetup WHERE type = 'take order' OR type = 'both'")
    LiveData<List<PrinterSetup>> fetchAllTakeOrderType();

    @Query("UPDATE printerSetup SET deviceCount = 0")
    void clearDevices();

    @Query("SELECT * FROM printerSetup WHERE id = :printerSetupId")
    PrinterSetup fetchPrinterSetup(int printerSetupId);

    @Query("SELECT * FROM printerSetup")
    List<PrinterSetup> fetchPrinsterSetupList();

}
