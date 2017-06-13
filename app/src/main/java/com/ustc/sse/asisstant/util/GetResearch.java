package com.ustc.sse.asisstant.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.ustc.sse.asisstant.entity.ResearchEntity;

import org.json.JSONObject;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Thinkpad on 2017/5/26.
 */

public class GetResearch implements HttpBase{

    private Handler handler=null;




    public GetResearch(Handler h)
    {
        handler=h;
    }




    public void getData(int type)
    {
        String sendurl=researchUrl+"?subject="+type;

        String result=null;

        Request request=new Request.Builder().url(sendurl).build();

        try {
            Response response=client.newCall(request).execute();
            result=response.body().string().trim();

            if(null==result||"".equals(result))
            {
                Message message=new Message();
                message.what=ResearchEntity.RESULT_JSONERR;
                handler.sendMessage(message);
            }
            else
            {
                JSONObject object = new JSONObject(result);
                int err=object.getInt("result");
                if(err== ResearchEntity.RESULT_SUCCESS)
                {

                    Message message = new Message();
                    message.what = ResearchEntity.RESULT_SUCCESS;
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
            message.what=ResearchEntity.RESULT_JSONERR;
            handler.sendMessage(message);
        }

    }




    public void getData(int subject,String id)
    {
        String sendurl=researchUrl+"?subject="+subject+"&id="+id;

        String result=null;

        Request request=new Request.Builder().url(sendurl).build();

        try {
            Response response=client.newCall(request).execute();
            result=response.body().string().trim();

            if(null==result||"".equals(result))
            {
                Message message=new Message();
                message.what=ResearchEntity.RESULT_JSONERR;
                handler.sendMessage(message);
            }
            else
            {
                JSONObject object = new JSONObject(result);
                int err=object.getInt("result");
                if(err== ResearchEntity.RESULT_SUCCESS)
                {

                    Message message = new Message();
                    message.what = ResearchEntity.RESULT_SUCCESS;
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
            message.what=ResearchEntity.RESULT_JSONERR;
            handler.sendMessage(message);
        }

    }

}
