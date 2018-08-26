package com.example.kjw.a2018summerproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class ex_ExerciseStart extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_exercisestart);

        Intent getRoutineFromCycle = getIntent();
        final ArrayList<ex_ExerciseRoutine> nth_routine = (ArrayList<ex_ExerciseRoutine>) getRoutineFromCycle.getSerializableExtra("routine");

        ImageButton nextButton = (ImageButton) findViewById(R.id.ex_exercisestart_image_preprocess);
        nextButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });
    }

    public void backBtnClicked(View v) {
        Intent backToExerciseCycle = new Intent(ex_ExerciseStart.this,ex_Cycle.class);
        startActivity(backToExerciseCycle);
    }

}
