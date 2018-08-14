package com.example.kjw.a2018summerproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ex_ExerciseStart extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_exercisestart);
    }
    public void backBtnClicked(View v) {
        Intent intent = new Intent(ex_ExerciseStart.this,ex_Cycle.class);

    }
}
