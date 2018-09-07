package com.example.kjw.a2018summerproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
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
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static com.example.kjw.a2018summerproject.activity.GVCalendarActivity.SUNDAY;

/**
 * Created by jwell on 2018-07-15.
 */

public class diary_Mainactivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    static diary_Content[] diaryContent; //일기정보
    static ArrayList<diary_Content> diaryTotal; // 변환한 일기정보
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
    static boolean intentToView;

    ArrayList<diary_Content> diaryContentHolder;

    //    ArrayList<String> picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_mainactivity);

        intentToView = false;
        diaryTotal = new ArrayList<diary_Content>();

        //csv파일로부터 데이터 읽고 setData함수로 세팅 -> DIaryTotal을 내림차순정렬
        readCSV();


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

    //캘린더 아이템 클릭하면 다이얼로그로 리스트뷰 띄워주는 함수
    private void dialogShow(final ArrayList<diary_Content> inPutDiary) {
        int Temp = inPutDiary.size();
        final String[] items = new String[Temp];
        for (int i = 0; i < Temp; i++) {
            items[i] = inPutDiary.get(i).getDiaryTitle();
        }
        AlertDialog.Builder diaryMainDialogBuilder = new AlertDialog.Builder(this);
        diaryMainDialogBuilder.setTitle("다음중 보고 싶은 일기를 고르세요");
        diaryMainDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                intentToView = true;
                Intent IntentMainToView = new Intent(diary_Mainactivity.this, diary_View.class);
                IntentMainToView.putExtra("SelectedDiaryMood", inPutDiary.get(position).getDiaryMood());
                IntentMainToView.putExtra("SelectedDiaryWhether", inPutDiary.get(position).getDiaryWeather());
                IntentMainToView.putExtra("SelectedDiaryDate", inPutDiary.get(position).getDiaryDate());
                IntentMainToView.putExtra("SelectedDiaryTitle", inPutDiary.get(position).getDiaryTitle());
                IntentMainToView.putExtra("SelectedDiaryContent", inPutDiary.get(position).getDiaryContent());
                IntentMainToView.putExtra("SelectedDiaryPicture", inPutDiary.get(position).getDiaryUriTotal());
                dialogInterface.dismiss();
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
        diaryContentHolder = new ArrayList<diary_Content>(); // 날짜에 맞는 어레이를 찾기 위한 어레이
        String day = mDayList.get(position).getDay();
        String Temp = year + "-" + month + "-" + day;
        int TempCount = diaryTotal.size();
        Log.d("김준성Temp", Temp);
        if (TempCount == 0) {
            return;
        } else {
            for (int i = 0; i < TempCount; i++) {
                if (Temp.equals(diaryTotal.get(i).getDiaryDate())) {
                    diaryContentHolder.add(diaryTotal.get(i));
                }
            }
        }
        if (diaryContentHolder.size() != 0) {
            dialogShow(diaryContentHolder);
        }
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
                diaryTotal.add(diary_Data);
            }
            intentFromTotal = false;
        }
    }

    //csv파일 읽기
    private void readCSV() {
        int mood = 0;
        int weather = 0;
        String date = "";
        String title = "";
        String content = "";
        String picture1 = "";
        String picture2 = "";
        String picture3 = "";
        String picture4 = "";
        String picture5 = "";

        String line = "";
        BufferedReader br = null;

        int totalRow = 0;
        int rowCount = 0;

        ArrayList<String> picture;

        String FilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Calendar2018";
        File dirFile = new File(FilePath);
        if (!dirFile.exists()) { //폴더 존재 x이면 폴더 생성
            dirFile.mkdir();
            return;
        }

        String FileName = dirFile + "/diary.csv";
        File csvFile = new File(FileName);
        if (!csvFile.exists()) {
            return;
        }

        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(csvFile), "UTF-8"); //UTF-8일수도?
            br = new BufferedReader(in);

            while ((line = br.readLine()) != null) {
                totalRow++;
            }
            br.close();
            in.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        diaryContent = new diary_Content[totalRow];

        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(csvFile), "UTF-8");
            br = new BufferedReader(in);


            while ((line = br.readLine()) != null) {

                picture = new ArrayList<String>();

                mood = 0;
                weather = 0;
                date = "";
                title = "";
                content = "";
                picture1 = "";
                picture2 = "";
                picture3 = "";
                picture4 = "";
                picture5 = "";

                String[] array = line.split(",", -1);


                for (int i = 0; i < 10; i++) {

                    switch (i) {
                        case 0:
                            mood = Integer.parseInt(array[i]);
                            Log.d("김준성", mood + "");
                            break;
                        case 1:
                            weather = Integer.parseInt(array[i]);
                            break;
                        case 2:
                            date = array[i].toString();
                            Log.d("김준성", date + "");
                            break;
                        case 3:
                            title = array[i].toString();
                            break;
                        case 4:
                            content = array[i].toString();
                            break;
                        case 5:
                            picture1 = array[i].toString();
                            picture.add(picture1);
                            break;
                        case 6:
                            picture2 = array[i].toString();
                            picture.add(picture2);
                            break;
                        case 7:
                            picture3 = array[i].toString();
                            picture.add(picture3);
                            break;
                        case 8:
                            picture4 = array[i].toString();
                            picture.add(picture4);
                            break;
                        case 9:
                            picture5 = array[i].toString();
                            picture.add(picture5);

                    }
                }

                diary_Content Temp = new diary_Content(mood, weather, date, title, content, picture);
                diaryContent[rowCount] = Temp;
                rowCount = rowCount + 1;
            }
            br.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setData();
    }

    //배열형태의 읽은 CSV파일을 어레이 리스트에 넣어주고 Date순으로 내림차순 정렬
    private void setData() {
        for (int i = 0; i < diaryContent.length; i++) {
            diaryTotal.add(diaryContent[i]);
        }
        Collections.sort(diaryTotal);
    }

    @Override //온클릭 오버라이드
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

class diary_Content implements Serializable, Comparable<diary_Content> {

    ArrayList<String> diaryPictureUri; //Uri를 담을 스트링
    int diaryMood; //기분을 표현하는 스피너 값
    int diaryWeather; //날씨를 표현하는 스피너 값
    String diaryDate; //날짜
    String diaryTitle; //일기 제목
    String diaryContent; //일기 내용

    //생성자
    public diary_Content(int _diaryMood, int _diaryWeather, String _Date, String _Title, String _Content, ArrayList<String> _diaryMainUri) {
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

    //정렬기능 구현
    @Override
    public int compareTo(@NonNull diary_Content diary) {

        SimpleDateFormat transToDate = new SimpleDateFormat("yyyy-mm-dd");
        Date diary1_Date = null;
        Date diary2_Date = null;

        try {
            diary1_Date = transToDate.parse(this.getDiaryDate());
            diary2_Date = transToDate.parse(diary.getDiaryDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int compareDate = diary1_Date.compareTo(diary2_Date);
        if (compareDate > 0) {
            return -1;
        } else if (compareDate < 0) {
            return 1;
        }
        return 0;
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
            convertView = inflater.inflate(R.layout.ex_cyclesaved_listview, null);
        }
        ImageView listViewImage = (ImageView) convertView.findViewById(R.id.ex_cycle_image_representimage_upside);
        TextView listViewText = (TextView) convertView.findViewById(R.id.ex_cycle_text_title_upside);
        listViewText.setText(listViewContent.get(position).getDiaryTitle());

        return convertView;
    }
}
