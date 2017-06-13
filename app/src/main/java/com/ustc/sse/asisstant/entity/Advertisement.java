package com.ustc.sse.asisstant.entity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ustc.sse.asisstant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thinkpad on 2017/2/20.
 */

public class Advertisement {

    Context context=null;
    private List<View> list=null;

    public Advertisement(Context context)
    {
        this.context=context;
        list=new ArrayList<View>();
    }

    public List getAdvertisement()
    {
        if(list!=null&&context!=null)
        {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.title_commercial);
            list.add(imageView);
            imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.title_commercial1);
            list.add(imageView);
            imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.title_commercial2);
            list.add(imageView);
            imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.title_commercial3);
            list.add(imageView);

        }
        return list;
    }
}
