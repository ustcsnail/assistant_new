package com.ustc.sse.asisstant.entity;

/**
 * Created by Thinkpad on 2017/6/3.
 */

public class Director {

    private  String title;
    private  String content;
    private  String type;
    private  String id;

    public static final String GOV="gov";
    public static final String LiFE="life";

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
