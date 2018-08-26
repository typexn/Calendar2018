package com.example.kjw.a2018summerproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by KJW on 2018-08-15.
 */

public class ex_Mainactivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener, DatePickerDialog.OnDateSetListener{



    Button diall;
    ex_Help sd;
    int ex_CycleCount = 0;

    private LinearLayout container;


    private TextView mTvCalendarTitle;
    private GridView mGvCalendar;
    private ArrayList<DayInfo> mDayList;
    private CalendarAdapter mCalendarAdapter;
    Calendar mLastMonthCalendar;
    Calendar mThisMonthCalendar;
    Calendar mNextMonthCalendar;

    public static int SUNDAY = 1;
    public static int MONDAY = 2;
    public static int TUESDAY = 3;
    public static int WEDNSESDAY = 4;
    public static int THURSDAY = 5;
    public static int FRIDAY = 6;
    public static int SATURDAY = 7;
    static ArrayList<Schedule> exList = new ArrayList<Schedule>(); // 일정 정보

    Button buttonReport;
    Button buttonHelp;
    Button buttonLastMonth;
    Button buttonNextMonth;

    View previousDayView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_mainactivity);
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
        final int width = dm.widthPixels; //디바이스 화면 너비
        final int height = dm.heightPixels; //디바이스 화면 높이

        mTvCalendarTitle = (TextView) findViewById(R.id.ex_main_text_monthtitle);
        mGvCalendar = (GridView) findViewById(R.id.ex_main_gridview_calendar);


        mTvCalendarTitle.setOnClickListener(this);
        mGvCalendar.setOnItemClickListener(this);
        mDayList = new ArrayList<DayInfo>();

        // 이번달 의 캘린더 인스턴스를 생성한다.
        mThisMonthCalendar = Calendar.getInstance();
        mThisMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        getCalendar(mThisMonthCalendar);

        setData();


        //mainReport로 가는 버튼
        buttonReport = (Button) findViewById(R.id.ex_main_button_report);
        buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToExerciseReport = new Intent(ex_Mainactivity.this, ex_Routine.class);
                startActivity(goToExerciseReport);
            }
        });



        buttonHelp = (Button) findViewById(R.id.ex_main_button_help);
        buttonHelp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                diall = (Button) findViewById(R.id.ex_main_button_help);
                sd = new ex_Help(this);
                WindowManager.LayoutParams wm = sd.getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
                wm.copyFrom(sd.getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미
                wm.width = width / 2;  //화면 너비의 절반
                wm.height = height / 2;  //화면 높이의 절반
                diall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sd.show();  //다이얼로그
                    }
                });

            }
        });

        buttonLastMonth = (Button) findViewById(R.id.ex_main_button_lastmonth);
        buttonNextMonth = (Button) findViewById(R.id.ex_main_button_nextmonth);


    }
    private void setData() {

        for (int i = 1; i < 11; i++) {
            Schedule ex = new Schedule("title" + i, "location" + i, 2018, 8, i, 2018, 8, i, "08", "00", "08", "00", "");
            exList.add(ex);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        for (int i = 0; i < exList.size(); i++) {
            //Toast.makeText(this, exList.get(i).title, Toast.LENGTH_LONG).show();
            for (int j = exList.get(i).startDay; j < exList.get(i).endDay + 1; j++) { //8/26~9/1 => 오류
                if ( (exList.get(i).startMonth == (mThisMonthCalendar.get(Calendar.MONTH) + 1)) && (exList.get(i).startYear == mThisMonthCalendar.get(Calendar.YEAR))) {
                    mCalendarAdapter.changeDayInfo(j + mThisMonthCalendar.get(Calendar.DAY_OF_WEEK) - 2, true);
                }
            }
        }
        mCalendarAdapter.notifyDataSetChanged();


    }

    private void getCalendar(Calendar calendar) {
        int lastMonthStartDay;
        int dayOfMonth;
        int thisMonthLastDay;

        mDayList.clear();
        // 이번달 시작일의 요일을 구한다. 시작일이 일요일인 경우 인덱스를 1(일요일)에서 8(다음주 일요일)로 바꾼다.)
        dayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        thisMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.MONTH, -1);

        Log.e("지난달 마지막일", calendar.get(Calendar.DAY_OF_MONTH) + "");

        // 지난달의 마지막 일자를 구한다.
        lastMonthStartDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.MONTH, 1);

        Log.e("이번달 시작일", calendar.get(Calendar.DAY_OF_MONTH) + "");
        if (dayOfMonth == SUNDAY) {
            dayOfMonth += 7;
        }

        lastMonthStartDay -= (dayOfMonth - 1) - 1;
        // 캘린더 타이틀(년월 표시)을 세팅한다.
        mTvCalendarTitle.setText(calendar.get(Calendar.YEAR) + "년 "
                + (calendar.get(Calendar.MONTH) + 1) + "월");
        DayInfo day;

        Log.e("DayOfMOnth", dayOfMonth + "");
        for (int i = 0; i < dayOfMonth - 1; i++) {
            int date = lastMonthStartDay + i;
            day = new DayInfo();
            day.setYear(calendar.get(Calendar.YEAR));
            day.setMonth(calendar.get(Calendar.MONTH) + 1);
            day.setDay(Integer.toString(date));
            day.setInMonth(false);
            day.setExistSch(false); //minyoung
            mDayList.add(day);
        }

        for (int i = 1; i <= thisMonthLastDay; i++) {
            day = new DayInfo();
            day.setYear(calendar.get(Calendar.YEAR));
            day.setMonth(calendar.get(Calendar.MONTH) + 1);
            day.setDay(Integer.toString(i));
            day.setInMonth(true);
            day.setExistSch(false); //minyoung

            mDayList.add(day);
        }

        for (int i = 1; i < 42 - (thisMonthLastDay + dayOfMonth - 1) + 1; i++) {

            day = new DayInfo();
            day.setYear(calendar.get(Calendar.YEAR));
            day.setMonth(calendar.get(Calendar.MONTH) + 1);
            day.setDay(Integer.toString(i));
            day.setInMonth(false);
            day.setExistSch(false);//minyoung
            mDayList.add(day);
        }
        initCalendarAdapter();
    }

    /**
     * 지난달의 Calendar 객체를 반환합니다.
     *
     * @param calendar
     * @return LastMonthCalendar
     */

    private Calendar getLastMonth(Calendar calendar) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.add(Calendar.MONTH, -1);
        mTvCalendarTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
                + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");

        return calendar;
    }


    /**
     * 다음달의 Calendar 객체를 반환합니다.
     *
     * @param calendar
     * @return NextMonthCalendar
     */

    private Calendar getNextMonth(Calendar calendar) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.add(Calendar.MONTH, +1);
        mTvCalendarTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
                + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");

        return calendar;
    }



    public void onItemClick(AdapterView<?> parent, View v, int position, long arg3) {
        View convertView = ((CalendarAdapter) parent.getAdapter()).getView(position, null, null);
        CalendarAdapter.DayViewHolde dayViewHolder = (CalendarAdapter.DayViewHolde) convertView.getTag();

        String day = ((TextView) v.findViewById(R.id.day_cell_tv_day)).getText().toString();
        //Integer.parseInt(day)
        if (previousDayView != v) {
            if (previousDayView == null) {
                v.setBackgroundColor(Color.GRAY);
                previousDayView = v;
                return;
            }
            previousDayView.setBackgroundColor(Color.TRANSPARENT);
            previousDayView = v;
            v.setBackgroundColor(Color.GRAY);
        } else {
            if (true) { //일정이 없으면
                Intent intent = new Intent(ex_Mainactivity.this, ex_LastResult.class);
                startActivity(intent); //또는 *forResult
            } else { //일정이 있으면
                Intent toCheck = new Intent(ex_Mainactivity.this, ex_Routine.class);
                startActivity(toCheck);
            }
        }
    }


    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ex_main_button_lastmonth:
                mThisMonthCalendar = getLastMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
                break;

            case R.id.ex_main_button_nextmonth:
                mThisMonthCalendar = getNextMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
                break;

            case R.id.ex_main_button_report:
                Intent toSearchActivity = new Intent(ex_Mainactivity.this, ex_ExerciseReport.class);
                startActivity(toSearchActivity);
                break;

            case R.id.ex_main_button_help:
                Intent toAddActivity = new Intent(ex_Mainactivity.this, ex_Help.class);
                startActivity(toAddActivity);
                break;

            case R.id.ex_main_text_monthtitle:
                DatePickerDialog datePickerDialog = new DatePickerDialog(ex_Mainactivity.this, android.R.style.Theme_Holo_Dialog, mDateSetListener, 2012, 5, 2);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                datePickerDialog.show();


                break;
        }
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        }
    };


    private void initCalendarAdapter() {

        mCalendarAdapter = new CalendarAdapter(this, R.layout.day, mDayList);
        mGvCalendar.setAdapter(mCalendarAdapter);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }
}
