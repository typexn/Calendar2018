package com.example.kjw.a2018summerproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;


public class ex_Cycle extends AppCompatActivity {
    Button dial;
    ex_CycleMenu cd;
    int ex_CycleCount = 0;
    ListView listView_Upside;
    ListView listView_Downside;
    ex_CycleStartBaseAdapter ex_CycleBaseUpside;
    ex_CycleSavedBaseAdapter ex_CycleBaseDownside;
    ArrayList<ex_ExerciseRoutine> routineList = new ArrayList<ex_ExerciseRoutine>();
    ArrayList<ex_ExerciseCycle> newCycleList = new ArrayList<ex_ExerciseCycle>();

    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_cycle);
//        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
//        int width = dm.widthPixels; //디바이스 화면 너비
//        int height = dm.heightPixels; //디바이스 화면 높이

        Intent getRoutine = getIntent();
        final int nth_Couting = getRoutine.getIntExtra("position", 1);
        ArrayList<Integer> intentCount = new ArrayList<Integer>();
        final String nth_Title = getRoutine.getStringExtra("title");
        int nowRoutine;
        if (intentCount.contains(nth_Couting)) {
            nowRoutine = intentCount.indexOf(nth_Couting);
        } else {
            intentCount.add(nth_Couting);
            nowRoutine = routineList.size();
            ex_ExerciseRoutine newRoutine = new ex_ExerciseRoutine(nth_Title);
            routineList.add(newRoutine);
        }

//        final ArrayList<ex_CycleAdapter> list_ItemUpsideArrayList = new ArrayList<ex_CycleAdapter>();
//        final ArrayList<ex_CycleAdapter> list_ItemDownsideArrayList = new ArrayList<ex_CycleAdapter>();

//        ex_CycleBaseUpside = new ex_CycleBaseAdapter(this,list_ItemUpsideArrayList);
//        ex_CycleBaseDownside = new ex_CycleBaseAdapter(this,list_ItemDownsideArrayList);
        Button dial = (Button) findViewById(R.id.ex_cycle_image_plus);
        dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCycleMenu = new Intent(ex_Cycle.this, ex_CycleMenu.class);
                startActivityForResult(goToCycleMenu, 3000);
            }
        });
//        cd = new ex_CycleMenu(this);
//        WindowManager.LayoutParams wm = cd.getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
//        wm.copyFrom(cd.getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미
//        wm.width = width / 2;  //화면 너비의 절반
//        wm.height = height / 2;  //화면 높이의 절반
//        dial.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ex_CycleCount++;
//                cd.show();  //다이얼로그
//            }
//        });
//        final ArrayList<ex_ExerciseRoutine> nth_Routine = (ArrayList<ex_ExerciseRoutine>) getRoutine.getSerializableExtra("Routine");
        Button btn_go = (Button) findViewById(R.id.ex_cycle_image_start);
        btn_go.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent goToStart = new Intent(getApplicationContext(), ex_ExerciseStart.class);
                        goToStart.putExtra("cycle", newCycleList);
                        goToStart.putExtra("count", nth_Couting);
                        startActivity(goToStart);
                    }
                }
        );
        final ArrayList<String> cycleItems = new ArrayList<String>();
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, cycleItems);
// CheckBox
//        CustomChoiceSavedListViewAdapter savedAdapter = new CustomChoiceSavedListViewAdapter();
//        CustomChoiceStartListViewAdapter startAdapter = new CustomChoiceStartListViewAdapter();
        final ArrayList<ex_CycleSaveAdapter> cycleSaveAdapters = new ArrayList<ex_CycleSaveAdapter>();
        final ArrayList<ex_CycleStartAdapter> cycleStartAdapters = new ArrayList<ex_CycleStartAdapter>();

        ex_CycleSavedBaseAdapter savedAdapter = new ex_CycleSavedBaseAdapter(this, cycleSaveAdapters);
        ex_CycleStartBaseAdapter startAdapter = new ex_CycleStartBaseAdapter(this, cycleStartAdapters);

        listView_Upside = (ListView) findViewById(R.id.ex_cycle_listview_launch);
        listView_Downside = (ListView) findViewById(R.id.ex_cycle_listview_savedlist);

        listView_Downside.setAdapter(savedAdapter);
        listView_Upside.setAdapter(startAdapter);

        listView_Downside.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ex_CycleStartAdapter newCycleAdapter = new ex_CycleStartAdapter(R.drawable.ic_launcher_foreground, newCycleList.get(newCycleList.size() - 1).exerciseTitle, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background);
                ex_CycleBaseUpside.addItem(newCycleAdapter);
                ex_CycleBaseUpside.notifyDataSetChanged();
            }
        });
//        ex_CycleAdapter newCycleAdapter = new ex_CycleAdapter(R.drawable.ic_launcher_foreground,newCycleList.get(newCycleList.size()-1).exerciseTitle,R.drawable.ic_launcher_background);
//        ex_CycleBaseDownside.addItem(newCycleAdapter);
//        ex_CycleBaseDownside.notifyDataSetChanged();

        // 리스트 올려보내기
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Intent getFromCycleMenu = data;
            final String getTitle = getFromCycleMenu.getStringExtra("cycletitle");
            Log.d("Uk", "Uk Title" + getTitle);
            final String getPart = getFromCycleMenu.getStringExtra("cyclepart");
//            final String getWeight = getFromCycleMenu.getStringExtra("cycleweight");
            String getWeight = "1.5";
            double changedWeight = Double.parseDouble(getWeight);
//            final String getCount = getFromCycleMenu.getStringExtra("cyclenumber");
            String getCount = "1";
            int changedCount = Integer.parseInt(getCount);
//            final String getTime = getFromCycleMenu.getStringExtra("cycletime");
            String getTime = "1";
            int changedTime = Integer.parseInt(getTime);
//            final String getBreaktime = getFromCycleMenu.getStringExtra("cyclebreaktime");
            String getBreaktime = "1";
            int changedBreakTime = Integer.parseInt(getBreaktime);
            newCycleList.add(new ex_ExerciseCycle(getTitle, getPart, changedWeight, changedCount, changedTime, changedBreakTime, newCycleList.size()));
            ex_CycleSaveAdapter newCycleAdapter = new ex_CycleSaveAdapter(R.drawable.ic_launcher_foreground, newCycleList.get(newCycleList.size() - 1).exerciseTitle, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background);
            ex_CycleBaseDownside.addItem(newCycleAdapter);
            ex_CycleBaseDownside.notifyDataSetChanged();
        }
    }
}

//class CheckableLinearLayout extends LinearLayout implements Checkable {
//    public CheckableLinearLayout(Context context, AttributeSet attrs) {
//        super(context,attrs);
//    }
//
//    @Override
//    public void setChecked(boolean checked) {
//        CheckBox checkBox = (CheckBox) findViewById(R.id.ex_cycle_checkbox_downside);
//
//        if(checkBox.isChecked() != checked) {
//            checkBox.setChecked(checked);
//        }
//    }
//
//    @Override
//    public boolean isChecked() {
//        CheckBox checkBox = (CheckBox) findViewById(R.id.ex_cycle_checkbox_downside);
//
//        return checkBox.isChecked();
//    }
//
//    @Override
//    public void toggle() {
//        CheckBox checkbox = (CheckBox) findViewById(R.id.ex_cycle_checkbox_downside);
//
//        setChecked(checkbox.isChecked() ? false : true);
//    }
//}
//class ListViewItem {
//    private Drawable icon ;
//    private String text ;
//
//    public void setIcon(Drawable icon) {
//        this.icon = icon ;
//    }
//    public void setText(String text) {
//        this.text = text ;
//    }
//
//    public Drawable getIcon() {
//        return this.icon ;
//    }
//    public String getText() {
//        return this.text ;
//    }
//}
//Checkbox어댑터
//class CustomChoiceSavedListViewAdapter extends BaseAdapter {
//    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
//    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;
//
//    // ListViewAdapter의 생성자
//    public CustomChoiceSavedListViewAdapter() {
//
//    }
//
//    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
//    @Override
//    public int getCount() {
//        return listViewItemList.size() ;
//    }
//
//    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        final int pos = position;
//        final Context context = parent.getContext();
//
//        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.ex_cyclesaved_listview, parent, false);
//        }
//
//        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
//        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.ex_cycle_image_representimage_downside) ;
//        TextView textTextView = (TextView) convertView.findViewById(R.id.ex_cycle_text_title_downside) ;
//
//        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
//        ListViewItem listViewItem = listViewItemList.get(position);
//
//        // 아이템 내 각 위젯에 데이터 반영
//        iconImageView.setImageDrawable(listViewItem.getIcon());
//        textTextView.setText(listViewItem.getText());
//
//        return convertView;
//    }
//
//    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
//    @Override
//    public long getItemId(int position) {
//        return position ;
//    }
//
//    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
//    @Override
//    public Object getItem(int position) {
//        return listViewItemList.get(position) ;
//    }
//
//    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
//    public void addItem(Drawable icon, String text) {
//        ListViewItem item = new ListViewItem();
//
//        item.setIcon(icon);
//        item.setText(text);
//
//        listViewItemList.add(item);
//    }
//}
//
//class CustomChoiceStartListViewAdapter extends BaseAdapter {
//    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
//    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;
//
//    // ListViewAdapter의 생성자
//    public CustomChoiceStartListViewAdapter() {
//
//    }
//
//    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
//    @Override
//    public int getCount() {
//        return listViewItemList.size() ;
//    }
//
//    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        final int pos = position;
//        final Context context = parent.getContext();
//
//        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.ex_cyclestart_listview, parent, false);
//        }
//
//        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
//        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.ex_cycle_image_representimage_upside) ;
//        TextView textTextView = (TextView) convertView.findViewById(R.id.ex_cycle_text_title_upside) ;
//
//        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
//        ListViewItem listViewItem = listViewItemList.get(position);
//
//        // 아이템 내 각 위젯에 데이터 반영
//        iconImageView.setImageDrawable(listViewItem.getIcon());
//        textTextView.setText(listViewItem.getText());
//
//        convertView.setFocusable(true);
//
//        return convertView;
//    }
//
//    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
//    @Override
//    public long getItemId(int position) {
//        return position ;
//    }
//
//    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
//    @Override
//    public Object getItem(int position) {
//        return listViewItemList.get(position) ;
//    }
//
//    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
//    public void addItem(Drawable icon, String text) {
//        ListViewItem item = new ListViewItem();
//        item.setIcon(icon);
//        item.setText(text);
//        listViewItemList.add(item);
//    }
//}
class ex_CycleStartAdapter {
    private int profile_image;
    private String title;
    private int plus_image;
    private int delete_image;

    public ex_CycleStartAdapter(int profile_image, String title, int plus_image, int delete_image) {
        this.profile_image = profile_image;
        this.title = title;
        this.plus_image = plus_image;
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

    public int getPlus_image() {

        return plus_image;
    }

    public void setPlus_image(int profile_image) {
        this.profile_image = profile_image;
    }

    public void setDelete_image(int delete_image) {
        this.delete_image = delete_image;
    }

    public int getDelete_image() {
        return delete_image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

class ex_CycleSaveAdapter {
    private int profile_image;
    private String title;
    private int plus_image;
    private int delete_image;

    public ex_CycleSaveAdapter(int profile_image, String title, int plus_image, int delete_image) {
        this.profile_image = profile_image;
        this.title = title;
        this.plus_image = plus_image;
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

    public int getPlus_image() {

        return plus_image;
    }

    public void setPlus_image(int profile_image) {
        this.profile_image = profile_image;
    }

    public void setDelete_image(int delete_image) {
        this.delete_image = delete_image;
    }

    public int getDelete_image() {
        return delete_image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

class ex_CycleStartBaseAdapter extends BaseAdapter {

    Context context;
    ArrayList<ex_CycleStartAdapter> list_ex_CycleAdapter;
    LayoutInflater mLiInflater;

    public ex_CycleStartBaseAdapter(Context context, ArrayList<ex_CycleStartAdapter> list_ex_CycleAdapter) {
        this.context = context;
        this.list_ex_CycleAdapter = list_ex_CycleAdapter;
        this.mLiInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public void addItem(ex_CycleStartAdapter item) {
        list_ex_CycleAdapter.add(item);
    }

    ImageView profile_imageView;
    TextView title_textView;
    ImageButton plus_imagebutton;
    ImageButton delete_imagebutton;

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.ex_cyclesaved_listview, null);
            profile_imageView = (ImageView) view.findViewById(R.id.ex_cycle_image_representimage_upside);
            title_textView = (TextView) view.findViewById(R.id.ex_cycle_text_title_upside);
            plus_imagebutton = (ImageButton) view.findViewById(R.id.ex_cycle_imagebutton_upside_plus);
            delete_imagebutton = (ImageButton) view.findViewById(R.id.ex_cycle_imagebutton_upside_delete);
        }
        profile_imageView.setImageResource(list_ex_CycleAdapter.get(i).getProfile_image());
        title_textView.setText(list_ex_CycleAdapter.get(i).getTitle());
        plus_imagebutton.setImageResource(list_ex_CycleAdapter.get(i).getPlus_image());
        delete_imagebutton.setImageResource(list_ex_CycleAdapter.get(i).getDelete_image());
        return view;
    }
}

class ex_CycleSavedBaseAdapter extends BaseAdapter {

    Context context;
    ArrayList<ex_CycleSaveAdapter> list_ex_CycleAdapter;
    LayoutInflater mLiInflater;

    public ex_CycleSavedBaseAdapter(Context context, ArrayList<ex_CycleSaveAdapter> list_ex_CycleAdapter) {
        this.context = context;
        this.list_ex_CycleAdapter = list_ex_CycleAdapter;
        this.mLiInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public void addItem(ex_CycleSaveAdapter item) {
        list_ex_CycleAdapter.add(item);
    }

    ImageView profile_imageView;
    TextView title_textView;
    ImageButton plus_ImageButton;
    ImageButton delete_ImageButton;

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.ex_cyclesaved_listview, null);
            profile_imageView = (ImageView) view.findViewById(R.id.ex_cycle_image_representimage_downside);
            title_textView = (TextView) view.findViewById(R.id.ex_cycle_text_title_downside);
            plus_ImageButton = (ImageButton) view.findViewById(R.id.ex_cycle_imagebutton_downside_plus);
            delete_ImageButton = (ImageButton) view.findViewById(R.id.ex_cycle_imagebutton_downside_delete);
        }
        profile_imageView.setImageResource(list_ex_CycleAdapter.get(i).getProfile_image());
        title_textView.setText(list_ex_CycleAdapter.get(i).getTitle());
        plus_ImageButton.setImageResource(list_ex_CycleAdapter.get(i).getDelete_image());
        delete_ImageButton.setImageResource(list_ex_CycleAdapter.get(i).getDelete_image());

        return view;
    }
}


class ex_ExerciseCycle implements Serializable {
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
