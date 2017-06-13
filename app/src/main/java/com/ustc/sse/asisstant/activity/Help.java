package com.ustc.sse.asisstant.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ustc.sse.asisstant.R;
import com.ustc.sse.asisstant.fragment.HelpMainFragment;

public class Help extends AppCompatActivity {

    FragmentManager manager=getFragmentManager();
    FragmentTransaction transaction=manager.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        HelpMainFragment mainFragment=new HelpMainFragment();
        transaction.replace(R.id.help_fragment_container,mainFragment);
        transaction.commit();




    }



}
