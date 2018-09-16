package com.example.kjw.a2018summerproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.kjw.a2018summerproject.R;

/**
 * Created by KJW on 2018-08-15.
 */

public class ex_ExerciseFinish extends AppCompatActivity {

    RatingBar ratingBar;
    private TextView txtRatingValue;
    private Button btnSubmit;
    EditText finishmemo;
    String resultsummary;

    float rate = Float.valueOf(getIntent().getExtras().get("edtRating").toString());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_exercisefinish);

        addListenerOnRatingBar();
        addListenerOnButton();
        finishmemo = (EditText) findViewById(R.id.ex_exercisefinish_text_memo);

        TextView text = (TextView)findViewById(R.id.ex_exercisefinish_text_result);
        text .setText(resultsummary);



    }

    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ex_exercisefinish_rating);

    }

    public void addListenerOnButton() {

        ratingBar = (RatingBar) findViewById(R.id.ex_exercisefinish_rating);
        btnSubmit = (Button) findViewById(R.id.ex_exercisefinish_button_submit);

        //if click on me, then display the current rating value.
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }

        });

    }
}
