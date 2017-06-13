package com.ustc.sse.asisstant.util;

import okhttp3.OkHttpClient;

/**
 * Created by Thinkpad on 2017/6/1.
 */

public interface HttpBase {

    OkHttpClient client=new OkHttpClient();

    String URL="http://192.168.0.104:8080/assistant/";

    String userUrl=URL+"UserServlet";

    String directorUrl=URL+"DirectorServlet";

    String researchUrl=URL+"ResearchServlet";
    String restaurantUrl=URL+"RestaurantServlet";
    String bbsDir=URL+"BBSServlet";
    String bbsInsert=URL+"BBSServlet";
    String responseSelect=URL+"ResponseServelt";
    String responseInsert=URL+"ResponseServelt";
    String collegeUrl=URL+"CollegeServlet";
    String tradeUrl=URL+"TradeServlet";
    String responseTrade=URL+"ResponseTradeServlet";


}
