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

import static com.example.kjw.a2018summerproject.diary_Mainactivity.intentToView;

/**
 * Created by jwell on 2018-07-25.
 */

public class diary_View extends AppCompatActivity {

    ArrayList<String> diaryUri;
    ArrayList<Bitmap> diaryBitmap;

    int diaryMood = -1;
    int diaryWeather = -1;
    String day = "";
    String title = "";
    String content = "";

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
        diaryUri = new ArrayList<String>();
        diaryBitmap = new ArrayList<Bitmap>();
        int selectedMood = -1;

        setData();

        for (int i = 0; i < diaryUri.size(); i++) {
            addBitmapImage(Uri.parse(diaryUri.get(i)));
            diaryBitmap.add(tempImage);
        }

        diaryDay = (TextView) findViewById(R.id.diary_textview_day_view);
        diaryTitle = (TextView) findViewById(R.id.diary_textview_title_view);
        diaryContent = (TextView) findViewById(R.id.diary_textview_content_view);

        diaryDay.setText(day);
        diaryTitle.setText(title);
        diaryContent.setText(content);

        //어댑터 연걸
        diaryImageVIewPager = (ViewPager) findViewById(R.id.diary_viewPager);
        diaryImageViewAdapter = new diary_ImageSlideAdapter(this, diaryBitmap);
        diaryImageVIewPager.setAdapter(diaryImageViewAdapter);
    }

    private void setData() {
        if (intentToView == false) {
            Intent getDiaryIntent = getIntent();
            diaryUri = (ArrayList<String>) getDiaryIntent.getStringArrayListExtra("view_Uri");
            diaryMood = getDiaryIntent.getIntExtra("view_Mood", 0);
            diaryWeather = getDiaryIntent.getIntExtra("view_Weather", 0);
            day = getDiaryIntent.getStringExtra("view_Day");
            title = getDiaryIntent.getStringExtra("view_Title");
            content = getDiaryIntent.getStringExtra("view_Content");
            Log.d("Uri", diaryUri.get(0));

        } else {
            Intent getDiaryIntent = getIntent();
            diaryMood = getDiaryIntent.getIntExtra("SelectedDiaryMood", 0);
            diaryWeather = getDiaryIntent.getIntExtra("SelectedDiaryWhether", 0);
            day = getDiaryIntent.getStringExtra("SelectedDiaryDate");
            title = getDiaryIntent.getStringExtra("SelectedDiaryTitle");
            content = getDiaryIntent.getStringExtra("SelectedDiaryContent");
            diaryUri = (ArrayList<String>) getDiaryIntent.getStringArrayListExtra("SelectedDiaryPicture");
            Log.d("Uri", diaryUri.get(0));
            intentToView = false;
        }
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
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }


}