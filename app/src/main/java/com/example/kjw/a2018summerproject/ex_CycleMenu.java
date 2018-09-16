package com.example.kjw.a2018summerproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import java.util.ArrayList;

import static android.R.color.transparent;
import static android.R.layout.simple_spinner_item;

public class ex_CycleMenu extends AppCompatActivity {

    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_cyclemenu);

        final EditText exerciseCycleTitle = (EditText) findViewById(R.id.ex_cyclemenu_edittext_name);
        String getExerciseCycleTitle = exerciseCycleTitle.getText().toString();
        Spinner part_Spinner = (Spinner)findViewById(R.id.ex_cyclemenu_spinner_exercisepart);
        part_Spinner.setSelection(0);
        final EditText exerciseCycleWeight = (EditText) findViewById(R.id.ex_cyclemenu_edittext_weight);
        String getExerciseCycleWeight = exerciseCycleWeight.getText().toString();
        exerciseCycleWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                String getExerciseCycleWeight = exerciseCycleWeight.getText().toString();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }
        });
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

        if(getExerciseCycleWeight == null)
        {
            getExerciseCycleWeight = "0.0";
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

        final String getExerciseCyclePart = part_Spinner.getSelectedItem().toString();
        final String takeExerciseCycleTitle = getExerciseCycleTitle;
        final String takeExerciseCyclePart = getExerciseCyclePart;
        final String takeExerciseCycleWeight = getExerciseCycleWeight;
        final String takeExerciseCycleNumber = getExerciseCycleNumber;
        final String takeExerciseCycleTime = getExerciseCycleTime;
        final String takeExerciseCycleBreakTime = getExerciseCycleBreakTime;

        Button saveValueToCycle = (Button) findViewById(R.id.ex_cyclemenu_button_save);
        saveValueToCycle.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v){
                Intent goToCycle = new Intent();
                goToCycle.putExtra("cycletitle",takeExerciseCycleTitle);
                goToCycle.putExtra("cyclepart",takeExerciseCyclePart);
                goToCycle.putExtra("cycleweight",takeExerciseCycleWeight);
                goToCycle.putExtra("cyclenumber",takeExerciseCycleNumber);
                goToCycle.putExtra("cycletime",takeExerciseCycleTime);
                goToCycle.putExtra("cyclebreaktime",takeExerciseCycleBreakTime);
                setResult(RESULT_OK,goToCycle);
                finish();
            }
        });
    }
}


