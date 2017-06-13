package com.ustc.sse.asisstant.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.adapter.MainPagerAdapter;
import com.ustc.sse.asisstant.entity.CollegeEntity;
import com.ustc.sse.asisstant.entity.ResearchEntity;
import com.ustc.sse.asisstant.fragment.CollegeFragment;
import com.ustc.sse.asisstant.fragment.ResearchFragment;

import java.util.ArrayList;
import java.util.List;

public class CollegeActivity extends AppCompatActivity {

    private TabLayout tabLayout=null;
    private ViewPager pager=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college);

        pager=(ViewPager)findViewById(R.id.college_pager);
        tabLayout=(TabLayout)findViewById(R.id.college_tab);

        List<Fragment> list=new ArrayList<Fragment>() ;

        CollegeFragment frag1=new CollegeFragment();
        frag1.setType(1);

        CollegeFragment frag2=new CollegeFragment();
        frag2.setType(CollegeEntity.TYPE_XJTU);

        CollegeFragment frag3=new CollegeFragment();
        frag3.setType(CollegeEntity.TYPE_DNDX);

        CollegeFragment frag4=new CollegeFragment();
        frag4.setType(CollegeEntity.TYPE_SZDX);

        CollegeFragment frag5=new CollegeFragment();
        frag5.setType(CollegeEntity.TYPE_XJLW);

        CollegeFragment frag6=new CollegeFragment();
        frag6.setType(ResearchEntity.TYPE_OTHERS);

        list.add(frag1);
        list.add(frag2);
        list.add(frag3);
        list.add(frag4);
        list.add(frag5);
        list.add(frag6);

        List<String> title=new ArrayList<>();
        title.add("中国科大");
        title.add("西安交大");
        title.add("中国人大");
        title.add("南京大学");
        title.add("东南大学");
        title.add("其他大学");


        MainPagerAdapter adapter=new MainPagerAdapter(getSupportFragmentManager(),list,title);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }
}
