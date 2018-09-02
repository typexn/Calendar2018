package com.example.kjw.a2018summerproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.kjw.a2018summerproject.activity.GVCalendarActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class acc_MainActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {
    public static int SUNDAY = 1;
    public static int MONDAY = 2;
    public static int TUESDAY = 3;
    public static int WEDNSESDAY = 4;
    public static int THURSDAY = 5;
    public static int FRIDAY = 6;
    public static int SATURDAY = 7;


    private TextView textViewTitle;
    private GridView gridViewCalendar;


    private ArrayList<DayInfo> mDayList;
    private CalendarAdapter mCalendarAdapter;


    Calendar mLastMonthCalendar;
    Calendar mThisMonthCalendar;
    Calendar mNextMonthCalendar;

    //입금 출금 금액 변수
    int setMoneyIn = 0 ;
    int setMoneyOut = 0 ;
    int setTotalMoney = 0 ;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_main);

        // (이미지)버튼, 텍스트뷰 객체 생성
        ImageButton imageButtonGoMenu = (ImageButton) findViewById(R.id.acc_main_imagebutton_menu);
        ImageButton imageButtonSearch = (ImageButton) findViewById(R.id.acc_main_imagebutton_search);
        ImageButton imageButtonAdd = (ImageButton) findViewById(R.id.acc_main_imagebutton_add);

        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goEdit = new Intent(acc_MainActivity.this, acc_EditActivity.class);
                goEdit.putExtra("setMoneyIn",setMoneyIn);
                goEdit.putExtra("setMoneyOut",setMoneyOut);
                goEdit.putExtra("setTotalMoney",setTotalMoney);
                startActivity(goEdit);
            }
        });

        ImageButton imageButtonGoTotal = (ImageButton) findViewById(R.id.acc_main_imagebutton_gototal);

        imageButtonGoTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goTotal = new Intent(acc_MainActivity.this, GVCalendarActivity.class);
                startActivity(goTotal);
            }
        });

        ImageButton imageButtonGoScheldule = (ImageButton) findViewById(R.id.acc_main_imagebutton_goscheldule);
        imageButtonGoScheldule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goSche = new Intent(acc_MainActivity.this, sch_Mainactivity.class);
                startActivity(goSche);
            }
        });

        ImageButton imageButtonGoDiary = (ImageButton) findViewById(R.id.acc_main_imagebutton_godiary);
        imageButtonGoDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goDiary = new Intent(acc_MainActivity.this, diary_Mainactivity.class);
                startActivity(goDiary);
            }
        });

        ImageButton imageButtonGoExercise = (ImageButton) findViewById(R.id.acc_main_imagebutton_goexercise);
        imageButtonGoExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goExer = new Intent(acc_MainActivity.this, ex_ExerciseStart.class);
                startActivity(goExer);
            }
        });

        Button buttontoday = (Button) findViewById(R.id.acc_main_button_today);
        buttontoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button buttonweek = (Button) findViewById(R.id.acc_main_button_week);
        buttonweek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goweek = new Intent(acc_MainActivity.this, acc_Weeklyactivity.class);
                startActivity(goweek);
            }
        });

        Button buttononeday = (Button) findViewById(R.id.acc_main_button_oneday);
        buttononeday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent godaily = new Intent(acc_MainActivity.this, acc_DailyActivity.class);
                startActivity(godaily);
            }
        });

        Button buttonchart = (Button) findViewById(R.id.acc_main_button_chart);
        buttonchart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button ButtonBeforeMonth = (Button) findViewById(R.id.acc_main_button_beforemonth);
        Button ButtonNextMonth = (Button) findViewById(R.id.acc_main_button_nextmonth);

        TextView textViewIncome = (TextView) findViewById(R.id.acc_main_textview_income);
        TextView textViewOutput = (TextView) findViewById(R.id.acc_main_textview_output);
        TextView textViewTotalMoney = (TextView) findViewById(R.id.acc_main_textview_totalmoney);

        //인텐트 받아오기
        Intent mainGetIntent = getIntent();
        int moneyIn = mainGetIntent.getIntExtra("MoneyIn", 0);
        int moneyOut = mainGetIntent.getIntExtra("MoneyOut", 0);
        int x = mainGetIntent.getIntExtra("in&out", 0);

        setMoneyIn = mainGetIntent.getIntExtra("setMoneyIn", 0);
        setMoneyOut = mainGetIntent.getIntExtra("setMoneyOut",0);
        setTotalMoney = mainGetIntent.getIntExtra("setTotalmoney",0);

        if (x == 0) {
            setMoneyIn =  setMoneyIn + moneyIn ;
        }
        if (x == 1) {
            setMoneyOut = setMoneyOut + moneyOut;
        }
        textViewIncome.setText(setMoneyIn+ "원");
        textViewOutput.setText(setMoneyOut + "원");
        textViewTotalMoney.setText(setTotalMoney + "원");

        textViewTitle = (TextView) findViewById(R.id.acc_main_textview_yearmonth);
        gridViewCalendar = (GridView) findViewById(R.id.acc_main_gridview_calendar);

        ButtonBeforeMonth.setOnClickListener(this);
        ButtonNextMonth.setOnClickListener(this);
        gridViewCalendar.setOnItemClickListener(this);

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
        textViewTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
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

        for (int i = 1; i <= thisMonthLastDay; i++) {

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
        textViewTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
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
        textViewTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
                + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");

        return calendar;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long arg3) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.acc_main_button_beforemonth:
                mThisMonthCalendar = getLastMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
                break;

            case R.id.acc_main_button_nextmonth:
                mThisMonthCalendar = getNextMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);

                break;
        }
    }

    private void initCalendarAdapter() {

        mCalendarAdapter = new CalendarAdapter(this, R.layout.day, mDayList);
        gridViewCalendar.setAdapter(mCalendarAdapter);

    }
}
