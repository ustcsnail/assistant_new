package com.ustc.sse.asisstant.activity.xingzuo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpTool {

	public static String get(String url){
		String result = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);

		try {

			HttpResponse httpResponse = httpClient.execute(get);
			if(httpResponse.getStatusLine().getStatusCode()==200){

				HttpEntity entity = httpResponse.getEntity();

				InputStream is = entity.getContent();
				ByteArrayOutputStream baso = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = -1;
				while((len=is.read(buffer))!=-1){
					baso.write(buffer, 0, len);
				}

				is.close();
				httpClient.getConnectionManager().closeExpiredConnections();
				
				result = new String(baso.toByteArray(),"UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
