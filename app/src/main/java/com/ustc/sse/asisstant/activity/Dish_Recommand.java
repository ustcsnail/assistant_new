package com.ustc.sse.asisstant.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.adapter.TitlePagerAdapter;
import com.ustc.sse.asisstant.entity.Advertisement;
import com.ustc.sse.asisstant.entity.Restaurant;
import com.ustc.sse.asisstant.fragment.RestaurantFragment;
import com.ustc.sse.asisstant.util.GetResearch;
import com.ustc.sse.asisstant.util.RestaurantControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dish_Recommand extends AppCompatActivity {

    private  FragmentManager fm=null;
    private RestaurantFragment frag_chinese=null;
    private RestaurantFragment frag_west=null;
    private RestaurantFragment frag_dessert=null;
    private RestaurantFragment frag_fastfood=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish__recommand);


        fm=getFragmentManager();

        //设置分类菜单
        List<Map<String,Object>> item=new ArrayList<Map<String,Object>>();
        String []name=new String[] {"中餐","西餐","快餐","甜品"};
        int []image=new int[]{R.drawable.menu_dish_chinese,R.drawable.menu_dish_west,R.drawable.menu_dish_fast,R.drawable.menu_dish_dessert};

        for (int i=0;i<4;i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name",name[i]);
            map.put("icon",image[i]);
            item.add(map);
        }

        GridView gridView=(GridView)findViewById(R.id.dish_gridview);

        SimpleAdapter simpleAdapter=new SimpleAdapter(this,item,R.layout.main_menu,new String[]{"name","icon"},new int[]{R.id.menu_text,R.id.menu_image});

        gridView.setAdapter(simpleAdapter);

        FragmentTransaction ft=fm.beginTransaction();
        frag_chinese = new RestaurantFragment();
        frag_chinese.setType(Restaurant.TYPE_CHINESE);
        ft.replace(R.id.restaurant_container,frag_chinese);
        ft.commit();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch(position)
                {
                    case 0:
                    {
                        FragmentTransaction ft=fm.beginTransaction();
                       if (frag_chinese==null) {
                           frag_chinese = new RestaurantFragment();
                           frag_chinese.setType(Restaurant.TYPE_CHINESE);
                       }
                        ft.replace(R.id.restaurant_container,frag_chinese);
                        ft.commit();
                        break;
                    }
                    case 1:
                    {
                        FragmentTransaction ft=fm.beginTransaction();
                        if (frag_west==null) {
                            frag_west = new RestaurantFragment();
                            frag_west.setType(Restaurant.TYPE_WESTERN);
                        }
                        ft.replace(R.id.restaurant_container,frag_west);
                        ft.commit();
                        break;
                    }
                    case 2:
                    {
                        FragmentTransaction ft=fm.beginTransaction();
                        if (frag_fastfood==null) {
                            frag_fastfood = new RestaurantFragment();
                            frag_fastfood.setType(Restaurant.TYPE_FASTFOOD);
                        }
                        ft.replace(R.id.restaurant_container,frag_fastfood);
                        ft.commit();
                        break;
                    }
                    case 3:
                    {
                        FragmentTransaction ft=fm.beginTransaction();
                        if (frag_dessert==null) {
                            frag_dessert = new RestaurantFragment();
                            frag_dessert.setType(Restaurant.TYPE_DESSERT);
                        }
                        ft.replace(R.id.restaurant_container,frag_dessert);
                        ft.commit();
                        break;
                    }

                }
            }
        });


    }


}
