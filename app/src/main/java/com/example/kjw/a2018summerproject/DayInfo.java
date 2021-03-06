package com.example.kjw.a2018summerproject;

public class DayInfo {


    private String day;
    private int year;
    private int month;
    private boolean inMonth;
    private boolean inYear; //minyoung
    private boolean isExistSch; //minyoung

    /**
     * 날짜를 반환한다.
     *
     * @return day 날짜
     */
    public String getDay()
    {
        return day;
    }

    /**
     * 날짜를 저장한다.
     *
     * @param day 날짜
     */
    public void setDay(String day)
    {
        this.day = day;
    }

    /**
     * 이번달의 날짜인지 정보를 반환한다.
     *
     * @return inMonth(true/false)
     */
    public boolean isInMonth()
    {
        return inMonth;
    }

    /**
     * 이번달의 날짜인지 정보를 저장한다.
     *
     * @param inMonth(true/false)
     */
    public void setInMonth(boolean inMonth)
    {
        this.inMonth = inMonth;
    }

    public boolean isInYear() { return inYear; }
    public void setInYear(boolean inYear) { this.inYear = inYear; }
    public void setExistSch(boolean isExist){ this.isExistSch = isExist; } //minyoung
    public boolean isExistSch() { return isExistSch; } //minyoung
    public void setYear(int year){ this.year = year; }
    public int getYear(){ return this.year; }

    public void setMonth(int month){ this.month = month; }
    public int getMonth(){ return this.month; }
    public String getMonthString(){
        String ret = String.valueOf(this.month);
        if(this.month / 10 == 0){
            ret = "0"+ret;
        }
        return ret;
    }

    public String getDayString(){
        String ret = this.day;
        if(Integer.parseInt(this.day) / 10 == 0){
            ret = "0"+ret;
        }
        return ret;
    }
}

