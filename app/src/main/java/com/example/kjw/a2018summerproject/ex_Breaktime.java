package com.example.kjw.a2018summerproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class ex_Breaktime extends AppCompatActivity {
    Chronometer cm;
    TextView miliTextView;
    LinearLayout resurtlayout;
    int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_breaktime);

        cm = (Chronometer)findViewById(R.id.ex_breaktime_text_timer);
        miliTextView = (TextView)findViewById(R.id.TextView1) ;
//        resurtlayout = (LinearLayout)findViewById(R.id.)
        final ToggleButton tb=(ToggleButton)findViewById(R.id.ex_breaktime_image_stoptime);
        tb.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        if (tb.isChecked()){
                            cm.setBase(SystemClock.elapsedRealtime());
                            cm.start();
                            num=1;
                        }
                        else {
                            cm.stop();
                        }
                    }
                }
        );



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
