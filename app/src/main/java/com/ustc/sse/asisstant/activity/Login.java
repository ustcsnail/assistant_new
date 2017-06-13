package com.ustc.sse.asisstant.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.entity.User;
import com.ustc.sse.asisstant.util.CheckUser;

public class Login extends AppCompatActivity {

    private TextView teleText=null;
    private TextView passwdText=null;

    private SharedPreferences preferences=null;
    private  SharedPreferences.Editor editor=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences=getSharedPreferences("user",MODE_PRIVATE);
        editor=preferences.edit();

        teleText=(TextView)findViewById(R.id.login_userName);
        passwdText=(TextView)findViewById(R.id.login_userPassword);

        String tele=preferences.getString("tele","");
        String passwd=preferences.getString("passwd","");
        if(!"".equals(tele)&&!"".equals(passwd))
        {
            teleText.setText(tele);
            passwdText.setText(passwd);
        }


    }


    public void onClickRegist(View view)
    {
        Intent intent=new Intent(Login.this,Register.class);
        startActivity(intent);
    }

    public void onClickLogin(View view)
    {


        String tele=teleText.getText().toString().trim();
        String passwd=passwdText.getText().toString().trim();

        if(null==tele||null==passwd||"".equals(tele)||"".equals(passwd))
        {
            Toast.makeText(this, "手机号和密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        if(tele.length()!=11)
        {
            Toast.makeText(this, "手机号输入位数错误！", Toast.LENGTH_SHORT).show();
            return;
        }

        if(passwd.length()<6)
        {
            Toast.makeText(this, "密码位数不能少于6位！", Toast.LENGTH_SHORT).show();
            return;
        }

        User user=new User();
        user.setPasswd(passwd);
        user.setTele(tele);
        CheckUser c=new CheckUser(this);
        c.execute(user);


    }
}
