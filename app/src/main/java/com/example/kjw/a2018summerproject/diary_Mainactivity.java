package com.example.kjw.a2018summerproject;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.zip.Inflater;

import static com.example.kjw.a2018summerproject.activity.GVCalendarActivity.SUNDAY;

/**
 * Created by jwell on 2018-07-15.
 */

public class diary_Mainactivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    static ArrayList<diary_Content> diaryContent = new ArrayList<diary_Content>(); //일기정보

    static final int MAIN_TO_WRITE = 0;
    static final int MAIN_TO_TOTAL = 1;

    private TextView mTvCalendarTitle;
    private GridView mGvCalendar;
    private ArrayList<DayInfo> mDayList;
    private CalendarAdapter mCalendarAdapter;

    Calendar mThisMonthCalendar;

    Date diary_date;
    int year;
    int month;

    static boolean isDirectToWrite = false;
    static boolean intentFromTotal = false;
    static boolean intentToView = false;

    ListView listViewDialog;
    diary_ListViewAdapter listViewAdapter;
    ArrayList<diary_Content> diaryContentHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_mainactivity);

        Button bLastMonth = (Button) findViewById(R.id.diary_main_button_last);
        Button bNextMonth = (Button) findViewById(R.id.diary_main_button_next);
        Button buttonGoTotal = (Button) findViewById(R.id.diary_main_button_gototal);
        Button buttonGoWrite = (Button) findViewById(R.id.diary_main_button_gowrite);

        mTvCalendarTitle = (TextView) findViewById(R.id.diary_main_text_title);
        mGvCalendar = (GridView) findViewById(R.id.diary_main_gridview_calendar);

        bLastMonth.setOnClickListener(this);
        bNextMonth.setOnClickListener(this);
        buttonGoTotal.setOnClickListener(this);
        buttonGoWrite.setOnClickListener(this);

        mTvCalendarTitle.setOnClickListener(this);
        mGvCalendar.setOnItemClickListener(this);

        mDayList = new ArrayList<DayInfo>();
        diaryContentHolder = new ArrayList<diary_Content>();

        getData();

    }//온크리에이트의 끝


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case MAIN_TO_TOTAL:
                if (resultCode != RESULT_OK) {
                    return;
                }


                break;
            case MAIN_TO_WRITE:
                if (resultCode != RESULT_OK) {
                    isDirectToWrite = false;
                    return;
                }

                break;
        }
    }

    private void setData(ArrayList<diary_Content> Content) {
        int rowNum = Content.size();
        for (int i = 0; i < rowNum; i++) {

        }
    }

    private void dialogShow(final ArrayList<diary_Content> inPutDiary) {
        int Temp = inPutDiary.size();
        final String[] items = new String[Temp];
        for(int i =0; i < Temp; i++){
            items[i] = inPutDiary.get(i).getDiaryTitle();
        }
        AlertDialog.Builder diaryMainDialogBuilder = new AlertDialog.Builder(this);
        diaryMainDialogBuilder.setTitle("다음중 보고 싶은 일기를 고르세요");
        diaryMainDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                Intent IntentMainToView = new Intent(diary_Mainactivity.this, diary_View.class);
                IntentMainToView.putExtra("SelectedDiary", inPutDiary.get(position));
                dialogInterface.dismiss();
                intentToView = true;
                startActivity(IntentMainToView);
            }
        });
        AlertDialog diaryMainDialog = diaryMainDialogBuilder.create();
        diaryMainDialog.show();
    }


    @Override  //onResume()
    protected void onResume() {
        super.onResume();


        // 이번달 의 캘린더 인스턴스를 생성한다.
        mThisMonthCalendar = Calendar.getInstance();
        mThisMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        getCalendar(mThisMonthCalendar);

        mCalendarAdapter.notifyDataSetChanged(); // 월 이동하면 reset됨

    }

    @Override //그리드뷰 아이템 클릭 -> 다이얼로그로 listView 띄우기
    public void onItemClick(AdapterView<?> parent, View v, int position, long arg3) {
        String day = mDayList.get(position).getDay();
        String Temp = year + " - " + month + " - " + day;

        if (diaryContent.size() == 0) {
        } else {
            diaryContentHolder.clear();
            for (int i = 0; i < diaryContent.size(); i++) {
                if (Temp.equals(diaryContent.get(i).getDiaryDate())) {
                   diaryContentHolder.add(diaryContent.get(i));
                }
            }
        }
        dialogShow(diaryContentHolder);

    }

    //그리드 뷰에 캘린더 할당
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
        year = calendar.get(Calendar.YEAR);
        month = (calendar.get(Calendar.MONTH) + 1);

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

    private Calendar getLastMonth(Calendar calendar) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.add(Calendar.MONTH, -1);
        mTvCalendarTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
                + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");

        return calendar;
    }

    private Calendar getNextMonth(Calendar calendar) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.add(Calendar.MONTH, +1);
        mTvCalendarTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
                + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");

        return calendar;
    }

    //인텐트로 자료 받아오기
    private void getData() {
        if (intentFromTotal == true) {
            Intent data = getIntent();
            int Temp = data.getIntExtra("Size", -1);
            Log.d("Temp", Temp + "");
            for (int i = 0; i < Temp; i++) {
                diary_Content diary_Data = (diary_Content) data.getSerializableExtra("Data" + i);
                diaryContent.add(diary_Data);
            }
            intentFromTotal = false;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.diary_main_button_last:
                mThisMonthCalendar = getLastMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
                break;

            case R.id.diary_main_button_next:
                mThisMonthCalendar = getNextMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
                break;

            case R.id.diary_main_button_gototal:
                Intent goToDiaryTotal = new Intent(diary_Mainactivity.this, diary_Total.class);
                startActivityForResult(goToDiaryTotal, MAIN_TO_TOTAL);
                break;

            case R.id.diary_main_button_gowrite:
                isDirectToWrite = true;
                Intent goToDiaryWrite = new Intent(diary_Mainactivity.this, diary_Write.class);
                startActivityForResult(goToDiaryWrite, MAIN_TO_WRITE);
                break;

            case R.id.diary_main_text_title: //날짜가 안떠야함.
                MyDatePickerDialog datePickerDialog = new MyDatePickerDialog(diary_Mainactivity.this, mDateSetListener, mThisMonthCalendar.get(Calendar.YEAR), (mThisMonthCalendar.get(Calendar.MONTH)), mThisMonthCalendar.get(Calendar.DAY_OF_MONTH));
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

class diary_Content implements Serializable {

    ArrayList<String> diaryPictureUri; //Uri를 담을 스트링
    int diaryMood; //기분을 표현하는 스피너 값
    int diaryWeather; //날씨를 표현하는 스피너 값
    String diaryDate; //날짜
    String diaryTitle; //일기 제목
    String diaryContent; //일기 내용

    //생성자
    public diary_Content(ArrayList<String> _diaryMainUri, int _diaryMood, int _diaryWeather, String _Date, String _Title, String _Content) {
        this.diaryPictureUri = _diaryMainUri;
        this.diaryMood = _diaryMood;
        this.diaryWeather = _diaryWeather;
        this.diaryDate = _Date;
        this.diaryTitle = _Title;
        this.diaryContent = _Content;
    }


    //다이어리 총 uri 어레이 리스트 리턴
    public ArrayList<String> getDiaryUriTotal() {
        return diaryPictureUri;
    }

    //position을 받으면 그 uri 리턴
    public String getDIaryPictureUri(int position) {
        return diaryPictureUri.get(position);
    }

    //날씨 값 리턴
    public int getDiaryWeather() {
        return diaryWeather;
    }

    //기분 값 리턴
    public int getDiaryMood() {
        return diaryMood;
    }

    //날짜 호출
    public String getDiaryDate() {
        return diaryDate;
    }

    //일게 제목 호출
    public String getDiaryTitle() {
        return diaryTitle;
    }

    //일기 내용 호출
    public String getDiaryContent() {
        return diaryContent;
    }

}

class diary_ListViewAdapter extends BaseAdapter {
    private ArrayList<diary_Content> listViewContent;
    private LayoutInflater inflater = null;

    public diary_ListViewAdapter(ArrayList<diary_Content> inPutList) {
        this.listViewContent = inPutList;
    }

    @Override
    public int getCount() {
        return listViewContent.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewContent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.ex_cycle_listview, null);
        }
        ImageView listViewImage = (ImageView) convertView.findViewById(R.id.ex_cycle_image_representimage);
        TextView listViewText = (TextView) convertView.findViewById(R.id.ex_cycle_text_title);
        listViewText.setText(listViewContent.get(position).getDiaryTitle());

        return convertView;
    }
}
