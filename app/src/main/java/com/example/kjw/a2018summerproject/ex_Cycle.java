package com.example.kjw.a2018summerproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ex_Cycle extends AppCompatActivity {
    Button dial;
    ex_CycleMenu cd;
    int ex_CycleCount = 0;
    ListView listView_Upside;
    ListView listView_Downside;
    ex_CycleBaseAdapter ex_CycleBaseUpside;
    ex_CycleBaseAdapter ex_CycleBaseDownside;

    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_cycle);
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
        int width = dm.widthPixels; //디바이스 화면 너비
        int height = dm.heightPixels; //디바이스 화면 높이

        final ArrayList<ex_CycleAdapter> list_ItemUpsideArrayList = new ArrayList<ex_CycleAdapter>();
        final ArrayList<ex_CycleAdapter> list_ItemDownsideArrayList = new ArrayList<ex_CycleAdapter>();

        listView_Upside = (ListView) findViewById(R.id.ex_cycle_listview_launch);
        listView_Downside = (ListView) findViewById(R.id.ex_cycle_listview_savedlist);

        ex_CycleBaseUpside = new ex_CycleBaseAdapter(this,list_ItemUpsideArrayList);
        ex_CycleBaseDownside = new ex_CycleBaseAdapter(this,list_ItemDownsideArrayList);


        dial = (Button) findViewById(R.id.ex_cycle_image_plus);
        cd = new ex_CycleMenu(this);
        WindowManager.LayoutParams wm = cd.getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
        wm.copyFrom(cd.getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미
        wm.width = width / 2;  //화면 너비의 절반
        wm.height = height / 2;  //화면 높이의 절반
        dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ex_CycleCount++;
                cd.show();  //다이얼로그
            }
        });

        Intent getRoutine = getIntent();
        final int nth_Couting = getRoutine.getIntExtra("position",1);
        Log.d("Uk", "routineposition " + nth_Couting + "");
//        final ArrayList<ex_ExerciseRoutine> nth_Routine = (ArrayList<ex_ExerciseRoutine>) getRoutine.getSerializableExtra("Routine");

        ex_ExerciseCycle tmpCycle = new ex_ExerciseCycle("a","a",(double)1,1,1,1,1);
//        nth_Routine.get(nth_Couting).addCycle(tmpCycle);


        Button btn_go = (Button) findViewById(R.id.ex_cycle_image_start);
        btn_go.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent goToStart = new Intent(getApplicationContext(), ex_ExerciseStart.class);
                        goToStart.putExtra("count",nth_Couting);
                        startActivity(goToStart);
                    }
                }
        );



    }
}
    class ex_CycleAdapter {
        private int profile_image;
        private String title;

        public ex_CycleAdapter(int profile_image, String title) {
            this.profile_image = profile_image;
            this.title = title;
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

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.ex_cycle_listview, null);
                profile_imageView = (ImageView) view.findViewById(R.id.ex_cycle_image_representimage);
                title_textView = (TextView) view.findViewById(R.id.ex_cycle_text_title);
            }
            profile_imageView.setImageResource(list_ex_CycleAdapter.get(i).getProfile_image());
            title_textView.setText(list_ex_CycleAdapter.get(i).getTitle());
            return view;
        }
    }


    class ex_ExerciseCycle {
        String exerciseTitle;
        String exercisePart;
        double exerciseWeight;
        int exerciseCount;
        int exerciseTimeSecond;
        int exerciseBreakTime;
        int exerciseSequence;
            /*
                exerciseTitle : 운동 제목
                exercisePart : 운동 부위
                exerciseWeight : 무게 설정
                exerciseCount : 개수
                exerciseTimeSecond : 운동시간
                exerciseBreakTime : 쉬는시간
                exerciseSequence : n번째 운동
             */

        ex_ExerciseCycle(String exerciseTitle, String exercisePart, double exerciseWeight, int exerciseCount, int exerciseTimeSecond, int exerciseBreakTime, int cycleCount) {
            this.exerciseTitle = exerciseTitle;
            this.exercisePart = exercisePart;
            this.exerciseWeight = exerciseWeight;
            this.exerciseCount = exerciseCount;
            this.exerciseTimeSecond = exerciseTimeSecond;
            this.exerciseBreakTime = exerciseBreakTime;
            this.exerciseSequence = cycleCount;
        }
    }
