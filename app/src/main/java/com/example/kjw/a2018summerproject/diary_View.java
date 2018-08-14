package com.example.kjw.a2018summerproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jwell on 2018-07-25.
 */

public class diary_View extends AppCompatActivity {

    ArrayList<String> diaryUri;
    ArrayList<Bitmap> diaryBitmap;
    int diaryMood;
    int diaryWeather;
    TextView diaryDay;
    TextView diaryTitle;
    TextView diaryContent;

    Bitmap tempImage;
    //슬라이드어댑터
    diary_ImageSlideAdapter diaryImageViewAdapter;
    ViewPager diaryImageVIewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_view);

        tempImage = null;
        diaryBitmap = new ArrayList<Bitmap>();
        diaryUri = new ArrayList<String>();

        Intent getDiaryIntent = getIntent();
        ArrayList<String> selectedUri = (ArrayList<String>) getDiaryIntent.getStringArrayListExtra("view_Uri");
        int selectedMood = getDiaryIntent.getIntExtra("view_Mood", 0);
        int selectedWeather = getDiaryIntent.getIntExtra("view_Weather", 0);
        String selectedDay = getDiaryIntent.getStringExtra("view_Day");
        String selectedTitle = getDiaryIntent.getStringExtra("view_Title");
        String selectedContent = getDiaryIntent.getStringExtra("view_Content");
        diaryUri = selectedUri;

        for (int i = 0; i < diaryUri.size(); i++) {
            addBitmapImage(Uri.parse(diaryUri.get(i)));
            Log.d("준성URI",diaryUri.get(i)+"");
            diaryBitmap.add(tempImage);
        }

        diaryDay = (TextView) findViewById(R.id.diary_textview_day_view);
        diaryTitle = (TextView) findViewById(R.id.diary_textview_title_view);
        diaryContent = (TextView) findViewById(R.id.diary_textview_content_view);

        diaryDay.setText(selectedDay);
        diaryTitle.setText(selectedTitle);
        diaryContent.setText(selectedContent);

        //어댑터 연걸
        diaryImageVIewPager = (ViewPager)findViewById(R.id.diary_viewPager);
        diaryImageViewAdapter = new diary_ImageSlideAdapter(this, diaryBitmap);
        diaryImageVIewPager.setAdapter(diaryImageViewAdapter);
    }

    //입력받은 Uri를 tempImage에 bitmap 형식으로 저장
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
}

class diary_ImageSlideAdapter extends PagerAdapter {

    private ArrayList<Bitmap> Diary_Picture_Bitmap;
    private Context context;
    private LayoutInflater inflater;

    public diary_ImageSlideAdapter(Context context, ArrayList<Bitmap> Bitmap) {
        this.context = context;
        this.Diary_Picture_Bitmap = Bitmap;
    }

    @Override
    public int getCount() {
        return Diary_Picture_Bitmap.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        //object를 linearlayout 의 형태로 형변환했을때 view와 같은지
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(Diary_Picture_Bitmap.get(position));
        ((ViewPager)container).addView(imageView,0);
        return  imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        ((ViewPager) container).removeView((ImageView) object);
    }


}