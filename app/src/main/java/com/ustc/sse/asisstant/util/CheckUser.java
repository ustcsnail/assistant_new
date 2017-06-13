package com.ustc.sse.asisstant.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.ustc.sse.asisstant.MainActivity;
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

public class CheckUser extends AsyncTask<User,String,String> implements HttpBase{

    private Context context=null;
    private ProgressDialog dialog=null;

    private  String passwd=null;

    public CheckUser(Context c)
    {
        context=c;
    }

    @Override
    protected void onPreExecute() {

        dialog=new ProgressDialog(context);
        dialog.setMessage("正在登录...");
        dialog.show();
    }
    @Override
    protected String doInBackground(User... params) {


        String sendurl=userUrl+"?tele="+params[0].getTele()+"&passwd="+params[0].getPasswd();

        passwd=params[0].getPasswd().trim();
        String result=null;

        Request request=new Request.Builder().url(sendurl).build();

        try {
            Response response=client.newCall(request).execute();
            result=response.body().string().trim();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.dismiss();
        if (s!=null&&!"".equals(s))
        {
            try {
                JSONObject object=new JSONObject(s);
                User user=new User();
                user.setStatus(object.getBoolean("status"));

                //登录成功
                if(user.isStatus())
                {
                    user.setId(object.getString("id"));
                    user.setName(object.getString("name"));
                    user.setTele(object.getString("tele"));

                    SharedPreferences preferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("id",user.getId());
                    editor.putString("tele",user.getTele());
                    editor.putString("name",user.getName());
                    editor.putString("passwd",passwd);
                    editor.putBoolean("status",user.isStatus());
                    editor.apply();

                    AlertDialog.Builder builder= new AlertDialog.Builder(context).setTitle("登录成功").setMessage("尊敬的 "+user.getName()+" 欢迎您回来！");
                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent=new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        }
                    });

                    builder.create().show();
                }
                else
                {
                    String error=object.getString("passwd");
                    AlertDialog.Builder builder= new AlertDialog.Builder(context).setTitle("错误").setMessage(error);
                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.create().show();

                }


            } catch (JSONException e) {
                e.printStackTrace();

                AlertDialog.Builder builder= new AlertDialog.Builder(context).setTitle("错误").setMessage("返回值异常！");
                builder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();

            }
        }
        else
        {
            AlertDialog.Builder builder= new AlertDialog.Builder(context).setTitle("错误").setMessage("返回值异常！");
            builder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.create().show();
        }
    }
}
