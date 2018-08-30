package com.example.kjw.a2018summerproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class ex_Routine extends Activity {

    int routineCount = 0;
    ex_ExerciseRoutine [] routineArray = new ex_ExerciseRoutine[0];
    ListView listView;
    ex_RoutineBaseAdapter ex_RoutineBase;
    ArrayList<ex_ExerciseRoutine> routinelist = new ArrayList<ex_ExerciseRoutine>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_routine);
        listView = (ListView)findViewById(R.id.ex_routine_listview_routine);


        final ArrayList<ex_RoutineAdapter> list_ItemArrayList = new ArrayList<ex_RoutineAdapter>();

        ex_RoutineBase = new ex_RoutineBaseAdapter(this, list_ItemArrayList);

        listView.setAdapter(ex_RoutineBase);

        Button routinePlus = (Button) findViewById(R.id.ex_routine_button_routineplus);
        routinePlus.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v)
            {
                final EditText routineTitle = (EditText) findViewById(R.id.ex_routine_edittext_routinetitle);
                String value = routineTitle.getText().toString();
//                ex_ExerciseRoutine newRoutine = new ex_ExerciseRoutine(value);
//
//                ex_ExerciseRoutine[] tmpRoutine = new ex_ExerciseRoutine[routineArray.length+1];
//                System.arraycopy(routineArray,0,tmpRoutine,0,routineArray.length);
//                routineArray = tmpRoutine;
//                routineArray[routineCount] = newRoutine;
//
//                Log.d("Uk", "routineArray.length : " + routineArray.length + "");
                routinelist.add(new ex_ExerciseRoutine(value));
                ex_RoutineAdapter newAdapter = new ex_RoutineAdapter(R.drawable.ic_launcher_foreground, routinelist.get(routinelist.size()-1).routineTitle, R.drawable.ic_launcher_foreground);
                ex_RoutineBase.addItem(newAdapter);
//
//                Log.d("Uk", "list_itemArrayList.size : " + list_ItemArrayList.size() + "");

                ex_RoutineBase.notifyDataSetChanged();

//                routineCount++;
            }
        });
        Button tmpButton = (Button) findViewById(R.id.ex_routine_button_delactivate);
        tmpButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent tmpintent = new Intent(ex_Routine.this,ex_Cycle.class);
                startActivity(tmpintent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent goToCycle = new Intent(ex_Routine.this, ex_Cycle.class);
                goToCycle.putExtra("nth",routinelist.size());
                Log.d("Uk", "routinelist.size() " + routinelist.size() + "");
                goToCycle.putExtra("position",position);
                goToCycle.putExtra("Routine",routinelist);
                startActivity(goToCycle);
            }
        });

    }
}

/*
    Routine 객체 (이미지 / 텍스트 / 이미지)
 */
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
    LayoutInflater mLiInflater;

    public ex_RoutineBaseAdapter(Context context, ArrayList<ex_RoutineAdapter> list_ex_RoutineAdapter) {
        this.context = context;
        this.list_ex_RoutineAdapter = list_ex_RoutineAdapter;
        this.mLiInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(ex_RoutineAdapter item){
        list_ex_RoutineAdapter.add(item);
    }

    @Override
    public int getCount() {
        return this.list_ex_RoutineAdapter.size();
    }

    public int getPosition() { return this.getPosition(); }

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
            view = mLiInflater.inflate(R.layout.ex_routine_listview, null);
//            view = LayoutInflater.from(context).inflate(R.layout.ex_routine_listview, null);
            profile_imageView = (ImageView)view.findViewById(R.id.ex_routine_listview_image_part);
            title_textView  =(TextView)view.findViewById(R.id.ex_routine_listview_text_exercisetitle);
            delete_imageView = (ImageView)view.findViewById(R.id.ex_routine_listview_image_delete);
        }
        profile_imageView.setImageResource(list_ex_RoutineAdapter.get(i).getProfile_image());
        title_textView.setText(list_ex_RoutineAdapter.get(i).getTitle());
        delete_imageView.setImageResource(list_ex_RoutineAdapter.get(i).getDelete_image());


        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), ex_Cycle.class);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
class ex_ExerciseRoutine implements Serializable {

    String routineTitle;

    int cycleIndexNumber = 0;
    ex_ExerciseCycle [] Cycle = new ex_ExerciseCycle [5];
    int cycleCounting = 0;

    ex_ExerciseRoutine(String routineTitle)
    {
        this.routineTitle =  routineTitle;
    }

    void addCycle(ex_ExerciseCycle newCycle) {
        if(cycleIndexNumber >= Cycle.length) {
            ex_ExerciseCycle[] tmpCycleCart = new ex_ExerciseCycle[Cycle.length*2];
            System.arraycopy(Cycle,0,tmpCycleCart,0,Cycle.length);
            Cycle = tmpCycleCart;
        }
        Cycle[cycleIndexNumber++] = newCycle;
        cycleCounting++;
    }

    void deleteCycle(int deleteIndexNumber){
        for(int i = deleteIndexNumber; i < cycleIndexNumber ; i++){
            Cycle[i].exerciseTitle = Cycle[i+1].exerciseTitle;
            Cycle[i].exercisePart = Cycle[i+1].exercisePart;
            Cycle[i].exerciseWeight = Cycle[i+1].exerciseWeight;
            Cycle[i].exerciseTimeSecond = Cycle[i+1].exerciseTimeSecond;
            Cycle[i].exerciseBreakTime = Cycle[i+1].exerciseBreakTime;
            Cycle[i].exerciseSequence = Cycle[i+1].exerciseSequence;
        }
        Cycle[cycleIndexNumber] = null;
        cycleIndexNumber--;
    }
}
