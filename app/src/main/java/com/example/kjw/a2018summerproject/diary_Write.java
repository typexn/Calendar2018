package com.example.kjw.a2018summerproject;

import android.content.ContentUris;
import android.content.Context;
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

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Savepoint;
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

import static android.os.Environment.MEDIA_MOUNTED;
import static android.os.Environment.getExternalStorageState;
import static com.example.kjw.a2018summerproject.diary_Mainactivity.diaryTotal;


/**
 * Created by jwell on 2018-07-17.
 */

public class diary_Write extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Bitmap tempImage = null; //임시
    int pictureCount;

    //일기 내용 들어갈 에딧텍스트
    EditText editTextDiaryTitle;
    EditText editTextDiaryContent;

    //일기의 사진, Uri를 담을 어레이
    ArrayList<Bitmap> pictureContent;
    ArrayList<String> pictureUri;

    //기분, 날씨 position을 받을 인트
    int weatherPosition;
    int moodPosition;

    //일기 내용을 받을 스트링
    String day = "";
    String title = "";
    String content = "";

    //사진 촬영, 이미지 추가 버튼
    Button buttonAddPhoto;
    Button buttonAddMultiple;
    Button buttonDeletePhoto;
    Button buttonDelete;

    Uri albumUri;
    String absolutepath;

    //기분 날씨 스피너 , 텍스트뷰
    Spinner diaryMoodSpinner;
    Spinner diaryWeatherSpinner;
    TextView diaryMoodText;
    TextView diaryWeatherText;
    TextView countPicutre;
    TextView diaryDatePickerTextView;

    int diaryDatePickerYear;
    int diaryDatePickerMonth;
    int diaryDatePickerDay;

    //수정버튼 groupDataNum과 childDataNum을 받아오기
    int groupDataNum;
    int childDataNum;

    static final int Diary_Date_Dialog_ID = 0;
    static final int PICK_FROM_ALBUM = 0;
    static final int CROP_FROM_IMAGE = 1;
    static final int PICK_MULTIPLE = 2;
    static boolean ChangeData = false;
    ImageView tempImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_write);

        pictureCount = 0;
        weatherPosition = 0;
        moodPosition = 0;

        tempImageView = (ImageView) findViewById(R.id.Temp_Image_View); //임시
        countPicutre = (TextView) findViewById(R.id.diary_write_txtv_count_photo); //임시

        editTextDiaryTitle = (EditText) findViewById(R.id.diary_write_title);
        editTextDiaryContent = (EditText) findViewById(R.id.diary_write_contents);

        diaryWeatherText = (TextView) findViewById(R.id.diary_weather_text);
        diaryMoodText = (TextView) findViewById(R.id.diary_mood_text);

        diaryDatePickerTextView = (TextView) findViewById(R.id.diary_date_picker_text);

        //사진을 담을 어레이
        pictureContent = new ArrayList<Bitmap>();
        pictureUri = new ArrayList<String>();

        //확인버튼 누르면 일기 내용이 total로 넘어가게 구현 -> 일단 임시
        Button buttonSendDiary = (Button) findViewById(R.id.diary_write_button_confirm);
        buttonSendDiary.setOnClickListener(this);

        //DatePicker 시작

        diaryDatePickerTextView.setOnClickListener(this);

        //현재 날짜 인식
        final Calendar diaryDatePickerToday = Calendar.getInstance();
        diaryDatePickerYear = diaryDatePickerToday.get(Calendar.YEAR);
        diaryDatePickerMonth = diaryDatePickerToday.get(Calendar.MONTH);
        diaryDatePickerDay = diaryDatePickerToday.get(Calendar.DAY_OF_MONTH);


        //인식된 날짜 출력
        updateDisplay();

        //날씨, 기분 스피너에서 고른후 텍스트에 표시
        diaryWeatherSpinner = (Spinner) findViewById(R.id.diary_weather_spinner);
        diaryMoodSpinner = (Spinner) findViewById(R.id.diary_mood_spinner);


        diaryWeatherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diaryWeatherText.setText("" + "" + parent.getItemAtPosition(position));
                Log.d("현준", "다이어리웨덜");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        diaryMoodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diaryMoodText.setText("" + "" + parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        //사진 하나 추가 버튼 이벤트   //사진 여라장 추가 버튼 이벤트
        buttonAddPhoto = (Button) findViewById(R.id.diary_write_button_add_photo);
        buttonAddMultiple = (Button) findViewById(R.id.diary_write_button_add_multiple_photo);
        buttonDeletePhoto = (Button) findViewById(R.id.diary_write_button_delete_photo);
        buttonDelete = (Button)findViewById(R.id.diary_write_button_delete);

        buttonAddPhoto.setOnClickListener(this);
        buttonAddMultiple.setOnClickListener(this);
        buttonDeletePhoto.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        //수정 버튼에서 왔으면 기본 값을 설정해줌
        if (diary_Total.isButtonChangeClick == true) {
            isComeFromChange();
        }


    }//온크리에이트의 끝

    @Override  //온Resume 시작
    protected void onResume() {
        super.onResume();
        Log.d("현준", "온리슘 시작");
    }

    @Override //온스탑 시작
    protected void onStop() {
        super.onStop();
        Log.d("현준", "온스탑 시작");
    }

    //csv 파일 저장하는 함수
    public void saveCSV(diary_Content TempDiary) {
        String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Calendar2018";
        int rowCount = 0;
        diaryTotal.add(TempDiary);

        diary_Content SaveDiaryMember = TempDiary;
        int Mood = SaveDiaryMember.getDiaryMood();
        int Weather = SaveDiaryMember.getDiaryWeather();
        String Date = SaveDiaryMember.getDiaryDate();
        String diarytitle = SaveDiaryMember.getDiaryTitle();
        String Content = SaveDiaryMember.getDiaryContent();
        ArrayList<String> Picture = SaveDiaryMember.getDiaryUriTotal();
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(filepath + "/diary.csv", true));
            String PictureHolder[] = new String[5];

            int PictureCount = 0;

            for (int j = 0; j < Picture.size(); j++) {
                PictureHolder[j] = Picture.get(j);
            }
            for (int j = Picture.size(); j < 5; j++) {
                PictureHolder[j] = "";
            }
            fw.write(Mood + "," + Weather + "," + Date + "," + diarytitle + "," + Content + "," + PictureHolder[0] + ","
                    + PictureHolder[1] + "," + PictureHolder[2] + "," + PictureHolder[3] + "," + PictureHolder[4]);

            fw.append('\n');

            fw.flush();
            fw.close();
        } catch (
                IOException e1)

        {
            e1.printStackTrace();
        }

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
                        .append(diaryDatePickerYear).append("-")
                        .append(diaryDatePickerMonth + 1).append("-")
                        .append(diaryDatePickerDay).append("")
        );
    }

    //사진 하나 선택하는 방법으로 갤러리로 가는 함수
    private void GoToGallery() {
        Intent intentToGallery = new Intent(Intent.ACTION_PICK);
        intentToGallery.setType("image/*");
        intentToGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentToGallery, PICK_FROM_ALBUM);
    }

    //사진 여러장 선택하는 방법으로 갤러리로 가는 함수
    private void GoToMultipleGallery() {
        Intent intentToMultipleGallery = new Intent(Intent.ACTION_PICK);
        intentToMultipleGallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intentToMultipleGallery.setType("image/*");
        intentToMultipleGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intentToMultipleGallery, "Select Picture"), PICK_FROM_ALBUM);
        startActivityForResult(intentToMultipleGallery, PICK_MULTIPLE);
    }

    //앨범에서 선택한 사진 받아오기
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PICK_FROM_ALBUM:
                albumUri = data.getData();
                Intent intentToCropPicutre = new Intent("com.android.camera.action.CROP");
                intentToCropPicutre.setDataAndType(albumUri, "image/*");
                intentToCropPicutre.putExtra("outputX", 200);
                intentToCropPicutre.putExtra("outputY", 200);
                intentToCropPicutre.putExtra("aspectX", 1);
                intentToCropPicutre.putExtra("aspectY", 1);
                intentToCropPicutre.putExtra("scale", true);
                intentToCropPicutre.putExtra("return-data", true);
                startActivityForResult(intentToCropPicutre, CROP_FROM_IMAGE);
                break;
            case CROP_FROM_IMAGE:

                if (resultCode != RESULT_OK) {
                    return;
                }
                final Bundle extras = data.getExtras();
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/Calendar2018/" + System.currentTimeMillis() + ".jpg";
                Log.d("파일 이름", filePath + "");
                if (extras != null) {
                    Bitmap cropedPhoto = extras.getParcelable("data"); //crop된 비트맵
                    tempImageView.setImageBitmap(cropedPhoto); //임시 이미지 뷰에 선택한 이미지를 보여줌

                    storeCropImage(cropedPhoto, filePath); //외부저장소, 앨범에 크롭된 이미지를 저장한다.
                    absolutepath = "file://" + filePath;

                    Uri tempUri = Uri.parse(absolutepath);
                    if (pictureUri.size() < 5) {
                        pictureUri.add(absolutepath);
                    } else {
                        Toast.makeText(this, "사진은 5개 까지만 추가 가능합니다.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    addBitmapImage(tempUri);
                    pictureContent.add(tempImage);
                    tempImageView.setImageBitmap(tempImage);
                    break;
                }

                //임시 파일 삭제
                File f = new File(albumUri.getPath());
                if (f.exists()) {
                    f.delete();
                }
                break;

            case PICK_MULTIPLE:
                if (resultCode != RESULT_OK) {
                    return;
                }
                ClipData clipData = data.getClipData();
                if (clipData == null) {
                    Log.d("클립데이터 에러", "에러에러에러에러");
                    Toast.makeText(this, "오류가 발생했습니다. 다시 선택해주세요", Toast.LENGTH_LONG).show();
                    return;
                }
                if (clipData.getItemCount() + pictureUri.size() > 5) {
                    Toast.makeText(this, "사진은 5개까지 선택가능합니다.", Toast.LENGTH_LONG).show();
                    return;
                } else if (clipData.getItemCount() > 1) {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        albumUri = clipData.getItemAt(i).getUri();
                        pictureUri.add(albumUri.toString());
                        addBitmapImage(albumUri);
                        pictureContent.add(tempImage);
                    }
                } else {
                    albumUri = data.getData();
                    pictureUri.add(albumUri.toString());
                    addBitmapImage(albumUri);
                    pictureContent.add(tempImage);
                }
                tempImageView.setImageBitmap(pictureContent.get(0));
                break;

        }
    }

    //외부저장소에 크롭된 이미지를 저장하는 함수
    private void storeCropImage(Bitmap bitmap, String filePath) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Calendar2018";
        File directory_Calendar2018 = new File(dirPath);
        if (!directory_Calendar2018.exists()) { //Calendar2018 폴더가 없으면 새로 만들어줌
            directory_Calendar2018.mkdir();
        }
        File copyFile = new File(filePath);
        BufferedOutputStream out = null;
        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
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

        pictureUri = new ArrayList<String>();
        Intent getDiaryDataBeforeChange = getIntent();
        int Temp = getDiaryDataBeforeChange.getIntExtra("diaryUriSize", -1);

        if (Temp == 1) {
            pictureUri = (ArrayList<String>) getDiaryDataBeforeChange.getStringArrayListExtra("diaryReWriteUri");
            addBitmapImage(Uri.parse(pictureUri.get(0)));
            tempImageView.setImageBitmap(tempImage);
            countPicutre.setText(pictureUri.size() + "개입니다.");
        }

        moodPosition = getDiaryDataBeforeChange.getIntExtra("diaryReWriteMood", 0);
        weatherPosition = getDiaryDataBeforeChange.getIntExtra("diaryReWriteWeather", 0);
        day = getDiaryDataBeforeChange.getStringExtra("diaryReWriteDate");
        title = getDiaryDataBeforeChange.getStringExtra("diaryReWriteTitle");
        content = getDiaryDataBeforeChange.getStringExtra("diaryReWriteContent");

        groupDataNum = getDiaryDataBeforeChange.getIntExtra("diaryGroupNum", -1);
        childDataNum = getDiaryDataBeforeChange.getIntExtra("diaryChildNum", -1);


        //스피너 값 할당
        diaryMoodSpinner.setSelection(moodPosition);
        diaryWeatherSpinner.setSelection(weatherPosition);

        //텍스트 할당
        diaryMoodText.setText(diaryMoodSpinner.getItemAtPosition(moodPosition) + "");
        diaryWeatherText.setText(diaryWeatherSpinner.getItemAtPosition(weatherPosition) + "");

        diaryDatePickerTextView.setText(day);
        editTextDiaryTitle.setText(title);
        editTextDiaryContent.setText(content);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.diary_write_button_add_photo:
                GoToGallery();
                break;
            case R.id.diary_write_button_add_multiple_photo:
                GoToMultipleGallery();
                break;
            case R.id.diary_date_picker_text:
                showDialog(Diary_Date_Dialog_ID);
                break;
            case R.id.diary_write_button_delete_photo:
                pictureUri.clear();
                pictureUri = new ArrayList<String>();
                tempImage = null;
                countPicutre.setText(pictureUri.size() + "개");
                break;
            case R.id.diary_write_button_delete:
                editTextDiaryContent.setText(null);
                editTextDiaryTitle.setText(null);

                editTextDiaryContent.setHint("내용을 입력하세요");
                editTextDiaryTitle.setHint("제목을 입력하세요");

                SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
                SimpleDateFormat dateFormatMonth = new SimpleDateFormat("-mm");
                SimpleDateFormat dateFormatDay = new SimpleDateFormat("-dd");
                Date now = new Date();
                String dateFormatYearString = dateFormatYear.format(now);
                String dateFormatMonthString = dateFormatMonth.format(now);
                String dateFormatDayString = dateFormatDay.format(now);
                Log.d("현준", "궁금");
                diaryDatePickerTextView.setText(dateFormatYearString + dateFormatMonthString + dateFormatDayString);
                diaryMoodSpinner.setSelection(0);
                diaryWeatherSpinner.setSelection(0);

                break;
            case R.id.diary_write_button_confirm:
                if (editTextDiaryTitle.getText().toString().equals("")) {
                    Toast.makeText(this, "제목이 없습니다.", Toast.LENGTH_LONG);
                } else if (editTextDiaryContent.getText().toString().equals("")) {
                    Toast.makeText(this, "내용이 없습니다.", Toast.LENGTH_LONG);
                } else {
                    String sendedDate = diaryDatePickerTextView.getText().toString();
                    String title = editTextDiaryTitle.getText().toString();
                    String content = editTextDiaryContent.getText().toString();

                    //다이어리 토탈을 거쳐서 바로왔을때 아닐때의 처리를 다르게 해줌
                    if (diary_Mainactivity.isDirectToWrite == false) {
                        if (diary_Total.isButtonChangeClick == true) {
                            Intent Temp = new Intent(diary_Write.this, diary_Total.class);
                            Temp.putExtra("diary___Mood", moodPosition);
                            Temp.putExtra("diary___Weather", weatherPosition);
                            Temp.putExtra("diary___Date", sendedDate);
                            Temp.putExtra("diary___Title", title);
                            Temp.putExtra("diary___Content", content);
                            Temp.putStringArrayListExtra("diary___Uri", pictureUri);
                            Temp.putExtra("groupDataNum", groupDataNum);
                            Temp.putExtra("childDataNum", childDataNum);
                            ChangeData = true;
                            startActivity(Temp);
                        } else {
                            Intent SendToDiaryTotal = new Intent();

                            SendToDiaryTotal.putExtra("diary__Mood", moodPosition);
                            SendToDiaryTotal.putExtra("diary__Weather", weatherPosition);
                            SendToDiaryTotal.putExtra("diary__Date", sendedDate);
                            SendToDiaryTotal.putExtra("diary__Title", title);
                            SendToDiaryTotal.putExtra("diary__Content", content);
                            SendToDiaryTotal.putStringArrayListExtra("diary__Uri", pictureUri);

                            setResult(RESULT_OK, SendToDiaryTotal);
                        }
                        finish();
                    } else {
                        Intent SendToDiaryTotal = new Intent(diary_Write.this, diary_Total.class);
                        SendToDiaryTotal.putExtra("diary_Mood", moodPosition);
                        SendToDiaryTotal.putExtra("diary_Weather", weatherPosition);
                        SendToDiaryTotal.putExtra("diary_Date", sendedDate);
                        SendToDiaryTotal.putExtra("diary_Title", title);
                        SendToDiaryTotal.putExtra("diary_Content", content);
                        SendToDiaryTotal.putStringArrayListExtra("diary_Uri", pictureUri);

                        startActivity(SendToDiaryTotal);
                    }
                }
                break;

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        switch (view.getId()) {
            case R.id.diary_mood_spinner:
                diaryMoodText.setText("" + parent.getItemAtPosition(position));
                Log.d("현준", parent.getItemAtPosition(position)+"");
                moodPosition = position;
                break;
            case R.id.diary_weather_spinner:
                diaryWeatherText.setText("" + parent.getItemAtPosition(position));
                weatherPosition = position;
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}

