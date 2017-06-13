package com.ustc.sse.asisstant.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.ustc.sse.asisstant.MainActivity;
import com.ustc.sse.asisstant.entity.Director;
import com.ustc.sse.asisstant.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Thinkpad on 2017/5/26.
 */

public class GetDirector implements HttpBase{

    private Handler handler=null;




    public GetDirector(Handler h)
    {
        handler=h;
    }




    public void getData(String type)
    {
        String sendurl=directorUrl+"?type="+type;

        String result=null;

        Request request=new Request.Builder().url(sendurl).build();

        try {
            Response response=client.newCall(request).execute();
            result=response.body().string().trim();

            if(null==result||"".equals(result))
            {
                Message message=new Message();
                message.what=Director.RESULT_JSONERR;
                handler.sendMessage(message);
            }
            else
            {
                JSONObject object = new JSONObject(result);
                int err=object.getInt("result");
                if(err==Director.RESULT_SUCCESS)
                {

                    Message message = new Message();
                    message.what = Director.RESULT_SUCCESS;
                    Bundle bundle = new Bundle();
                    bundle.putString("result", result);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                else
                {
                    Message message = new Message();
                    message.what=err;
                    handler.sendMessage(message);
                }
    }

        } catch (Exception e) {

            e.printStackTrace();
            Message message=new Message();
            message.what=Director.RESULT_JSONERR;
            handler.sendMessage(message);
        }

    }



}
