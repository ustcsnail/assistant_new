package com.ustc.sse.asisstant.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.entity.User;
import com.ustc.sse.asisstant.util.RegisterForm;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;

public class Register extends AppCompatActivity {

    /*注册表项控件*/
    private TextView nameText;
    private TextView passwdText;
    private TextView repasswdText;
    private TextView teleText;

    private Button button;

    private User user=new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameText=(TextView)findViewById(R.id.reg_nickname);
        passwdText=(TextView)findViewById(R.id.reg_userPassword);
        repasswdText=(TextView)findViewById(R.id.reg_reuserPassword);
        teleText=(TextView)findViewById(R.id.reg_tele);


        button=(Button)findViewById(R.id.button_register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setName(nameText.getText().toString().trim());
                user.setPasswd(passwdText.getText().toString().trim());
                user.setRepasswd(repasswdText.getText().toString().trim());
                user.setTele(teleText.getText().toString().trim());

                if (!user.isEmpty())
                {
                    if(verify()) {

                        JSONObject object=new JSONObject();
                        try {
                            object.put("name",user.getName());
                            object.put("tele",user.getTele());
                            object.put("passwd",user.getPasswd());
                            object.put("type","register");

                            RegisterForm registerForm =new RegisterForm(Register.this);
                            registerForm.execute(object.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),"请将表单填写完整！",Toast.LENGTH_SHORT).show();

            }
        });
    }

    protected boolean verify()
    {
        if(user.getName().length()>15)
        {
            Toast.makeText(getApplicationContext(),"用户名字符个数超出范围！",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (user.getPasswd().length()<6)
        {
            Toast.makeText(getApplicationContext(),"密码字符个数太少，不能少于6个字符！",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (user.getPasswd().length()>15)
        {
            Toast.makeText(getApplicationContext(),"密码字符个数超出范围！",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!user.getRepasswd().equals(user.getPasswd()))
        {
            Toast.makeText(getApplicationContext(),"重复密码输入不一致！",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(user.getTele().length()!=11)
        {
            Toast.makeText(getApplicationContext(),"手机号长度不合法！",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}
