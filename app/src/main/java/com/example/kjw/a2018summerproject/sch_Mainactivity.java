package com.example.kjw.a2018summerproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;

public class sch_Mainactivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    public static int SUNDAY = 1;
    public static int MONDAY = 2;
    public static int TUESDAY = 3;
    public static int WEDNSESDAY = 4;
    public static int THURSDAY = 5;
    public static int FRIDAY = 6;
    public static int SATURDAY = 7;

    static ArrayList<Schedule> schList = new ArrayList<Schedule>(); // 일정 정보

    private TextView mTvCalendarTitle;
    private GridView mGvCalendar;
    private ArrayList<DayInfo> mDayList;
    private CalendarAdapter mCalendarAdapter;
    Calendar mLastMonthCalendar;
    Calendar mThisMonthCalendar;
    Calendar mNextMonthCalendar;

    View previousDayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sch_main);

        //다이어리꺼 버튼 넘어가는거 임시
        Button button = (Button) findViewById(R.id.btn_diary);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent temp = new Intent(sch_Mainactivity.this, diary_Mainactivity.class);
                startActivity(temp);
            }
        });

        Button bLastMonth = (Button) findViewById(R.id.sch_main_button_last);
        Button bNextMonth = (Button) findViewById(R.id.sch_main_button_next);
        Button bSearch = findViewById(R.id.sch_main_button_search);
        Button bAdd = findViewById(R.id.sch_main_button_add);

        mTvCalendarTitle = (TextView) findViewById(R.id.sch_main_text_title);
        mGvCalendar = (GridView) findViewById(R.id.sch_main_gridview_calendar);

        bLastMonth.setOnClickListener(this);
        bNextMonth.setOnClickListener(this);
        bSearch.setOnClickListener(this);
        bAdd.setOnClickListener(this);
        mTvCalendarTitle.setOnClickListener(this);

        mGvCalendar.setOnItemClickListener(this);

        mDayList = new ArrayList<DayInfo>();

        setData();
    }

    private void setData() {

        for (int i = 1; i < 21; i++) {
            Schedule sch = new Schedule("title" + i, "location" + i, 2018,8,i,2018,8,i,8,0,8,0, "");
            schList.add(sch);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // 이번달 의 캘린더 인스턴스를 생성한다.
        mThisMonthCalendar = Calendar.getInstance();
        mThisMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        getCalendar(mThisMonthCalendar);


        //Log.d("minyoung", schList.size() + "");
        View v;
        for (int i = 0; i < schList.size(); i++) {
            Toast.makeText(this, schList.get(i).title, Toast.LENGTH_LONG).show();
            //LayoutInflater layoutInFlater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //View rootView = layoutInFlater.inflate(R.layout.day, null);
            //v = mCalendarAdapter.getView(schList.get(i).startDay, null, mGvCalendar);
//            TextView tvExist = v.findViewById(R.id.day_cell_tv_isExist);
//            TextView tvtest = v.findViewById(R.id.day_cell_tv_day);
//            tvtest.setText("test");
//            tvExist.setText("●");
//            Log.d("minyoungcheck", tvExist.getText().toString());
//            Log.d("minyoung", schList.get(i).startDay + "/" + v.toString() + "/" + tvExist.toString());
            mCalendarAdapter.changeDayInfo(schList.get(i).startDay + mThisMonthCalendar.get(Calendar.DAY_OF_WEEK) - 2, true);
        }
        mCalendarAdapter.notifyDataSetChanged(); // 월 이동하면 reset됨




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
            day.setDay(Integer.toString(date));
            day.setInMonth(false);
            day.setExistSch(false); //minyoung
            mDayList.add(day);
        }

        for (int i = 1; i <= thisMonthLastDay; i++) {
            day = new DayInfo();
            day.setDay(Integer.toString(i));
            day.setInMonth(true);
            day.setExistSch(false); //minyoung

            mDayList.add(day);
        }

        for (int i = 1; i < 42 - (thisMonthLastDay + dayOfMonth - 1) + 1; i++) {

            day = new DayInfo();
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


    @Override

    public void onItemClick(AdapterView<?> parent, View v, int position, long arg3) {
        View convertView = ((CalendarAdapter) parent.getAdapter()).getView(position, null, null);
        CalendarAdapter.DayViewHolde dayViewHolder = (CalendarAdapter.DayViewHolde) convertView.getTag();

        String day = ((TextView) v.findViewById(R.id.day_cell_tv_day)).getText().toString();
        //Integer.parseInt(day)
        //if(!(((DayInfo)mCalendarAdapter.getItem(position)).isInMonth())){}

        DayInfo selectedDay = ((DayInfo)((CalendarAdapter) parent.getAdapter()).getItem(position));

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
            if (!selectedDay.isExistSch()) { //일정이 없으면
                Intent intent = new Intent(sch_Mainactivity.this, sch_AddActivity.class);
                intent.putExtra("year", mThisMonthCalendar.get(Calendar.YEAR));
                intent.putExtra("month", mThisMonthCalendar.get(Calendar.MONTH)+1);
                intent.putExtra("day", selectedDay.getDay());
                Log.d("minyoung", selectedDay.getDay());
                startActivity(intent); //또는 *forResult
            } else { //일정이 있으면
                Intent toCheck = new Intent(sch_Mainactivity.this, sch_Verify.class);
                startActivity(toCheck);
            }
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sch_main_button_last:
                mThisMonthCalendar = getLastMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
                break;

            case R.id.sch_main_button_next:
                mThisMonthCalendar = getNextMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
                break;

            case R.id.sch_main_button_search:
                Intent toSearchActivity = new Intent(sch_Mainactivity.this, SchSearchActivity.class);
                startActivity(toSearchActivity);
                break;

            case R.id.sch_main_button_add:
                Intent toAddActivity = new Intent(sch_Mainactivity.this, sch_AddActivity.class);
                startActivity(toAddActivity);
                break;

            case R.id.sch_main_text_title: //날짜가 안떠야함.
                MyDatePickerDialog datePickerDialog = new MyDatePickerDialog(sch_Mainactivity.this, mDateSetListener, mThisMonthCalendar.get(Calendar.YEAR), (mThisMonthCalendar.get(Calendar.MONTH)), mThisMonthCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                datePickerDialog.show();

                break;
        }
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar newCal = Calendar.getInstance();
            newCal.set(year, monthOfYear, dayOfMonth);
            getCalendar(newCal);
        }
    };


    private void initCalendarAdapter() {

        mCalendarAdapter = new CalendarAdapter(this, R.layout.day, mDayList);
        mGvCalendar.setAdapter(mCalendarAdapter);
    }
}

class Schedule {
    public String title;
    public String location;
    public int startDay;
    public int startMonth;
    public int startYear;
    public int endDay;
    public int endMonth;
    public int endYear;
    public int startHour;
    public int startMinute;
    public int endHour;
    public int endMinute;
    public String memo;

    public Schedule() {
    }

    public Schedule(String title, String location, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay, int startHour, int startMinute, int endHour, int endMinute, String memo) {
        this.title = title;
        this.location = location;
        this.startDay = startDay;
        this.startMonth = startMonth;
        this.startYear = startYear;
        this.endDay = endDay;
        this.endMonth = endMonth;
        this.endYear = endYear;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.memo = memo;
    }
}


class MyDatePickerDialog extends DatePickerDialog {

    public MyDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, android.R.style.Theme_Holo_Dialog, callBack, year, monthOfYear, dayOfMonth);

        Log.d("minyoung", "year:" + year + "month" + monthOfYear + "day" + dayOfMonth);
        try {
            Field[] f = DatePickerDialog.class.getDeclaredFields();
            for (Field dateField : f) {
                if (dateField.getName().equals("mDatePicker")) {
                    dateField.setAccessible(true);

                    DatePicker datePicker = (DatePicker) dateField.get(this);

                    Field datePickerFields[] = dateField.getType().getDeclaredFields();

                    for (Field datePickerField : datePickerFields) {
                        if (datePickerField.getName().equals("mDayPicker") ||
                                datePickerField.getName().equals("mDaySpinner")) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = new Object();
                            dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
            setTitle(year + "년 " + (monthOfYear+1) + "월");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        super.onDateChanged(view, year, month, day);
        setTitle(year + "년 " + (month + 1) + "월");
    }
}


//쓰레기통
class SchCalendarAdapter extends CalendarAdapter {

    public SchCalendarAdapter(Context context, int textResource, ArrayList<DayInfo> dayList) {
        super(context, textResource, dayList);

        //mLiInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

}