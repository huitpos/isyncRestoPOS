package com.example.isyncpos.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class Cache {

    private SharedPreferences sharedPreferences;

    public Cache(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public void saveString(String key, String value){
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key){
        return sharedPreferences.getString(key, "DEFAULT_VALUE");
    }

    public void clearString(String key){
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public void clearCache(){
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
