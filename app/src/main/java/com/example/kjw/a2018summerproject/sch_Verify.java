package com.example.kjw.a2018summerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class sch_Verify extends AppCompatActivity {

    Button btnToEdit;
    TextView textTitle;
    TextView textLocation;
    TextView textStart;
    TextView textEnd;
    TextView textMemo;

    String title;
    String location;
    int startYear;
    int startMonth;
    int startDay;
    String startHour;
    String startMinute;
    int endYear;
    int endMonth;
    int endDay;
    String endHour;
    String endMinute;
    String memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sch_verify);

        btnToEdit = findViewById(R.id.sch_verify_button_edit);
        textTitle = findViewById(R.id.sch_verify_text_title);
        textLocation = findViewById(R.id.sch_verify_text_location);
        textStart = findViewById(R.id.sch_verify_text_startTime);
        textEnd = findViewById(R.id.sch_verify_text_endTime);
        textMemo = findViewById(R.id.sch_verify_text_memo);

        Intent getIntent = getIntent();
        title = getIntent.getStringExtra("title");
        location = getIntent.getStringExtra("location");
        startYear = getIntent.getIntExtra("startYear",2018);
        startMonth = getIntent.getIntExtra("startMonth",1);
        startDay = getIntent.getIntExtra("startDay",1);
        startHour = getIntent.getStringExtra("startHour");
        startMinute = getIntent.getStringExtra("startMinute");
        endYear = getIntent.getIntExtra("endYear",2018);
        endMonth = getIntent.getIntExtra("endMonth",1);
        endDay = getIntent.getIntExtra("endDay",1);
        endHour = getIntent.getStringExtra("endHour");
        endMinute = getIntent.getStringExtra("endMinute");
        memo = getIntent.getStringExtra("memo");

        textTitle.setText(title);
        textLocation.setText(location);
        textStart.setText(startYear + "년" + startMonth + "월" + startDay + "일  " + startHour + ":" + startMinute);
        textEnd.setText(endYear + "년" + endMonth + "월" + endDay + "일  " + endHour + ":" + endMinute);
        textMemo.setText(memo);

        btnToEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sch_Verify.this, sch_AddActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("location", location);
                intent.putExtra("memo", memo);

                intent.putExtra("startYear", startYear);
                intent.putExtra("startMonth", startMonth);
                intent.putExtra("startDay", startDay);
                intent.putExtra("startHour", startHour);
                intent.putExtra("startMinute", startMinute);

                intent.putExtra("endYear", endYear);
                intent.putExtra("endMonth", endMonth);
                intent.putExtra("endDay", endDay);
                intent.putExtra("endHour", endHour);
                intent.putExtra("endMinute", endMinute);

            }
        });
    }
}
