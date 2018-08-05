package com.example.kjw.a2018summerproject;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class acc_Weeklyactivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_weekly);

        // 버튼 객체 생성

        ImageButton imageButtonGomenu = (ImageButton) findViewById(R.id.acc_weekly_imagebutton_menu);

        ImageButton imageButtonGosearch = (ImageButton) findViewById(R.id.acc_weekly_imagebutton_search);

        ImageButton imageButtonGoadd = (ImageButton) findViewById(R.id.acc_weekly_imagebutton_add);

        Button buttonGobefroemonth = (Button) findViewById(R.id.acc_weekly_button_beforemonth);

        Button buttonGonextmonth = (Button) findViewById(R.id.acc_weekly_button_nextmonth);

        TextView textViewThismonth = (TextView) findViewById(R.id.acc_weekly_textview_yearmonth);

        Button buttonGotoday = (Button) findViewById(R.id.acc_weekly_button_today);

        Button buttonGomonth = (Button) findViewById(R.id.acc_weekly_button_month);

        Button buttonGoweek = (Button) findViewById(R.id.acc_weekly_button_week);

        Button buttonGoday = (Button) findViewById(R.id.acc_weekly_button_oneday);

        Button buttonGochart = (Button) findViewById(R.id.acc_weekly_button_chart);

        TextView textViewfirstweek = (TextView) findViewById(R.id.acc_weekly_textview_firstweek);

        TextView textViewsecondweek = (TextView) findViewById(R.id.acc_weekly_textview_secondweek);

        TextView textViewthirdweek = (TextView) findViewById(R.id.acc_weekly_textview_thirdweek);

        TextView textViewfourthweek = (TextView) findViewById(R.id.acc_weekly_textview_fourthweek);

        TextView textViewfifthweek = (TextView) findViewById(R.id.acc_weekly_textview_fifthweek);

        TextView textViewfirstincome = (TextView) findViewById(R.id.acc_weekly_textview_firstincome);

        TextView textViewsecondincome = (TextView) findViewById(R.id.acc_weekly_textview_secondincome);

        TextView textViewthirdincome = (TextView) findViewById(R.id.acc_weekly_textview_thirdincome);

        TextView textViewfourthincome = (TextView) findViewById(R.id.acc_weekly_textview_fourthincome);

        TextView textViewfifthincome = (TextView) findViewById(R.id.acc_weekly_textview_fifthincome);

        TextView textViewfirsthout = (TextView) findViewById(R.id.acc_weekly_textview_firstout);

        TextView textViewsecondout = (TextView) findViewById(R.id.acc_weekly_textview_secondout);

        TextView textViewthirdhout = (TextView) findViewById(R.id.acc_weekly_textview_thirdout);

        TextView textViewfourthout = (TextView) findViewById(R.id.acc_weekly_textview_fourthout);

        TextView textViewfifthout = (TextView) findViewById(R.id.acc_weekly_textview_fifthout);


    }
}
