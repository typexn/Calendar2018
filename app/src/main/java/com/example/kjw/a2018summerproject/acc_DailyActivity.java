package com.example.kjw.a2018summerproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class acc_DailyActivity extends AppCompatActivity {
    @Override

        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_daily);

        // 버튼 객체 생성

        ImageButton imageButtonGomenu = (ImageButton) findViewById(R.id.acc_daily_imagebutton_menu);

        ImageButton imageButtonGosearch = (ImageButton) findViewById(R.id.acc_daily_imagebutton_search);

        ImageButton imageButtonGoadd = (ImageButton) findViewById(R.id.acc_daily_imagebutton_add);

        Button buttonGobefroemonth = (Button) findViewById(R.id.acc_daily_button_beforemonth);

        Button buttonGonextmonth = (Button) findViewById(R.id.acc_daily_button_nextmonth);

        TextView textViewThismonth = (TextView) findViewById(R.id.acc_daily_textview_yearmonth);

        Button buttonGotoday = (Button) findViewById(R.id.acc_daily_button_today);

        Button buttonGomonth = (Button) findViewById(R.id.acc_daily_button_month);

        Button buttonGoweek = (Button) findViewById(R.id.acc_daily_button_week);

        Button buttonGoday = (Button) findViewById(R.id.acc_daily_button_oneday);

        Button buttonGochart = (Button) findViewById(R.id.acc_daily_button_chart);

        ListView listViewincomelist = (ListView) findViewById(R.id.acc_daily_listview_income);

        ListView listViewoutlist = (ListView) findViewById(R.id.acc_daily_listview_out);

        TextView textViewdailyincome = (TextView) findViewById(R.id.acc_daily_textview_income);

        TextView textViewdailyout = (TextView) findViewById(R.id.acc_daily_textview_output);

        TextView textViewdailytotal = (TextView) findViewById(R.id.acc_daily_textview_totalmoney);

        buttonGomonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goaccmonth = new Intent(acc_DailyActivity.this, acc_MainActivity.class);
                startActivity(goaccmonth);
                }
            });

        imageButtonGoadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goaccadd = new Intent(acc_DailyActivity.this, acc_EditActivity.class);
                startActivity(goaccadd);
                }
             });
        buttonGoweek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goaccweek = new Intent(acc_DailyActivity.this, acc_Weeklyactivity.class);
                startActivity(goaccweek);
                }
        });
        }
    }
