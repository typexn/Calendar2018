package com.example.kjw.a2018summerproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinYoung on 2018-07-22.
 */

public class SchSearchActivity extends Activity{

    private List<Schedule> list;          // 모든 데이터를 넣은 리스트변수(리스트목록)
    private ListView listView;          // 결과를 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SchSearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<Schedule> arraylist;

    //검색 성공
    static final int SUCCESS = 1;

    int selectNum; //선택한 스피너 index

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sch_search);

        editSearch = (EditText) findViewById(R.id.sch_search_edittext_input);
        listView = (ListView) findViewById(R.id.sch_search_listview_result);

        // 리스트를 생성한다.
        list = new ArrayList<Schedule>();

        // 검색에 사용할 데이터을 미리 저장한다.
        settingList();

        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = new ArrayList<Schedule>();
        arraylist.addAll(list);

        // 리스트에 연동될 아답터를 생성한다.
        adapter = new SchSearchAdapter(list, this);

        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter);

        //스피너 선언
        Spinner selectSpinner;
        selectSpinner = (Spinner) findViewById(R.id.sch_search_spinner_filter);
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.select, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        selectSpinner.setAdapter(adapter);

        //스피너 클릭이벤트
        selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        selectNum = 0;
                        break;
                    case 1:
                        selectNum = 1;
                        break;

                    case 2:
                        selectNum = 2;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //리스트뷰 아이템 클릭하면 need의 리스트뷰에 아이템을 넘김
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView, View childView, final int position, long id) {
                /* 상세창으로 넘어감 */
            }

        });

        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });


    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();


        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else {
            if (selectNum == 0) {
                for (int i = 0; i < arraylist.size(); i++) {
                    // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
//                    if (arraylist.get(i).title.contains(charText.toString())) {
//                        // 검색된 데이터를 리스트에 추가한다.
//                        list.add(arraylist.get(i));
//                    }
                }
            } else if (selectNum == 1) {
                // 리스트의 모든 데이터를 검색한다.
                for (int i = 0; i < arraylist.size(); i++) {

                }
            } else if (selectNum == 2) {

                for (int i = 0; i < arraylist.size(); i++) {

                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }

    // 검색에 사용될 데이터를 리스트에 추가한다.
    private void settingList() {
//        for (int i = 0; i < AppContext.onlySubjectList.size(); i++)
//            list.add(AppContext.onlySubjectList.get(i));

    }
}


class SchSearchAdapter extends BaseAdapter {

    private List<Schedule> list;
    private LayoutInflater inflater = null;


    public SchSearchAdapter(List<Schedule> list, Context context){
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if(convertView == null){
            convertView = inflater.inflate(R.layout.sch_search_listview,null);
        }

        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
        TextView tv = (TextView) convertView.findViewById(R.id.sch_search_listview_text_title);
        String listText = "";
        int i = 0;
        Schedule selected = list.get(position);

        //listText += selected.getName() + " / " + selected.cProf + "교수\n" + selected.day() + " " + selected.getTime();

        //같은과목(ex 선대1반 화욜/목욜)시간표시
//        while(i < AppContext.subjectList.length){
//            Subject sub = AppContext.subjectList[i];
//            if((sub.cNum.equals(selected.cNum)) && ((sub.cStart != selected.cStart) || sub.cDay != selected.cDay)){
//                listText += " / " + AppContext.subjectList[i].day() + " " + AppContext.subjectList[i].getTime();
//            }
//            i++;
//        }
        tv.setText(listText);

        return convertView;
    }

    public void addItem(Schedule sch){
        list.add(sch);
    }

}