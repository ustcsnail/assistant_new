package com.ustc.sse.asisstant.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.entity.ResearchEntity;
import com.ustc.sse.asisstant.util.GetResearch;

import org.json.JSONException;
import org.json.JSONObject;

public class ConcreteResearch extends AppCompatActivity {

    private  String title=null;
    private  String author=null;
    private  String date=null;
    private  String id=null;
    private  String content=null;

    private TextView text_title=null;
    private TextView text_author=null;
    private TextView text_date=null;
    private TextView text_content=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concrete_research);

        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        author=intent.getStringExtra("author");
        date=intent.getStringExtra("date");
        id=intent.getStringExtra("id");


        text_title=(TextView)findViewById(R.id.research_con_title);
        text_author=(TextView)findViewById(R.id.research_con_author);
        text_date=(TextView)findViewById(R.id.research_con_date);
        text_content=(TextView)findViewById(R.id.research_con_content);




        getData();

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

                    try {
                        JSONObject object=new JSONObject(data);
                        content=object.getString("content");

                        text_title.setText(title);
                        text_author.setText(author);
                        text_date.setText(date);
                        text_content.setText(content);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                }
                case ResearchEntity.RESULT_JSONERR:
                {
                    Toast.makeText(getApplicationContext(),"数据解析错误！",Toast.LENGTH_SHORT).show();
                    break;
                }
                case ResearchEntity.RESULT_TYPEERR:
                {
                    Toast.makeText(getApplicationContext(),"查询类型错误！",Toast.LENGTH_SHORT).show();
                    break;
                }
                case ResearchEntity.RESULT_SERVERERR:
                {
                    Toast.makeText(getApplicationContext(),"服务器错误！",Toast.LENGTH_SHORT).show();
                    break;
                }
                case ResearchEntity.RESULT_EMPTYERR:
                {
                    Toast.makeText(getApplicationContext(),"当前没有可用信息！", Toast.LENGTH_SHORT).show();
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
                GetResearch getResearch=new GetResearch(handler);
                getResearch.getData(ResearchEntity.TYPE_CONCRETE,id);
            }
        }).start();

    }
}
