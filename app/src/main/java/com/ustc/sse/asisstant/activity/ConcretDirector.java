package com.ustc.sse.asisstant.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ustc.sse.asisstant.R;

public class ConcretDirector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concret_director);

        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String content=intent.getStringExtra("content");

        TextView title_text=(TextView)findViewById(R.id.director_title);
        title_text.setText(title);
        TextView content_text=(TextView)findViewById(R.id.director_content);
        content_text.setText(content);
    }
}
