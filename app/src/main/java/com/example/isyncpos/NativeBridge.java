package com.example.isyncpos;

public class NativeBridge {

    static {
        System.loadLibrary("native-lib");
    }

    public native String getAlgorithm();
    public native String getMode();
    public native String getIV();
    public native String getSecretKey();

}
