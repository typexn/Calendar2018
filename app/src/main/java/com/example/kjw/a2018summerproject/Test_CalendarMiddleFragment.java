package com.example.kjw.a2018summerproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kjw.a2018summerproject.R;

import java.util.Calendar;


/**
 * Created by MinYoung on 2018-07-18.
 */

public class Test_CalendarMiddleFragment extends Fragment {

    public static final String ARG_YEAR = "year";
    public static final String ARG_MONTH = "month";

    public Test_CalendarMiddleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mYear = getArguments().getInt(ARG_YEAR);
//            mMonth = getArguments().getInt(ARG_MONTH);
//        } else {
//            Calendar now = Calendar.getInstance();
//            mYear = now.get(Calendar.YEAR);
//            mMonth = now.get(Calendar.MONTH);
//        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        adapter = new MonthlySlidePagerAdapter(context);
    }

    private TextView thisMonthTv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = null;
//        View v = inflater.inflate(R.layout.fragment_middle_calendar, container, false);
//
//        thisMonthTv = v.findViewById(R.id.this_month_tv);
//
//        MonthlyFragment mf = new MonthlyFragment();
//        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();
//        childFt.replace(R.id.frameLayout_childContainer, mf);
//        childFt.addToBackStack(null);
//        childFt.commit();
//
//        Button btnGoToSearch = v.findViewById(R.id.sch_main_button_search);
//
//        btnGoToSearch.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), SchSearchActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        mf.setOnMonthChangeListener(new MonthlyFragment.OnMonthChangeListener() {
//
//            @Override
//            public void onChange(int year, int month) {
//                thisMonthTv.setText(year + "년 " + (month + 1) + "월");
//            }
//
//            @Override
//            public void onDayClick(OneDayView dayView) {
//                Toast.makeText(getContext(), "Click  " + (dayView.get(Calendar.MONTH)+1) + "/" + dayView.get(Calendar.DAY_OF_MONTH), Toast.LENGTH_SHORT)
//                        .show();
//            }
//        });

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    /**
     * Object to preserve year and month
     *
     * @author Brownsoo
     */

}
