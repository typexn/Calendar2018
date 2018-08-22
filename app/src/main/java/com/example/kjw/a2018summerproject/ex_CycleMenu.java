package com.example.kjw.a2018summerproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
//        final String[] ar = {"팔", "가슴", "상체", "하체", "전신"};
//        Spinner part_Spinner = (Spinner)findViewById(R.id.ex_cyclemenu_spinner_exercisepart);
//
//        final String getExerciseCyclePart;
//        ArrayAdapter<String> ex_cyclemenu_spinner_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ar);
//        part_Spinner.setAdapter(ex_cyclemenu_spinner_adapter);
//        part_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                switch (i)
//                case 0:
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        String getExerciseCyclePart = part_Spinner.getSelectedItem().toString();
        final EditText exerciseCycleWeight = (EditText) findViewById(R.id.ex_cyclemenu_edittext_weight);
        String getExerciseCycleWeight = exerciseCycleWeight.getText().toString();
        final EditText exerciseCycleNumber = (EditText) findViewById(R.id.ex_cyclemenu_edittext_number);
        String getExerciseCycleNumber = exerciseCycleNumber.getText().toString();
        final EditText exerciseCycleTime = (EditText) findViewById(R.id.ex_cyclemenu_edittext_time);
        String getExerciseCycleTime = exerciseCycleTime.getText().toString();
        final EditText exerciseCycleBreakTime = (EditText) findViewById(R.id.ex_cyclemenu_edittext_break);
        String getExerciseCycleBreakTime = exerciseCycleBreakTime.getText().toString();

        if(getExerciseCycleTitle == null)
        {
            getExerciseCycleTitle = "제목 없음";
        }
//        if(getExerciseCyclePart == null)
//        {
//            getExerciseCyclePart = "전신";
//        }
        if(getExerciseCycleWeight == null)
        {
            getExerciseCycleWeight = "0";
        }
        if(getExerciseCycleNumber == null)
        {
            getExerciseCycleNumber = "0";
        }
        if(getExerciseCycleTime == null)
        {
            getExerciseCycleTime = "0";
        }
        if(getExerciseCycleBreakTime == null)
        {
            getExerciseCycleBreakTime = "0";
        }

        Button saveValueToCycle = (Button) findViewById(R.id.ex_cyclemenu_button_next);
        saveValueToCycle.setOnClickListener(new Button.OnClickListener()
            {
            public void onClick(View v){

            }
        });
    }
}

