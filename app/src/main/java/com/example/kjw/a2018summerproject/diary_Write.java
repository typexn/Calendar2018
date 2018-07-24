package com.example.kjw.a2018summerproject;

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
import android.widget.Button;
import android.widget.DatePicker;
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
    ClipData selectManyPicture ;
    ArrayList<String> mImageCaptureUri;
    String pictureUri;


    static final int PICK_FROM_CAMERA = 0;
    static final int PICK_FROM_ALBUM = 1;

    ImageView tempImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_write);

//        pictureContent = new ArrayList<MediaStore.Images>();
        tempImageView = (ImageView)findViewById(R.id.Temp_Image_View);
        editTextDiaryTitle = (EditText) findViewById(R.id.diary_write_title);
        editTextDiaryContent = (EditText) findViewById(R.id.diary_write_contents);

        //사진을 담을 어레이
        pictureContent = new ArrayList<Bitmap>();

        //수정 버튼에서 왔는가
//        isComeFromChange();

        //확인버튼 누르면 일기 내용이 total로 넘어가게 구현 -> 일단 임시
        Button buttonSendDiary = (Button) findViewById(R.id.diary_write_button_confirm);

        buttonSendDiary.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                if (editTextDiaryTitle.getText().toString().equals("")) {
                    Toast.makeText(getApplication(), "제목이 없습니다.", Toast.LENGTH_LONG);
                } else if (editTextDiaryContent.getText().toString().equals("")) {
                    Toast.makeText(getApplication(), "내용이 없습니다.", Toast.LENGTH_LONG);
                } else {
                    //오늘 날짜 가져오기  -> 임시로.. datepicker로 구현 할 예정
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
                    SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
                    SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);//임시 날짜 부여 끝

//                    mImageCaptureUri = new ArrayList<String>();
//                    for(int i = 0; i <selectManyPicture.getItemCount(); i++){
//                        String Temp = selectManyPicture.getItemAt(i).toString();
//                        mImageCaptureUri.add(Temp);
//                    }

                    String sendedDate = curYearFormat.format(date) + " - " + curMonthFormat.format(date) + " - " + curDayFormat.format(date);
                    String title = editTextDiaryTitle.getText().toString();
                    String content = editTextDiaryContent.getText().toString();


                    Intent SendToDiaryTotal = new Intent();
                    SendToDiaryTotal.putExtra("Uri",pictureUri);
                    SendToDiaryTotal.putExtra("Date", sendedDate);
                    SendToDiaryTotal.putExtra("Title", title);
                    SendToDiaryTotal.putExtra("Content", content);
                    setResult(RESULT_OK, SendToDiaryTotal);
                    finish();
                }
            }
        });
        //사진 촬영 버튼으로 이벤트 -> 미완성
        buttonTakePhoto = (Button) findViewById(R.id.diary_write_button_take_photo);
        buttonTakePhoto.setOnClickListener(new View.OnClickListener() {
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


//    -----------------------사진 기능 구현-----------------------//
      //사진 찍는 화면으로 가기
//    private void TakePhoto() {
//        Intent intentTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        //임시로 사용할 파일 경로 생성
//        String uri = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
//        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), uri));
//        intentTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
//        startActivityForResult(intentTakePhoto, PICK_FROM_CAMERA);
//    }

    //갤러리로 가는 함수
    private void GoToGallery() {
        Intent intentToGallery = new Intent(Intent.ACTION_PICK);
        intentToGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentToGallery.setType("image/*");
        startActivityForResult(intentToGallery, PICK_FROM_ALBUM);
    }

    //인텐트로 선택한 사진의 Uri들을 받아온다.
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PICK_FROM_ALBUM:
                pictureUri = data.getData().toString();

                //임시로 만든 이미지 뷰에 이미지 할당
                tempImageView = (ImageView)findViewById(R.id.Temp_Image_View);
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
    private void isComeFromChange(){
        if (diary_Total.isButtonChangeClick == true){
            Intent getDiaryDataBeforeChange = getIntent();
            diary_Content getChangeTitle  = (diary_Content) getDiaryDataBeforeChange.getSerializableExtra("diaryChange");
            pictureContent = getChangeTitle.getBitmapTotal();                   //사진 불러오기
            editTextDiaryTitle.setText(getChangeTitle.getDiaryTitle());         //제목 불러오기
            editTextDiaryContent.setText(getChangeTitle.getDiaryContent());     //내용 불러오기
        }
        diary_Total.isButtonChangeClick = false;
    }

}
