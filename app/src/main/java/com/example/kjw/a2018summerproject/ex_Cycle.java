package com.example.kjw.a2018summerproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageButton;
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
//    ex_CycleBaseAdapter ex_CycleBaseUpside;
//    ex_CycleBaseAdapter ex_CycleBaseDownside;
    ArrayList<ex_ExerciseRoutine> routineList = new ArrayList<ex_ExerciseRoutine>();

    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_cycle);
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
        int width = dm.widthPixels; //디바이스 화면 너비
        int height = dm.heightPixels; //디바이스 화면 높이

        Intent getRoutine = getIntent();
        final int nth_Couting = getRoutine.getIntExtra("position",1);
        ArrayList<Integer> intentCount = new ArrayList<Integer>();
        final String nth_Title = getRoutine.getStringExtra("title");
        int nowRoutine;
        if(intentCount.contains(nth_Couting))
        {
            nowRoutine = intentCount.indexOf(nth_Couting);
        }
        else
        {
            intentCount.add(nth_Couting);
            nowRoutine = routineList.size();
            ex_ExerciseRoutine newRoutine = new ex_ExerciseRoutine(nth_Title);
            routineList.add(newRoutine);
        }

//        final ArrayList<ex_CycleAdapter> list_ItemUpsideArrayList = new ArrayList<ex_CycleAdapter>();
//        final ArrayList<ex_CycleAdapter> list_ItemDownsideArrayList = new ArrayList<ex_CycleAdapter>();

//        ex_CycleBaseUpside = new ex_CycleBaseAdapter(this,list_ItemUpsideArrayList);
//        ex_CycleBaseDownside = new ex_CycleBaseAdapter(this,list_ItemDownsideArrayList);


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
        final ArrayList<String> cycleItems = new ArrayList<String>();
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, cycleItems);

        CustomChoiceSavedListViewAdapter savedAdapter;
        CustomChoiceStartListViewAdapter startAdapter;

        listView_Upside = (ListView) findViewById(R.id.ex_cycle_listview_launch);
        listView_Downside = (ListView) findViewById(R.id.ex_cycle_listview_savedlist);

        ImageButton btn_listUp = (ImageButton) findViewById(R.id.ex_cycle_image_listup);
        btn_listUp.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        int count;
                        count = adapter.getCount();
                        cycleItems.add("List");
                        adapter.notifyDataSetChanged();
                    }
                }
        );
    }
}
class CheckableDownsideLinearLayout extends LinearLayout implements Checkable {
    public CheckableDownsideLinearLayout(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    @Override
    public void setChecked(boolean checked) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.ex_cycle_checkbox_downside);

        if(checkBox.isChecked() != checked) {
            checkBox.setChecked(checked);
        }
    }

    @Override
    public boolean isChecked() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.ex_cycle_checkbox_downside);

        return checkBox.isChecked();
    }

    @Override
    public void toggle() {
        CheckBox checkbox = (CheckBox) findViewById(R.id.ex_cycle_checkbox_downside);

        setChecked(checkbox.isChecked() ? false : true);
    }
}
class ListViewItem {
    private Drawable icon ;
    private String text ;

    public void setIcon(Drawable icon) {
        this.icon = icon ;
    }
    public void setText(String text) {
        this.text = text ;
    }

    public Drawable getIcon() {
        return this.icon ;
    }

    public String getText() {
        return this.text ;
    }
}

class CustomChoiceSavedListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    // ListViewAdapter의 생성자
    public CustomChoiceSavedListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ex_cyclesaved_listview, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.ex_cycle_image_representimage_downside) ;
        TextView textTextView = (TextView) convertView.findViewById(R.id.ex_cycle_text_title_downside) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIcon());
        textTextView.setText(listViewItem.getText());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, String text) {
        ListViewItem item = new ListViewItem();

        item.setIcon(icon);
        item.setText(text);

        listViewItemList.add(item);
    }
}

class CustomChoiceStartListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    // ListViewAdapter의 생성자
    public CustomChoiceStartListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ex_cyclestart_listview, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.ex_cycle_image_representimage_upside) ;
        TextView textTextView = (TextView) convertView.findViewById(R.id.ex_cycle_text_title_upside) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIcon());
        textTextView.setText(listViewItem.getText());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, String text) {
        ListViewItem item = new ListViewItem();

        item.setIcon(icon);
        item.setText(text);

        listViewItemList.add(item);
    }
}
//class ex_CycleAdapter {
//        private int profile_image;
//        private String title;
//
//        public ex_CycleAdapter(int profile_image, String title) {
//            this.profile_image = profile_image;
//            this.title = title;
//        }
//
//        public int getProfile_image() {
//
//            return profile_image;
//        }
//
//        public void setProfile_image(int profile_image) {
//            this.profile_image = profile_image;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//    }
//
//    class ex_CycleBaseAdapter extends BaseAdapter {
//
//        Context context;
//        ArrayList<ex_CycleAdapter> list_ex_CycleAdapter;
//
//        public ex_CycleBaseAdapter(Context context, ArrayList<ex_CycleAdapter> list_ex_CycleAdapter) {
//            this.context = context;
//            this.list_ex_CycleAdapter = list_ex_CycleAdapter;
//        }
//
//        @Override
//        public int getCount() {
//            return this.list_ex_CycleAdapter.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return list_ex_CycleAdapter.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        ImageView profile_imageView;
//        TextView title_textView;
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            if (view == null) {
//                view = LayoutInflater.from(context).inflate(R.layout.ex_cyclesaved_listview, null);
//                profile_imageView = (ImageView) view.findViewById(R.id.ex_cycle_image_representimage);
//                title_textView = (TextView) view.findViewById(R.id.ex_cycle_text_title);
//            }
//            profile_imageView.setImageResource(list_ex_CycleAdapter.get(i).getProfile_image());
//            title_textView.setText(list_ex_CycleAdapter.get(i).getTitle());
//            return view;
//        }
//    }


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
