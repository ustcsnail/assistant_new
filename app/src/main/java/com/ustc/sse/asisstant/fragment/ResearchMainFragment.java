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
import com.ustc.sse.asisstant.activity.ConcreteResearch;
import com.ustc.sse.asisstant.entity.ResearchEntity;
import com.ustc.sse.asisstant.util.GetResearch;
import com.ustc.sse.asisstant.util.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResearchMainFragment extends Fragment {

    private boolean flag =true;
    private int type=1;
    private static int check=0;
    private ListView listView=null;

    private List<HashMap<String,Object>> data_list=new ArrayList<HashMap<String, Object>>();

    private List<ResearchEntity> list=new ArrayList<ResearchEntity>();

    private   SimpleAdapter adapter=null;

    public ResearchMainFragment() {
        // Required empty public constructor
    }
    Singleton singleton=Singleton.getSingleton();

    public void setType(int type)
    {
        this.type=type;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.v("thread",Thread.currentThread().getId()+" ");
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
                    Intent intent=new Intent(getActivity(), ConcreteResearch.class);
                    intent.putExtra("title",e.getTitle());
                    intent.putExtra("id",e.getId()+"");
                    intent.putExtra("author",e.getAuthor());
                    intent.putExtra("date",e.getDate());
                    startActivity(intent);

                }
            }
        });

            getData();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
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
                   // prepareDate(data);
                    DataThread thread=new DataThread(data,handler);
                    thread.start();

                   // adapter.notifyDataSetChanged();
                    break;
                }
                case 12:
                    adapter.notifyDataSetChanged();
                    break;

            }

        }
    };


    /*开启多线程，从网络获取数据*/
    private void getData()
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("thread","check"+check++);
                GetResearch getResearch=new GetResearch(handler);
                getResearch.getData(type);
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

                switch(d.getUniversity())
                {
                    case "ustc":{map.put("image",R.drawable.ustc);break;}
                    case "rmu":{map.put("image",R.drawable.rmu);break;}
                    case "xjd":{map.put("image", R.drawable.xjd);break;}
                    case "nju":{map.put("image",R.drawable.nju);break;}
                    case "seu":{map.put("image",R.drawable.seu);break;}
                }


                list.add(d);
                data_list.add(map);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    class DataThread extends Thread{
        private String data;
        private Handler handler;
        public DataThread(String data,Handler handler){
            this.data=data;
            this.handler=handler;
        }

        @Override
        public void run() {
            Log.e("thread","2"+Thread.currentThread().getId());
            prepareDate(data);
            Message message=new Message();
            message.what=12;
            handler.sendMessage(message);
        }
    }

}
