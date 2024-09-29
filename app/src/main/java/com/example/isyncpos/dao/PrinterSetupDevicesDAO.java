package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.isyncpos.entity.PrinterSetupDevices;

import java.util.List;

@Dao
public interface PrinterSetupDevicesDAO {

    @Insert
    void insert(PrinterSetupDevices printerSetupDevices);

    @Delete
    void remove(PrinterSetupDevices printerSetupDevices);

    @Query("SELECT * FROM printerSetupDevices WHERE printerSetupId = :printerSetupId")
    List<PrinterSetupDevices> fetchPrinterSetupDevicesPrinterSetupID(int printerSetupId);

    @Query("SELECT * FROM printerSetupDevices WHERE printerSetupId = :printerSetupId AND deviceId = :deviceId")
    PrinterSetupDevices validatePrinterSetupDevices(int printerSetupId, int deviceId);

    @Query("DELETE FROM printerSetupDevices")
    void removeAll();

    @Query("SELECT * FROM printerSetupDevices WHERE id = :printerSetupDeviceId")
    PrinterSetupDevices fetchPrinterSetupDevice(int printerSetupDeviceId);

    @Query("SELECT * FROM printerSetupDevices WHERE deviceId = :deviceId")
    List<PrinterSetupDevices> fetchPrinterSetupDevicesDeviceID(int deviceId);

}
