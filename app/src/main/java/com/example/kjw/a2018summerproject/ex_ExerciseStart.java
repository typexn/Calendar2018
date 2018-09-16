package com.example.kjw.a2018summerproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ex_ExerciseStart extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_exercisestart);

        Intent getRoutineFromCycle = getIntent();
        final ArrayList<ex_ExerciseRoutine> nth_routine = (ArrayList<ex_ExerciseRoutine>) getRoutineFromCycle.getSerializableExtra("routine");
        final int nth_counting = getRoutineFromCycle.getIntExtra("count",1);
        final ArrayList<ex_ExerciseCycle> getCycleFromStart = (ArrayList<ex_ExerciseCycle>) getRoutineFromCycle.getSerializableExtra("cycle");

        final int nowExerciseCounting = 0;

        TextView exerciseTitle = (TextView) findViewById(R.id.ex_exercisestart_text_title);
        exerciseTitle.setText(getCycleFromStart.get(0).exerciseTitle);

        TextView exerciseWeight = (TextView) findViewById(R.id.ex_exercisestart_text_weight_amount);
        String forWeightText = Double.toString(getCycleFromStart.get(0).exerciseWeight);
        exerciseWeight.setText(forWeightText);

        TextView exerciseCount = (TextView) findViewById(R.id.ex_exercisestart_text_count_amount);
        String forCountText = Integer.toString(getCycleFromStart.get(0).exerciseCount);
        exerciseCount.setText(forCountText);

        TextView exerciseTime = (TextView) findViewById(R.id.ex_exercisestart_text_time_amount);
        String forTimeText = Integer.toString(getCycleFromStart.get(0).exerciseTimeSecond);
        exerciseCount.setText(forTimeText);



        ImageButton nextButton = (ImageButton) findViewById(R.id.ex_exercisestart_image_preprocess);
        nextButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(nth_routine.get(nth_counting).cycleCounting == nowExerciseCounting)
                {
                    Intent goToExerciseFinish = new Intent(ex_ExerciseStart.this, ex_ExerciseFinish.class);
                    startActivity(goToExerciseFinish);
                }
                else
                {
                    Intent goToBreakTime = new Intent(ex_ExerciseStart.this,ex_Breaktime.class);
                    goToBreakTime.putExtra("routine",nth_routine);
                    goToBreakTime.putExtra("count",nth_counting);
                    goToBreakTime.putExtra("nowcount",nowExerciseCounting);
                    startActivityForResult(goToBreakTime,3000);
                }
            }
        });
    }

    public void backBtnClicked(View v) {
        Intent backToExerciseCycle = new Intent(ex_ExerciseStart.this,ex_Cycle.class);
        startActivity(backToExerciseCycle);
    }

}
