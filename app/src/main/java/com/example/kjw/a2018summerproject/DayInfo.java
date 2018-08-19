package com.example.kjw.a2018summerproject;

public class DayInfo {


    private String day;
    private boolean inMonth;
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

    public void setExistSch(boolean isExist){ this.isExistSch = isExist; } //minyoung
    public boolean isExistSch() { return isExistSch; } //minyoung
}

