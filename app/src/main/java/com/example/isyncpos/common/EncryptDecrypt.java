package com.example.isyncpos.common;

import android.util.Base64;

import com.example.isyncpos.NativeBridge;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDecrypt {

    NativeBridge nativeBridge = new NativeBridge();
    private static EncryptDecrypt instance;

    public synchronized static EncryptDecrypt getInstance(){
        if(instance == null){
            instance = new EncryptDecrypt();
        }
        return instance;
    }

    public String encrypt(String value){
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(nativeBridge.getSecretKey().getBytes(), nativeBridge.getAlgorithm());
            Cipher cipher = Cipher.getInstance(nativeBridge.getMode());
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(nativeBridge.getIV().getBytes()));
            byte[] values = cipher.doFinal(value.getBytes());
            return Base64.encodeToString(values, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException |
                 NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String value){
        try {
            byte[] values = Base64.decode(value, Base64.DEFAULT);
            SecretKeySpec secretKeySpec = new SecretKeySpec(nativeBridge.getSecretKey().getBytes(), nativeBridge.getAlgorithm());
            Cipher cipher = Cipher.getInstance(nativeBridge.getMode());
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(nativeBridge.getIV().getBytes()));
            return new String(cipher.doFinal(values));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }


}
