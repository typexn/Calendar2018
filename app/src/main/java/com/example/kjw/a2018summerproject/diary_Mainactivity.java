package com.example.kjw.a2018summerproject;

import java.io.Serializable;

/**
 * Created by jwell on 2018-07-15.
 */

public class diary_Mainactivity {
}

class diary_Content implements Serializable {


    //    ImageView imgv_Picture; //사진
    String diaryDate; //날짜
    String diaryTitle; //일기 제목
    String diaryContent; //일기 내용

    //생성자
    public diary_Content(String _Date, String _Title, String _Content) {
        this.diaryDate = _Date;
        this.diaryTitle = _Title;
        this.diaryContent = _Content;
    }

    //날짜 호출
    public String getDiaryDate() {
        return diaryDate;
    }

    //일게 제목 호출
    public String getDiaryTitle() {
        return diaryTitle;
    }

    //일기 내용 호출
    public String getDiaryContent() {
        return diaryContent;
    }

}

