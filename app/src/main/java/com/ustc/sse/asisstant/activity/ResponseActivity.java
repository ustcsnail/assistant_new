package com.ustc.sse.asisstant.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ustc.sse.asisstant.R;

public class ResponseActivity extends AppCompatActivity {
    private ImageView backBtn,refreshBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_thread_page);
        backBtn=(ImageView) findViewById(R.id.newThreadBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        refreshBtn=(ImageView)findViewById(R.id.imageButton1);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ResponseActivity.this,"回复成功", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });
    }

}
