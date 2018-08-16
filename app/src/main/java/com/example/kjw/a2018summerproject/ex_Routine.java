package com.example.kjw.a2018summerproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ex_Routine extends Activity {

    ListView listView;
    ex_RoutineBaseAdapter ex_RoutineBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_routine);
        listView = (ListView)findViewById(R.id.ex_routine_listview);

        ArrayList<ex_RoutineAdapter> list_ItemArrayList = new ArrayList<ex_RoutineAdapter>();

        /*  "내용에 따른 목록 추가"
        list_ItemArrayList.add()
        */
    }
}
class ex_RoutineAdapter {
    private int profile_image;
    private String title;
    private int delete_image;

    public ex_RoutineAdapter(int profile_image, String title, int delete_image) {
        this.profile_image = profile_image;
        this.title = title;
        this.delete_image = delete_image;
    }

    public int getProfile_image() {

        return profile_image;
    }

    public void setProfile_image(int profile_image) {
        this.profile_image = profile_image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDelete_image() {
        return delete_image;
    }

    public void setDelete_image(int delete_image) {
        this.delete_image = delete_image;
    }
}

class ex_RoutineBaseAdapter extends BaseAdapter {

    Context context;
    ArrayList<ex_RoutineAdapter> list_ex_RoutineAdapter;

    public ex_RoutineBaseAdapter(Context context, ArrayList<ex_RoutineAdapter> list_ex_RoutineAdapter) {
        this.context = context;
        this.list_ex_RoutineAdapter = list_ex_RoutineAdapter;
    }

    @Override
    public int getCount() {
        return this.list_ex_RoutineAdapter.size();
    }

    @Override
    public Object getItem(int i) {
        return list_ex_RoutineAdapter.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    ImageView profile_imageView;
    TextView title_textView;
    ImageView delete_imageView;

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.ex_routine_listview, null);
            profile_imageView = (ImageView)view.findViewById(R.id.ex_routine_listview_image_part);
            title_textView  =(TextView)view.findViewById(R.id.ex_routine_listview_text_exercisetitle);
            profile_imageView = (ImageView)view.findViewById(R.id.ex_routine_listview_image_delete);
        }
        profile_imageView.setImageResource(list_ex_RoutineAdapter.get(i).getProfile_image());
        title_textView.setText(list_ex_RoutineAdapter.get(i).getTitle());
        delete_imageView.setImageResource(list_ex_RoutineAdapter.get(i).getDelete_image());
        return view;
    }
}
class ex_ExerciseRoutine {

    ex_ExerciseCycle tmpCycleOne = new ex_ExerciseCycle("ad","ad",3.0,5,50,20, 0);
    ex_ExerciseCycle tmpCycletwo = new ex_ExerciseCycle("ad","ad",3.5,5,55,25, 1);
}
