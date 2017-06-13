package com.ustc.sse.asisstant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.entity.Director;
import com.ustc.sse.asisstant.util.GetDirector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LifeDirector extends AppCompatActivity {

    private ListView listView=null;

    private List<HashMap<String,Object>> data_list=new ArrayList<HashMap<String, Object>>();

    private SimpleAdapter adapter=null;

    private String type=null;

    private List<Director> list=new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_director);


        /*初始化数据*/

        listView=(ListView)findViewById(R.id.director_list);

        adapter=new SimpleAdapter(this,data_list,R.layout.director_item,new String[]{"title"},new int[]{R.id.director_item_title});

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(LifeDirector.this,ConcretDirector.class);
                intent.putExtra("title",list.get(position).getTitle());
                intent.putExtra("content",list.get(position).getContent());
                startActivity(intent);
            }
        });
        type=Director.GOV;
        getData();



        /*初始化类型菜单*/
        GridView gridView=(GridView)findViewById(R.id.director_grid);
        List<HashMap<String,Object>> grid_list=new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("title","办事指南");
        map.put("image",R.drawable.director_gov);
        grid_list.add(map);

        map=new HashMap<String, Object>();
        map.put("title","生活指南");
        map.put("image",R.drawable.director_life);
        grid_list.add(map);


        SimpleAdapter gridadapter=new SimpleAdapter(this,grid_list,R.layout.director_menu,new String[]{"title","image"},new int[]{R.id.menu_text1,R.id.menu_image1});
        gridView.setAdapter(gridadapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0)
                {
                    type=Director.GOV;
                    getData();
                }

                if(position==1)
                {
                    type=Director.LiFE;
                    getData();
                }
            }
        });





    }



    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what)
            {
                case 1: {
                    Bundle bundle = msg.getData();
                    String data = bundle.getString("result");
                    prepareDate(data);
                    adapter.notifyDataSetChanged();
                    break;
                }
                case 2:
                {
                    Toast.makeText(getApplicationContext(),"数据解析错误！",Toast.LENGTH_SHORT).show();
                    break;
                }
                case 3:
                {
                    Toast.makeText(getApplicationContext(),"查询类型错误！",Toast.LENGTH_SHORT).show();
                    break;
                }
                case 4:
                {
                    Toast.makeText(getApplicationContext(),"服务器错误！",Toast.LENGTH_SHORT).show();
                    break;
                }
                case 5:
                {
                    Toast.makeText(getApplicationContext(),"当前没有可用信息！",Toast.LENGTH_SHORT).show();
                    break;
                }
            }



        }
    };




    /*开启多线程，从网络获取数据*/
    private void getData()
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetDirector getDirector=new GetDirector(handler);
                getDirector.getData(type);
            }
        }).start();

    }

    /*将获取到的网络数据转化为list*/
    private void prepareDate(String data)
    {
        list.clear();
        data_list.clear();

        try {
            JSONObject obj=new JSONObject(data);
            JSONArray array=obj.getJSONArray("array");

            Director d=null;
            HashMap<String,Object> map=null;

            for(int i=0;i<array.length();i++)
            {
                d=new Director();
                map=new HashMap<String, Object>();

                obj=array.getJSONObject(i);
                d.setTitle(obj.getString("title"));
                d.setContent(obj.getString("content"));
                map.put("title",d.getTitle());

                list.add(d);
                data_list.add(map);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
