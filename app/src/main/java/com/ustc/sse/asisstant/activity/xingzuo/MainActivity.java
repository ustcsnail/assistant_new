package com.ustc.sse.asisstant.activity.xingzuo;

import com.kyview.InitConfiguration;
import com.kyview.InitConfiguration.BannerSwitcher;
import com.kyview.InitConfiguration.InstlSwitcher;
import com.kyview.InitConfiguration.RunMode;
import com.kyview.InitConfiguration.UpdateMode;
import com.kyview.interfaces.AdViewBannerListener;
import com.kyview.manager.AdViewBannerManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ustc.sse.asisstant.R;

public class MainActivity extends Activity implements OnClickListener,
		AdViewBannerListener {
	LinearLayout baiy, jinn, shuangz, juxie, shizi, chunv, tianp, tianxie,
			sheshou, mojie, shuip, shuangyu;

	String[] key = { "SDK20161302010953x7suk0pis01hrtz" };
	LinearLayout adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xingzuo_main2);

		baiy = (LinearLayout) findViewById(R.id.baiyz);
		jinn = (LinearLayout) findViewById(R.id.jinnz);
		shuangz = (LinearLayout) findViewById(R.id.shungzz);
		juxie = (LinearLayout) findViewById(R.id.juxz);
		shizi = (LinearLayout) findViewById(R.id.shizz);
		chunv = (LinearLayout) findViewById(R.id.chunz);
		tianp = (LinearLayout) findViewById(R.id.tianpz);
		tianxie = (LinearLayout) findViewById(R.id.tianxz);
		sheshou = (LinearLayout) findViewById(R.id.shesz);
		mojie = (LinearLayout) findViewById(R.id.mojz);
		shuip = (LinearLayout) findViewById(R.id.shuipz);
		shuangyu = (LinearLayout) findViewById(R.id.shuangyz);
		//adView = (LinearLayout) findViewById(R.id.adView);
		baiy.setOnClickListener(this);
		jinn.setOnClickListener(this);
		shuangz.setOnClickListener(this);
		juxie.setOnClickListener(this);
		shizi.setOnClickListener(this);
		chunv.setOnClickListener(this);
		tianp.setOnClickListener(this);
		tianxie.setOnClickListener(this);
		sheshou.setOnClickListener(this);
		mojie.setOnClickListener(this);
		shuip.setOnClickListener(this);
		shuangyu.setOnClickListener(this);
		
/*
		InitConfiguration initConfig = new InitConfiguration.Builder(this)
				.setUpdateMode(UpdateMode.EVERYTIME)
				.setBannerCloseble(BannerSwitcher.CANCLOSED)
				.setRunMode(RunMode.TEST).build();
		AdViewBannerManager.getInstance(this).init(initConfig, key);
		View view = AdViewBannerManager.getInstance(this).getAdViewLayout(this,
				"SDK20161302010953x7suk0pis01hrtz");
		if (null != view) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				parent.removeAllViews();
			}
		}
		AdViewBannerManager.getInstance(this).requestAd(this,
				"SDK20161302010953x7suk0pis01hrtz");
		adView.addView(view);
		adView.invalidate();*/
		
	}

	@Override
	public void onClick(View v) {
		Bitmap bitmap = null;
		switch (v.getId()) {
		case R.id.baiyz:
			Intent intent = new Intent();
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.baiyang);
			intent.putExtra("bitmap", bitmap);
			intent.putExtra("name", "白羊座");
			intent.setClass(MainActivity.this, Main2Activity.class);
			startActivity(intent);
			break;
		case R.id.jinnz:
			Intent intent2 = new Intent();
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.jinniu);
			intent2.putExtra("bitmap", bitmap);
			intent2.putExtra("name", "金牛座");
			intent2.setClass(MainActivity.this, Main2Activity.class);
			startActivity(intent2);
			break;
		case R.id.shungzz:
			Intent intent3 = new Intent();
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.shuangzi);
			intent3.putExtra("bitmap", bitmap);
			intent3.putExtra("name", "双子座");
			intent3.setClass(MainActivity.this, Main2Activity.class);
			startActivity(intent3);

			break;
		case R.id.juxz:
			Intent intent4 = new Intent();
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.pangxie);
			intent4.putExtra("bitmap", bitmap);
			intent4.putExtra("name", "巨蟹座");
			intent4.setClass(MainActivity.this, Main2Activity.class);
			startActivity(intent4);
			break;
		case R.id.shizz:
			Intent intent5 = new Intent();
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.shizi);
			intent5.putExtra("bitmap", bitmap);
			intent5.putExtra("name", "狮子座");
			intent5.setClass(MainActivity.this, Main2Activity.class);
			startActivity(intent5);
			break;
		case R.id.chunz:
			Intent intent6 = new Intent();
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.chunv);
			intent6.putExtra("bitmap", bitmap);
			intent6.putExtra("name", "处女座");
			intent6.setClass(MainActivity.this, Main2Activity.class);
			startActivity(intent6);
			break;
		case R.id.tianpz:
			Intent intent7 = new Intent();
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.tiancheng);
			intent7.putExtra("bitmap", bitmap);
			intent7.putExtra("name", "天秤座");
			intent7.setClass(MainActivity.this, Main2Activity.class);
			startActivity(intent7);
			break;
		case R.id.tianxz:
			Intent intent8 = new Intent();
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.tianxie);
			intent8.putExtra("bitmap", bitmap);
			intent8.putExtra("name", "天蝎座");
			intent8.setClass(MainActivity.this, Main2Activity.class);
			startActivity(intent8);
			break;
		case R.id.shesz:
			Intent intent9 = new Intent();
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.sheshou);
			intent9.putExtra("bitmap", bitmap);
			intent9.putExtra("name", "射手座");
			intent9.setClass(MainActivity.this, Main2Activity.class);
			startActivity(intent9);
			break;
		case R.id.mojz:
			Intent intent11 = new Intent();
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.mojie);
			intent11.putExtra("bitmap", bitmap);
			intent11.putExtra("name", "摩羯座");
			intent11.setClass(MainActivity.this, Main2Activity.class);
			startActivity(intent11);
			break;
		case R.id.shuipz:
			Intent intent12 = new Intent();
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.shuiping);
			intent12.putExtra("bitmap", bitmap);
			intent12.putExtra("name", "水瓶座");
			intent12.setClass(MainActivity.this, Main2Activity.class);
			startActivity(intent12);
			break;
		case R.id.shuangyz:
			Intent intent13 = new Intent();
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.shuangyu);
			intent13.putExtra("bitmap", bitmap);
			intent13.putExtra("name", "双鱼座");
			intent13.setClass(MainActivity.this, Main2Activity.class);
			startActivity(intent13);
			break;
		}
	}

	@Override
	public void onAdClick(String arg0) {
		// TODO Auto-generated method stub
		Log.i("AdBannerActivity", "onAdClick");
	}

	@Override
	public void onAdClose(String arg0) {
		// TODO Auto-generated method stub
		Log.i("AdBannerActivity", "onAdClose");
		if (null != adView)
			adView.removeView(adView.findViewWithTag(arg0));
	}

	@Override
	public void onAdDisplay(String arg0) {
		// TODO Auto-generated method stub
		Log.i("AdBannerActivity", "onDisplayAd");
	}

	@Override
	public void onAdFailed(String arg0) {
		// TODO Auto-generated method stub
		Log.i("AdBannerActivity", "onAdFailed");
	}

	@Override
	public void onAdReady(String arg0) {
		// TODO Auto-generated method stub
		Log.i("AdBannerActivity", "onAdReady");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (null != adView)
			adView.removeAllViews();
	}
}
