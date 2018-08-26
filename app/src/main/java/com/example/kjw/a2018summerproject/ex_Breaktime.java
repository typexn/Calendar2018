package com.example.kjw.a2018summerproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class ex_Breaktime extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_breaktime);

        Intent getRoutineFromExerciseStart = getIntent();
        final ArrayList<ex_ExerciseRoutine> nth_routine = (ArrayList<ex_ExerciseRoutine>) getRoutineFromExerciseStart.getSerializableExtra("routine");
        final int nth_counting = getRoutineFromExerciseStart.getExtras().getInt("count");
        final int nowExerciseCounting = getRoutineFromExerciseStart.getExtras().getInt("nowcount");

        TextView exerciseProcessCountTitle = (TextView) findViewById(R.id.ex_breaktime_text_processcount);
        exerciseProcessCountTitle.setText(nowExerciseCounting + nth_counting);
        TextView nextExerciseTitle = (TextView) findViewById(R.id.ex_breaktime_text_nextexercise);
        nextExerciseTitle.setText(nth_routine.get(nth_counting + 1).Cycle[nowExerciseCounting+1].exerciseTitle);
    }
}
