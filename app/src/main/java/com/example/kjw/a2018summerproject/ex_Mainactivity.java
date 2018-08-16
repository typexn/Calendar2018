package com.example.kjw.a2018summerproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.sql.Time;
import java.util.Date;

/**
 * Created by KJW on 2018-08-15.
 */

public class ex_Mainactivity extends AppCompatActivity {

    Button buttonReport;
    Button buttonHelp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_mainactivity);

        //mainReport로 가는 버튼
        buttonReport = (Button) findViewById(R.id.ex_main_button_report);
        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToExerciseReport = new Intent(ex_Mainactivity.this, ex_ExerciseReport.class);
                startActivity(goToExerciseReport);
            }
        });

        buttonHelp = (Button) findViewById(R.id.ex_main_button_help);
        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToExerciseHelp = new Intent(ex_Mainactivity.this, ex_Cycle.class);
                /*
                    잠시 Cycle로 넘어가게 설정
                 */
                startActivity(goToExerciseHelp);
            }
        });


    }

    /*
        현재 날짜와 같으면 ex_Routine 실행
        이전이면 ex_lastresult 실행
     */
    long now = System.currentTimeMillis();
    Date date = new Date(now);

}
