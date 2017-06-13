package com.ustc.sse.asisstant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.adapter.ForumDisplayAdapter;
import com.ustc.sse.asisstant.adapter.ThreadAdapter;
import com.ustc.sse.asisstant.widget.XListView;

import java.io.IOException;

public class NewsActivity extends AppCompatActivity implements XListView.IXListViewListener,AdapterView.OnItemClickListener{

        private XListView m_listView=null;
        private TextView m_titleView=null;
        ThreadAdapter m_adapter=null;
        private String result=null;
    private Handler my_handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1){
                case 1:
                    m_listView.setAdapter(new ForumDisplayAdapter(NewsActivity.this,result));
                    m_listView.stopLoadMore();
                    m_listView.stopRefresh();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_mythread_page);
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        /*接受到request字符串，根据request字符串进行数据库访问*/
       final String request = null;
               bundle.getString("request");
        m_listView = (XListView) this.findViewById(R.id.showthreadListview);
        m_listView.setPullLoadEnable(false);
        m_listView.setPullRefreshEnable(true);
        m_listView.setXListViewListener(this);
        m_listView.setOnItemClickListener(this);
        View titleHeaderView = LayoutInflater.from(this).inflate(R.layout.show_thread_title_header, null);
        m_listView.addHeaderView(titleHeaderView);
        m_titleView = (TextView) titleHeaderView.findViewById(R.id.showThreadTitle);
        m_titleView.setVisibility(View.GONE);
        m_adapter = new ThreadAdapter(this,null);
       // m_listView.setAdapter(m_adapter);
        m_listView.requestFocus();

    }

    @Override
    public void onRefresh() throws IOException {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
