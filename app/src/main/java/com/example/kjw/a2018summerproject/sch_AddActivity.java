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

    final int FROMMAIN = 0;
    final int FROMVERIFY = 1;

    EditText editTitle;
    EditText editLocation;
    EditText editMemo;
    TextView textStartDay;
    TextView startTime;
    TextView textEndDay;
    TextView endTime;
    Button btnComplete;

    DayInfo startDayInfo;
    DayInfo endDayInfo;
    TimeInfo startTimeInfo;
    TimeInfo endTimeInfo;

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

    int year;
    int month;
    String day;

    int fromWhere;
    Schedule schedule;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sch_add);

        editTitle = findViewById(R.id.sch_add_edit_title);
        editLocation = findViewById(R.id.sch_add_edit_location);
        editMemo = findViewById(R.id.sch_add_edit_memo);
        textStartDay = findViewById(R.id.sch_add_text_startDay);
        startTime = findViewById(R.id.sch_add_text_startTime);
        textEndDay = findViewById(R.id.sch_add_text_endDay);
        endTime = findViewById(R.id.sch_add_text_endTime);
        btnComplete = findViewById(R.id.sch_add_button_complete);

        setData();

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString();
                String location = editLocation.getText().toString();
                String memo = editMemo.getText().toString();

                if(editTitle.getText() == null || editTitle.getText().toString().equals("")) {
                    Toast.makeText(sch_AddActivity.this,"제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                schedule.title = title;
                schedule.location = location;
                schedule.memo = memo;

                schedule.startYear = startDayInfo.getYear();
                schedule.startMonth = startDayInfo.getMonth();
                schedule.startDay = Integer.parseInt(startDayInfo.getDay());
                schedule.startHour = startTimeInfo.getHourString();
                schedule.startMinute = startTimeInfo.getMinuteString();

                schedule.endYear = endDayInfo.getYear();
                schedule.endMonth = endDayInfo.getMonth();
                schedule.endDay = Integer.parseInt(endDayInfo.getDay());
                schedule.endHour = endTimeInfo.getHourString();
                schedule.endMinute = endTimeInfo.getMinuteString();

                if(fromWhere == FROMMAIN){
                    sch_Mainactivity.schList.add(schedule);
                }else{
                    Schedule sch = sch_Mainactivity.schList.get(index);
                    sch.title = title;
                    sch.location = location;
                    sch.memo = memo;

                    sch.startYear = startDayInfo.getYear();
                    sch.startMonth = startDayInfo.getMonth();
                    sch.startDay = Integer.parseInt(startDayInfo.getDay());
                    sch.startHour = startTimeInfo.getHourString();
                    sch.startMinute = startTimeInfo.getMinuteString();

                    sch.endYear = endDayInfo.getYear();
                    sch.endMonth = endDayInfo.getMonth();
                    sch.endDay = Integer.parseInt(endDayInfo.getDay());
                    sch.endHour = endTimeInfo.getHourString();
                    sch.endMinute = endTimeInfo.getMinuteString();
                }
                android.util.Log.d("minyoung", startTimeInfo.getHourString() + "" + startTimeInfo.getMinuteString());

                finish();
            }
        });

        textStartDay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(sch_AddActivity.this, android.R.style.Theme_Holo_Dialog, mStartSetListener, startDayInfo.getYear(), startDayInfo.getMonth()-1, Integer.parseInt(startDayInfo.getDay()));
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                datePickerDialog.show();
            }
        });

        textEndDay.setOnClickListener(new View.OnClickListener(){
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(sch_AddActivity.this, android.R.style.Theme_Holo_Dialog, mStartTimeSetListener, startTimeInfo.getHour(), startTimeInfo.getMinute(), true);
                timePickerDialog.show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(sch_AddActivity.this, android.R.style.Theme_Holo_Dialog, mEndTimeSetListener, endTimeInfo.getHour(), endTimeInfo.getMinute(), true);
                timePickerDialog.show();
            }
        });
    }

    private void setData(){
        Intent getIntent = getIntent();

        index = getIntent.getIntExtra("index", -1);
        fromWhere = getIntent.getIntExtra("activity", 0);
        if(fromWhere == FROMVERIFY) { //확인화면 - 편집버튼으로
            schedule = (Schedule)getIntent.getSerializableExtra("Schedule");

            title = schedule.title;
            editTitle.setText(title);
            location = schedule.location;
            editLocation.setText(location);

            startYear = schedule.startYear;
            startMonth = schedule.startMonth;
            startDay = schedule.startDay;
            startHour = schedule.startHour;
            startMinute = schedule.startMinute;
//            textStartDay.setText(startYear + "년 " + startMonth + "월 " + startDay + "일");
            startTime.setText(startHour + ":" + startMinute);

            endYear = schedule.endYear;
            endMonth = schedule.endMonth;
            endDay = schedule.endDay;
            endHour = schedule.endHour;
            endMinute = schedule.endMinute;
//            textEndDay.setText(endYear + "년 " + endMonth + "월 " + endDay + "일");
            endTime.setText(endHour + ":" + endMinute);

            memo = schedule.memo;
            editMemo.setText(memo);
        }else if(fromWhere == FROMMAIN){ // 메인화면 - 추가버튼으로
            schedule = new Schedule();
            endYear = startYear = getIntent.getIntExtra("startYear", 2018);
            endMonth = startMonth = getIntent.getIntExtra("startMonth", 1);
            endDay = startDay = Integer.parseInt(getIntent.getStringExtra("startDay"));

            startTime.setText("08:00");
            endTime.setText("08:00");
        }

        textStartDay.setText(startYear + "년 " + startMonth + "월 " + startDay + "일");
        textEndDay.setText(endYear + "년 " + endMonth + "월 " + endDay + "일");

        startDayInfo = new DayInfo();
        startDayInfo.setYear(startYear);
        startDayInfo.setMonth(startMonth);
        startDayInfo.setDay(String.valueOf(startDay));

        endDayInfo = new DayInfo();
        endDayInfo.setYear(endYear);
        endDayInfo.setMonth(endMonth);
        endDayInfo.setDay(String.valueOf(endDay));

//        textStartDay.setText(startYear + "년 " + startMonth + "월 " + startDay + "일");
//        textEndDay.setText(endYear + "년 " + endMonth + "월 " + endDay + "일");

        startTimeInfo = new TimeInfo();
        startTimeInfo.setHour(8);
        startTimeInfo.setMinute(0);

        endTimeInfo = new TimeInfo();
        endTimeInfo.setHour(8);
        endTimeInfo.setMinute(0);
//
//        startTime.setText("08:00");
//        endTime.setText("08:00");




    }

    private DatePickerDialog.OnDateSetListener mStartSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            textStartDay.setText(year + "년 " + (monthOfYear+1) + "월 " + dayOfMonth + "일");
            startDayInfo.setYear(year);
            startDayInfo.setMonth(monthOfYear+1);
            startDayInfo.setDay(String.valueOf(dayOfMonth));

            if(!checkTime()) {
                android.util.Log.d("minyoung", "되냐start");
                endDayInfo.setYear(year);
                endDayInfo.setMonth(monthOfYear+1);
                endDayInfo.setDay(String.valueOf(dayOfMonth));
                textEndDay.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");

                endTimeInfo.setHour(startTimeInfo.getHour());
                endTimeInfo.setMinute(startTimeInfo.getMinute());
                endTime.setText(startTimeInfo.getHourString() + ":" + startTimeInfo.getMinuteString());
            }
        }
    };

    private DatePickerDialog.OnDateSetListener mEndSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            textEndDay.setText(year + "년 " + (monthOfYear+1) + "월 " + dayOfMonth + "일");
            endDayInfo.setYear(year);
            endDayInfo.setMonth(monthOfYear+1);
            endDayInfo.setDay(String.valueOf(dayOfMonth));

            if(!checkTime()) {
                android.util.Log.d("minyoung", "되냐end");
                startDayInfo.setYear(year);
                startDayInfo.setMonth(monthOfYear+1);
                startDayInfo.setDay(String.valueOf(dayOfMonth));
                textStartDay.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");

                startTimeInfo.setHour(endTimeInfo.getHour());
                startTimeInfo.setMinute(endTimeInfo.getMinute());
                startTime.setText(endTimeInfo.getHourString() + ":" + endTimeInfo.getMinuteString());
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

        String start = startDayInfo.getYear() + "" + startDayInfo.getMonthString() + startDayInfo.getDayString() + startTimeInfo.getHourString() + startTimeInfo.getMinuteString();
        String end = endDayInfo.getYear() + "" + endDayInfo.getMonthString() + endDayInfo.getDayString() + endTimeInfo.getHourString() + endTimeInfo.getMinuteString();

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