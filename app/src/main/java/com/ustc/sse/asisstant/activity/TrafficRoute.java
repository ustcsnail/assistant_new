package com.ustc.sse.asisstant.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.fragment.BusPlanFragment;
import com.ustc.sse.asisstant.fragment.DriveRouteFragment;
import com.ustc.sse.asisstant.fragment.RideRouteFragment;
import com.ustc.sse.asisstant.fragment.WalkRouteFragment;

import static com.amap.api.services.route.RouteSearch.BUS_DEFAULT;
import static com.amap.api.services.route.RouteSearch.DRIVING_SINGLE_DEFAULT;

public class TrafficRoute extends AppCompatActivity implements AMapLocationListener,RouteSearch.OnRouteSearchListener{

    private String des_title=null;  //目的地信息
    private LatLng destination=null;
    private String start_title=null;//起始位置信息
    private LatLng startLocation=null;
    private Spinner spinner=null;
    private TextView textView=null;

    private RouteSearch  routeSearch=null;//线路规划查询
    private String routeType=null;
    private FragmentManager fragmentManager=getFragmentManager();
    private FragmentTransaction fragmentTransaction=null;


    private AMapLocationClient mLocationClient = null;    //声明定位客户端类对象
    private AMapLocationClientOption mLocationOption = null; //声明定位选项

    private ProgressDialog dialog=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_route);

        dialog=new ProgressDialog(this);
        dialog.setMessage("路线规划中...");
        dialog.show();

        /*获取目的地信息*/
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        des_title=bundle.getString("title");
        destination=bundle.getParcelable("latitude");
        routeType=bundle.getString("type");

        if (destination!=null) {
            /*设置起点Spinner*/
            spinner = (Spinner) findViewById(R.id.traffic_spinner);
            setSpinner();
            /*显示终点*/
            textView = (TextView) findViewById(R.id.traffic_destination);
            textView.setText(des_title.trim());
            /*初始化线路规划*/
            routeSearch=new RouteSearch(this);
            routeSearch.setRouteSearchListener(this);
            /*初始化定位客户端*/
            mLocationClient = new AMapLocationClient(getApplicationContext());
            mLocationClient.setLocationListener(this);
            /*设置定位选项*/
            mLocationOption=new AMapLocationClientOption();
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setOnceLocation(true);
            mLocationOption.setOnceLocationLatest(true);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            //启动定位
            mLocationClient.startLocation();

        }
        else
        {
            Toast.makeText(this,"目标位置有误！",Toast.LENGTH_LONG).show();
            finish();
        }

    }
    /*设置起点组件选项*/
    private void setSpinner()
    {
        if (spinner==null)
            return;
        String[] mItems ={"我的位置","重新定位"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner .setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              switch (position)
              {
                  case 1:{
                      dialog.show();
                      mLocationClient.startLocation();
                      Toast.makeText(getApplicationContext(),"当前定位起始地："+start_title,Toast.LENGTH_LONG).show();
                      break;
                  }
              }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    /*重写定位回调函数*/
    @Override
    public void onLocationChanged(AMapLocation amapLocation)
    {

        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //可在其中解析amapLocation获取相应内容。
                startLocation=new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude());
                start_title=amapLocation.getPoiName();
                routeSearch();
            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Toast.makeText(getApplicationContext(),"当前位置定位失败",Toast.LENGTH_LONG).show();

            }
        }
    }

    public void routeSearch()
    {
        /*设置路线起点与终点*/
        LatLonPoint start=new LatLonPoint(startLocation.latitude,startLocation.longitude);
        LatLonPoint end=new LatLonPoint(destination.latitude,destination.longitude);
        RouteSearch.FromAndTo fromAndTo=new RouteSearch.FromAndTo(start,end);

        switch (routeType)
        {
            case "walk":
            {
                RouteSearch.WalkRouteQuery query=new RouteSearch.WalkRouteQuery(fromAndTo);
                routeSearch.calculateWalkRouteAsyn(query);//开始算路
                break;
            }
            case "ride":
            {
                RouteSearch.RideRouteQuery query=new RouteSearch.RideRouteQuery(fromAndTo);
                routeSearch.calculateRideRouteAsyn(query);//开始算路
                break;
            }
            case "drive":
            {
                RouteSearch.DriveRouteQuery query=new RouteSearch.DriveRouteQuery(fromAndTo,DRIVING_SINGLE_DEFAULT,null,null,null);
                routeSearch.calculateDriveRouteAsyn(query);//开始算路
                break;
            }
            case "bus":
            {
                RouteSearch.BusRouteQuery query=new RouteSearch.BusRouteQuery(fromAndTo,BUS_DEFAULT,"0512",0);
                routeSearch.calculateBusRouteAsyn(query);//开始算路
                break;
            }

        }

    }
    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {
        dialog.dismiss();
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                Log.v("步行方案数",result.getPaths().size()+"");
                fragmentTransaction=fragmentManager.beginTransaction();
                WalkRouteFragment fg=new WalkRouteFragment();
                fg.setWalkPaths(result.getPaths());
                //fg.setWalkSteps(result.getPaths().get(0).getSteps());
                fragmentTransaction.replace(R.id.route_fragment,fg);
                fragmentTransaction.commit();

            }
            else
            {
                Toast.makeText(this,"没有可用到达路线！",Toast.LENGTH_LONG).show();
            }

        }
        else
        {
            Toast.makeText(this,"路线规划失败！",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBusRouteSearched(BusRouteResult result, int errorCode) {
        dialog.dismiss();
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                Log.v("公交方案数",result.getPaths().size()+"");
                fragmentTransaction=fragmentManager.beginTransaction();
                BusPlanFragment planFragment=new BusPlanFragment();
                planFragment.setDrivePaths(result.getPaths());
                fragmentTransaction.replace(R.id.route_fragment,planFragment);
                fragmentTransaction.commit();
            }
            else
            {
                Toast.makeText(this,"没有可用到达路线！",Toast.LENGTH_LONG).show();
            }

        }
        else
        {
            Toast.makeText(this,"路线规划失败！",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
        dialog.dismiss();
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                Log.v("驾驶方案数",result.getPaths().size()+"");
                fragmentTransaction=fragmentManager.beginTransaction();
                DriveRouteFragment plan=new DriveRouteFragment();
                plan.setDrivePaths(result.getPaths());
                fragmentTransaction.replace(R.id.route_fragment,plan);
                fragmentTransaction.commit();
            }
            else
            {
                Toast.makeText(this,"没有可用到达路线！",Toast.LENGTH_LONG).show();
            }

        }
        else
        {
            Toast.makeText(this,"路线规划失败！",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRideRouteSearched(RideRouteResult result, int errorCode) {
        dialog.dismiss();
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                Log.v("骑行方案数",result.getPaths().size()+"");
                fragmentTransaction=fragmentManager.beginTransaction();
                RideRouteFragment fg=new RideRouteFragment();
                fg.setRidePaths(result.getPaths());
                fragmentTransaction.replace(R.id.route_fragment,fg);
                fragmentTransaction.commit();

            }
            else
            {
                Toast.makeText(this,"没有可用到达路线！",Toast.LENGTH_LONG).show();
            }

        }
        else
        {
            Toast.makeText(this,"路线规划失败！",Toast.LENGTH_LONG).show();
        }
    }
}
