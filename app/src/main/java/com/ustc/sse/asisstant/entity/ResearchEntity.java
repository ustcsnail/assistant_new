package com.ustc.sse.asisstant.entity;

/**
 * Created by Thinkpad on 2017/6/3.
 */

public class ResearchEntity {

    private  String title;
    private  String content;
    private  int subject;
    private String author;
    private  String university;
    private  String date;
    private  int id;

    public static final int TYPE_LI=1;
    public static final int TYPE_GONG=2;
    public static final int TYPE_ECONOMICS=3;
    public static final int TYPE_MEDICAL=4;
    public static final int TYPE_SOCIETY=5;
    public static final int TYPE_OTHERS=6;

    public static final int TYPE_CONCRETE=10;

    public static final int RESULT_SUCCESS =1;
    public static final int RESULT_JSONERR =2;
    public static final int RESULT_TYPEERR =3;
    public static final int RESULT_SERVERERR =4;
    public static final int RESULT_EMPTYERR =5;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
