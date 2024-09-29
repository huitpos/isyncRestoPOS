package com.example.isyncpos.screen;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;

import com.example.isyncpos.R;

public class SecondScreenMainPresentation extends Presentation {

    private View view;

    public SecondScreenMainPresentation(Context context, Display display) {
        super(context, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.second_screen_main, null);
        setContentView(view);
    }

}
