package com.ustc.sse.asisstant.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.widget.ImageViewWithCache;
import com.ustc.sse.asisstant.widget.ThreadItemFooter;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by gaodengji on 2017/6/4.
 */

public class ThreadAdapter extends BaseAdapter {
    private Activity context;
    private JSONObject jsonObject=null;
    String titleArray[]={"朱老师讲的很好的，就是作业有点多","作业太多了 每周必须通宵，不过能够学到知识也是不错的","学到了很多东西 作业还好吧 不是太难","有基础就好","美得很啊",
            "独墅湖图书馆借书多久归还","如果你用过Visual Studio的自动补全功能后，再来用eclipse的自动补全功能，相信大家会有些许失望。","顺性别"};
    String autorArray[]={"美好的祝福","我最美","沃斯沙哔","Curt Cohain","云贵高原","Lebron James","Kobe Brant","Kevin Durant"};
    JSONArray jsonArray=null;
    String response =null;

    public ThreadAdapter(Activity context, String reponse) {
        this.context=context;
        this.response = response;
    }
    public String parseJson(String json){
        return null;
    }
    @Override
    public int getCount() {

        return Math.min(titleArray.length,autorArray.length);
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

        TextView username = (TextView)convertView.findViewById(R.id.showthreadUsername);
        TextView floorNum = (TextView)convertView.findViewById(R.id.showThreadFloorNum);
        floorNum.setText((position + 1) + "#");
        TextView posttime = (TextView)convertView.findViewById(R.id.showthreadPosttime);
        final TextView msg = (TextView)convertView.findViewById(R.id.showthreadMsg);
        ImageViewWithCache img = (ImageViewWithCache)convertView.findViewById(R.id.showthreadHeadImg);
        ThreadItemFooter itemFooter = (ThreadItemFooter)convertView.findViewById(R.id.showthreadLoadTip);
        username.setText(autorArray[position]);
        posttime.setText(titleArray[position]);
        img.setImageResource(R.mipmap.default_user_head_img);
        return convertView;
    }
}
