package com.ustc.sse.asisstant.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.VideoView;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Settings extends AppCompatActivity {

    ListView listView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        /*初始化listview*/
        listView=(ListView)findViewById(R.id.info_list);
        List<HashMap<String,Object>> list=prepareData();

        SimpleAdapter adapter=new SimpleAdapter(this,list,R.layout.settings_info,new String[]{"item","content"},new int[]{R.id.info_item,R.id.info_content});

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 1://修改昵称
                    {
                        Intent intent=new Intent(Settings.this,ModifyNickName.class);
                        startActivity(intent);
                        break;
                    }
                    default:
                    {
                        Toast.makeText(getApplicationContext(),"该项目不可修改！",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


    /*为listview准备数据*/
    List<HashMap<String,Object>> prepareData()
    {
        SharedPreferences  preferences=getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor  editor=preferences.edit();

        List<HashMap<String,Object>> list=new ArrayList<HashMap<String, Object>>();
        //id
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("item","账户ID");
        map.put("content",preferences.getString("id","").trim());
        list.add(map);

        //昵称
        map=new HashMap<String,Object>();
        map.put("item","用户名");
        map.put("content",preferences.getString("name","").trim());
        list.add(map);

        //电话
        map=new HashMap<String,Object>();
        map.put("item","电话");
        map.put("content",preferences.getString("tele","").trim());
        list.add(map);

        return list;
    }

    /*修改密码按钮响应函数*/

    public void modifyPasswd(View view)
    {
        Intent intent=new Intent(Settings.this,ModifyPasswd.class);
        startActivity(intent);
    }
    /*注销按钮响应函数*/
    public void logout(View view)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this).setTitle("退出登录").setMessage("确认退出登录？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences  preferences=getSharedPreferences("user",MODE_PRIVATE);
                SharedPreferences.Editor  editor=preferences.edit();
                editor.putBoolean("status",false);
                editor.apply();
                Toast.makeText(getApplicationContext(),"注销成功！",Toast.LENGTH_SHORT).show();
                finish();

            }
        });


        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();

    }
}
