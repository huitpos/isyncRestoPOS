package com.example.isyncpos.payload;

public class ActivateMachineDeviceIdPayload {

    private String product_key, serial, model, android_id, manufacturer, board;
    private int device_id;

    public ActivateMachineDeviceIdPayload(String product_key, String serial, String model, String android_id, String manufacturer, String board, int device_id) {
        this.product_key = product_key;
        this.serial = serial;
        this.model = model;
        this.android_id = android_id;
        this.manufacturer = manufacturer;
        this.board = board;
        this.device_id = device_id;
    }

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(String android_id) {
        this.android_id = android_id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

}
