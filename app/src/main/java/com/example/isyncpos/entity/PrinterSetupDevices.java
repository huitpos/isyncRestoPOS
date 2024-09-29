package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "printerSetupDevices",
    indices = {
        @Index(value = {
            "printerSetupId",
            "deviceId"
        })
    }
)
public class PrinterSetupDevices {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "printerSetupId")
    private int printerSetupId;

    @ColumnInfo(name = "deviceId")
    private int deviceId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "treg")
    private String treg;

    public PrinterSetupDevices(int printerSetupId, int deviceId, String name, String treg) {
        this.printerSetupId = printerSetupId;
        this.deviceId = deviceId;
        this.name = name;
        this.treg = treg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrinterSetupId() {
        return printerSetupId;
    }

    public void setPrinterSetupId(int printerSetupId) {
        this.printerSetupId = printerSetupId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

}
