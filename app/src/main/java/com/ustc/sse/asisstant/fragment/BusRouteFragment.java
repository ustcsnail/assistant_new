package com.ustc.sse.asisstant.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.WalkStep;
import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.entity.SchemeBusStep;
import com.ustc.sse.asisstant.util.TextUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusRouteFragment extends Fragment {

    private BusPath busPaths=null;
    private List<BusStep> busSteps=null;  //获取的路径信息
    private List<SchemeBusStep> mBusStepList = new ArrayList<SchemeBusStep>();//转化为分步的路径信息
    private List<HashMap<String,Object>>list=null;  //转化为适配器资源
    private String pathDuration=null;
    private String pathDistance=null;

    public BusRouteFragment() {
        // Required empty public constructor
    }

    /*设置规划方案数组*/
    public void setBusPaths(BusPath busPaths)
    {
        this.busPaths=busPaths;
        setBusSteps(busPaths.getSteps());
    }
    /*提取规划方案*/
    public void setBusSteps(List<BusStep> busSteps)
    {
        this.busSteps=busSteps;
    }

    /*设置选取方案的时间和距离*/
    public void setDistanceAndDuration()
    {
        DecimalFormat decimalFormat=new DecimalFormat("#.0");
        float duration=busPaths.getDuration();
        if (duration <=60)
        {
            String str=decimalFormat.format(duration);
            pathDuration=str+"秒";
        }
        else if(duration>60&&duration<=3600)
        {
            String str=decimalFormat.format(duration/60);
            pathDuration=str+"分钟";
        }
        else
        {
            String str=decimalFormat.format(duration/3600);
            pathDuration=str+"小时";
        }

        float distance =busPaths.getDistance();
        if(distance<=1000)
        {
            String str=decimalFormat.format(distance);
            pathDistance=str+"米";
        }
        else
        {
            String str=decimalFormat.format(distance/1000);
            pathDistance=str+"公里";
        }
    }
    private void convertList()
    {
        //开始结点
        SchemeBusStep start = new SchemeBusStep(null);
        start.setStart(true);
        mBusStepList.add(start);

        //导航详细信息
        for (BusStep busStep : busSteps) {
            if (busStep.getWalk() != null && busStep.getWalk().getDistance() > 0) {
                SchemeBusStep walk = new SchemeBusStep(busStep);
                walk.setWalk(true);
                mBusStepList.add(walk);
            }
            if (busStep.getBusLines() != null) {
                SchemeBusStep bus = new SchemeBusStep(busStep);
                bus.setBus(true);
                mBusStepList.add(bus);
            }
            if (busStep.getRailway() != null) {
                SchemeBusStep railway = new SchemeBusStep(busStep);
                railway.setRailway(true);
                mBusStepList.add(railway);
            }

            if (busStep.getTaxi() != null) {
                SchemeBusStep taxi = new SchemeBusStep(busStep);
                taxi.setTaxi(true);
                mBusStepList.add(taxi);
            }
        }

        //结束结点
        SchemeBusStep end = new SchemeBusStep(null);
        end.setEnd(true);
        mBusStepList.add(end);
    }

    private void convertMapList()
    {
        list=new ArrayList<HashMap<String, Object>>();

        for(SchemeBusStep step:mBusStepList)
        {

            if(step.isStart())
            {
                HashMap<String,Object> hashMap=new HashMap<String, Object>();
                hashMap.put("street","起点");
                hashMap.put("instruction","从当前路段出发");
                hashMap.put("image",R.drawable.walk);
                list.add(hashMap);
            }
            else if (step.isEnd())
            {
                HashMap<String,Object> hashMap=new HashMap<String, Object>();
                hashMap.put("street","终点");
                hashMap.put("instruction","到达目的地");
                hashMap.put("image",R.drawable.walk);
                list.add(hashMap);
            }
            else if (step.isWalk()&&step.getWalk() != null && step.getWalk().getDistance() > 0)
            {
                List<WalkStep> walkSteps=step.getWalk().getSteps();
                for (WalkStep item:walkSteps) {
                    HashMap<String, Object> hashMap = new HashMap<String, Object>();
                    hashMap.put("street", item.getRoad());
                    hashMap.put("instruction", item.getInstruction());
                    hashMap.put("image", R.drawable.walk);
                    list.add(hashMap);
                }
            }
            else if(step.isBus() && step.getBusLines().size() > 0)
            {
                HashMap<String,Object> hashMap=new HashMap<String, Object>();
                String start=step.getBusLines().get(0).getDepartureBusStation().getBusStationName();
                String end=step.getBusLines().get(0).getArrivalBusStation().getBusStationName();
                hashMap.put("street", TextUtil.getBusPathTitle(step.getBusLines()));
                hashMap.put("instruction",start+" 站上车"+"---"+end+" 站下车");
                hashMap.put("image",R.drawable.bus);
                list.add(hashMap);
            }
            else if (step.isRailway() && step.getRailway() != null)
            {
                HashMap<String,Object> hashMap=new HashMap<String, Object>();
                String start=step.getRailway().getDeparturestop().getName();
                String end=step.getRailway().getArrivalstop().getName();
                hashMap.put("street", step.getRailway().getTrip());
                hashMap.put("instruction",start+" 站上车"+"---"+end+" 站下车");
                hashMap.put("image", R.drawable.bus);
                list.add(hashMap);
            }
            else if(step.isTaxi() && step.getTaxi() != null)
            {
                HashMap<String,Object> hashMap=new HashMap<String, Object>();
                String start=step.getTaxi().getmSname();
                String end=step.getTaxi().getmTname();
                hashMap.put("street", "出租车");
                hashMap.put("instruction",start+" 上车"+"---"+end+" 下车");
                hashMap.put("image", R.drawable.car);
                list.add(hashMap);
            }
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_route, container, false);
        ListView listView=(ListView)view.findViewById(R.id.route_list);
        TextView textView_duration=(TextView)view.findViewById(R.id.path_duration);
        TextView textView_distance=(TextView)view.findViewById(R.id.path_distance);
        /*设置全程时间与距离*/
        setDistanceAndDuration();
        textView_duration.setText(pathDuration);
        textView_distance.setText(pathDistance);

        convertList();
        convertMapList();
        SimpleAdapter simpleAdapter=new SimpleAdapter(getActivity().getApplicationContext(),list,R.layout.route_list,
                new String[]{"street","instruction","image"},
                new int[]{R.id.route_list_street,R.id.route_list_instruction,R.id.route_list_image});

        listView.setAdapter(simpleAdapter);
        return view;
    }

}
