package com.ustc.sse.asisstant.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.adapter.ShowThreadAdaper;
import com.ustc.sse.asisstant.util.AssSharedPreferences;
import com.ustc.sse.asisstant.util.GetDirector;
import com.ustc.sse.asisstant.util.GetDirectorBBS;
import com.ustc.sse.asisstant.widget.RefreshActionBtn;
import com.ustc.sse.asisstant.widget.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by gaodengji on 2017/2/22.
 */

public class NewThreadPage extends AppCompatActivity implements XListView.IXListViewListener, AdapterView.OnItemClickListener {
    private XListView m_listView;
    private ShowThreadAdaper m_adapter;
    private int m_currentPage = 1;
    private int m_totalPage = 0;
    private int m_id = 0;
    private ProgressBar m_pBar;
    private JSONArray m_model = null;
    private ProgressDialog m_pd = null;
    private RefreshActionBtn m_refreshBtn;
    private TextView m_titleView;
    private long m_lastUpdateTime = 0;
    private EditText m_subject;
    private String result;
    private ImageButton responseBtn=null;
    private int id,bbs_id;
    private Handler m_handler=null;
   private String name,send;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_thread_page);
        Intent intent = this.getIntent();

        Bundle data = intent.getExtras();
        bbs_id=data.getInt("bbs_id");
        Log.e("bbs"," bbs: "+bbs_id);
        m_listView = (XListView) this.findViewById(R.id.showthreadListview);
        m_listView.setPullLoadEnable(false);
        m_listView.setPullRefreshEnable(true);
        m_listView.setXListViewListener(this);
        m_listView.setOnItemClickListener(this);
        View titleHeaderView = LayoutInflater.from(this).inflate(R.layout.show_thread_title_header, null);
        m_listView.addHeaderView(titleHeaderView);
        m_titleView = (TextView) titleHeaderView.findViewById(R.id.showThreadTitle);
        m_titleView.setVisibility(View.GONE);
        m_adapter = new ShowThreadAdaper(this, " ");
        m_listView.setAdapter(m_adapter);
        m_listView.requestFocus();
        m_subject = (EditText) findViewById(R.id.showThreadReplyText);
//        m_pBar = (ProgressBar) this.findViewById(R.id.showThreadProgressBar);
        m_refreshBtn = (RefreshActionBtn) this.findViewById(R.id.showThreadRefreshBtn);


        if (data.getString("open") == null) {
            this.findViewById(R.id.showThreadReplyBar).setVisibility(View.GONE);
        }
        m_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.arg1) {
                    case 1:
                        Bundle bundle = msg.getData();
                        result = bundle.getString("result");
                        Log.d("test", " " + result);
                        m_listView.setAdapter(new ShowThreadAdaper(NewThreadPage.this, result));
                        m_listView.stopLoadMore();
                        //停止刷新
                        m_listView.stopRefresh();
                        break;
                    case 2:
                        Toast.makeText(NewThreadPage.this,"回帖成功",Toast.LENGTH_LONG).show();
                        getData();
                        break;
                }

            }
        };


        getData();//获取回帖
        responseBtn = (ImageButton) findViewById(R.id.showThreadReplyBtn);
        responseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssSharedPreferences sharedPreferences=new AssSharedPreferences(NewThreadPage.this);

                if (!sharedPreferences.isLogin()) {
                    //若当前用户尚未登录
                    Intent intent = new Intent(NewThreadPage.this, Login.class);
                    startActivity(intent);
                    return;
                }
                name = sharedPreferences.getUser().getName();
                String s_id=sharedPreferences.getUser().getId();
                id = Integer.parseInt(s_id);
                if (m_subject.length() == 0) {
                    Toast.makeText(NewThreadPage.this, "回帖内容不能为空", Toast.LENGTH_SHORT).show();
                    m_subject.requestFocus();
                    return;
                } else if (m_subject.length() < 6) {
                    Toast.makeText(NewThreadPage.this, "回帖内容长度不能小于" + 6 + "个字符", Toast.LENGTH_SHORT).show();
                    m_subject.requestFocus();
                    return;
                }

                JSONObject json = new JSONObject();
                String title = m_subject.getText().toString();
                try {
                    json.put("id", id);
                    json.put("author", name);
                    json.put("title", title);
                    json.put("bbs_id",bbs_id);
                    String reponse = json.toString();
                    Log.d("new", json.toString());
                    String name = reponse.trim();
                    reponse = name.replace("{", "%7B");
                    name = reponse.replace("}", "%7D");
                   send = new String(name.getBytes("utf-8"));
                    new Thread(){
                        @Override
                        public void run() {
                            new GetDirectorBBS(m_handler).getData(GetDirectorBBS.RESPBBS_INSERT, send);
                        }
                    }.start();


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                m_subject.setText("");
                m_subject.clearFocus();
                //关闭软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(m_subject.getWindowToken(), 0);

            }
        });
    }
    @Override
    public void onRefresh() throws IOException {
        getData();
        m_listView.stopRefresh();
        m_listView.stopLoadMore();
    }

    @Override
    public void onLoadMore() {

    }
    public void onRefreshBtnClick(View view){
        try {
            this.onRefresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void onBackBtnClick(View view)
    {
        this.onBackPressed();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,ResponseActivity.class);
        startActivity(intent);
    }
    private void getData()
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetDirectorBBS getDirectorBBS=new GetDirectorBBS(m_handler);
                getDirectorBBS.getData(GetDirectorBBS.RESPBBS_SELECT,""+bbs_id);
            }
        }).start();

    }

}

