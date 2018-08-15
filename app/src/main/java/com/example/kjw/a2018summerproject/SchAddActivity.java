package com.example.kjw.a2018summerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kjw.a2018summerproject.R;

public class SchAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sch_add);

        final EditText editTitle = findViewById(R.id.sch_add_edit_title);
        final EditText editLocation = findViewById(R.id.sch_add_edit_location);
        EditText editMemo = findViewById(R.id.sch_add_edit_memo);

        Button btnComplete = findViewById(R.id.sch_add_button_complete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString();
                String location = editLocation.getText().toString();
                String memo = editLocation.getText().toString();

                Schedule newSchedule = new Schedule(title, location, 15, 1, null, null, memo);
                sch_Mainactivity.schList.add(newSchedule);

                finish();
            }
        });
    }
}
