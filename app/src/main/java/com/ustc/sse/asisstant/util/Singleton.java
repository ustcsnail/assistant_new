package com.ustc.sse.asisstant.util;

/**
 * Created by gaodengji on 2017/6/7.
 */
public class Singleton {
    public boolean circle[];
    private volatile static Singleton singleton;
    private Singleton (){
        circle=new boolean[6];
        for(int i=0;i<6;i++){
            circle[i]=false;
        }
        circle[0]=true;
    }
    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}

