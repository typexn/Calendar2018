package com.example.kjw.a2018summerproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.util.Calendar;

public class Test_MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Button button_tab1 = findViewById(R.id.button_tab1);
//        Button button_tab2 = findViewById(R.id.button_tab2);
//        Button button_tab3 = findViewById(R.id.button_tab3);
//        Button button_tab4 = findViewById(R.id.button_tab4);
//        Button button_tab5 = findViewById(R.id.button_tab5);
//
//        button_tab1.setOnClickListener(this);
//        button_tab2.setOnClickListener(this);
//        button_tab3.setOnClickListener(this);
//        button_tab4.setOnClickListener(this);
//        button_tab5.setOnClickListener(this);

        callFragment(1);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.button_tab1:
//                callFragment(1);
//                break;
//            case R.id.button_tab2:
//                callFragment(2);
//                break;
//            case R.id.button_tab3:
//                callFragment(3);
//                break;
//            case R.id.button_tab4:
//                callFragment(4);
//                break;
//            case R.id.button_tab5:
//                callFragment(5);
//                break;
        }
    }

    private void callFragment(int position){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch  (position){
//            case 1:
//                CalendarMiddleFragment monthlyFragment = new CalendarMiddleFragment();
//                transaction.replace(R.id.framLayout_container, monthlyFragment);
//                transaction.commit();
//                break;
//            case 2:
//                ScheduleFragment scheduleFragment = new ScheduleFragment();
//                transaction.replace(R.id.framLayout_container, scheduleFragment);
//                transaction.commit();
//                break;
//            case 3:
//                DiaryFragment diaryFragment = new DiaryFragment();
//                transaction.replace(R.id.framLayout_container, diaryFragment);
//                transaction.commit();
//                break;
//            case 4:
//                ExerciseFragment exerciseFragment = new ExerciseFragment();
//                transaction.replace(R.id.framLayout_container, exerciseFragment);
//                transaction.commit();
//                break;
//            case 5:
//                AccountFragment accountFragment = new AccountFragment();
//                transaction.replace(R.id.framLayout_container, accountFragment);
//                transaction.commit();
//                break;
        }
    }
}