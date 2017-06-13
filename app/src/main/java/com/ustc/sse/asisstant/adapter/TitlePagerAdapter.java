package com.ustc.sse.asisstant.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Thinkpad on 2016/12/19.
 */

public class TitlePagerAdapter extends PagerAdapter {

    List<View> list=null;

    public TitlePagerAdapter(List<View> list)
    {
        this.list=list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView(list.get(position%list.size()));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //return super.instantiateItem(container, position);
        container.addView(list.get(position%list.size()));
        return list.get(position%list.size());
    }
}
