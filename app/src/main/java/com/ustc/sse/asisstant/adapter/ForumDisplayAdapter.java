package com.ustc.sse.asisstant.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.widget.ImageViewWithCache;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gaodengji on 2017/2/21.
 */

public class ForumDisplayAdapter extends BaseAdapter {

    private Activity context;
    private String result;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    public ForumDisplayAdapter(Activity context, String result) {
        this.context=context;
        this.result=result;
         Log.e("test",result+" ");
        if (result!=null) {
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

        if(convertView==null)
        {
            convertView=context.getLayoutInflater().inflate(R.layout.forum_display_item,null);
        }
        TextView title = (TextView) convertView
                .findViewById(R.id.forumDisplayTitle);
        TextView replyCnt = (TextView) convertView
                .findViewById(R.id.replyCnt);
        TextView viewsCnt = (TextView) convertView
                .findViewById(R.id.viewsCnt);
        TextView userName = (TextView) convertView
                .findViewById(R.id.postUserName);
        ImageViewWithCache img = (ImageViewWithCache) convertView
                .findViewById(R.id.headImg);

        String title_1=null,author=null,topic=null;
        int point_count,sorce;
//        title.setText("信息");
        if(jsonArray!=null) {
            try {


                //这里获取的是装载有所有test对象的数组
                JSONObject jsonpet = jsonArray.getJSONObject(position);//获取这个数组中第一个pet对象
                //   Log.e(String.valueOf(jsonArray.length()),"length");
                title_1 = jsonpet.getString("title");//获取pet对象的参数
                author = jsonpet.getString("author");
                point_count = jsonpet.getInt("lightBBS");
                sorce = jsonpet.getInt("responseBBS");
                title.setText(title_1);
                replyCnt.setText(sorce + "");
//

                viewsCnt.setText(point_count + "");
                userName.setText(author);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        img.setImageResource(R.mipmap.default_user_head_img);
//        convertView.findViewById(R.id.forumDisplayLock).setVisibility(
//                (View.VISIBLE));

        return convertView;
    }
}

