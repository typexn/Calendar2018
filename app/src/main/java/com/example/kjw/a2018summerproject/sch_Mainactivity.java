package com.example.kjw.a2018summerproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kjw.a2018summerproject.activity.GVCalendarActivity;

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
        mTvCalendarTitle = (TextView) findViewById(R.id.sch_main_text_title);
        mGvCalendar = (GridView) findViewById(R.id.sch_main_gridview_calendar);

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

        Log.d("minyoung", schList.size()+"");
        for(int i = 0; i < schList.size(); i++) {
            Toast.makeText(this, schList.get(0).title, Toast.LENGTH_LONG).show();
        }
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
        View convertView = ((CalendarAdapter)parent.getAdapter()).getView(position, null, null);
        CalendarAdapter.DayViewHolde dayViewHolder = (CalendarAdapter.DayViewHolde) convertView.getTag();

        if (previousDayView != v) {
            if(previousDayView == null){
                previousDayView = v;
                return;
            }
            previousDayView.setBackgroundColor(Color.TRANSPARENT);
            previousDayView = v;
            v.setBackgroundColor(Color.GRAY);
        }else{
            Intent intent = new Intent(sch_Mainactivity.this, SchAddActivity.class);
            startActivity(intent); //또는 *forResult
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
        }
    }

    private void initCalendarAdapter() {

        mCalendarAdapter = new CalendarAdapter(this, R.layout.day, mDayList);
        mGvCalendar.setAdapter(mCalendarAdapter);
    }
}

class Schedule{
        public String title;
        public String location;
        public String startDay;
        public String endDay;
        public String startTime;
        public String endTime;
        public String memo;

    public Schedule(){}

    public Schedule(String title, String location, String startDay, String endDay, String startTime, String endTime, String memo){
        this.title = title;
        this.location = location;
        this.startDay = startDay;
        this.endDay = endDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.memo = memo;
    }
}




//쓰레기통
class SchCalendarAdapter extends CalendarAdapter{

    public SchCalendarAdapter(Context context, int textResource, ArrayList<DayInfo> dayList) {
        super(context, textResource, dayList);

        //mLiInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

}