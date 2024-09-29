package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "printerSetup",
    indices = {
        @Index(value = {
            "type"
        })
    }
)
public class PrinterSetup {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "deviceCount")
    private int deviceCount;

    @ColumnInfo(name = "printCount")
    private int printCount;

    @ColumnInfo(name = "cashDrawer")
    private int cashDrawer;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "treg")
    private String treg;

    public PrinterSetup(String name, int deviceCount, int printCount, int cashDrawer, String type, String treg) {
        this.name = name;
        this.deviceCount = deviceCount;
        this.printCount = printCount;
        this.cashDrawer = cashDrawer;
        this.type = type;
        this.treg = treg;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(int deviceCount) {
        this.deviceCount = deviceCount;
    }

    public int getPrintCount() {
        return printCount;
    }

    public void setPrintCount(int printCount) {
        this.printCount = printCount;
    }

    public int getCashDrawer() {
        return cashDrawer;
    }

    public void setCashDrawer(int cashDrawer) {
        this.cashDrawer = cashDrawer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

}
