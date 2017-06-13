package com.ustc.sse.asisstant.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Thinkpad on 2016/12/19.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment>list=null;
    private List<String> title=null;

    public MainPagerAdapter(FragmentManager fragmentManager, List<Fragment> list,List<String> title)
    {
        super(fragmentManager);
        this.list=list;
        this.title=title;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
