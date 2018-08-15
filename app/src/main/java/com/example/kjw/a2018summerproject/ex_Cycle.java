package com.example.kjw.a2018summerproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class ex_Cycle extends Activity {

    private LinearLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_cycle);

        Button btn_go = (Button) findViewById(R.id.ex_cycle_image_start);
        btn_go.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), ex_ExerciseStart.class);
                        startActivity(intent);
                    }
                }
        );

        ArrayList<ex_RoutineAdapter> list_ItemArrayList = new ArrayList<ex_RoutineAdapter>();

    }

}

class ex_CycleAdapter {
    private int profile_image;
    private String title;
    private int delete_image;

    public ex_CycleAdapter(int profile_image, String title, int delete_image) {
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

class ex_CycleBaseAdapter extends BaseAdapter {

    Context context;
    ArrayList<ex_CycleAdapter> list_ex_CycleAdapter;

    public ex_CycleBaseAdapter(Context context, ArrayList<ex_CycleAdapter> list_ex_CycleAdapter) {
        this.context = context;
        this.list_ex_CycleAdapter = list_ex_CycleAdapter;
    }

    @Override
    public int getCount() {
        return this.list_ex_CycleAdapter.size();
    }

    @Override
    public Object getItem(int i) {
        return list_ex_CycleAdapter.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.ex_cycle_listview, null);
            profile_imageView = (ImageView)view.findViewById(R.id.ex_cycle_image_representimage);
            title_textView  =(TextView)view.findViewById(R.id.ex_cycle_text_title);
        }
        profile_imageView.setImageResource(list_ex_CycleAdapter.get(i).getProfile_image());
        title_textView.setText(list_ex_CycleAdapter.get(i).getTitle());
        delete_imageView.setImageResource(list_ex_CycleAdapter.get(i).getDelete_image());
        return view;
    }
}

class ex_ExerciseCycle{
            String exerciseTitle;
            String exercisePart;
            double exerciseWeight;
            int exerciseCount;
            int exerciseTimeMinute;
            int exerciseTimeSecond;
            int exerciseBreakTime;

        }