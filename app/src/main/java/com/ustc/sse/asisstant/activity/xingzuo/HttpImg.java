package com.ustc.sse.asisstant.activity.xingzuo;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.widget.ImageView;
import com.ustc.sse.asisstant.R;
public class HttpImg {
	public static void loadimage(Context context, ImageView iV, String url) {
		if (url != null && !url.equals("") && !url.equals("null")) {
			Picasso.with(context).load(url).into(iV);
		} else {
			iV.setBackgroundResource(R.drawable.ic_launcher);
		}
	}
}
