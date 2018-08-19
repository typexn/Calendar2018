package com.example.kjw.a2018summerproject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by KJW on 2018-08-15.
 */

public class ex_Help extends Dialog {


    private LinearLayout container;


    public ex_Help(@NonNull View.OnClickListener context) {
        super((Context) context);
<<<<<<< HEAD
=======
        setContentView(R.layout.activity_ex_help);
>>>>>>> 2e3829cb06a588dbf91b0c21cb8ccb718a3e4b63
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_help);
    }
}
