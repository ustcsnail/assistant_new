package com.ustc.sse.asisstant.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.activity.Login;
import com.ustc.sse.asisstant.activity.NewThreadActivity;
import com.ustc.sse.asisstant.activity.NewThreadPage;
import com.ustc.sse.asisstant.adapter.ForumDisplayAdapter;

import com.ustc.sse.asisstant.util.AssSharedPreferences;
import com.ustc.sse.asisstant.util.GetDirectorBBS;
import com.ustc.sse.asisstant.widget.RefreshActionBtn;
import com.ustc.sse.asisstant.widget.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * Created by Thinkpad on 2016/12/19.
 */

public class PagerFragment2 extends Fragment implements AdapterView.OnItemClickListener {
    private RefreshActionBtn refreshbtn;
    private ImageButton newThreadBtn;
    private Button timeBtn;
    private XListView xListView;
    private String result="";
    private String result1="";
    private Handler m_handler=null;
    private int m_id=0;
    private SharedPreferences preferences=null;
    private boolean loginStatus=false;
    private int id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.forum_display_page,container,false);
        xListView=(XListView)view.findViewById(R.id.forumDisplayListView);
        xListView.setAdapter(new ForumDisplayAdapter(getActivity(),null));
        xListView.setOnItemClickListener(this);
        AssSharedPreferences sharedPreferences=new AssSharedPreferences(getActivity());
        String s_id=sharedPreferences.getUser().getId();
        if (sharedPreferences.isLogin()) {
            id = Integer.parseInt(s_id);
        }

        m_handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.arg1==1){
                   // Log.e("test",result);
                    Bundle bundle= msg.getData();
                    result = bundle.getString("result");

                    Log.d("test"," "+ result);
                    xListView.setAdapter(new ForumDisplayAdapter(getActivity(),result));
                    xListView.stopLoadMore();
                    //停止刷新
                    xListView.stopRefresh();
                }
                if (msg.arg1==2&&result1!=null){
                    Log.d("test",result1);
                    xListView.setAdapter(new ForumDisplayAdapter(getActivity(),result1));
                    xListView.stopLoadMore();
                    //停止刷新
                    xListView.stopRefresh();
                }

            }
        };
        xListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() throws IOException {
                getData();
            }
            @Override
            public void onLoadMore() {
                Log.d("onLoadMore","onLoadMore");
                //停止加载更多
                xListView.stopLoadMore();
                //停止刷新
                xListView.stopRefresh();
            }
        });
        xListView.setAdapter(new ForumDisplayAdapter(getActivity(),null));
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        JSONObject obj = null;
        String title=null,author = null;
        int id = 0,bbs_id=0;
        try {
            jsonObject=new JSONObject(result);
            jsonArray=jsonObject.getJSONArray("bbs");
            obj = jsonArray.getJSONObject(position-1);
            title=obj.getString("title");
            author=obj.getString("author");
           // id = obj.getInt("id");
            bbs_id=obj.getInt("bbs_id");
            Log.e("bbs","result:"+result);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Bundle data = new Bundle();
        data.putInt("id",id);
        data.putString("author", author);
        data.putString("open", title);
        data.putInt("bbs_id",bbs_id);
        Intent intent = new Intent(getActivity(), NewThreadPage.class);
        intent.putExtras(data);
        this.startActivity(intent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshbtn=(RefreshActionBtn)getActivity().findViewById(R.id.forumDisplayRefreshBtn);
        refreshbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Toast.makeText(getActivity(),"刷新成功", Toast.LENGTH_LONG).show();
            }
        });
        newThreadBtn=(ImageButton)getActivity().findViewById(R.id.forumDisplayNewThreadBtn);
        newThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AssSharedPreferences sharedPreferences=new AssSharedPreferences(getActivity());
                String s_id=sharedPreferences.getUser().getId();
                if(!sharedPreferences.isLogin())
                {
                    //若当前用户尚未登录
                    Intent intent=new Intent(getActivity(), Login.class);
                    startActivity(intent);
                    return;
                }
                Intent intent=new Intent(getActivity(), NewThreadActivity.class);
                Bundle data = new Bundle();
                m_id=Integer.parseInt(s_id);
                data.putInt("id",m_id);
                data.putString("author", "author");
                data.putString("open", "title");
                intent.putExtras(data);
                startActivityForResult(intent, 0);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
                Bundle bunde = data.getExtras();
                String title=bunde.getString("title");
                JSONObject item = new JSONObject();
                 switch (resultCode) {
                    case 1:
                        try {
                            item.put("title", bunde.getString("title"));
                            item.put("id", bunde.getInt("id"));
                            item.put("author",bunde.getString("author"));
                            item.put("lightBBS", 0);
                            item.put("responseBBS", 0);
                           Log.d("testj",item.toString());
                            if (result!=""&& !title.equals("title")){
                                JSONObject json =new JSONObject(result);
                                JSONArray jsonArray=json.getJSONArray("bbs");
                                jsonArray.put(0,item);
                                json.put("bbs",jsonArray);
                                String result1=json.toString();
                                Log.d("http",result1);
                                xListView.setAdapter(new ForumDisplayAdapter(getActivity(),result1));
                            }
                            else if(!title.equals("title")){
                                JSONObject json = new JSONObject();
                                JSONObject[] jsonArray=new JSONObject[1];
                                jsonArray[0]=item;
                                json.put("bbs",jsonArray);
                                json.toString();
                                xListView.setAdapter(new ForumDisplayAdapter(getActivity(),json.toString()));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }

    }
    private void getData()
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetDirectorBBS getDirectorBBS =new GetDirectorBBS(m_handler);
                getDirectorBBS.getData(GetDirectorBBS.BBSREQUEST_SELECT,"lightBBS");
            }
        }).start();

    }
}

