package com.ustc.sse.asisstant.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.amap.api.services.route.RidePath;
import com.amap.api.services.route.RideStep;
import com.ustc.sse.asisstant.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RideRouteFragment extends Fragment {

    private List<RidePath> ridePaths=null;
    private List<RideStep> rideSteps=null;
    private List< HashMap<String,Object> >list=null;
    private String pathDuration=null;
    private String pathDistance=null;

    public RideRouteFragment() {
        // Required empty public constructor
    }


    /*设置规划方案数组*/
    public void setRidePaths(List<RidePath> ridePaths)
    {
        this.ridePaths=ridePaths;
        setRideSteps(ridePaths.get(0).getSteps());
    }
    /*提取规划方案*/
    public void setRideSteps(List<RideStep> rideSteps)
    {
        this.rideSteps=rideSteps;
    }
    /*设置选取方案的时间和距离*/
    public void setDistanceAndDuration(int index)
    {
        DecimalFormat decimalFormat=new DecimalFormat("#.0");
        float duration=ridePaths.get(index).getDuration();
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

        float distance =ridePaths.get(index).getDistance();
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

    public void convertList()
    {
        list=new ArrayList<HashMap<String, Object>>();

        for (RideStep step:rideSteps)
        {
            HashMap<String,Object> hashMap=new HashMap<String, Object>();
            hashMap.put("street",step.getRoad());
            hashMap.put("instruction",step.getInstruction());
            hashMap.put("image",R.drawable.bike);
            list.add(hashMap);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_route, container, false);
        ListView listView=(ListView)view.findViewById(R.id.route_list);
        TextView textView_duration=(TextView)view.findViewById(R.id.path_duration);
        TextView textView_distance=(TextView)view.findViewById(R.id.path_distance);
        /*设置全程时间与距离*/
        setDistanceAndDuration(0);
        textView_duration.setText(pathDuration);
        textView_distance.setText(pathDistance);

        convertList();
        SimpleAdapter simpleAdapter=new SimpleAdapter(getActivity().getApplicationContext(),list,R.layout.route_list,
                new String[]{"street","instruction","image"},
                new int[]{R.id.route_list_street,R.id.route_list_instruction,R.id.route_list_image});

        listView.setAdapter(simpleAdapter);

        return view;
    }

}
