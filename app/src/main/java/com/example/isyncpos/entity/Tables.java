package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "tables",
    indices = {
        @Index(value = {
            "coreId",
            "tableStat",
            "orderingNumber",
            "isSentToServer",
            "status"
        })
    }
)
public class Tables {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "coreId")
    private int coreId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "tableStat")
    private int tableStat;

    @ColumnInfo(name = "tableTypeId")
    private int tableTypeId;

    @ColumnInfo(name = "transactionId")
    private int transactionId;

    @ColumnInfo(name = "orderingToken")
    private String orderingToken;

    @ColumnInfo(name = "orderingNumber")
    private int orderingNumber;

    @ColumnInfo(name = "isSentToServer")
    private int isSentToServer;

    @ColumnInfo(name = "status")
    private int status;

    public Tables(int coreId, String name, int tableStat, int tableTypeId, int transactionId, String orderingToken, int orderingNumber, int isSentToServer, int status) {
        this.coreId = coreId;
        this.name = name;
        this.tableStat = tableStat;
        this.tableTypeId = tableTypeId;
        this.transactionId = transactionId;
        this.orderingToken = orderingToken;
        this.orderingNumber = orderingNumber;
        this.isSentToServer = isSentToServer;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoreId() {
        return coreId;
    }

    public void setCoreId(int coreId) {
        this.coreId = coreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTableStat() {
        return tableStat;
    }

    public void setTableStat(int tableStat) {
        this.tableStat = tableStat;
    }

    public int getTableTypeId() {
        return tableTypeId;
    }

    public void setTableTypeId(int tableTypeId) {
        this.tableTypeId = tableTypeId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getOrderingToken() {
        return orderingToken;
    }

    public void setOrderingToken(String orderingToken) {
        this.orderingToken = orderingToken;
    }

    public int getOrderingNumber() {
        return orderingNumber;
    }

    public void setOrderingNumber(int orderingNumber) {
        this.orderingNumber = orderingNumber;
    }

    public int getIsSentToServer() {
        return isSentToServer;
    }

    public void setIsSentToServer(int isSentToServer) {
        this.isSentToServer = isSentToServer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
