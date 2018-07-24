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
import java.util.Date;
import java.util.HashMap;

//import static com.example.kjw.a2018summerproject.diary_Total.isButtonChangeClick;

/**
 * Created by jwell on 2018-07-15.
 */

public class diary_Total extends AppCompatActivity {

    Bitmap tempImage = null;

    //수정버튼 태그로 호출
    LinearLayout child;


    //확장 리스트뷰에 필요한 어레이, 해시맵 선언
    ArrayList<String> list_Date;
    HashMap<String, ArrayList<diary_Content>> list_Diary;

    //확장 리스트뷰 + 어댑터 선언
    ExpandableListView expandableListViewDiaryTotal;
    diary_ExpandableListAdapter expandableListAdapterDiaryTotal;

    //확장 리스트뷰에 그룹 데이터가 몇개 있는지 확인하는 int값
    static int elstv_group_num = 0;

    //일기를 수정하려고 했는가 boolean값 -> 수정 버튼을 누르면 그 child의 item을 갖고 diary_write 페이지로 넘어가기
    static Boolean isButtonChangeClick = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_total);

        list_Date = new ArrayList<String>();
        list_Diary = new HashMap<String, ArrayList<diary_Content>>();

        //차일드에 tag설정

        child = new LinearLayout(this);

        child.findViewWithTag(1_2);


        //확장리스트뷰 선언, 어댑터 할당
        expandableListViewDiaryTotal = (ExpandableListView) findViewById(R.id.diary_total_expandablelistview_total);
        expandableListAdapterDiaryTotal = new diary_ExpandableListAdapter(this, list_Date, list_Diary);
        expandableListViewDiaryTotal.setAdapter(expandableListAdapterDiaryTotal);


        //일기 작성 페이지로 가기
        Button buttonGoWrite = (Button) findViewById(R.id.diary_total_button_gowrite);
        buttonGoWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent GoToWriteDiary = new Intent(diary_Total.this, diary_Write.class);
                startActivityForResult(GoToWriteDiary, 3000);
            }
        });
    }

    //일기 내용 받아오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 3000:
                    String getPicutreUri = data.getStringExtra("Uri");
                    String getDate = data.getStringExtra("Date");
                    String getTitle = data.getStringExtra("Title");
                    String getContent = data.getStringExtra("Content");

                    ArrayList<Bitmap> BitmapArrayList = new ArrayList<Bitmap>();
//                    for(int i = 0; i < getArrayListUri.size();i++){
//                        Uri Temp = Uri.parse(getArrayListUri.get(i));
//                        addBitmapImage(Temp);
//                        BitmapArrayList.add(tempImage);
//                        tempImage = null;
//                    }

                    addBitmapImage(Uri.parse(getPicutreUri));
                    BitmapArrayList.add(tempImage);

                    diary_Content Temp_Content = new diary_Content(BitmapArrayList, getDate, getTitle, getContent);

                    expandableListAdapterDiaryTotal.ExpandableListViewAddItem(Temp_Content);
                    expandableListAdapterDiaryTotal.notifyDataSetChanged();

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

//    private void setButtonTag(int groupPosition, int childPosition){
//        child.findViewWithTag(groupPosition + "_" + childPosition);
//
//    }
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
        txtv_diarygroup.setText(getGroup(groupPosition));  //임시로

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
        if (childdata.get(groupdata.get(groupPosition)).get(childPosition).getDiaryMainPicture(0) != null) {
            Bitmap kim = childdata.get(groupdata.get(groupPosition)).get(childPosition).getDiaryMainPicture(0);
            imgview.setImageBitmap(kim);
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
//                buttonChange.setTag(groupPosition + "_" + childPosition);
//                diary_Content diary = childdata.get(groupdata.get(groupPosition)).get(childPosition); //차일드의 일기 내용 가져오기
//
//                Intent intentToWriteDairy = new Intent(context, diary_Write.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intentToWriteDairy.putExtra("diaryChange", diary);
//                context.startActivity(intentToWriteDairy);
                    ExpandableListViewChangeItem(groupPosition, childPosition);
                }
            });

        }

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
//        Log.d("김준성", Temp_Date + "");
        boolean hasSamegroup = false;
//        int groupsize = 0;

        //확장리스트뷰 그룹에 날짜가 같은게 있으면 그곳에 데이터 추가 아니면 새로운 그룹을 만들어 데이터 추가
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
            childdata.put(groupdata.get(diary_Total.elstv_group_num), Temp_child);
            diary_Total.elstv_group_num = diary_Total.elstv_group_num + 1;

        }
        hasSamegroup = false;
//        Log.d("날짜", Temp_Date+"");
    }

    //확장리스트뷰의 데이터 삭제 함수   -> 차일드의 사이즈가 1이면 차일드를 포함하는 그룹 삭제 아니면 차일드만 삭제
    public void ExpandableListViewDeleteItem(int groupDataNum, int childDataNum) {
        int childDataSize = childdata.get(groupdata.get(groupDataNum)).size();

        if (childDataSize == 1) {
            childdata.get(groupdata.get(groupDataNum)).clear();
            groupdata.remove(groupDataNum);
            diary_Total.elstv_group_num = diary_Total.elstv_group_num - 1;
        } else {
            childdata.get(groupdata.get(groupDataNum)).remove(childDataNum);
        }
    }

    //확장 리스트뷰의 데이터 수정 함수 -> 인텐트를 사용해서 일기 작성 페이지로 넘어가야 할듯
    public void ExpandableListViewChangeItem(int groupDataNum, int childDataNum) {
//        int childDataSize = childdata.get(this.groupdata.get(groupDataNum)).size();
        diary_Content sendToDiaryWrite = childdata.get(this.groupdata.get(groupDataNum)).get(childDataNum);
        Intent intentToWriteDairy = new Intent(context, diary_Write.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentToWriteDairy.putExtra("diaryChange", sendToDiaryWrite);
        context.startActivity(intentToWriteDairy);
    }

    //확장 리스트뷰의 이미지 뷰에 이미지를 추가하기
    public void ExpandableListViewAddImage(int groupPosition, int childPosition) {

    }

}



