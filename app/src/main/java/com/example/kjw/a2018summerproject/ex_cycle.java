package com.example.kjw.a2018summerproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ex_cycle extends Activity {

    private LinearLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_cycle);

        //부모 뷰
        container = (LinearLayout) findViewById(R.id.parent);

        //TextView 생성
        TextView view1 = new TextView(this);
        view1.setText("나는 텍스트뷰");

        //layout_width, layout_height, gravity 설정
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        view1.setLayoutParams(lp);

        //부모 뷰에 추가
        container.addView(view1);

    }

    public void nextBtnClicked(View v) {
        Intent intent = new Intent(ex_cycle.this, ex_ExerciseStart.class);
        startActivity(intent);
    }
}

class ex_cycle_sub  {


}