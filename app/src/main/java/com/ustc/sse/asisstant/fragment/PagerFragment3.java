package com.ustc.sse.asisstant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.activity.WeatherActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thinkpad on 2016/12/19.
 */

public class PagerFragment3 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.pager_page3,container,false);


        List<Map<String,Object>> item=new ArrayList<Map<String,Object>>();
        String []name=new String[] {"备忘录","课程表","扫一扫","天气通","查公交","地铁图","星座运势"};
        int []image=new int[]{R.drawable.tool_bei,R.drawable.tool_ke,R.drawable.tool_sao,R.drawable.tool_tian,R.drawable.tool_gong,R.drawable.tool_ditie,R.drawable.tool_xingzuo};

        for (int i=0;i<7;i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name",name[i]);
            map.put("icon",image[i]);
            item.add(map);
        }
        ListView listView=(ListView)view.findViewById(R.id.tool_list1);

        SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),item,R.layout.list_menu_layout,new String[]{"name","icon"},new int[]{R.id.tool_text,R.id.tool_image});

        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 3:
                    {
                        Intent intent=new Intent(getActivity(), WeatherActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
        return view;
    }
}
