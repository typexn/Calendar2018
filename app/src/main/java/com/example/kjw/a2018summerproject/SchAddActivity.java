package com.example.kjw.a2018summerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kjw.a2018summerproject.R;

public class SchAddActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editLocation;
    EditText editMemo;
    TextView startDay;
    TextView startTime;
    TextView endDay;
    TextView endTime;
    Button btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sch_add);

        editTitle = findViewById(R.id.sch_add_edit_title);
        editLocation = findViewById(R.id.sch_add_edit_location);
        editMemo = findViewById(R.id.sch_add_edit_memo);
        startDay = findViewById(R.id.sch_add_text_startDay);
        startTime = findViewById(R.id.sch_add_text_startTime);
        endDay = findViewById(R.id.sch_add_text_endDay);
        endTime = findViewById(R.id.sch_add_text_endTime);

        btnComplete = findViewById(R.id.sch_add_button_complete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString();
                String location = editLocation.getText().toString();
                String memo = editLocation.getText().toString();

                Schedule newSchedule = new Schedule(title, location, 15, 1, null, null, memo);
                sch_Mainactivity.schList.add(newSchedule);

                finish();
            }
        });

        startDay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
    }
}
