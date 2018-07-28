package com.example.kjw.a2018summerproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.zip.Inflater;

/**
 * Created by jwell on 2018-07-15.
 */

public class diary_Mainactivity extends AppCompatActivity {
    Button buttonGoTotal;
    Button buttonGoWrite;
    static boolean isDirectToWrite = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_mainactivity);

        //DiaryTotal로 가는 버튼
        buttonGoTotal = (Button)findViewById(R.id.diary_main_button_gototal);
        buttonGoTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToDiaryTotal = new Intent(diary_Mainactivity.this, diary_Total.class);
                startActivity(goToDiaryTotal);
            }
        });

        buttonGoWrite = (Button)findViewById(R.id.diary_main_button_gowrite);
        buttonGoWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDirectToWrite = true;
                Intent goToDiaryWrite = new Intent(diary_Mainactivity.this,diary_Write.class);
                startActivity(goToDiaryWrite);
            }
        });

        //달력 만들기

    }

}
class diary_Content implements Serializable {

    ArrayList<Bitmap> diaryMainPicture; //사진을 담을 어레이 리스트
    String diaryDate; //날짜
    String diaryTitle; //일기 제목
    String diaryContent; //일기 내용

    //생성자
    public diary_Content(ArrayList<Bitmap> _diaryMainPicture, String _Date, String _Title, String _Content) {
        this.diaryMainPicture = _diaryMainPicture;
        this.diaryDate = _Date;
        this.diaryTitle = _Title;
        this.diaryContent = _Content;
    }
    //다이어리 총 비트맵 어레이 리스트 리턴
    public ArrayList<Bitmap> getBitmapTotal(){
        return diaryMainPicture;
    }
    //position을 입력받으면 그 이미지 뷰 리턴
    public Bitmap getDiaryMainPicture(int position){
        return diaryMainPicture.get(position);
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

