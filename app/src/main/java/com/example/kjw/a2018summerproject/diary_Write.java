package com.example.kjw.a2018summerproject;

import android.widget.TextView;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;

import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLNonTransientException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * Created by jwell on 2018-07-17.
 */

public class diary_Write extends AppCompatActivity {


    Bitmap tempImage = null; //임시

    //일기 내용 들어갈 에딧텍스트
    EditText editTextDiaryTitle;
    EditText editTextDiaryContent;

    //일기의 사진을 담을 어레이
    ArrayList<Bitmap> pictureContent;

    //사진 촬영, 이미지 추가 함수
    Button buttonAddPhoto;
    Button buttonTakePhoto;

    //사진의 경로를 담을 ClipData + arrayList
    ClipData selectManyPicture;
    ArrayList<String> mImageCaptureUri;
    String pictureUri;


    TextView diaryDatePickerTextView;
    int diaryDatePickerYear;
    int diaryDatePickerMonth;
    int diaryDatePickerDay;
    static final int Diary_Date_Dialog_ID = 0;

    //임시 이미지뷰
//    ImageView image;


    static final int PICK_FROM_CAMERA = 0;
    static final int PICK_FROM_ALBUM = 1;

    ImageView tempImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_write);

        tempImageView = (ImageView) findViewById(R.id.Temp_Image_View);
        editTextDiaryTitle = (EditText) findViewById(R.id.diary_write_title);
        editTextDiaryContent = (EditText) findViewById(R.id.diary_write_contents);


        //사진을 담을 어레이
        pictureContent = new ArrayList<Bitmap>();

        //수정 버튼에서 왔는가
        if (diary_Total.isButtonChangeClick == true) {
            isComeFromChange();
        }

        //확인버튼 누르면 일기 내용이 total로 넘어가게 구현 -> 일단 임시
        Button buttonSendDiary = (Button) findViewById(R.id.diary_write_button_confirm);
        buttonSendDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextDiaryTitle.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "제목이 없습니다.", Toast.LENGTH_LONG);
                } else if (editTextDiaryContent.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "내용이 없습니다.", Toast.LENGTH_LONG);
                } else {


                    String sendedDate = diaryDatePickerTextView.getText().toString();
                    String title = editTextDiaryTitle.getText().toString();
                    String content = editTextDiaryContent.getText().toString();

                    //다이어리 토탈을 거쳐서 바로왔을때 아닐때의 처리를 다르게 해줌
                    if (diary_Mainactivity.isDirectToWrite == false) {
                        Intent SendToDiaryTotal = new Intent();
                        SendToDiaryTotal.putExtra("Uri", pictureUri);
                        SendToDiaryTotal.putExtra("Date", sendedDate);
                        SendToDiaryTotal.putExtra("Title", title);
                        SendToDiaryTotal.putExtra("Content", content);
                        setResult(RESULT_OK, SendToDiaryTotal);
                        finish();
                    } else {
                        Intent Temp = new Intent(diary_Write.this, diary_Total.class);
                        Temp.putExtra("Uri_", pictureUri);
                        Temp.putExtra("Date_", sendedDate);
                        Temp.putExtra("Title_", title);
                        Temp.putExtra("Content_", content);
                        startActivity(Temp);
                    }
                }
            }
        });

        //DatePicker 시작
        diaryDatePickerTextView = (TextView) findViewById(R.id.diary_date_picker_text);
        diaryDatePickerTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
        final TextView diaryWeatherText = (TextView) findViewById(R.id.diary_weather_text);
        Spinner diaryWeatherSpinner = (Spinner) findViewById(R.id.diary_weather_spinner);
        diaryWeatherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                diaryWeatherText.setText("" + parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //기분스피너에서 고른후 텍스트에 표시
        final TextView diaryMoodText = (TextView) findViewById(R.id.diary_mood_text);
        Spinner diaryMoodSpinner = (Spinner) findViewById(R.id.diary_mood_spinner);
        diaryMoodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                diaryMoodText.setText("" + parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //사진 촬영 버튼으로 이벤트 -> 미완성
        buttonTakePhoto = (Button) findViewById(R.id.diary_write_button_take_photo);
        buttonTakePhoto.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
//                TakePhoto();
            }
        });

        //사진 추가 버튼 이벤트
        buttonAddPhoto = (Button) findViewById(R.id.diary_write_button_add_photo);
        buttonAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToGallery();
            }
        });
    }


    //다이어로그 출력시 DatePicker 다이어로그 출력
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
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
                        .append(diaryDatePickerYear).append(" - ")
                        .append(diaryDatePickerMonth + 1).append(" - ")
                        .append(diaryDatePickerDay).append("")
        );
    }

    //갤러리로 가는 함수
    private void GoToGallery() {
        Intent intentToGallery = new Intent(Intent.ACTION_PICK);
        intentToGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentToGallery.setType("image/*");
        startActivityForResult(intentToGallery, PICK_FROM_ALBUM);

    }

    //인텐트 -> startActivityResult 받는 오버라이드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PICK_FROM_ALBUM:
                pictureUri = data.getData().toString();
                //임시로 만든 이미지 뷰에 이미지 할당
                tempImageView = (ImageView) findViewById(R.id.Temp_Image_View);
                addBitmapImage(data.getData());
                tempImageView.setImageBitmap(tempImage);
                break;
//            case PICK_FROM_CAMERA:  //더 수정해야함
//                mImageCaptureUri = data.getData();
//                Log.d("김준성", mImageCaptureUri+"");
////                sendPicture(mImageCaptureUri);
//                break;
        }

    }

    //Uri를 받으면 사진으로 바꿔주는 함수
    private void addBitmapImage(Uri imageUri) {
        tempImage = null;
        try {
            tempImage = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), imageUri);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //수정 버튼을 통해 왔는가
    private void isComeFromChange() {

        Intent getDiaryDataBeforeChange = getIntent();
        String getChangeDate = getDiaryDataBeforeChange.getStringExtra("diaryDate");
        String getChangeTitle = getDiaryDataBeforeChange.getStringExtra("diaryTitle");
        String getChangeContent = getDiaryDataBeforeChange.getStringExtra("diaryContent");

        editTextDiaryTitle.setText(getChangeTitle);
        editTextDiaryContent.setText(getChangeContent);
        diary_Total.isButtonChangeClick = false;
    }
}

