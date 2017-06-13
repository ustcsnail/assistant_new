package com.ustc.sse.asisstant.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ustc.sse.asisstant.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Thinkpad on 2017/5/26.
 */

public class CheckUserBackground extends AsyncTask<User,String,String> implements HttpBase{

    private Context context=null;
    public CheckUserBackground(Context c)
    {
        context=c;
    }



    @Override
    protected String doInBackground(User... params) {


        String url=userUrl+"?tele="+params[0].getTele()+"&passwd="+params[0].getPasswd();
        String result=null;

        Request request=new Request.Builder().url(url).build();

        try {
            Response response=client.newCall(request).execute();
            result=response.body().string().trim();

        } catch (IOException e) {
            e.printStackTrace();
            //无法连接到服务器

            result="";
        }


        return result;
    }


    @Override
    protected void onPostExecute(String s) {
        //super.onPostExecute(s);
        SharedPreferences preferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        if (s!=null&&!"".equals(s))
        {
            try {
                JSONObject object=new JSONObject(s);
                User user=new User();
                user.setStatus(object.getBoolean("status"));

                //登录失败
                if(!user.isStatus())
                {

                    editor.putBoolean("status",false);
                    editor.apply();

                    String error=object.getString("passwd");
                    Toast.makeText(context.getApplicationContext(),"登录失败，"+error,Toast.LENGTH_LONG).show();

                }
                //登录成功
                else
                {
                    user.setId(object.getString("id"));
                    user.setName(object.getString("name"));
                    user.setTele(object.getString("tele"));
                    /*将最新信息存入配置文件*/
                    editor.putString("id",user.getId());
                    editor.putString("tele",user.getTele());
                    editor.putString("name",user.getName());
                    editor.putBoolean("status",user.isStatus());
                    editor.apply();
                }

            } catch (JSONException e) {
                e.printStackTrace();

                editor.putBoolean("status",false);
                editor.apply();
                //服务器连接错误或超时
                Toast.makeText(context.getApplicationContext(),"登录失败，服务器连接超时...",Toast.LENGTH_LONG).show();

            }
        }
        else
        {
            editor.putBoolean("status",false);
            editor.apply();
            //服务器连接错误或超时
            Toast.makeText(context.getApplicationContext(),"登录失败，服务器连接超时...",Toast.LENGTH_LONG).show();
        }

    }
}
