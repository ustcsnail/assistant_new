package com.ustc.sse.asisstant.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.activity.CollegeActivity;
import com.ustc.sse.asisstant.activity.ConcreteCollege;
import com.ustc.sse.asisstant.activity.ConcreteResearch;
import com.ustc.sse.asisstant.entity.CollegeEntity;
import com.ustc.sse.asisstant.entity.ResearchEntity;
import com.ustc.sse.asisstant.util.GetCollege;
import com.ustc.sse.asisstant.util.GetResearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollegeFragment extends Fragment {


    private int type=0;

    private ListView listView=null;

    private List<HashMap<String,Object>> data_list=new ArrayList<HashMap<String, Object>>();

    private List<ResearchEntity> list=new ArrayList<ResearchEntity>();

    private   SimpleAdapter adapter=null;

    public CollegeFragment() {
        // Required empty public constructor
    }


    public void setType(int type)
    {
        this.type=type;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("thread",Thread.currentThread().getId()+" ");
        View view=inflater.inflate(R.layout.fragment_research, container, false);

        listView=(ListView)view.findViewById(R.id.researsh_list);

        adapter=new SimpleAdapter(getActivity(),data_list,R.layout.research_item,new String[]{"title","author","date","image"},new int[]{R.id.research_item_title,R.id.research_item_author,R.id.research_item_date,R.id.uiversity_logo});

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(list!=null)
                {
                    ResearchEntity e=list.get(position);
                    Intent intent=new Intent(getActivity(), ConcreteCollege.class);
                    intent.putExtra("title",e.getTitle());
                    intent.putExtra("id",e.getId()+"");
                    intent.putExtra("author",e.getAuthor());
                    intent.putExtra("date",e.getDate());
                    startActivity(intent);

                }
            }
        });

       Log.e("type","type:"+type);
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
            }

        }
    };


    /*开启多线程，从网络获取数据*/
    private void getData()
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetCollege getCollege=new GetCollege(handler);
                getCollege.getData(type);
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

            ResearchEntity d=null;
            HashMap<String,Object> map=null;

            for(int i=0;i<array.length();i++)
            {
                d=new ResearchEntity();
                map=new HashMap<String, Object>();

                obj=array.getJSONObject(i);
                d.setId(obj.getInt("id"));
                d.setSubject(obj.getInt("subject"));
                d.setAuthor(obj.getString("author"));
                d.setUniversity(obj.getString("university"));
                d.setTitle(obj.getString("title"));
                d.setDate(obj.getString("date"));

                map.put("title",d.getTitle());
                map.put("author",d.getAuthor());
                map.put("university",d.getUniversity());
                map.put("date",d.getDate());
                switch(type)
                {
                    case CollegeEntity.TYPE_USTC:{map.put("image",R.drawable.ustc);break;}
                    case CollegeEntity.TYPE_XJTU:{map.put("image",R.drawable.xjd);break;}
                    case CollegeEntity.TYPE_DNDX:{map.put("image", R.drawable.rmu);break;}
                    case CollegeEntity.TYPE_SZDX:{map.put("image",R.drawable.nju);break;}
                    case CollegeEntity.TYPE_XJLW:{map.put("image",R.drawable.seu);break;}
                    case CollegeEntity.TYPE_OTHERS:{map.put("image",R.drawable.harvard);break;}
                }


                list.add(d);
                data_list.add(map);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
