package com.example.isyncpos.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;

import com.example.isyncpos.R;

public class Font {

    private static Font instance;

    public static Font getInstance(){
        if(instance == null){
            instance = new Font();
        }
        return instance;
    }

    public Typeface getSanFranciscoRegularFont(Resources resources){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Typeface typeface = resources.getFont(R.font.sfuitextregular);
            return typeface;
        }
        return null;
    }

    public Typeface getSanFranciscoBoldFont(Resources resources){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Typeface typeface = resources.getFont(R.font.sfprotextbold);
            return typeface;
        }
        return null;
    }

}
