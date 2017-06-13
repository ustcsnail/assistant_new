package com.ustc.sse.asisstant.fragment;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ustc.sse.asisstant.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpMainFragment extends Fragment {


    private ListView listView1=null;
    private ListView listView2=null;


    public HelpMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_help_main, container, false);

        listView1=(ListView)view.findViewById(R.id.help_list1);

        List<HashMap<String,Object>> list1=prepareHelpData();


        SimpleAdapter adapter1=new SimpleAdapter(getActivity(),list1,R.layout.help_list,new String[]{"item"},new int[]{R.id.help_item});


        listView1.setAdapter(adapter1);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 1:
                    {
                        FragmentManager manager=getActivity().getFragmentManager();
                        FragmentTransaction transaction=manager.beginTransaction();
                        transaction.hide(HelpMainFragment.this);
                        transaction.addToBackStack(null);
                        transaction.add(R.id.help_fragment_container,new HelpVersionFragment());
                        transaction.commit();
                        break;

                    }
                    case 2:
                    {
                        FragmentManager manager=getActivity().getFragmentManager();
                        FragmentTransaction transaction=manager.beginTransaction();
                        transaction.hide(HelpMainFragment.this);
                        transaction.addToBackStack(null);
                        transaction.add(R.id.help_fragment_container,new HelpAboutFragment());
                        transaction.commit();
                        break;
                    }
                }
            }
        });

        return view;
    }

    private List<HashMap<String,Object>> prepareHelpData()
    {
        List<HashMap<String,Object>> list=new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("item","常见问题");
        list.add(map);

        map=new HashMap<String, Object>();
        map.put("item","版权信息");
        list.add(map);

        map=new HashMap<String, Object>();
        map.put("item","关于");
        list.add(map);
        return list;
    }


}
