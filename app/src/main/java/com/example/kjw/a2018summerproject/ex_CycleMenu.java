package com.example.kjw.a2018summerproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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

        final EditText exerciseCycleTitle = (EditText) findViewById(R.id.ex_cyclemenu_edittext_name);
        String getExerciseCycleTitle = exerciseCycleTitle.getText().toString();

        Spinner part_Spinner = (Spinner)findViewById(R.id.ex_cyclemenu_spinner_exercisepart);
        String getExerciseCyclePart = part_Spinner.getSelectedItem().toString();
    }
}

