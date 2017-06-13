package com.ustc.sse.asisstant.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.ustc.sse.asisstant.entity.User;

/**
 * Created by gaodengji on 2017/6/7.
 */

public class AssSharedPreferences {
    private Activity activity;
    private SharedPreferences sharedPreferences;
    private boolean isLoginStates=false;
    public AssSharedPreferences(Activity activity){
        this.activity=activity;
        this.sharedPreferences=activity.getSharedPreferences("user", Context.MODE_PRIVATE);
        this.isLoginStates=sharedPreferences.getBoolean("status",false);
    }
    public boolean isLogin(){
        return  isLoginStates;
    }
    public User getUser(){
        User user = new User();
        user.setId(sharedPreferences.getString("id",""));
        user.setName(sharedPreferences.getString("name",""));
        return  user;
    }
}
