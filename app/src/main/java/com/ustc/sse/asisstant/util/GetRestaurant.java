package com.ustc.sse.asisstant.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.ustc.sse.asisstant.entity.Director;

import org.json.JSONObject;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Thinkpad on 2017/5/26.
 */

public class GetRestaurant implements HttpBase{

    private Handler handler=null;




    public GetRestaurant(Handler h)
    {
        handler=h;
    }




    public void getData(int type)
    {
        String sendurl=restaurantUrl+"?type="+type;

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
