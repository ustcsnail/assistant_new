package com.ustc.sse.asisstant.util;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.entity.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thinkpad on 2017/2/21.
 * 餐厅列表类
 */

public class RestaurantControl
{
    private List<Restaurant> list=null;

    //从服务器获取餐馆信息

    //为Listview提供数据源
    /*public List showRestaurants()
    {
        getRemoteRes();
        List<Map<String, Object>> dish =null;
        if(list!=null)
        {
           *//* dish = new ArrayList<Map<String, Object>>();

            for (Restaurant restaurant : list) {
                Map<String, Object> dishmap = new HashMap<String, Object>();
                dishmap.put("name", restaurant.getResterant_name());
                dishmap.put("pic", restaurant.getImage());
                dishmap.put("price", restaurant.getPrice_avg());
                dishmap.put("district", restaurant.getDistrict());
                switch(restaurant.getMark())
                {
                    case "1":{ dishmap.put("mark",R.drawable.star1);break;}
                    case "2":{ dishmap.put("mark",R.drawable.star2);break;}
                    case "3":{ dishmap.put("mark",R.drawable.star3);break;}
                    case "4":{ dishmap.put("mark",R.drawable.star4);break;}
                    case "5":{ dishmap.put("mark",R.drawable.star5);break;}
                }
                switch (restaurant.getType())
                {
                    case 'c':{dishmap.put("type",R.drawable.logoc);break;}
                    case 'w':{dishmap.put("type",R.drawable.logow);break;}
                    case 'd':{dishmap.put("type",R.drawable.logod);break;}
                    case 'f':{dishmap.put("type",R.drawable.logof);break;}
                }

                dish.add(dishmap);*//*
            }
        }

        return dish;
    }*/
}
