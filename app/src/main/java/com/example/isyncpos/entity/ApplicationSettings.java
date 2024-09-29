package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "applicationSettings")
public class ApplicationSettings {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "automaticCashFund")
    private int automaticCashFund;

    @ColumnInfo(name = "showProductDescriptionMenu")
    private int showProductDescriptionMenu;

    @ColumnInfo(name = "printerNBRCharacterLine")
    private int printerNBRCharacterLine;

    @ColumnInfo(name = "printerWidthMM")
    private float printerWidthMM;

    @ColumnInfo(name = "firstInstall")
    private int firstInstall;

    public ApplicationSettings(int showProductDescriptionMenu, int printerNBRCharacterLine, float printerWidthMM, int firstInstall, int automaticCashFund) {
        this.showProductDescriptionMenu = showProductDescriptionMenu;
        this.printerNBRCharacterLine = printerNBRCharacterLine;
        this.printerWidthMM = printerWidthMM;
        this.firstInstall = firstInstall;
        this.automaticCashFund = automaticCashFund;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShowProductDescriptionMenu() {
        return showProductDescriptionMenu;
    }

    public void setShowProductDescriptionMenu(int showProductDescriptionMenu) {
        this.showProductDescriptionMenu = showProductDescriptionMenu;
    }

    public int getPrinterNBRCharacterLine() {
        return printerNBRCharacterLine;
    }

    public void setPrinterNBRCharacterLine(int printerNBRCharacterLine) {
        this.printerNBRCharacterLine = printerNBRCharacterLine;
    }

    public float getPrinterWidthMM() {
        return printerWidthMM;
    }

    public void setPrinterWidthMM(float printerWidthMM) {
        this.printerWidthMM = printerWidthMM;
    }

    public int getFirstInstall() {
        return firstInstall;
    }

    public void setFirstInstall(int firstInstall) {
        this.firstInstall = firstInstall;
    }

    public int getAutomaticCashFund() {
        return automaticCashFund;
    }

    public void setAutomaticCashFund(int automaticCashFund) {
        this.automaticCashFund = automaticCashFund;
    }

}
