package com.example.kjw.a2018summerproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class acc_EditActivity extends AppCompatActivity {
    int x = 0 ;
    Integer moneyIn ;
    Integer moneyOut ;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_edit);


        //버튼 객체 생성

        TextView textViewYearMonth = (TextView) findViewById(R.id.acc_edit_textview_yearmonth);

        GridView gridViewlist = (GridView) findViewById(R.id.acc_edit_gridview_list);
        gridViewlist.setAdapter(new ImageAdapter(this));
        gridViewlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(acc_EditActivity.this, ""+position, Toast.LENGTH_SHORT).show();
            }
        });



        ImageButton imageButtonIncome = (ImageButton) findViewById(R.id.acc_edit_imagebutton_income);
        imageButtonIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x = 0;
            }
        });

        ImageButton imageButtonOutcome = (ImageButton) findViewById(R.id.acc_edit_imagebutton_outcome);
        imageButtonOutcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x = 1;
            }
        });

        ImageButton imageButtonOK = (ImageButton) findViewById(R.id.acc_edit_imagebutton_OK);
        imageButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editTextMoney = (EditText) findViewById(R.id.acc_edit_edittext_money);
                if(x==0){
                    moneyIn = Integer.parseInt(editTextMoney.getText().toString());
                    Toast.makeText(acc_EditActivity.this, "입금으로 저장되었습니다.", Toast.LENGTH_SHORT).show();
                }if(x==1){
                    moneyOut = Integer.parseInt(editTextMoney.getText().toString());
                    Toast.makeText(acc_EditActivity.this, "지출로 저장되었습니다.", Toast.LENGTH_SHORT).show();
                }
                Intent gomain = new Intent(acc_EditActivity.this, acc_MainActivity.class);
                gomain.putExtra("in&out" , x);
                gomain.putExtra("MoneyIn", moneyIn);
                gomain.putExtra("MoneyOut", moneyOut);
                startActivity(gomain);
            }
        });

        //


    }
}
