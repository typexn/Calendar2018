package com.example.kjw.a2018summerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class sch_Mainactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sch_main);

        //다이어리 메인으로 넘어가는 임시 버튼
        Button btn_diary = (Button) findViewById(R.id.btn_diary);
        btn_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goTodiaryMain = new Intent (sch_Mainactivity.this, diary_Mainactivity.class);
                startActivity(goTodiaryMain);
            }
        });
    }
}
