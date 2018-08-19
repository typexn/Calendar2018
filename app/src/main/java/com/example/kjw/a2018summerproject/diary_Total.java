package com.example.kjw.a2018summerproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import static com.example.kjw.a2018summerproject.diary_Mainactivity.kim;

//import static com.example.kjw.a2018summerproject.diary_Total.isButtonChangeClick;

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

    int GroupNum = -1;
    int ChildNum = -1;

    //온크리에이트 시작
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_total);


        list_Date = new ArrayList<String>();
        list_Diary = new HashMap<String, ArrayList<diary_Content>>();
        main_Diary = new ArrayList<diary_Content>();

        //확장리스트뷰 선언, 어댑터 할당
        expandableListViewDiaryTotal = (ExpandableListView) findViewById(R.id.diary_total_expandablelistview_total);
        expandableListAdapterDiaryTotal = new diary_ExpandableListAdapter(this, list_Date, list_Diary);
        expandableListViewDiaryTotal.setAdapter(expandableListAdapterDiaryTotal);


        //write를 먼저 실행했을 경우 데이터를 받아 오기 -> 아닐경우 activityforresult의 값으로 받아줌
        if (diary_Mainactivity.isDirectToWrite == true) {
            Intent getDiary = getIntent();

            diary_Content Temp = (diary_Content) getDiary.getSerializableExtra("Object");
            ArrayList<String> uri = Temp.getDiaryUriTotal();

            expandableListAdapterDiaryTotal.ExpandableListViewAddItem(Temp);
            main_Diary.add(Temp);
            diary_Mainactivity.isDirectToWrite = false;

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


    //일기 내용 받아오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TOAL_TO_WRITE:
//                    ArrayList<String> getPicutreUri = data.getStringArrayListExtra("Uri");
//                    int getMood = data.getIntExtra("Mood", 0);
//                    int getWeather = data.getIntExtra("Weather", 0);
//                    String getDate = data.getStringExtra("Date");
//                    String getTitle = data.getStringExtra("Title");
//                    String getContent = data.getStringExtra("Content");
                    diary_Content Temp_Content = (diary_Content) data.getSerializableExtra("Diary");
                    main_Diary.add(Temp_Content);
                    if (isButtonChangeClick == true) {
                        int groupDataNum = data.getIntExtra("groupDataNum", -1);
                        int childDataNum = data.getIntExtra("childDataNum", -1);

                        if (groupDataNum == -1) {
                        } else {
                            expandableListAdapterDiaryTotal.ExpandableListViewDeleteItem(groupDataNum, childDataNum);
                        }
                    }
                    expandableListAdapterDiaryTotal.ExpandableListViewAddItem(Temp_Content);
                    expandableListAdapterDiaryTotal.notifyDataSetChanged();
                    break;
            }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.diary_total_button_total_to_main:
                Intent GoToMain = new Intent(diary_Total.this, diary_Mainactivity.class);
                int Count = 0;
                for(int i = 0; i < main_Diary.size(); i++) {
                    GoToMain.putExtra("Data"+i, main_Diary.get(i));
                    Count ++;
                }
                GoToMain.putExtra("Size", Count);
                kim = true;
                startActivity(GoToMain);
                break;
            case R.id.diary_total_button_total_to_write:
                Intent GoToWriteDiary = new Intent(diary_Total.this, diary_Write.class);
                startActivityForResult(GoToWriteDiary, TOAL_TO_WRITE);
                break;

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

//        if (childdata.get(groupdata.get(groupPosition)).get(childPosition).getDIaryPictureUri(0) != null) {
//            Bitmap kim = childdata.get(groupdata.get(groupPosition)).get(childPosition).getDiaryMainPicture(0);
//            imgview.setImageBitmap(kim);
//        }

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


    //확장 리스트뷰에 데이터 추가 함수
    public void ExpandableListViewAddItem(diary_Content diary_content) {

        String Temp_Date = diary_content.getDiaryDate();
        boolean hasSamegroup = false;

        //확장리스트뷰 그룹에 날짜가 같은게 있으면 그곳에 데이터 추가 아니면 새로운 그룹을 만들어 데이터 추가
        if (groupdata.size() < 1) {
            groupdata.add(Temp_Date);
            ArrayList<diary_Content> Temp_child = new ArrayList<diary_Content>();
            Temp_child.add(diary_content);
            childdata.put(groupdata.get(0), Temp_child);
        } else {
            for (int i = 0; i < groupdata.size(); i++) {
                if (groupdata.get(i).equals(Temp_Date)) {
                    childdata.get(groupdata.get(i)).add(diary_content);
                    hasSamegroup = true;
                }
            }
            if (hasSamegroup == false) {
                groupdata.add(Temp_Date);
                ArrayList<diary_Content> Temp_child = new ArrayList<diary_Content>();
                Temp_child.add(diary_content);
                childdata.put(groupdata.get(groupdata.size() - 1), Temp_child);

            }

            //그룹데이터를 정렬
            if (groupdata.size() > 0) {
                Collections.sort(groupdata);
                Collections.reverse(groupdata);
            }
        }
        notifyDataSetChanged();
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
    }

    //확장 리스트뷰의 데이터 수정버튼 함수
    public void ExpandableListViewChangeItem(int groupDataNum, int childDataNum) {
        diary_Content sendToDiaryWrite = childdata.get(this.groupdata.get(groupDataNum)).get(childDataNum);
        Intent intentToWriteDairy = new Intent(context, diary_Write.class);
        intentToWriteDairy.putStringArrayListExtra("diaryUri", sendToDiaryWrite.getDiaryUriTotal());
        intentToWriteDairy.putExtra("diaryMood", sendToDiaryWrite.getDiaryMood());
        intentToWriteDairy.putExtra("diaryWeather", sendToDiaryWrite.getDiaryWeather());
        intentToWriteDairy.putExtra("diaryDate", sendToDiaryWrite.getDiaryDate());
        intentToWriteDairy.putExtra("diaryTitle", sendToDiaryWrite.getDiaryTitle());
        intentToWriteDairy.putExtra("diaryContent", sendToDiaryWrite.getDiaryContent());
        intentToWriteDairy.putExtra("diaryGroupNum", groupDataNum);
        intentToWriteDairy.putExtra("diaryChildNum", childDataNum);
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

    //확장 리스트뷰의 이미지 뷰에 이미지를 추가하기
    public void ExpandableListViewAddImage(int groupPosition, int childPosition) {

    }

}



