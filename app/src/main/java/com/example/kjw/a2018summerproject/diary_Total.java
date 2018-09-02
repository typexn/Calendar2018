package com.example.kjw.a2018summerproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import static com.example.kjw.a2018summerproject.diary_Mainactivity.MAIN_TO_TOTAL;
import static com.example.kjw.a2018summerproject.diary_Mainactivity.diaryContent;
import static com.example.kjw.a2018summerproject.diary_Mainactivity.diaryTotal;
import static com.example.kjw.a2018summerproject.diary_Mainactivity.intentFromTotal;

import static com.example.kjw.a2018summerproject.diary_Mainactivity.isDirectToWrite;
import static com.example.kjw.a2018summerproject.diary_Total.isButtonChangeClick;

/**
 * Created by jwell on 2018-07-15.
 */

public class diary_Total extends AppCompatActivity implements Button.OnClickListener {

    Bitmap tempImage = null;

    //다른 그룹이 열리면 열려있는 그룹이 닫히게 하는데 필요한 변수 선언
    private int lastExpandedPosition = -1;

    //확장 리스트뷰에 필요한 어레이, 해시맵 선언
    ArrayList<String> list_Date;
    HashMap<String, ArrayList<diary_Content>> list_Diary;

    ArrayList<diary_Content> main_Diary;

    //확장 리스트뷰 + 어댑터 선언
    ExpandableListView expandableListViewDiaryTotal;
    diary_ExpandableListAdapter expandableListAdapterDiaryTotal;

    //일기를 수정하려고 했는가 boolean값 -> 수정 버튼을 누르면 그 child의 item을 갖고 diary_write 페이지로 넘어가기
    static Boolean isButtonChangeClick = false;
    static final int TOAL_TO_WRITE = 0;

    private boolean isdiaryTotalDeleted;

    //온크리에이트 시작
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_total);

        Log.d("김준성", "OnCreate시작");


        list_Date = new ArrayList<String>();
        list_Diary = new HashMap<String, ArrayList<diary_Content>>();

        isdiaryTotalDeleted = false;

        //확장리스트뷰 선언 ->  어댑터 할당은 initializeData내부에 있음
        expandableListViewDiaryTotal = (ExpandableListView) findViewById(R.id.diary_total_expandablelistview_total);

        if (diaryTotal.size() == 0) {
        } else {
            //csv파일로 읽은 arraylist를 확장리스트뷰에 들어가게 잘 설정한다.
            initializeData();
        }

        //write를 먼저 실행했을 경우 데이터를 받아 오기 -> 아닐경우 activityforresult의 값으로 받아줌
        if (diary_Mainactivity.isDirectToWrite == true) {
            getData();
        }

        //일기 작성 페이지로 가기
        Button buttonGoWrite = (Button) findViewById(R.id.diary_total_button_total_to_write);
        Button buttonGoMain = (Button) findViewById(R.id.diary_total_button_total_to_main);

        buttonGoWrite.setOnClickListener(this);
        buttonGoMain.setOnClickListener(this);

        //확장 리스트뷰 그룹 하나열리면 열려 있던거 닫히게 하는 기능
        expandableListViewDiaryTotal.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != lastExpandedPosition) {
                    expandableListViewDiaryTotal.collapseGroup(lastExpandedPosition);
                    lastExpandedPosition = groupPosition;
                } else
                    lastExpandedPosition = groupPosition;
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("김준성", "OnResume시작");
        if (diary_Write.ChangeData == true) {
            changeDiary();
            isButtonChangeClick = false;
            diary_Write.ChangeData = false;
            isdiaryTotalDeleted = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("김준성", "OnStop시작");

        if (isdiaryTotalDeleted == true) {
            //csv 지우고 저장
            reWriteCsvFile(diaryTotal);
            isdiaryTotalDeleted = false;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("김준성", "OnRestart시작");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("김준성", "OnDestroy시작");
    }

    //일기 내용 받아오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
        } else {
            switch (requestCode) {
                case TOAL_TO_WRITE:

                    int Mood = data.getIntExtra("diary__Mood", -1);
                    int Weather = data.getIntExtra("diary__Weather", -1);
                    String Date = data.getStringExtra("diary__Date");
                    String Title = data.getStringExtra("diary__Title");
                    String Content = data.getStringExtra("diary__Content");
                    ArrayList<String> Uri = (ArrayList<String>) data.getStringArrayListExtra("diary__Uri");

                    diary_Content getDiary = new diary_Content(Mood, Weather, Date, Title, Content, Uri);

                    diaryTotal.add(getDiary);
                    saveCSV(getDiary);

                    list_Date.clear();
                    list_Diary.clear();

                    initializeData();

                    break;
            }
        }
    }

    //확장 리스트뷰에 맞게 데이터 세팅
    private void initializeData() {

        int CountGroup = 0;

        Collections.sort(diaryTotal);

        list_Date = new ArrayList<String>();
        list_Diary = new HashMap<String, ArrayList<diary_Content>>();

        ArrayList<diary_Content> TempChild = new ArrayList<diary_Content>();

        if (diaryTotal.size() == 1) {
            list_Date.add(diaryTotal.get(0).getDiaryDate());
            TempChild.add(diaryTotal.get(0));
            list_Diary.put(list_Date.get(0), TempChild);
        } else {
            list_Date.add(diaryTotal.get(0).getDiaryDate());
            TempChild.add(diaryTotal.get(0));

            for (int i = 1; i < diaryTotal.size(); i++) {
                if (diaryTotal.get(i).getDiaryDate().equals(diaryTotal.get(i - 1).getDiaryDate())) {
                    TempChild.add(diaryTotal.get(i));
                } else {
                    list_Diary.put(list_Date.get(CountGroup), TempChild);
                    TempChild = new ArrayList<diary_Content>();
                    TempChild.add(diaryTotal.get(i));
                    list_Date.add(diaryTotal.get(i).getDiaryDate());
                    CountGroup = CountGroup + 1;
                }
                if (i == diaryTotal.size() - 1) {
                    list_Diary.put(list_Date.get(CountGroup), TempChild);
                }
            }
        }
        expandableListAdapterDiaryTotal = new diary_ExpandableListAdapter(this, list_Date, list_Diary);
        expandableListViewDiaryTotal.setAdapter(expandableListAdapterDiaryTotal);
    }

    //HashMap형식을 어레이 리스트로 직렬화
    private ArrayList<diary_Content> extractDataFormExpListView(ArrayList<String> TempGroup, HashMap<String, ArrayList<diary_Content>> TempChild) {
        ArrayList<diary_Content> returnArray = new ArrayList<diary_Content>();
        for (int i = 0; i < TempGroup.size(); i++) {
            for (int j = 0; j < TempChild.get(TempGroup.get(i)).size(); j++) {
                returnArray.add(TempChild.get(TempGroup.get(i)).get(j));
            }
        }
        return returnArray;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.diary_total_button_total_to_main:
                Intent GoToMain = new Intent(diary_Total.this, diary_Mainactivity.class);
                intentFromTotal = true;
                setResult(RESULT_OK, GoToMain);
                finish();
                break;
            case R.id.diary_total_button_total_to_write:
                Intent GoToWriteDiary = new Intent(diary_Total.this, diary_Write.class);
                startActivityForResult(GoToWriteDiary, TOAL_TO_WRITE);
                break;

        }
    }

    public void getData() {

        Intent getDiary = getIntent();

        int getMood = getDiary.getIntExtra("diary_Mood", 0);
        int getWeather = getDiary.getIntExtra("diary_Weather", 0);
        String getDate = getDiary.getStringExtra("diary_Date");
        String getTitle = getDiary.getStringExtra("diary_Title");
        String getContent = getDiary.getStringExtra("diary_Content");
        ArrayList<String> getURI = (ArrayList<String>) getDiary.getStringArrayListExtra("diary_Uri");

        diary_Content Temp = new diary_Content(getMood, getWeather, getDate, getTitle, getContent, getURI);

        diaryTotal.add(Temp);
        Collections.sort(diaryTotal);
        saveCSV(Temp);

        list_Date.clear();
        list_Diary.clear();

        initializeData();

        expandableListAdapterDiaryTotal = new diary_ExpandableListAdapter(this, list_Date, list_Diary);
        expandableListViewDiaryTotal.setAdapter(expandableListAdapterDiaryTotal);

        diary_Mainactivity.isDirectToWrite = false;
        expandableListAdapterDiaryTotal.notifyDataSetChanged();

    }

    //수정을 했을 경우 일기 내용 받아오기
    private void changeDiary() {
        Intent getDiary = getIntent();
        int getMood = getDiary.getIntExtra("diary___Mood", -1);
        int getWeather = getDiary.getIntExtra("diary___Weather", -1);
        String getDate = getDiary.getStringExtra("diary___Date");
        String getTitle = getDiary.getStringExtra("diary___Title");
        String getContent = getDiary.getStringExtra("diary___Content");
        ArrayList<String> getURI = (ArrayList<String>) getDiary.getStringArrayListExtra("diary___Uri");

        diary_Content Temp = new diary_Content(getMood, getWeather, getDate, getTitle, getContent, getURI);

        int groupDataNum = getDiary.getIntExtra("groupDataNum", -1);
        int childDataNum = getDiary.getIntExtra("childDataNum", -1);

        if (groupDataNum == -1) {
        } else {
            expandableListAdapterDiaryTotal.ExpandableListViewDeleteItem(groupDataNum, childDataNum);
            expandableListAdapterDiaryTotal.notifyDataSetChanged();
        }
        diaryTotal.add(Temp);
        Collections.sort(diaryTotal);

        list_Date.clear();
        list_Diary.clear();

        initializeData();

        expandableListAdapterDiaryTotal = new diary_ExpandableListAdapter(this, list_Date, list_Diary);
        expandableListViewDiaryTotal.setAdapter(expandableListAdapterDiaryTotal);
    }

    //csv 파일 지우고 diaryToal로 다시 쓰기
    private void reWriteCsvFile(ArrayList<diary_Content> inputArray) {
        String inputPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Calendar2018/diary.csv";
        File CurrentCsv = new File(inputPath);
        if (CurrentCsv.exists()) {
            CurrentCsv.delete();
        }
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(inputPath, true));
            for (int i = 0; i < inputArray.size(); i++) {
                diary_Content SaveDiaryMember = inputArray.get(i);

                int Mood = SaveDiaryMember.getDiaryMood();
                int Weather = SaveDiaryMember.getDiaryWeather();
                String Date = SaveDiaryMember.getDiaryDate();
                String diarytitle = SaveDiaryMember.getDiaryTitle();
                String Content = SaveDiaryMember.getDiaryContent();
                ArrayList<String> Picture = SaveDiaryMember.getDiaryUriTotal();

                String PictureHolder[] = new String[5];

                for (int j = 0; j < Picture.size(); j++) {
                    PictureHolder[j] = Picture.get(j);
                }
                for (int j = Picture.size(); j < 5; j++) {
                    PictureHolder[j] = "";
                }
                fw.write(Mood + "," + Weather + "," + Date + "," + diarytitle + "," + Content + "," + PictureHolder[0] + ","
                        + PictureHolder[1] + "," + PictureHolder[2] + "," + PictureHolder[3] + "," + PictureHolder[4]);
                fw.append('\n');
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        diaryTotal = inputArray;
    }

    //csv파일에 일기 하나 추가
    public void saveCSV(diary_Content TempDiary) {
        String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Calendar2018";
        File csvfile = new File(filepath + "/diary.csv");

        int rowCount = 0;

        diary_Content SaveDiaryMember = TempDiary;
        int Mood = SaveDiaryMember.getDiaryMood();
        int Weather = SaveDiaryMember.getDiaryWeather();
        String Date = SaveDiaryMember.getDiaryDate();
        String diarytitle = SaveDiaryMember.getDiaryTitle();
        String Content = SaveDiaryMember.getDiaryContent();
        ArrayList<String> Picture = SaveDiaryMember.getDiaryUriTotal();

        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(csvfile, true));
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
}


//확장 리스트뷰 어댑터
class diary_ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context = null;
    private ArrayList<String> groupdata = null;
    private HashMap<String, ArrayList<diary_Content>> childdata = null;

    public diary_ExpandableListAdapter(Context context, ArrayList<String> groupdata, HashMap<String, ArrayList<diary_Content>> childdata) {
        this.context = context;
        this.groupdata = groupdata;
        this.childdata = childdata;
    }

    @Override
    public int getGroupCount() {
        return groupdata.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childdata.get(this.groupdata.get(groupPosition)).size();
    }

    @Override
    public String getGroup(int groupPosition) {
        return groupdata.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childdata.get(groupdata.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.diary_group_view, null);
        }
        TextView txtv_diarygroup = (TextView) convertView.findViewById(R.id.diary_textview_group);
        txtv_diarygroup.setText(childdata.get(groupdata.get(groupPosition)).get(0).getDiaryDate());  //임시로

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.diary_child_view, null);
        }

        //일기 사진 들어갈 이미지 뷰
        ImageView imgview = (ImageView) convertView.findViewById(R.id.diary_imageview_photo);
        if (childdata.get(groupdata.get(groupPosition)).get(childPosition).getDiaryUriTotal().size() > 0) {
            String imageViewUri = childdata.get(groupdata.get(groupPosition)).get(childPosition).getDIaryPictureUri(0);
            Bitmap imgviewBitmap = null;
            try {
                imgviewBitmap = MediaStore.Images.Media.getBitmap(context.getApplicationContext().getContentResolver(), Uri.parse(imageViewUri));
                imgview.setImageBitmap(imgviewBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            imgview.setImageResource(R.mipmap.ic_launcher);
        }

        //일기 제목 들어갈 텍스트뷰
        TextView txtView = (TextView) convertView.findViewById(R.id.diary_textview_child);
        txtView.setText(childdata.get(groupdata.get(groupPosition)).get(childPosition).getDiaryTitle() + "");

        //일기 수정 버튼
        final Button buttonChange = (Button) convertView.findViewById(R.id.diary_button_child_change);
        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diary_Total.isButtonChangeClick = true;
                ExpandableListViewChangeItem(groupPosition, childPosition);
            }
        });

        //일기 보기 버튼
        Button buttonView = (Button) convertView.findViewById(R.id.diary_button_child_view);
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpandableListViewGetView(groupPosition, childPosition);
            }
        });

        //일기 삭제버튼
        Button buttonDelete = (Button) convertView.findViewById(R.id.diary_Button_child_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpandableListViewDeleteItem(groupPosition, childPosition);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    //확장리스트뷰의 데이터 삭제 함수   -> 차일드의 사이즈가 1이면 차일드를 포함하는 그룹 삭제 아니면 차일드만 삭제
    public void ExpandableListViewDeleteItem(int groupDataNum, int childDataNum) {
        int childDataSize = childdata.get(groupdata.get(groupDataNum)).size();

        if (childDataSize == 1) {
            childdata.get(groupdata.get(groupDataNum)).clear();
            groupdata.remove(groupDataNum);
        } else {
            childdata.get(groupdata.get(groupDataNum)).remove(childDataNum);
        }

        ArrayList<diary_Content> Temp = new ArrayList<diary_Content>();

        for (int i = 0; i < groupdata.size(); i++) {
            for (int j = 0; j < childdata.get(groupdata.get(i)).size(); j++) {
                Temp.add(childdata.get(groupdata.get(i)).get(j));
            }
        }
        diaryTotal = Temp;
        Collections.sort(diaryTotal);
    }

    //확장 리스트뷰의 데이터 수정버튼 함수
    public void ExpandableListViewChangeItem(int groupDataNum, int childDataNum) {
        diary_Content sendToDiaryWrite = childdata.get(this.groupdata.get(groupDataNum)).get(childDataNum);
        Intent intentToWriteDairy = new Intent(context, diary_Write.class);
        if (sendToDiaryWrite.getDiaryUriTotal().size() > 0) {
            intentToWriteDairy.putExtra("diaryReWriteUri", sendToDiaryWrite.getDiaryUriTotal());
            intentToWriteDairy.putExtra("diaryUriSize", 1);
        }
        Log.d("개수", sendToDiaryWrite.getDiaryUriTotal().size() + "");
        intentToWriteDairy.putExtra("diaryReWriteMood", sendToDiaryWrite.getDiaryMood());
        intentToWriteDairy.putExtra("diaryReWriteWeather", sendToDiaryWrite.getDiaryWeather());
        intentToWriteDairy.putExtra("diaryReWriteDate", sendToDiaryWrite.getDiaryDate());
        intentToWriteDairy.putExtra("diaryReWriteTitle", sendToDiaryWrite.getDiaryTitle());
        intentToWriteDairy.putExtra("diaryReWriteContent", sendToDiaryWrite.getDiaryContent());

        intentToWriteDairy.putExtra("diaryGroupNum", groupDataNum);
        intentToWriteDairy.putExtra("diaryChildNum", childDataNum);
        diary_Total.isButtonChangeClick = true;
        context.startActivity(intentToWriteDairy);
    }

    //확장 리스트뷰의 데이터 보기 함수

    public void ExpandableListViewGetView(int groupDataNum, int childDataNum) {
        diary_Content sendToDiaryView = childdata.get(this.groupdata.get(groupDataNum)).get(childDataNum);
        Intent intentToViewDairy = new Intent(context, diary_View.class);
        intentToViewDairy.putStringArrayListExtra("view_Uri", sendToDiaryView.getDiaryUriTotal());
        intentToViewDairy.putExtra("view_Mood", sendToDiaryView.getDiaryMood());
        intentToViewDairy.putExtra("view_Weather", sendToDiaryView.getDiaryWeather());
        intentToViewDairy.putExtra("view_Day", sendToDiaryView.getDiaryDate());
        intentToViewDairy.putExtra("view_Title", sendToDiaryView.getDiaryTitle());
        intentToViewDairy.putExtra("view_Content", sendToDiaryView.getDiaryContent());
        context.startActivity(intentToViewDairy);
    }
}



