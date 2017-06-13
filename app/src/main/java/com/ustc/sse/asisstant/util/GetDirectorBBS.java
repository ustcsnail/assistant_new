package com.ustc.sse.asisstant.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Thinkpad on 2017/5/26.
 */

public class GetDirectorBBS implements HttpBase {

    private Handler handler=null;

    public  static final  int BBSREQUEST_SELECT=1;
    public static int BBSREQUEST_INSERT=2;
    public static int RESPBBS_SELECT=3;
    public static int RESPBBS_INSERT=4;
    public static int BBSSELECTBYID=5;
    public static int RESPBBSBYID=6;
    public static int TradeSelect=7;
    public static int ResponseTrade=8;
    public static int TradeInsert=9;
    public static int ResponseTradeInsert=10;
    public static int TradeBYID=11;
    public static int EvaluateSelect=12;
    public static int EvaluateInsert=13;
    public GetDirectorBBS(Handler h)
    {
        handler=h;
    }




    public void getData(int check,String type) {
        String sendurl=null;
        switch (check){
            case 1:
                sendurl = bbsDir + "?id=1" + "&name="+type;
                break;
            case 2:
                sendurl=bbsInsert+"?id=2"+"&name="+type;
                break;
            case 3:
                sendurl=responseSelect+"?id=1"+"&name="+type;
                break;
            case 4:
                sendurl=responseInsert+"?id=2"+"&name="+type;
                break;
            case 5:
                sendurl=bbsDir+"?id=3"+"&name="+type;
                break;
            case 6:
                sendurl=responseSelect+"?id=3"+"&name="+type;
                break;
            case 7:
                sendurl=tradeUrl+"?id=1"+"&name="+type;
                break;
            case 8:
                sendurl=responseTrade+"?id=1"+"&name="+type;
                break;
            case 9:
                sendurl=tradeUrl+"?id=2"+"&name="+type;
                break;
            case 10:
                sendurl=responseTrade+"?id=2"+"&name="+type;
                break;
            case 11:
                sendurl=tradeUrl+"?id=3"+"&name="+type;
                break;
            case 12:
                sendurl=responseTrade+"?id=4"+"&name="+type;
                break;
            case 13:
                sendurl=responseTrade+"?id=5"+"&name="+type;
                break;
            default:

                break;
        }


        String result = null;

        Request request = new Request.Builder().url(sendurl).build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string().trim();
            Message message=new Message();
            Bundle bundle = new Bundle();
            bundle.putString("result",result+" ");
            message.setData(bundle);
            if("success".equals(result))
                message.arg1=2;
            else
                message.arg1=1;
            handler.sendMessage(message);
            Log.v("test",result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
