package com.ustc.sse.asisstant.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.util.AssSharedPreferences;
import com.ustc.sse.asisstant.util.GetDirector;
import com.ustc.sse.asisstant.util.GetDirectorBBS;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

/**
 * Created by gaodengji on 2017/2/21.
 */

public class NewThreadActivity extends AppCompatActivity {
    private int m_id;
    private ProgressDialog m_pd;
    private EditText m_kxReward;
    private EditText m_subject;
    private int m_currentTopic;
    private ImageButton backBtn=null;
    private int id;
    private Handler m_handler = null;
    private String name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_thread_page);
        Bundle data = this.getIntent().getExtras();
        m_id = data.getInt("id");
        AssSharedPreferences sharedPreferences=new AssSharedPreferences(this);
        String s_id=sharedPreferences.getUser().getId();
        id =Integer.parseInt(s_id);
        name =sharedPreferences.getUser().getName();
        m_kxReward=(EditText)this.findViewById(R.id.newThreadKxReward);
        m_subject = (EditText)this.findViewById(R.id.newThreadSubject);
        m_handler=new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.arg1) {
                    case 1:
                        Bundle bundle = msg.getData();
                        String flag= bundle.getString("result").trim();
                        if (flag!=null&&flag.equals("success"))
                            Toast.makeText(NewThreadActivity.this,"发帖成功", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(NewThreadActivity.this,"发帖失败", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }

                //关闭ProgressDialog

            }
        };

        View v = this.findViewById(R.id.newThreadTopic);
        v.setVisibility(View.VISIBLE);
        Spinner spinner = (Spinner)this.findViewById(R.id.newThreadTopicSelect);
        final String[] topics = getResources().getStringArray(R.array.topic_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, topics);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int index, long arg3) {
                if (index == 0)
                    return;
                m_currentTopic = index;
                String tmp = m_subject.getText().toString();
                tmp = topics[index] + tmp;
                m_subject.setText(tmp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO 自动生成的方法存根

            }

        });
    }

    @Override
    public void onBackPressed() {
        Bundle data = new Bundle();
        data.putInt("id", 1);
        data.putString("author","author");
        data.putString("title","title");
        Intent intent = NewThreadActivity.this.getIntent();
        intent.putExtras(data);
        NewThreadActivity.this.setResult(1, intent);
        NewThreadActivity.this.finish();
    }

    public void onBackBtnClick(View v) {
        onBackPressed();
    }

    public void onSubmitBtnClick(View v) {
        EditText message = (EditText)this.findViewById(R.id.newThreadMessage);
        final String subjectText = m_subject.getText().toString();
        String msgText = message.getText().toString();

        if (m_subject.length() == 0) {
            Toast.makeText(NewThreadActivity.this, "标题不能为空", Toast.LENGTH_SHORT).show();
            m_subject.requestFocus();
            return;
        }


        if (m_currentTopic == 0) {
            Toast.makeText(NewThreadActivity.this, "请选择话题", Toast.LENGTH_SHORT).show();
            return;
        }

       /* if (message.length() == 0) {
           Toast.makeText(NewThreadActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
            message.requestFocus();
            return;
        } else if (message.length() < 6) {
            Toast.makeText(NewThreadActivity.this, "内容长度不能小于" + 6 + "个字符", Toast.LENGTH_SHORT).show();
            message.requestFocus();
            return;
        }*/
        new Thread(){
            @Override
            public void run() {
                JSONObject json = new JSONObject();
                try {
                    json.put("id",id);
                    json.put("author",name);
                    json.put("title",subjectText);
                    json.put("lightBBS",0);
                    json.put("responseBBS",0);
                    String reponse=json.toString();
                    Log.d("new",json.toString());
                    String name =reponse.trim();
                    reponse =name.replace("{","%7B");
                    name = reponse.replace("}","%7D");

                    String send=new String(name.getBytes("utf-8"));
                    new GetDirectorBBS(m_handler).getData(GetDirectorBBS.BBSREQUEST_INSERT,send);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }.start();

        Bundle data = new Bundle();
        data.putString("title", subjectText);
        data.putInt("id", m_id);
        data.putString("author","author");
        Intent intent = NewThreadActivity.this.getIntent();
        intent.putExtras(data);
        NewThreadActivity.this.setResult(1, intent);
        NewThreadActivity.this.finish();

    }
}
