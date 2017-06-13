package com.ustc.sse.asisstant.activity;

import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.adapter.MainPagerAdapter;
import com.ustc.sse.asisstant.entity.ResearchEntity;
import com.ustc.sse.asisstant.fragment.ResearchFragment;

import java.util.ArrayList;
import java.util.List;

public class Research extends AppCompatActivity {

    private TabLayout tabLayout=null;
    private ViewPager pager=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);

        pager=(ViewPager)findViewById(R.id.research_pager);
        tabLayout=(TabLayout)findViewById(R.id.research_tab);

        List<Fragment> list=new ArrayList<Fragment>() ;

        ResearchFragment frag1=new ResearchFragment();
        frag1.setType(ResearchEntity.TYPE_LI);
        frag1.setCircle(0);
        ResearchFragment frag2=new ResearchFragment();
        frag2.setType(ResearchEntity.TYPE_GONG);
        frag1.setCircle(1);
        ResearchFragment frag3=new ResearchFragment();
        frag3.setType(ResearchEntity.TYPE_ECONOMICS);
        frag3.setCircle(2);
        ResearchFragment frag4=new ResearchFragment();
        frag4.setType(ResearchEntity.TYPE_MEDICAL);
        frag4.setCircle(3);
        ResearchFragment frag5=new ResearchFragment();
        frag5.setType(ResearchEntity.TYPE_SOCIETY);
        frag5.setCircle(4);
        ResearchFragment frag6=new ResearchFragment();
        frag6.setType(ResearchEntity.TYPE_OTHERS);
        frag6.setCircle(5);
        list.add(frag1);
        list.add(frag2);
        list.add(frag3);
        list.add(frag4);
        list.add(frag5);
        list.add(frag6);

        List<String> title=new ArrayList<>();
        title.add("理学");
        title.add("工学");
        title.add("经济学");
        title.add("医学");
        title.add("社会学");
        title.add("其他");


        MainPagerAdapter adapter=new MainPagerAdapter(getSupportFragmentManager(),list,title);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }
}
