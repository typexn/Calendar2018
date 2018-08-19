package com.example.kjw.a2018summerproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class acc_EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_edit);

        final String money ;

        //버튼 객체 생성

        TextView textViewYearMonth = (TextView) findViewById(R.id.acc_edit_textview_yearmonth);

        GridView gridViewlist = (GridView) findViewById(R.id.acc_edit_gridview_list);

        EditText editTextMoney = (EditText) findViewById(R.id.acc_edit_edittext_money);
        money = editTextMoney.toString();

        ImageButton imageButtonIncome = (ImageButton) findViewById(R.id.acc_edit_imagebutton_income);
        ImageButton imageButtonOutcome = (ImageButton) findViewById(R.id.acc_edit_imagebutton_outcome);
        ImageButton imageButtonRepeat = (ImageButton) findViewById(R.id.acc_edit_imagebutton_repeat);
        ImageButton imageButtonOK = (ImageButton) findViewById(R.id.acc_edit_imagebutton_OK);
        imageButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gomain = new Intent(acc_EditActivity.this, acc_MainActivity.class);
                gomain.putExtra("Money", money);
                startActivity(gomain);
            }
        });

        //


    }
}
