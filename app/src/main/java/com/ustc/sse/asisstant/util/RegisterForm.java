package com.ustc.sse.asisstant.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.ustc.sse.asisstant.activity.Login;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Thinkpad on 2017/5/24.
 */

public class RegisterForm extends AsyncTask <String,String,String> implements HttpBase{
    private Context context=null;
    private ProgressDialog dialog=null;

    public RegisterForm(Context c)
    {
        context=c;
    }
    @Override
    protected void onPreExecute() {

        dialog=new ProgressDialog(context);
        dialog.setMessage("正在注册...");
        dialog.show();
    }

    @Override
    protected String doInBackground(String... body) {
        String result=null;
        MediaType type=MediaType.parse("application/json; charset=UTF-8");
        Request request=new Request.Builder().url(userUrl).post(RequestBody.create(type,body[0])).build();
      try {
            Response response=client.newCall(request).execute();
            result=response.body().string().trim();
        } catch (IOException e) {
            e.printStackTrace();
            result="error";
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        if(dialog!=null)
           dialog.dismiss();

        if("success".equals(s)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("注册完成").setMessage("恭喜您，注册成功！");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(context, Login.class);
                    context.startActivity(intent);
                }
            });
            builder.create().show();
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("注册失败").setMessage("很抱歉，注册失败！");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        }




    }
}
