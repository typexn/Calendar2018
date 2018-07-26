package com.example.kjw.a2018summerproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by jwell on 2018-07-17.
 */

public class diary_Write extends AppCompatActivity {
    //카메라로 사진찍을 이미지를 저장할 뷰
    ImageView imageViewTakePicture;

    TextView diaryDatePickerTextView;
    int diaryDatePickerYear;
    int diaryDatePickerMonth;
    int diaryDatePickerDay;
    static final int Diary_Date_Dialog_ID = 0;

    //임시 이미지뷰
//    ImageView image;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_write);

        //확인버튼 누르면 일기 내용이 total로 넘어가게 구현 -> 일단 임시
//        image.setImageResource(R.drawable.ic_launcher_foreground);
        Button buttonSendDiary = (Button)findViewById(R.id.diary_write_button_confirm);
        buttonSendDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageView images = null;
                String Date = getIntent().getStringExtra("CurrentTime");
                String title = "title"; ////바꾸기
                String content = "content";

                diary_Content sendDiary = new diary_Content(images, Date, title, content);
                Intent SendToDiaryTotal = new Intent();
                SendToDiaryTotal.putExtra("Diary", sendDiary);
                setResult(RESULT_OK, SendToDiaryTotal);
                finish();
            }
        });


        //DatePicker 시작
        diaryDatePickerTextView = (TextView)findViewById(R.id.diary_date_picker_text);
        diaryDatePickerTextView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                showDialog(Diary_Date_Dialog_ID);
            }
        });

        //현재 날짜 인식
        final Calendar diaryDatePickerToday = Calendar.getInstance();
        diaryDatePickerYear = diaryDatePickerToday.get(Calendar.YEAR);
        diaryDatePickerMonth = diaryDatePickerToday.get(Calendar.MONTH);
        diaryDatePickerDay = diaryDatePickerToday.get(Calendar.DAY_OF_MONTH);
        //인식된 날짜 출력
        updateDisplay();








        //날씨스피너에서 고른후 텍스트에 표시
        final TextView diaryWeatherText = (TextView)findViewById(R.id.diary_weather_text);


        Spinner diaryWeatherSpinner = (Spinner)findViewById(R.id.diary_weather_spinner);
        diaryWeatherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
                diaryWeatherText.setText("" + parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //기분스피너에서 고른후 텍스트에 표시
        final TextView diaryMoodText = (TextView)findViewById(R.id.diary_mood_text);
        Spinner diaryMoodSpinner = (Spinner)findViewById(R.id.diary_mood_spinner);
        diaryMoodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
                diaryMoodText.setText("" + parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });





        //사진 촬영 버튼으로 이벤트
        Button buttonTakePhoto = (Button) findViewById(R.id.diary_write_button_take_photo);
        buttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePhoto();
            }
        });


    }

    //다이어로그 출력시 DatePicker 다이어로그 출력
    @Override
    protected Dialog onCreateDialog(int id){
        switch (id){
            case Diary_Date_Dialog_ID:
                return new DatePickerDialog(this, diarySetListener, diaryDatePickerYear, diaryDatePickerMonth, diaryDatePickerDay);
        }
        return null;
    }

    //다이어로그에 있는 날짜를 설정하면 실행됨
    private DatePickerDialog.OnDateSetListener diarySetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker diaryDatePickerView, int diaryYear, int diaryMonthOfYear, int diaryDayOfMonth) {

            diaryDatePickerYear = diaryYear;
            diaryDatePickerMonth = diaryMonthOfYear;
            diaryDatePickerDay = diaryDayOfMonth;
            updateDisplay();

        }
    };

    //설정된 날짜를 TextView에 출력
    private void updateDisplay() {
        diaryDatePickerTextView.setText(
                new StringBuilder()
                //월은 시스템에서 0 - 11 로 인식하기 때문에 1을 더해줌
                .append(diaryDatePickerYear).append("-")
                .append(diaryDatePickerMonth + 1).append("-")
                .append(diaryDatePickerDay).append("")
        );
    }

    //사진 찍는 화면으로 가기
    private void TakePhoto() {
        Intent intentTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentTakePhoto, 7637);
    }

    //찍은 사진 가져오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            imageViewTakePicture.setImageURI(data.getData());
        }
    }
}
