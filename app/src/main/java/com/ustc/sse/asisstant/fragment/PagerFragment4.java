package com.ustc.sse.asisstant.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.activity.Help;
import com.ustc.sse.asisstant.activity.Login;
import com.ustc.sse.asisstant.activity.MyBBS;
import com.ustc.sse.asisstant.activity.MyResponseActivity;
import com.ustc.sse.asisstant.activity.MyTrade;
import com.ustc.sse.asisstant.activity.NewThreadPage;
import com.ustc.sse.asisstant.activity.Settings;
import com.ustc.sse.asisstant.activity.WeatherActivity;
import com.ustc.sse.asisstant.activity.course.Curriculum;
import com.ustc.sse.asisstant.activity.saoyisao.CaptureActivity;
import com.ustc.sse.asisstant.activity.xingzuo.WelcomeActivity;
import com.ustc.sse.asisstant.util.AssSharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thinkpad on 2016/12/19.
 */

public class PagerFragment4 extends Fragment {

    private boolean loginStatus=false;
    private SharedPreferences preferences=null;
    private int m_id;
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!loginStatus)
            {
                Intent intent=new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
            else
            {
                Intent intent=new Intent(getActivity(), Settings.class);
                startActivity(intent);
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.pager_page4,container,false);

        preferences=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        loginStatus=preferences.getBoolean("status",false);

        ImageView imageView=(ImageView)view.findViewById(R.id.person_head);
        TextView textView=(TextView)view.findViewById(R.id.text_login);
        if (loginStatus)
        {
            AssSharedPreferences sharedPreferences=new AssSharedPreferences(getActivity());
            String s_id=sharedPreferences.getUser().getId();
            m_id =Integer.parseInt(s_id);
            textView.setText(preferences.getString("name",""));
        }

        imageView.setOnClickListener(onClickListener);
        textView.setOnClickListener(onClickListener);

        //设置菜单
        List<Map<String,Object>> item=new ArrayList<Map<String,Object>>();
        String []name=new String[] {"我的帖子","我的交易","我的回复","账户设置","天气查询","课程表","星座运势","扫一扫","帮助反馈"};


        int []image=new int[]{R.drawable.person_file,R.drawable.person_transaction,R.drawable.person_reply,R.drawable.person_setting,R.drawable.tool_tian,R.drawable.tool_ke,R.drawable.tool_xingzuo,R.drawable.tool_sao,R.drawable.person_help};
        for (int i=0;i<9;i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name",name[i]);
            map.put("icon",image[i]);
            item.add(map);
        }

        GridView gridView=(GridView)view.findViewById(R.id.person_gridview);

        SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),item,R.layout.main_menu,new String[]{"name","icon"},new int[]{R.id.menu_text,R.id.menu_image});

        gridView.setAdapter(simpleAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!loginStatus)
                {
                    //若当前用户尚未登录
                    Intent intent=new Intent(getActivity(), Login.class);
                    startActivity(intent);
                    return;
                }

                switch (position)
                {

                    case 0:
                    {
                        Intent intent=new Intent(getActivity(), MyBBS.class);
                        startActivity(intent);
                        break;
                    }
                    case 3://账号设置
                    {
                        Intent intent=new Intent(getActivity(), Settings.class);
                        startActivity(intent);
                        break;
                    }
                    case 8://帮助与反馈
                    {
                        Intent intent=new Intent(getActivity(), Help.class);
                        startActivity(intent);
                        break;
                    }
                    case 1:{
                        Intent intent = new Intent(getActivity(), MyTrade.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("request", "trade");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    }
                    case 2:{
                        Intent intent = new Intent(getActivity(), MyResponseActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", m_id);
                        Log.e("bbs"," open");
                        bundle.putString("open",null);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    }
                    case 4:
                    {
                        Intent intent=new Intent(getActivity(), WeatherActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 7:
                    {
                        Intent intent=new Intent(getActivity(), CaptureActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 6:
                    {
                        Intent intent=new Intent(getActivity(), WelcomeActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 5:
                    {
                        Intent intent=new Intent(getActivity(), Curriculum.class);
                        startActivity(intent);
                        break;
                    }

                    default:
                        break;
                }

            }
        });
        return view;
    }
}
