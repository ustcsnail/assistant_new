package com.ustc.sse.asisstant.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.overlay.PoiOverlay;
import com.ustc.sse.asisstant.util.TextUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.list;

public class Traffic extends AbstractPermissionActivity implements PoiSearch.OnPoiSearchListener,Inputtips.InputtipsListener,TextWatcher {


    private MapView mapView = null;
    private AMap aMap=null;
    private PoiSearch.Query query=null;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    GridView gridView=null;
    LatLng destination=null;//目的地经纬度
    String des_title=null; //目的地建筑标题

    private AutoCompleteTextView searchText;// 输入搜索关键字


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);


        /*初始化出行方式菜单*/
        gridView=(GridView)findViewById(R.id.traffic_gridview);
        setMenu();
        /*初始化检索框*/
        searchText = (AutoCompleteTextView) findViewById(R.id.poikeyword);
        searchText.addTextChangedListener(this);
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
           @Override
           public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
               if (actionId== EditorInfo.IME_ACTION_DONE)
               {
                   search();
               }

               return false;
           }
       });

        /*初始化地图容器*/
        mapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);


        if (aMap == null) {
            aMap = mapView.getMap();
        }

        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮
        aMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置


        /*设置marker点击监听*/
        aMap.setOnMarkerClickListener(markerClickListener);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));   // 处理地图的缩放级别
        /*显示定位蓝点*/
        if(!checkPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},this))//权限申请
        {
            showLocation();
        }

    }

    @Override
    public void requestPermissionResult(boolean requestResult)
    {
        if(requestResult)
        {
            showLocation();
        }
        else
        {
            Toast.makeText(this,"授权失败，地图定位功能异常",Toast.LENGTH_LONG).show();
        }
    }

    /*定位，显示用户当前位置*/
    public void showLocation()
    {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
    }



    /*搜索框提示处理*/
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        String newText = s.toString().trim();
        if (!TextUtil.IsEmptyOrNullString(newText)) {
            InputtipsQuery inputquery = new InputtipsQuery(newText, "0512");
            inputquery.setCityLimit(true);
            Inputtips inputTips = new Inputtips(this, inputquery);

            inputTips.setInputtipsListener(this);
            inputTips.requestInputtipsAsyn();
        }
    }

    /*搜索提示框回调函数，处理查询结果*/
    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {

        if (rCode == AMapException.CODE_AMAP_SUCCESS) {// 正确返回
            List<String> listString = new ArrayList<String>();
            for (int i = 0; i < tipList.size(); i++) {
                listString.add(tipList.get(i).getName());  //将tip的名字保存到容器里
            }
            ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                    getApplicationContext(),
                    R.layout.route_inputs, listString);
            searchText.setAdapter(aAdapter);
            aAdapter.notifyDataSetChanged();
        }
    }



    /*检索关键字*/
    public void search()
    {
        String keyword=searchText.getText().toString();
        if (!TextUtil.IsEmptyOrNullString(keyword)) {
            query = new PoiSearch.Query(keyword, "", "0512");
            query.setCityLimit(true);
            query.setPageSize(10);// 设置每页最多返回多少条poiitem
            query.setPageNum(0);//设置查询页码

            PoiSearch poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.searchPOIAsyn();
        }
        else
        {
            Toast.makeText(this,"请输入关键字",Toast.LENGTH_LONG).show();
        }

    }



    /*处理POI查询结果*/
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {

        PoiResult  poiResult=null;
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始

                    if (poiItems != null && poiItems.size() > 0) {
                        aMap.clear();// 清理之前的图标
                        PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                    }
                }
            }
        }
    }


    /*点击marker监听器
    * 点击marker设定目的地经纬度*/

    AMap.OnMarkerClickListener markerClickListener=new AMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            destination=marker.getPosition();
            des_title=marker.getTitle();
            return false;
        }
    };


    /*设置出行方式菜单*/
    public void setMenu() {
        //设置菜单
        List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
        String[] name = new String[]{"步行", "骑行", "公交", "驾车"};
        int[] image = new int[]{R.drawable.walk, R.drawable.bike, R.drawable.bus, R.drawable.car};

        for (int i = 0; i < 4; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", name[i]);
            map.put("icon", image[i]);
            item.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, item, R.layout.traffic_menu, new String[]{"name", "icon"}, new int[]{R.id.traffic_text, R.id.traffic_image});
        gridView.setAdapter(simpleAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (destination == null)
                    Toast.makeText(getApplicationContext(), "请选择一个目的地！", Toast.LENGTH_LONG).show();
                else
                {
                    String type="walk";
                    switch (i) {
                        case 0: //步行
                            type="walk";break;
                        case 1:
                            type="ride";break;
                        case 2:
                            type="bus";break;
                        case 3:
                            type="drive";break;
                    }
                    Intent intent=new Intent(Traffic.this,TrafficRoute.class);
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("latitude",destination);
                    bundle.putString("title",des_title);
                    bundle.putString("type",type);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            }
        });

    }



    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i)
    {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }


}
