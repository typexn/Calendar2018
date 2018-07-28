package com.example.kjw.a2018summerproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by jwell on 2018-07-25.
 */

public class diary_View extends AppCompatActivity{

    TextView diaryDay;
    TextView diaryTitle;
    TextView diaryContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_view);

        Intent getDiaryIntent = getIntent();
         String selectedDay =getDiaryIntent.getStringExtra("view_Day");
         String selectedTitle = getDiaryIntent.getStringExtra("view_Title");
         String selectedContent = getDiaryIntent.getStringExtra("view_Content");

        diaryDay = (TextView)findViewById(R.id.diary_textview_day_view);
        diaryTitle = (TextView)findViewById(R.id.diary_textview_title_view);
        diaryContent = (TextView)findViewById(R.id.diary_textview_content_view);

        diaryDay.setText(selectedDay);
        diaryTitle.setText(selectedTitle);
        diaryContent.setText(selectedContent);


    }
}
