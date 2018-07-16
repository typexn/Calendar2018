package com.example.kjw.a2018summerproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by jwell on 2018-07-15.
 */

public class diary_Total extends AppCompatActivity{

    ArrayList<String> list_Date;
    HashMap<String, ArrayList<diary_Content>> list_Diary;

    ExpandableListView expandableListViewDiaryTotal;
    diary_ExpandableListAdapter expandableListAdapterDiaryTotal;


    static int elstv_group_num = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_total);

        //확장 리스트뷰에 필요한 어레이, 해시맵 선언
        list_Date = new ArrayList<String>();
        list_Diary = new HashMap<String, ArrayList<diary_Content>>();


        //임시로 데이터 할당
        list_Date.add("연습 그룹");
        ArrayList<diary_Content>  imsi= new ArrayList<diary_Content>();
        diary_Content immsi = new diary_Content("Date","title","content");
        imsi.add(immsi);
        list_Diary.put(list_Date.get(0),imsi);

        //확장리스트뷰 선언, 어댑터 할당
        expandableListViewDiaryTotal = (ExpandableListView) findViewById(R.id.diary_total_expandablelistview_total);
        expandableListAdapterDiaryTotal = new diary_ExpandableListAdapter(this,list_Date,list_Diary);
        expandableListViewDiaryTotal.setAdapter(expandableListAdapterDiaryTotal);





//        //일기 작성 페이지로 가기
//        btn_Write_Diary = (Button)findViewById(R.id.btn_Write_Diary);
//        btn_Write_Diary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //오늘 날짜 가져오기
//                long now = System.currentTimeMillis();
//                Date date = new Date(now);
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                final String getTime = sdf.format(date);
//
//                Intent GoToWriteDiary = new Intent(Diary_Total.this, Diary_Write.class);
//                GoToWriteDiary.putExtra("Current_Time", getTime);
//                startActivityForResult(GoToWriteDiary,3000);
//            }
//        });



    }

//    //일기 내용 받아오기
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode,Intent data){
//        if(resultCode == RESULT_OK){
//            switch(requestCode){
//                case 3000:
//                    Diary_Content Temp_Content = (Diary_Content) data.getSerializableExtra("Diary");
//                    elstv_Diary_Total_Adapter.addItem(Temp_Content);
//                    elstv_Diary_Total_Adapter.notifyDataSetChanged();
//
//            }
//        }
//    }


}
//확장 리스트뷰 어댑터
class diary_ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context = null;
    private ArrayList<String> groupdata = null;
    private HashMap<String, ArrayList<diary_Content>> childdata= null;

    public diary_ExpandableListAdapter(Context context, ArrayList<String> groupdata, HashMap<String, ArrayList<diary_Content>> childdata){
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
    public Object getGroup(int groupPosition) {
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
        String DateTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.diary_group_view, null);
        }
        TextView txtv_memogroup = (TextView) convertView.findViewById(R.id.diary_textview_group);
        txtv_memogroup.setText(DateTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.diary_child_view, null);
        }
//        ImageView imageView = (ImageView) convertView.findViewById(R.id.view_picture);
//        imageView

        //일기 제목 들어갈 텍스트뷰
        TextView txtView = (TextView) convertView.findViewById(R.id.diary_textview_child);
        txtView.setText(childdata.get(groupdata.get(groupPosition)).get(childPosition).getDiaryTitle() + "");

        //일기 수정 버튼
        Button buttonChange = (Button) convertView.findViewById(R.id.diary_button_child_change);
        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //일기 삭제버튼
        Button buttonDelete = (Button) convertView.findViewById(R.id.diary_Button_child_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }


    //확장 리스트뷰에 데이터 추가 함수
    public void addItem(diary_Content diary_content){
        String Temp_Date = diary_content.getDiaryDate();
        boolean hasSamegroup = false;
        int groupsize = 0;

        //확장리스트뷰 그룹에 날짜가 같은게 있으면 그곳에 데이터 추가 아니면 새로운 그룹을 만들어 데이터 추가
        for(int i = 0; i < groupdata.size();i++){
            if(groupdata.get(i).equals(Temp_Date)){
                childdata.get(groupdata.get(i)).add(diary_content);
                hasSamegroup = true;
            }
        }
        if(hasSamegroup == false){
            groupdata.add(Temp_Date);
            ArrayList<diary_Content> Temp_child =new ArrayList<diary_Content>();
            Temp_child.add(diary_content);
            childdata.put(groupdata.get(diary_Total.elstv_group_num),Temp_child);
            diary_Total.elstv_group_num = diary_Total.elstv_group_num+1;

        }
        hasSamegroup = false;
    }
}

