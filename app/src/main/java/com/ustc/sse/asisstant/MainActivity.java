package com.ustc.sse.asisstant;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import com.ustc.sse.asisstant.adapter.MainPagerAdapter;
import com.ustc.sse.asisstant.entity.User;
import com.ustc.sse.asisstant.fragment.*;
import com.ustc.sse.asisstant.util.CheckUserBackground;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPager viewPager=null;
    private TabLayout tabLayout=null;
    private  List<Fragment> list=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*获取user配置文件*/
        SharedPreferences  preferences=getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();

        /*初始化时，设置登录状态为false，验证登录成功后，设置为true*/
        editor.putBoolean("status",false);
        editor.apply();

        /*从sharedpreference中获取登录信息，进行登录验证*/
        User user=new User();
        user.setTele(preferences.getString("tele",""));
        user.setPasswd(preferences.getString("passwd",""));
        if(user.logCheck())
        {
            CheckUserBackground check=new CheckUserBackground(this);
            check.execute(user);
        }


        viewPager=(ViewPager)findViewById(R.id.main_pager);
        tabLayout=(TabLayout)findViewById(R.id.main_tablayout) ;
        //LayoutInflater layoutInflater=LayoutInflater.from(this);


        list=new ArrayList<Fragment>();

        //加载视图
        list.add(new PagerFragment1());
        list.add(new PagerFragment2());

        list.add(new PagerFragment4());
        //加载标题
        List<String> title=new ArrayList<>();
        title.add("生活");
        title.add("论坛");

        title.add("个人");

        //绑定适配器
        MainPagerAdapter mainAdapter=new MainPagerAdapter(getSupportFragmentManager(),list,title);
        viewPager.setAdapter(mainAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //设置tab图片导航
        tabLayout.getTabAt(0).setIcon(R.drawable.location);
        tabLayout.getTabAt(1).setIcon(R.drawable.bbs);
       // tabLayout.getTabAt(2).setIcon(R.drawable.tool);
        tabLayout.getTabAt(2).setIcon(R.drawable.user);




    }
}
