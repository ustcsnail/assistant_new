package com.ustc.sse.asisstant.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.entity.User;
import com.ustc.sse.asisstant.util.ModifyUser;

import org.json.JSONException;
import org.json.JSONObject;

public class ModifyPasswd extends AppCompatActivity {

    private TextView originPasswd;
    private TextView newPasswd;
    private TextView rePasswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_passwd);

        originPasswd=(TextView)findViewById(R.id.modify_userOrigedPasswd);
        newPasswd=(TextView)findViewById(R.id.modify_userNewPasswd);
        rePasswd=(TextView)findViewById(R.id.modify_reuserNewPasswd);
    }

    /*响应修改按钮*/
    public void modify(View view)
    {
        String o_passwd=originPasswd.getText().toString();
        String n_passwd=newPasswd.getText().toString();
        String r_passwd=rePasswd.getText().toString();

        if(null==o_passwd||"".equals(o_passwd)||null==n_passwd||"".equals(n_passwd)||null==r_passwd||"".equals(r_passwd))
        {
            Toast.makeText(this,"输入项不可以为空！",Toast.LENGTH_SHORT).show();
            return;
        }
        if(o_passwd.length()<6)
        {
            Toast.makeText(this,"原密码位数不合法！",Toast.LENGTH_SHORT).show();
            return;
        }
        if(n_passwd.length()<6)
        {
            Toast.makeText(this,"新密码位数不合法！",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!n_passwd.equals(r_passwd))
        {
            Toast.makeText(this,"重复密码输入不一致！",Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
        JSONObject object=new JSONObject();
        try {
            object.put("tele",preferences.getString("tele",""));
            object.put("passwd",o_passwd);
            object.put("newpasswd",n_passwd);
            object.put("type","modifypasswd");
            ModifyUser modifyUser=new ModifyUser(this);
            modifyUser.execute(object.toString());

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"修改失败！",Toast.LENGTH_SHORT).show();
        }

    }
}
