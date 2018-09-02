package com.example.kjw.a2018summerproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;

public class sch_Mainactivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {

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

        // 이번달 의 캘린더 인스턴스를 생성한다.
        mThisMonthCalendar = Calendar.getInstance();
        mThisMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        getCalendar(mThisMonthCalendar);

        setData();
    }

    private void setData() {

        for (int i = 1; i < 11; i++) {
            Schedule sch = new Schedule("title" + i, "location" + i, 2018, 9, i, 2018, 9, i, "08", "00", "08", "00", "");
            schList.add(sch);
        }

    }

    private boolean isContainInTerm(Schedule schedule, int year, int month, int day){
        String start = schedule.startYear + "" + schedule.startMonth + "" + schedule.startDay;
        String end = schedule.endYear + "" + schedule.endMonth + "" + schedule.endDay;
        int date = Integer.parseInt(year + "" + month + "" + day);
        if(Integer.parseInt(start) <= date &&  date <= Integer.parseInt(end)){
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("minyoung", schList.size() + "");
        for (int i = 0; i < schList.size(); i++) {
//            int endDay;
//            endDay = schList.get(i).startDay <= schList.get(i).endDay ? schList.get(i).endDay : mThisMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            int thisYear = mThisMonthCalendar.get(Calendar.YEAR);
            int thisMonth = mThisMonthCalendar.get(Calendar.MONTH) + 1;

            Log.d("minyoung", thisMonth + "");
            for(int j = 0; j < mThisMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); j++){
                if(isContainInTerm(schList.get(i), thisYear, thisMonth, j)){
                    mCalendarAdapter.changeDayInfo(j + mThisMonthCalendar.get(Calendar.DAY_OF_WEEK) - 2, true);
                }
            }

//            if(schList.get(i).startMonth == schList.get(i).endMonth) {
//                for (int j = schList.get(i).startDay; j < schList.get(i).endDay+1; j++) {
//
//                }
//            }
//            else if(schList.get(i).endMonth - schList.get(i).startMonth == 1){ // ex) 9월~10월
//                if(schList.get(i).startMonth == (mThisMonthCalendar.get(Calendar.MONTH)+1)){
//                    for (int j = schList.get(i).startDay; j < mThisMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); j++) {
//                        mCalendarAdapter.changeDayInfo(j + mThisMonthCalendar.get(Calendar.DAY_OF_WEEK) - 2, true);
//                    }
//                }else if(schList.get(i).endMonth == (mThisMonthCalendar.get(Calendar.MONTH)+1)){
//                    for (int j = 0; j < schList.get(i).endDay; j++) {
//                        mCalendarAdapter.changeDayInfo(j + mThisMonthCalendar.get(Calendar.DAY_OF_WEEK) - 2, true);
//                    }
//                }
//            }
//            else{ // ex) 9월~11월
//
//            }
//            for (int j = 1; j < endDay + 1; j++) {
//                if ( (schList.get(i).endMonth == (mThisMonthCalendar.get(Calendar.MONTH) + 1)) && (schList.get(i).startYear == mThisMonthCalendar.get(Calendar.YEAR))) {
//                    mCalendarAdapter.changeDayInfo(j + mThisMonthCalendar.get(Calendar.DAY_OF_WEEK) - 2, true);
//                }
//            }

            //8/26~9/1 => 오류
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


    @Override

    public void onItemClick(AdapterView<?> parent, View v, int position, long arg3) {
//        View convertView = ((CalendarAdapter) parent.getAdapter()).getView(position, null, null);
//        CalendarAdapter.DayViewHolde dayViewHolder = (CalendarAdapter.DayViewHolde) convertView.getTag();
//        String day = ((TextView) v.findViewById(R.id.day_cell_tv_day)).getText().toString();
        //if(!(((DayInfo)mCalendarAdapter.getItem(position)).isInMonth())){}

        ArrayList<Schedule> selected = new ArrayList<Schedule>();
        final DayInfo selectedDay = ((DayInfo) ((CalendarAdapter) parent.getAdapter()).getItem(position));
        int day = Integer.parseInt(selectedDay.getDay());

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
                intent.putExtra("startYear", mThisMonthCalendar.get(Calendar.YEAR));
                intent.putExtra("startMonth", mThisMonthCalendar.get(Calendar.MONTH) + 1);
                intent.putExtra("startDay", selectedDay.getDay());
                startActivity(intent); //또는 *forResult
            } else { //일정이 있으면
                //검색
                for (int i = 0; i < schList.size(); i++) {
                    for (int j = schList.get(i).startDay; j < schList.get(i).endDay + 1; j++) { // 8/26~9/1 => 오류
                        if (schList.get(i).startMonth == (mThisMonthCalendar.get(Calendar.MONTH) + 1)) {
                            if (j == day) {
                                selected.add(schList.get(i));
                            }
                        }
                    }
                }

                final AlertDialog listDialog = new AlertDialog.Builder(this).create();
                DialogListViewAdapter dialogAdapter = new DialogListViewAdapter(this, selected, listDialog);
                View view = this.getLayoutInflater().inflate(R.layout.activity_sch_cal_daily, null);
                ListView listview = (ListView) view.findViewById(R.id.sch_calDaily_listview_showList);
                TextView title = view.findViewById(R.id.sch_calDaily_text_title);
                Button addBtn = view.findViewById(R.id.sch_calDaily_button_add);

                title.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
                        + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월" + selectedDay.getDay() + "일");

                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(sch_Mainactivity.this, sch_AddActivity.class);
                        intent.putExtra("startYear", mThisMonthCalendar.get(Calendar.YEAR));
                        intent.putExtra("startMonth", mThisMonthCalendar.get(Calendar.MONTH) + 1);
                        intent.putExtra("startDay", selectedDay.getDay());
                        startActivity(intent);
                        listDialog.dismiss();
                    }
                });

                listview.setAdapter(dialogAdapter);

                listDialog.setView(view);
                listDialog.show();

            }
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sch_main_button_last:
                mThisMonthCalendar = getLastMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
                this.onResume();
                break;

            case R.id.sch_main_button_next:
                mThisMonthCalendar = getNextMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
                this.onResume();
                break;

            case R.id.sch_main_button_search:
                Intent toSearchActivity = new Intent(sch_Mainactivity.this, SchSearchActivity.class);
                startActivity(toSearchActivity);
                break;

            case R.id.sch_main_button_add:
                Intent toAddActivity = new Intent(sch_Mainactivity.this, sch_AddActivity.class);
                toAddActivity.putExtra("startYear", mThisMonthCalendar.get(Calendar.YEAR));
                toAddActivity.putExtra("startMonth", mThisMonthCalendar.get(Calendar.MONTH) + 1);
                toAddActivity.putExtra("startDay", String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
                startActivity(toAddActivity);
                break;

            case R.id.sch_main_text_title: //날짜가 안떠야함.
                MyDatePickerDialog datePickerDialog = new MyDatePickerDialog(sch_Mainactivity.this, this, mThisMonthCalendar.get(Calendar.YEAR), (mThisMonthCalendar.get(Calendar.MONTH)), mThisMonthCalendar.get(Calendar.DAY_OF_MONTH));
                ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(android.content.res.Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                datePickerDialog.show();

                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

//            mThisMonthCalendar = Calendar.getInstance();
        mThisMonthCalendar.set(year, monthOfYear, dayOfMonth);
        getCalendar(mThisMonthCalendar);

//            mThisMonthCalendar.add(Calendar.MONTH, +1);
//
//            mThisMonthCalendar = getNextMonth(mThisMonthCalendar);
//            getCalendar(mThisMonthCalendar);
//
//            mThisMonthCalendar.set(mThisMonthCalendar.get(Calendar.YEAR), mThisMonthCalendar.get(Calendar.MONTH), 1);
//            mThisMonthCalendar.add(Calendar.MONTH, +1);


        this.onResume();
    }


    private void initCalendarAdapter() {

        mCalendarAdapter = new CalendarAdapter(this, R.layout.day, mDayList);
        mGvCalendar.setAdapter(mCalendarAdapter);
    }
}

class Schedule implements Serializable {
    public String title;
    public String location;
    public int startDay;
    public int startMonth;
    public int startYear;
    public int endDay;
    public int endMonth;
    public int endYear;
    public String startHour;
    public String startMinute;
    public String endHour;
    public String endMinute;
    public String memo;

    public Schedule() {
    }

    public Schedule(String title, String location, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay, String startHour, String startMinute, String endHour, String endMinute, String memo) {
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

//달력에서 년월 선택
class MyDatePickerDialog extends DatePickerDialog {

    public MyDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, THEME_HOLO_DARK, callBack, year, monthOfYear, dayOfMonth);
        setTitle(year + "년 " + (monthOfYear + 1) + "월");
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        super.onDateChanged(view, year, month, day);
        setTitle(year + "년 " + (month + 1) + "월");
    }
}

//일 선택시 그날 일정목록 리스트
class DialogListViewAdapter extends BaseAdapter {

    private ArrayList<Schedule> schedules;
    private LayoutInflater inflater = null;
    private Context context;
    private AlertDialog listDialog;

    DialogListViewAdapter(Context context, ArrayList<Schedule> schedules, AlertDialog listDialog) {
        this.context = context;
        this.schedules = schedules;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listDialog = listDialog;
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Override
    public Object getItem(int position) {
        return schedules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final Schedule selected = (Schedule) getItem(position);
        Log.d("minyoung", String.valueOf(selected.startDay));

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.sch_cal_daily_listview, null);
        }
        TextView textTitle = convertView.findViewById(R.id.sch_calDaily_listview_text_title);
        TextView textTime = convertView.findViewById(R.id.sch_calDaily_listview_text_time);
        textTitle.setText(selected.title);
        textTime.setText(selected.startHour + ":" + selected.startMinute + "~" + selected.endHour + ":" + selected.endMinute);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), sch_Verify.class);

                intent.putExtra("Schedule", selected);

                int index = sch_Mainactivity.schList.indexOf(selected);
                intent.putExtra("index", index); //수정을 위해서

//                intent.putExtra("title", selected.title);
//                intent.putExtra("location", selected.location);
//                intent.putExtra("memo", selected.memo);
//
//                intent.putExtra("startYear", selected.startYear);
//                intent.putExtra("startMonth", selected.startMonth);
//                intent.putExtra("startDay", selected.startDay);
//                intent.putExtra("startHour", selected.startHour);
//                intent.putExtra("startMinute", selected.startMinute);
//
//                intent.putExtra("endYear", selected.endYear);
//                intent.putExtra("endMonth", selected.endMonth);
//                intent.putExtra("endDay", selected.endDay);
//                intent.putExtra("endHour", selected.endHour);
//                intent.putExtra("endMinute", selected.endMinute);
                context.startActivity(intent);

                listDialog.dismiss();
            }
        });


        return convertView;
    }
}


//쓰레기통
class SchCalendarAdapter extends CalendarAdapter {

    public SchCalendarAdapter(Context context, int textResource, ArrayList<DayInfo> dayList) {
        super(context, textResource, dayList);

        //mLiInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

}