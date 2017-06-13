package com.ustc.sse.asisstant.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.adapter.ForumDisplayAdapter;
import com.ustc.sse.asisstant.util.AssSharedPreferences;
import com.ustc.sse.asisstant.util.GetDirectorBBS;
import com.ustc.sse.asisstant.widget.RefreshActionBtn;
import com.ustc.sse.asisstant.widget.XListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class TradeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private XListView xListView;
    private String result="";
    private String result1="";
    private Handler m_handler=null;
    private int m_id=0;
    private int id = 0;
    private RefreshActionBtn refreshbtn;
    private ImageButton newThreadBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tradeer);
        AssSharedPreferences sharedPreferences=new AssSharedPreferences(this);
        String s_id=sharedPreferences.getUser().getId();
        if (sharedPreferences.isLogin()) {
            id = Integer.parseInt(s_id);
        }
        refreshbtn=(RefreshActionBtn)this.findViewById(R.id.forumDisplayRefreshBtndt);
        refreshbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TradeActivity.this,"刷新成功", Toast.LENGTH_LONG).show();
            }
        });
        newThreadBtn=(ImageButton)this.findViewById(R.id.forumDisplayNewThreadBtndt);
        newThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AssSharedPreferences sharedPreferences=new AssSharedPreferences(TradeActivity.this);
                String s_id=sharedPreferences.getUser().getId();
                if(!sharedPreferences.isLogin())
                {
                    //若当前用户尚未登录
                    Intent intent=new Intent(TradeActivity.this, Login.class);
                    startActivity(intent);
                    return;
                }
                Intent intent=new Intent(TradeActivity.this, TradeNewActivity.class);
                Bundle data = new Bundle();
                m_id=Integer.parseInt(s_id);
                data.putInt("id",m_id);
                data.putString("author", "author");
                data.putString("open", "title");
                intent.putExtras(data);
                startActivityForResult(intent, 0);
            }
        });

        xListView=(XListView)findViewById(R.id.forumDisplayListViewt);
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
                    xListView.setAdapter(new ForumDisplayAdapter(TradeActivity.this,result));
                    xListView.stopLoadMore();
                    //停止刷新
                    xListView.stopRefresh();
                }
                if (msg.arg1==2&&result1!=null){
                    Log.d("test",result1);
                    xListView.setAdapter(new ForumDisplayAdapter(TradeActivity.this,result1));
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


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Bundle data = new Bundle();
        data.putInt("bbs_id",bbs_id);
        data.putString("author", author);
        data.putString("open","open");
        Intent intent = new Intent(TradeActivity.this, NewTradePage.class);
        intent.putExtras(data);
        this.startActivity(intent);
    }

    private void getData()
    {

        new Thread(new Runnable() {
            @Override
            public void run() {

                GetDirectorBBS getDirectorBBS =new GetDirectorBBS(m_handler);
                getDirectorBBS.getData(GetDirectorBBS.TradeSelect,id+"");
            }
        }).start();

    }
}
