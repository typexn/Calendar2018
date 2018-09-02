package com.example.kjw.a2018summerproject;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CalendarAdapter extends BaseAdapter {

    private ArrayList<DayInfo> mDayList;
    private Context mContext;

    private ArrayList<Object> mData;
    private int mResource;

    private LayoutInflater mLiInflater;


    /**

     * Adpater 생성자

     *

     * @param context

     *            컨텍스트

     * @param textResource

     *            레이아웃 리소스

     * @param dayList

     *            날짜정보가 들어있는 리스트

     */

    public CalendarAdapter(Context context, int textResource, ArrayList<DayInfo> dayList) {
        this.mContext = context;
        this.mDayList = dayList;
        this.mResource = textResource;
        this.mLiInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        // TODO Auto-generated method stub

        return mDayList.size();
    }



    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub

        return mDayList.get(position);
    }



    @Override
    public long getItemId(int position) {

        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DayInfo day = mDayList.get(position);
        DayViewHolde dayViewHolder;

        if(convertView == null) {
            convertView = mLiInflater.inflate(mResource, null);
            if(position % 7 == 6) {
                convertView.setLayoutParams(new GridView.LayoutParams(getCellWidthDP()+getRestCellWidthDP(), getCellHeightDP()));
            }
            else {
                convertView.setLayoutParams(new GridView.LayoutParams(getCellWidthDP(), getCellHeightDP()));
            }
            dayViewHolder = new DayViewHolde();
            dayViewHolder.llBackground = (LinearLayout)convertView.findViewById(R.id.day_cell_ll_background);

            dayViewHolder.tvDay = (TextView) convertView.findViewById(R.id.day_cell_tv_day);
            dayViewHolder.tvExist = (TextView) convertView.findViewById(R.id.day_cell_tv_isExist);
            convertView.setTag(dayViewHolder);
        }
        else {
            dayViewHolder = (DayViewHolde) convertView.getTag();
        }

        if(day != null) {
            dayViewHolder.tvDay.setText(day.getDay());
            if(day.isInMonth()) {
                if(position % 7 == 0) {
                    dayViewHolder.tvDay.setTextColor(Color.RED);
                }
                else if(position % 7 == 6) {
                    dayViewHolder.tvDay.setTextColor(Color.BLUE);
                }
                else {
                    dayViewHolder.tvDay.setTextColor(Color.BLACK);
                }
            }
            else {
                dayViewHolder.tvDay.setTextColor(Color.GRAY);
            }

            if(day.isExistSch()){
                dayViewHolder.tvExist.setText("●");
            }else{
                dayViewHolder.tvExist.setText("");
            }
        }

        return convertView;
    }

    public void changeDayInfo(int position, boolean isExist){ //minyoung
        mDayList.get(position).setExistSch(isExist);
    }

//    public void add(int position){
//        DayViewHolde dayViewHolder = (DayViewHolde) getView(position, null, null).getTag();
//    }



    public class DayViewHolde {
        public LinearLayout llBackground;
        public TextView tvDay;
        public TextView tvExist; //add minyoung
//        public ArrayList<Object> mData;
    }



    private int getCellWidthDP() {
//      int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int cellWidth = 480/7;

        return cellWidth*2; // minyoung *2 했음 임시로
    }



    private int getRestCellWidthDP() {

//      int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int cellWidth = 480%7;

        return cellWidth;
    }



    private int getCellHeightDP() {
//      int height = mContext.getResources().getDisplayMetrics().widthPixels;
        int cellHeight = 230;

        return cellHeight;
    }

}