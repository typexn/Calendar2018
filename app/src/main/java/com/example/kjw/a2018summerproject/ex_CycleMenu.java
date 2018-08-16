package com.example.kjw.a2018summerproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.kjw.a2018summerproject.R;

import static android.R.color.transparent;

public class ex_CycleMenu extends Dialog {

    private LinearLayout container;

    public ex_CycleMenu(@NonNull Context context) {
        super(context);
        setContentView(R.layout.activity_ex_cyclemenu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_cyclemenu);
    }
}

