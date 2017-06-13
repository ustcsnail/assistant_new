package com.ustc.sse.asisstant.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.widget.ImageViewWithCache;
import com.ustc.sse.asisstant.widget.ThreadItemFooter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gaodengji on 2017/6/4.
 */

public class ShowThreadAdaper extends BaseAdapter {
    private String result;
    private Activity context;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    public ShowThreadAdaper(Activity context, String result) {
        this.result = result;
        this.context = context;
        Log.e("test", result + " ");
        if (result != " ") {
            try {

                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("bbs");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("test", result);
            }
        }
    }
    @Override

    public int getCount() {

        if (jsonArray==null)
            return 0;
        return jsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = context.getLayoutInflater().inflate(R.layout.show_thread_item, null);
        }
        String title_1=null,autor=null,topic=null;
        TextView username = (TextView)convertView.findViewById(R.id.showthreadUsername);
        TextView floorNum = (TextView)convertView.findViewById(R.id.showThreadFloorNum);
        floorNum.setText((position + 1) + "#");
        TextView posttime = (TextView)convertView.findViewById(R.id.showthreadPosttime);
        final TextView msg = (TextView)convertView.findViewById(R.id.showthreadMsg);
        ImageViewWithCache img = (ImageViewWithCache)convertView.findViewById(R.id.showthreadHeadImg);
        ThreadItemFooter itemFooter = (ThreadItemFooter)convertView.findViewById(R.id.showthreadLoadTip);

        if (jsonArray!=null) {
            try {


                //这里获取的是装载有所有test对象的数组
                JSONObject jsonpet = jsonArray.getJSONObject(position);//获取这个数组中第一个pet对象
                //   Log.e(String.valueOf(jsonArray.length()),"length");
                title_1 = jsonpet.getString("title");//获取pet对象的参数
                autor = jsonpet.getString("author");
                username.setText(autor);
                posttime.setText(title_1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        img.setImageResource(R.mipmap.default_user_head_img);
        return convertView;
    }
}
