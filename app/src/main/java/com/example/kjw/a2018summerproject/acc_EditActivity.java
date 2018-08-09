package com.example.kjw.a2018summerproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class acc_EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_edit);

        //버튼 객체 생성

        TextView textViewYearMonth = (TextView) findViewById(R.id.acc_edit_textview_yearmonth);

        ListView listViewlist = (ListView) findViewById(R.id.acc_edit_listview_list);

        EditText editTextMoney = (EditText) findViewById(R.id.acc_edit_edittext_money);

        ImageButton imageButtonIncome = (ImageButton) findViewById(R.id.acc_edit_imagebutton_income);
        ImageButton imageButtonOutcome = (ImageButton) findViewById(R.id.acc_edit_imagebutton_outcome);
        ImageButton imageButtonRepeat = (ImageButton) findViewById(R.id.acc_edit_imagebutton_repeat);
        ImageButton imageButtonOK = (ImageButton) findViewById(R.id.acc_edit_imagebutton_OK);

        //


    }
}
