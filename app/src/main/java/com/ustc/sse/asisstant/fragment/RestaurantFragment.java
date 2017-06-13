package com.ustc.sse.asisstant.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.activity.RestaurantEvaluateActivity;
import com.ustc.sse.asisstant.entity.ResearchEntity;
import com.ustc.sse.asisstant.entity.Restaurant;
import com.ustc.sse.asisstant.util.GetRestaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantFragment extends Fragment {


    private List<Map<String,Object>> data_list=new ArrayList<Map<String,Object>>();

    private List<Restaurant> list=new ArrayList<Restaurant>();

    private ListView listView=null;

    private SimpleAdapter adapter=null;

    private int type=0;

    public RestaurantFragment() {
        // Required empty public constructor
    }


    public void setType(int type) {
        this.type = type;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_restaurant, container, false);


        listView=(ListView)view.findViewById(R.id.dish_listview);


        //设置餐馆listview，联网加载信息
        adapter=new SimpleAdapter(getActivity(),data_list,R.layout.dish_listview_item,new String[]{"name","district","label","level","price","mark"}
                ,new int[]{R.id.dish_list_name,R.id.dish_district,R.id.dish_label,R.id.dish_level,R.id.dish_price,R.id.dish_mark});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Restaurant res = list.get(i);
                int id =res.getId();
                Log.e("id","id: "+id);
                Intent intent=new Intent(getActivity(), RestaurantEvaluateActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("id",id);
                bundle.putString("open","open");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        getData();

        return view;
    }


    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what)
            {
                case ResearchEntity.RESULT_SUCCESS:
                {
                    Bundle bundle = msg.getData();
                    String data = bundle.getString("result");
                    prepareDate(data);
                    adapter.notifyDataSetChanged();
                    break;
                }
                case ResearchEntity.RESULT_JSONERR:
                {
                    Toast.makeText(getActivity(),"数据解析错误！", Toast.LENGTH_SHORT).show();
                    break;
                }
                case ResearchEntity.RESULT_TYPEERR:
                {
                    Toast.makeText(getActivity(),"查询类型错误！",Toast.LENGTH_SHORT).show();
                    break;
                }
                case ResearchEntity.RESULT_SERVERERR:
                {
                    Toast.makeText(getActivity(),"服务器错误！",Toast.LENGTH_SHORT).show();
                    break;
                }
                case ResearchEntity.RESULT_EMPTYERR:
                {
                    Toast.makeText(getActivity(),"当前没有可用信息！",Toast.LENGTH_SHORT).show();
                    break;
                }
            }

        }
    };


    private void getData()
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetRestaurant getRestaurant=new GetRestaurant(handler);
                getRestaurant.getData(type);
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

            Restaurant d=null;
            HashMap<String,Object> map=null;

            for(int i=0;i<array.length();i++)
            {
                d=new Restaurant();
                map=new HashMap<String, Object>();

                obj=array.getJSONObject(i);
                d.setId(obj.getInt("id"));
                d.setResterant_name(obj.getString("name"));
                d.setAddress(obj.getString("address"));
                d.setDistrict(obj.getString("district"));
                d.setLevel(obj.getInt("level"));
                d.setType(obj.getInt("type"));
                d.setLabel(obj.getString("label"));
                d.setMark(obj.getInt("mark"));
                d.setPrice_avg(obj.getInt("price"));

                map.put("name",d.getResterant_name());
                map.put("district",d.getDistrict());
                map.put("label",d.getLabel());
                map.put("price",d.getPrice_avg());
                switch(d.getLevel())
                {
                    case 1:{map.put("level",R.drawable.levell);break;}
                    case 2:{map.put("level",R.drawable.levelm);break;}
                    case 3:{map.put("level",R.drawable.levelh);break;}
                }
                switch(d.getMark())
                {
                    case 1:{map.put("mark",R.drawable.star1);break;}
                    case 2:{map.put("mark",R.drawable.star2);break;}
                    case 3:{map.put("mark",R.drawable.star3);break;}
                    case 4:{map.put("mark",R.drawable.star4);break;}
                    default:{map.put("mark",R.drawable.star5);break;}
                }
              //list.get(position)  Restaurant   r.getId()   id  pinfen select avg(price),avg(mark)
               // set.getInt(1)
                list.add(d);
                data_list.add(map);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
