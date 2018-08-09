package com.example.kjw.a2018summerproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


import com.example.kjw.a2018summerproject.CalendarAdapter;
import com.example.kjw.a2018summerproject.DayInfo;
import com.example.kjw.a2018summerproject.R;
import com.example.kjw.a2018summerproject.diary_Mainactivity;

import java.util.ArrayList;
import java.util.Calendar;

public class GVCalendarActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {
    public static int SUNDAY = 1;
    public static int MONDAY = 2;
    public static int TUESDAY = 3;
    public static int WEDNSESDAY = 4;
    public static int THURSDAY = 5;
    public static int FRIDAY = 6;
    public static int SATURDAY = 7;

    private TextView mTvCalendarTitle;
    private GridView mGvCalendar;
    private ArrayList<DayInfo> mDayList;
    private CalendarAdapter mCalendarAdapter;
    Calendar mLastMonthCalendar;
    Calendar mThisMonthCalendar;
    Calendar mNextMonthCalendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gv_calendar_activity);

        //다이어리꺼 버튼 넘어가는거 임시
        Button button = (Button) findViewById(R.id.btn_diary);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent temp = new Intent(GVCalendarActivity.this, diary_Mainactivity.class);
                startActivity(temp);
            }
        });

        Button bLastMonth = (Button) findViewById(R.id.gv_calendar_activity_b_last);
        Button bNextMonth = (Button) findViewById(R.id.gv_calendar_activity_b_next);
        mTvCalendarTitle = (TextView) findViewById(R.id.gv_calendar_activity_tv_title);
        mGvCalendar = (GridView) findViewById(R.id.gv_calendar_activity_gv_calendar);

        mTvCalendarTitle = (TextView) findViewById(R.id.gv_calendar_activity_tv_title);
        mGvCalendar = (GridView) findViewById(R.id.gv_calendar_activity_gv_calendar);
        bLastMonth.setOnClickListener(this);
        bNextMonth.setOnClickListener(this);
        mGvCalendar.setOnItemClickListener(this);

        mDayList = new ArrayList<DayInfo>();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 이번달 의 캘린더 인스턴스를 생성한다.
        mThisMonthCalendar = Calendar.getInstance();
        mThisMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        getCalendar(mThisMonthCalendar);
    }

    /**
     * 달력을 셋팅한다.
     *
     * @param calendar 달력에 보여지는 이번달의 Calendar 객체
     */
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
        mTvCalendarTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
                + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");
        DayInfo day;
        Log.e("DayOfMOnth", dayOfMonth + "");
        for (int i = 0; i < dayOfMonth - 1; i++) {
            int date = lastMonthStartDay + i;

            day = new DayInfo();
            day.setDay(Integer.toString(date));
            day.setInMonth(false);
            mDayList.add(day);
        }

        for (int i = 1; i <= thisMonthLastDay; i++)

        {

            day = new DayInfo();
            day.setDay(Integer.toString(i));
            day.setInMonth(true);

            mDayList.add(day);
        }

        for (int i = 1; i < 42 - (thisMonthLastDay + dayOfMonth - 1) + 1; i++) {

            day = new DayInfo();
            day.setDay(Integer.toString(i));
            day.setInMonth(false);
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


    @Override

    public void onItemClick(AdapterView<?> parent, View v, int position,
                            long arg3) {

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.gv_calendar_activity_b_last:
                mThisMonthCalendar = getLastMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
                break;

            case R.id.gv_calendar_activity_b_next:
                mThisMonthCalendar = getNextMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
                break;
        }
    }

    private void initCalendarAdapter() {

        mCalendarAdapter = new CalendarAdapter(this, R.layout.day, mDayList);
        mGvCalendar.setAdapter(mCalendarAdapter);
    }

}
