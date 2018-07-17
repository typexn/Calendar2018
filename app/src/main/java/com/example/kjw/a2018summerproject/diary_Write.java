package com.example.kjw.a2018summerproject;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by jwell on 2018-07-17.
 */

public class diary_Write extends AppCompatActivity {
    //카메라로 사진찍을 이미지를 저장할 뷰
    ImageView imageViewTakePicture;

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
                String title = "title";
                String content = "content";

                diary_Content sendDiary = new diary_Content(images, Date, title, content);
                Intent SendToDiaryTotal = new Intent();
                SendToDiaryTotal.putExtra("Diary", sendDiary);
                setResult(RESULT_OK, SendToDiaryTotal);
                finish();
            }
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
