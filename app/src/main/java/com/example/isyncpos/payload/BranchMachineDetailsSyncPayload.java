package com.example.isyncpos.payload;

public class BranchMachineDetailsSyncPayload {

    private int device_id, or_counter, x_reading_counter, z_reading_counter, void_counter;

    public BranchMachineDetailsSyncPayload(int device_id, int or_counter, int x_reading_counter, int z_reading_counter, int void_counter) {
        this.device_id = device_id;
        this.or_counter = or_counter;
        this.x_reading_counter = x_reading_counter;
        this.z_reading_counter = z_reading_counter;
        this.void_counter = void_counter;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getOr_counter() {
        return or_counter;
    }

    public void setOr_counter(int or_counter) {
        this.or_counter = or_counter;
    }

    public int getX_reading_counter() {
        return x_reading_counter;
    }

    public void setX_reading_counter(int x_reading_counter) {
        this.x_reading_counter = x_reading_counter;
    }

    public int getZ_reading_counter() {
        return z_reading_counter;
    }

    public void setZ_reading_counter(int z_reading_counter) {
        this.z_reading_counter = z_reading_counter;
    }

    public int getVoid_counter() {
        return void_counter;
    }

    public void setVoid_counter(int void_counter) {
        this.void_counter = void_counter;
    }

}
