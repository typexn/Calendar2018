package com.example.kjw.a2018summerproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.kjw.a2018summerproject.R;

import java.util.Calendar;

public class sch_AddActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editLocation;
    EditText editMemo;
    TextView startDay;
    TextView startTime;
    TextView endDay;
    TextView endTime;
    Button btnComplete;

    DayInfo startDayInfo;
    DayInfo endDayInfo;
    TimeInfo startTimeInfo;
    TimeInfo endTimeInfo;

    int year;
    int month;
    String day;

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

        setData();

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString();
                String location = editLocation.getText().toString();
                String memo = editLocation.getText().toString();

                if(editTitle.getText() == null || editTitle.getText().toString().equals("")) {
                    Toast.makeText(sch_AddActivity.this,"제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                Schedule newSchedule = new Schedule(title, location, startDayInfo.getYear(), startDayInfo.getMonth(), Integer.parseInt(startDayInfo.getDay()),
                        endDayInfo.getYear(), endDayInfo.getMonth(), Integer.parseInt(endDayInfo.getDay()),
                        startTimeInfo.getHour(), startTimeInfo.getMinute(), endTimeInfo.getHour(), endTimeInfo.getMinute(), memo);
                sch_Mainactivity.schList.add(newSchedule);

                finish();
            }
        });

        startDay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(sch_AddActivity.this, android.R.style.Theme_Holo_Dialog, mStartSetListener, startDayInfo.getYear(), startDayInfo.getMonth()-1, Integer.parseInt(startDayInfo.getDay()));
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                datePickerDialog.show();
            }
        });

        endDay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(sch_AddActivity.this, android.R.style.Theme_Holo_Dialog, mEndSetListener, endDayInfo.getYear(), endDayInfo.getMonth()-1, Integer.parseInt(endDayInfo.getDay()));
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                datePickerDialog.show();
            }
        });

        startTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(sch_AddActivity.this, android.R.style.Theme_Holo_Dialog, mStartTimeSetListener, 8, 0, true);
                timePickerDialog.show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(sch_AddActivity.this, android.R.style.Theme_Holo_Dialog, mEndTimeSetListener, 8, 0, true);
                timePickerDialog.show();
            }
        });
    }

    private void setData(){
        Intent fromMain = getIntent();
        year = fromMain.getIntExtra("year", 2018);
        month = fromMain.getIntExtra("month", 1);
        day = fromMain.getStringExtra("day");

        startDayInfo = new DayInfo();
        startDayInfo.setYear(year);
        startDayInfo.setMonth(month);
        startDayInfo.setDay(day);

        endDayInfo = new DayInfo();
        endDayInfo.setYear(year);
        endDayInfo.setMonth(month);
        endDayInfo.setDay(day);

        startDay.setText(year + "년 " + month + "월 " + day + "일");
        endDay.setText(year + "년 " + month + "월 " + day + "일");

        startTimeInfo = new TimeInfo();
        startTimeInfo.setHour(8);
        startTimeInfo.setMinute(0);

        endTimeInfo = new TimeInfo();
        endTimeInfo.setHour(8);
        endTimeInfo.setMinute(0);

        startTime.setText("08:00");
        endTime.setText("08:00");
    }

    private DatePickerDialog.OnDateSetListener mStartSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            startDay.setText(year + "년 " + (monthOfYear+1) + "월 " + dayOfMonth + "일");
            startDayInfo.setYear(year);
            startDayInfo.setMonth(monthOfYear+1);
            startDayInfo.setDay(String.valueOf(dayOfMonth));

            if(!checkDay()) {
                android.util.Log.d("minyoung", "되냐start");
                endDayInfo.setYear(year);
                endDayInfo.setMonth(monthOfYear+1);
                endDayInfo.setDay(String.valueOf(dayOfMonth));
                endDay.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
            }
        }
    };

    private DatePickerDialog.OnDateSetListener mEndSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            endDay.setText(year + "년 " + (monthOfYear+1) + "월 " + dayOfMonth + "일");
            endDayInfo.setYear(year);
            endDayInfo.setMonth(monthOfYear+1);
            endDayInfo.setDay(String.valueOf(dayOfMonth));

            if(!checkDay()) {
                android.util.Log.d("minyoung", "되냐end");
                startDayInfo.setYear(year);
                startDayInfo.setMonth(monthOfYear+1);
                startDayInfo.setDay(String.valueOf(dayOfMonth));
                startDay.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
            }
        }
    };

    private TimePickerDialog.OnTimeSetListener mStartTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            startTimeInfo.setHour(hour);
            startTimeInfo.setMinute(minute);
            startTime.setText(startTimeInfo.getHourString() + ":" + startTimeInfo.getMinuteString());

            if(!checkTime()){
                endTimeInfo.setHour(hour);
                endTimeInfo.setMinute(minute);
                endTime.setText(startTimeInfo.getHourString() + ":" + startTimeInfo.getMinuteString());
            }
        }
    };

    private TimePickerDialog.OnTimeSetListener mEndTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            endTimeInfo.setHour(hour);
            endTimeInfo.setMinute(minute);
            endTime.setText(endTimeInfo.getHourString() + ":" + endTimeInfo.getMinuteString());

            if(!checkTime()){
                startTimeInfo.setHour(hour);
                startTimeInfo.setMinute(minute);
                startTime.setText(endTimeInfo.getHourString() + ":" + endTimeInfo.getMinuteString());
            }
        }
    };

    private boolean checkTime(){
        boolean ret = false;
        long startSpot = 0;
        long endSpot = 0;

        String start = startDayInfo.getYear() + "" + startDayInfo.getMonth() + startDayInfo.getDay() + startTimeInfo.getHourString() + startTimeInfo.getMinuteString();
        String end = endDayInfo.getYear() + "" + endDayInfo.getMonth() + endDayInfo.getDay() + endTimeInfo.getHourString() + endTimeInfo.getMinuteString();

        android.util.Log.d("minyoung", start + "/" + end);
        startSpot = Long.parseLong(start);
        endSpot = Long.parseLong(end);

        ret = startSpot > endSpot ? false : true;

        return ret;
    }

    private boolean checkDay(){
        boolean ret = false;
        int startSpot = 0;
        int endSpot = 0;

        String start = startDayInfo.getYear() + "" + startDayInfo.getMonth() + startDayInfo.getDay();
        String end = endDayInfo.getYear() + "" + endDayInfo.getMonth() + endDayInfo.getDay();
        android.util.Log.d("minyoung", start + "/" + end);

        startSpot = Integer.parseInt(start);
        endSpot = Integer.parseInt(end);

        ret = startSpot > endSpot ? false : true;

        return ret;
    }
}

class TimeInfo{
    int hour;
    int minute;

    TimeInfo(){
        this(8,0);
    }

    TimeInfo(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public String getHourString(){
        String ret = String.valueOf(this.hour);
        if(this.hour / 10 == 0){
            ret = "0"+ret;
        }
        return ret;
    }

    public String getMinuteString(){
        String ret = String.valueOf(this.minute);
        if(this.minute / 10 == 0){
            ret = "0"+ret;
        }
        return ret;
    }

    public void setHour(int hour) { this.hour = hour; }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) { this.minute = minute; }
}