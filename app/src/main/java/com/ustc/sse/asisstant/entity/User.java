package com.ustc.sse.asisstant.entity;

/**
 * Created by Thinkpad on 2017/5/23.
 */

public class User {

    /*注册信息*/
    private String id;
    private String name;
    private String passwd;
    private String repasswd;
    private String tele;
    private String modifyflag;
    private boolean status;



    public boolean isEmpty()
    {
        if(null==name||null==passwd||null==repasswd||null==tele||"".equals(name)||"".equals(passwd)||
                "".equals(repasswd)||"".equals(tele))
            return true;
        else
            return false;
    }


    public boolean logCheck()
    {
        if(null==passwd||null==tele||"".equals(passwd)||"".equals(tele))
            return false; //不可以登录
        else
            return true;//可以登录
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepasswd() {
        return repasswd;
    }

    public void setRepasswd(String repasswd) {
        this.repasswd = repasswd;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getModifyflag() {
        return modifyflag;
    }

    public void setModifyflag(String modifyflag) {
        this.modifyflag = modifyflag;
    }
}
