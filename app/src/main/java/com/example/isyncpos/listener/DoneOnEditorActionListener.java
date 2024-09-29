package com.example.isyncpos.listener;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class DoneOnEditorActionListener implements View.OnFocusChangeListener {
    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if(hasFocus){
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
