package com.ustc.sse.asisstant.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thinkpad on 2017/4/26.
 * 运行时权限申请抽象Activity类
 */

public abstract class AbstractPermissionActivity extends AppCompatActivity
{
    protected final int  REQUESTCODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    /*根据权限申请结果调用业务函数，延迟到子类实现*/
    public abstract void requestPermissionResult(boolean requestResult);


    /*获取未被授予的权限*/
    private List<String> findDeniedPermissions(String[] permissions,AppCompatActivity activity) {
        List<String> permissonList = new ArrayList<String>();

        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(activity, perm) != PackageManager.PERMISSION_GRANTED) //检查权限授予情况
            {
                permissonList.add(perm);
            }
        }
        return permissonList;
    }

    /*检查权限*/
   public boolean checkPermissions(String[] permissions,AppCompatActivity activity) {
        List<String> permissonList = findDeniedPermissions(permissions,activity);

        if (null != permissonList && permissonList.size() > 0) {
            String[] needRequestPer = permissonList.toArray(new String[permissonList.size()]);
            ActivityCompat.requestPermissions(activity, needRequestPer, REQUESTCODE);//申请权限
            return true;
        }
       else
        {
            return false;
        }
    }



    /*权限申请结果回调函数*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode ==REQUESTCODE) {
            if (verify(grantResults)) {
                requestPermissionResult(true);
            }
            else {
                requestPermissionResult(false);
            }
        }
    }

    /*确认所有权限都被授予*/
    private boolean verify(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}
