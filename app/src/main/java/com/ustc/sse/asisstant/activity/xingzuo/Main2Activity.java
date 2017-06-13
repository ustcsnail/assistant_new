package com.ustc.sse.asisstant.activity.xingzuo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.ustc.sse.asisstant.R;

import com.google.gson.Gson;

public class Main2Activity extends Activity {
	TextView name, tvColor, datet, all, health, love, summary, work, Qfriend,
			money, number;
	private String urll;
	ImageView imPhoto;
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Bean1 bean1 = (Bean1) msg.obj;
				Log.i("name------------------", "" + bean1.getName());
				name.setText(bean1.getName());
				tvColor.setText(bean1.getColor());
				datet.setText(bean1.getDatetime());
				all.setText(bean1.getAll());
				health.setText(bean1.getHealth());
				love.setText(bean1.getLove());
				summary.setText(bean1.getSummary());
				work.setText(bean1.getWork());
				money.setText(bean1.getMoney());
				Qfriend.setText(bean1.getQFriend());
				String number1 = String.valueOf(bean1.getNumber());
				number.setText(number1);
				// String code = String.valueOf(bean1.getError_code());
				// error.setText(code);
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xingzuo_itme);

		
		name = (TextView) findViewById(R.id.name);
		tvColor = (TextView) findViewById(R.id.color);
		datet = (TextView) findViewById(R.id.datetime);
		all = (TextView) findViewById(R.id.all);
		health = (TextView) findViewById(R.id.health);
		love = (TextView) findViewById(R.id.love);
		money = (TextView) findViewById(R.id.money);
		number = (TextView) findViewById(R.id.number);
		Qfriend = (TextView) findViewById(R.id.Qfriend);
		summary = (TextView) findViewById(R.id.summary);
		work = (TextView) findViewById(R.id.work);
		Bitmap bitmap = null;
		String nameX ="";
		imPhoto = (ImageView) findViewById(R.id.photo);
		Intent intent = getIntent();
		if (intent!=null) {
			bitmap = intent.getParcelableExtra("bitmap");
			imPhoto.setImageBitmap(bitmap);
			nameX = getIntent().getStringExtra("name");
		}

		final String url = "http://web.juhe.cn:8080/constellation/getAll?key=49b997864e12b460f652e79a979de025&consName="
				+ nameX + "&type=today";

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				urll = HttpTool.get(url);
				Log.i("datadatadazta", "" + urll);
				Gson gson = new Gson();
				Bean1 json = gson.fromJson(urll, Bean1.class);

				Message message = new Message();
				message.obj = json;
				message.what = 1;
				handler.sendMessage(message);
			}
		}).start();
	}
}
