package com.ustc.sse.asisstant.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.RouteBusLineItem;
import com.amap.api.services.route.RouteRailwayItem;
import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.util.TextUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusPlanFragment extends Fragment {

    private List<BusPath> busPaths=null;

    private List<HashMap<String,Object>>list=null;

    public void setDrivePaths(List<BusPath> busPaths)
    {
        this.busPaths=busPaths;
    }

    public BusPlanFragment() {
        // Required empty public constructor
    }


    public void convertList()
    {
        list=new ArrayList<HashMap<String, Object>>();

        for (BusPath path:busPaths)
        {
            HashMap<String,Object> hashMap=new HashMap<String, Object>();
            hashMap.put("pathname", TextUtil.getBusPathTitle(path));
            hashMap.put("image",R.drawable.bus);
            hashMap.put("cost",path.getCost()+"元");
            hashMap.put("duration",getDuration(path));
            hashMap.put("walkdistance",getWalkDistance(path));
            list.add(hashMap);
        }
    }

    public String getDuration(BusPath path)
    {
        DecimalFormat decimalFormat=new DecimalFormat("#.0");
        float duration=path.getDuration();
        String str=null;
        if (duration <=60)
        {
            str=decimalFormat.format(duration);
            str=str+"秒";
        }
        else if(duration>60&&duration<=3600)
        {
             str=decimalFormat.format(duration/60);
            str=str+"分钟";
        }
        else
        {
             str=decimalFormat.format(duration/3600);
            str=str+"小时";
        }
        return str;

    }

    public String getWalkDistance(BusPath path)
    {
        DecimalFormat decimalFormat=new DecimalFormat("#.0");
        String str=null;
        float distance =path.getWalkDistance();
        if(distance<=1000)
        {
            str=decimalFormat.format(distance);
            str="步行 "+str+"米";
        }
        else
        {
            str=decimalFormat.format(distance/1000);
            str="步行 "+str+"公里";
        }
        return  str;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_busplan, container, false);

        ListView listView=(ListView)view.findViewById(R.id.list_driveplan);
        convertList();

        SimpleAdapter simpleAdapter=new SimpleAdapter(getActivity().getApplicationContext(),list,R.layout.busplan_list,
                new String[]{"pathname","image","cost","duration","walkdistance"},
                new int[]{R.id.bus_pathname,R.id.busplan_image,R.id.bus_cost,R.id.bus_duration,R.id.bus_walkdistance});

        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FragmentManager fm=getActivity().getFragmentManager();
                FragmentTransaction  ft=fm.beginTransaction();
                BusRouteFragment busRouteFragment=new BusRouteFragment();
                busRouteFragment.setBusPaths(busPaths.get(position));

                ft.hide(BusPlanFragment.this);
                ft.addToBackStack(null);
                ft.add(R.id.route_fragment,busRouteFragment);
                ft.commit();

            }
        });
        return view;
    }

}
