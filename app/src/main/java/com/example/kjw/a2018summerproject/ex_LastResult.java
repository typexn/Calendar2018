package com.example.kjw.a2018summerproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

public class ex_LastResult extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_lastresult);

        /*
            tmpStarCount : 임시 별점
         */
        double tmpStarCount = 3.5;
        String tmpSummaryText = "tmp";

        final RatingBar ratingStar = (RatingBar) findViewById(R.id.ex_lastresult_ratingbar_rating);
        TextView summaryText = (TextView) findViewById(R.id.ex_lastresult_text_summary);
        TextView memoText = (TextView) findViewById(R.id.ex_lastresult_text_memo);

        ratingStar.setRating((float) tmpStarCount);
        summaryText.setText(tmpSummaryText);
        memoText.setText(tmpSummaryText);

    }
}
