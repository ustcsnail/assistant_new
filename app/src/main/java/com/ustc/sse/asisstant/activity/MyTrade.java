package com.ustc.sse.asisstant.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;


import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.adapter.ForumDisplayAdapter;
import com.ustc.sse.asisstant.util.AssSharedPreferences;
import com.ustc.sse.asisstant.util.GetDirectorBBS;
import com.ustc.sse.asisstant.widget.XListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class MyTrade extends Activity implements AdapterView.OnItemClickListener{

    private XListView xListView;
    private String result="";
    private String result1="";
    private Handler m_handler=null;
    private int m_id=0;
    private int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myforum_display_page);
        AssSharedPreferences sharedPreferences=new AssSharedPreferences(this);
        String s_id=sharedPreferences.getUser().getId();
        if (sharedPreferences.isLogin()) {
            id = Integer.parseInt(s_id);
        }
        xListView=(XListView)findViewById(R.id.forumDisplayListViewi);
        xListView.setAdapter(new ForumDisplayAdapter(this,null));
        xListView.setOnItemClickListener(this);

        m_handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.arg1==1){
                    // Log.e("test",result);
                    Bundle bundle= msg.getData();
                    result = bundle.getString("result");

                    Log.d("test"," "+ result);
                    xListView.setAdapter(new ForumDisplayAdapter(MyTrade.this,result));
                    xListView.stopLoadMore();
                    //停止刷新
                    xListView.stopRefresh();
                }
                if (msg.arg1==2&&result1!=null){
                    Log.d("test",result1);
                    xListView.setAdapter(new ForumDisplayAdapter(MyTrade.this,result1));
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

                xListView.stopLoadMore();
                //停止刷新
                xListView.stopRefresh();
            }
        });
        xListView.setAdapter(new ForumDisplayAdapter(this,null));

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        JSONObject obj = null;
        String title=null,author = null;
        int bbs_id=0;
        try {
            jsonObject=new JSONObject(result);
            jsonArray=jsonObject.getJSONArray("bbs");
            obj = jsonArray.getJSONObject(position - 1);
            title=obj.getString("title");
            author=obj.getString("author");
            bbs_id=obj.getInt("bbs_id");
            id = obj.getInt("id");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Bundle data = new Bundle();
        data.putInt("bbs_id",bbs_id);
        data.putInt("id",id);
        data.putString("author", author);
        data.putString("open",null);
        Intent intent = new Intent(MyTrade.this, NewTradePage.class);
        intent.putExtras(data);
        this.startActivity(intent);
    }

    private void getData()
    {

        new Thread(new Runnable() {
            @Override
            public void run() {

                GetDirectorBBS getDirectorBBS =new GetDirectorBBS(m_handler);
                getDirectorBBS.getData(GetDirectorBBS.TradeBYID,id+"");
            }
        }).start();

    }
}
