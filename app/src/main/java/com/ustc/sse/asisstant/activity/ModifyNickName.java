package com.ustc.sse.asisstant.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.util.ModifyUser;

import org.json.JSONException;
import org.json.JSONObject;

public class ModifyNickName extends AppCompatActivity {

    private TextView nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_nick_name);

        nameText=(TextView)findViewById(R.id.modify_nickname);
    }

    /*响应修改按钮*/
    public void modify(View view)
    {
        String name=nameText.getText().toString();

        if(null==name||"".equals(name))
        {
            Toast.makeText(this,"用户名不可以为空！",Toast.LENGTH_SHORT).show();
            return;
        }
        if(name.length()>15)
        {
            Toast.makeText(this,"用户名字符数超出范围！",Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
        JSONObject object=new JSONObject();
        try {
            object.put("tele",preferences.getString("tele",""));
            object.put("name",name);
            object.put("type","modifyname");
            ModifyUser modifyUser=new ModifyUser(this);
            modifyUser.execute(object.toString());

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"修改失败！",Toast.LENGTH_SHORT).show();
        }
    }

}
