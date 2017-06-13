package com.ustc.sse.asisstant.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ustc.sse.asisstant.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherActivity extends Activity {
	
	//百度天气
	String ak="b0nXa4zOeUDEsmGjwl3dibgo";//APIconsolekey

	//默认城市
	String editcity="苏州";
	//输入城市
	String Qcity;
	
//handler返回数据设置界面
String city;//城市
String date;//日期
String weather;//天气
String wind;//风向
String temperature;//温度
ArrayList<String>  alldate = new ArrayList<String> ();//全部日期数据
ArrayList<String>  allweather = new ArrayList<String> ();//全部天气数据
ArrayList<String>  alltemperature = new ArrayList<String> ();//全部温度数据
TextView cityshow;//显示城市
TextView windshow;//显示风向
TextView dateshow1;//显示日期1
TextView dateshow2;//显示日期2
TextView dateshow3;//显示日期3
TextView dateshow4;//显示日期4
TextView weathershow1;//显示天气1
TextView weathershow2;//显示天气2
TextView weathershow3;//显示天气3
TextView weathershow4;//显示天气4
TextView temperatureshow1;//显示温度1
TextView temperatureshow2;//显示温度2
TextView temperatureshow3;//显示温度3
TextView temperatureshow4;//显示温度4
TextView shishi;//实时温度反馈
ImageView  imageView1;//温度图1
ImageView  imageView2;//温度图2
ImageView  imageView3;//温度图3
ImageView  imageView4;//温度图
ImageView  tianqi;//天气图
ImageView  imgtq1;//小天气图
ImageView  imgtq2;//小天气图
ImageView  imgtq3;//小天气图
ImageView  imgtq4;//小天气图

EditText EditText;//文本输入框
Button btn_Query;//查询按钮
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg){
			if(msg.what==1){
				//btn_Query.setText(editcity);
				//城市
			cityshow.setText(city);//显示到城市文本框
		//日期
			dateshow1.setText(alldate.get(0));
			dateshow2.setText(alldate.get(1));
			dateshow3.setText(alldate.get(2));
			dateshow4.setText(alldate.get(3));
		//天气
			weathershow1.setText(allweather.get(0));
			weathershow2.setText(allweather.get(1));
			weathershow3.setText(allweather.get(2));
			weathershow4.setText(allweather.get(3));
			//右上角天气显示
			switch (allweather.get(0)) {
			case "晴":
				tianqi.setBackgroundResource(R.drawable.weather_alltext_0);
				imgtq1.setBackgroundResource(R.drawable.weather_small1);
				break;
			case "阴转晴":
				tianqi.setBackgroundResource(R.drawable.weather_alltext_0);
				imgtq1.setBackgroundResource(R.drawable.weather_small1);
				break;
			case "阴转多云":
				tianqi.setBackgroundResource(R.drawable.weather_alltext_1);
				imgtq1.setBackgroundResource(R.drawable.weather_small2);
				break;
			case "多云转阴":
				tianqi.setBackgroundResource(R.drawable.weather_alltext_2);
				imgtq1.setBackgroundResource(R.drawable.weather_small2);
				break;
			case "多云":
				tianqi.setBackgroundResource(R.drawable.weather_alltext_1);
				imgtq1.setBackgroundResource(R.drawable.weather_small2);
				break;
			case "阴":
				tianqi.setBackgroundResource(R.drawable.weather_alltext_2);
				imgtq1.setBackgroundResource(R.drawable.weather_small7);
				break;
			case "阵雨":
				tianqi.setBackgroundResource(R.drawable.weather_alltext_3);
				imgtq1.setBackgroundResource(R.drawable.weather_small5);
				break;
			case "雷阵雨转中雨":
				tianqi.setBackgroundResource(R.drawable.weather_alltext_4);
				imgtq1.setBackgroundResource(R.drawable.weather_small5);
				break;
			//依此类推 懒得加了。。。
			default:
				break;
			}
			switch (allweather.get(1)) {
			case "晴":
				imgtq2.setBackgroundResource(R.drawable.weather_small1);
				break;
			case "阴转晴":
				imgtq2.setBackgroundResource(R.drawable.weather_small1);
				break;
			case "阴转多云":
				imgtq2.setBackgroundResource(R.drawable.weather_small2);
				break;
			case "多云":
				imgtq2.setBackgroundResource(R.drawable.weather_small2);
				break;
			case "阴":
				imgtq2.setBackgroundResource(R.drawable.weather_small7);
				break;
			case "阵雨":
				imgtq2.setBackgroundResource(R.drawable.weather_small5);
				break;
			case "雷阵雨转中雨":
				imgtq2.setBackgroundResource(R.drawable.weather_small5);
				break;
			//依此类推 懒得加了。。。
			default:
				break;
			}
			switch (allweather.get(2)) {
			case "晴":
				imgtq3.setBackgroundResource(R.drawable.weather_small1);
				break;
			case "阴转晴":
				imgtq3.setBackgroundResource(R.drawable.weather_small1);
				break;
			case "阴转多云":
				imgtq3.setBackgroundResource(R.drawable.weather_small2);
				break;
			case "多云":
				imgtq3.setBackgroundResource(R.drawable.weather_small2);
				break;
			case "阴":
				imgtq3.setBackgroundResource(R.drawable.weather_small7);
				break;
			case "阵雨":
				imgtq3.setBackgroundResource(R.drawable.weather_small5);
				break;
			case "雷阵雨转中雨":
				imgtq3.setBackgroundResource(R.drawable.weather_small5);
				break;
			//依此类推 懒得加了。。。
			default:
				break;
			}
			switch (allweather.get(3)) {
			case "晴":
				imgtq4.setBackgroundResource(R.drawable.weather_small1);
				break;
			case "阴转晴":
				imgtq4.setBackgroundResource(R.drawable.weather_small1);
				break;
			case "阴转多云":
				imgtq4.setBackgroundResource(R.drawable.weather_small2);
				break;
			case "多云":
				imgtq4.setBackgroundResource(R.drawable.weather_small2);
				break;
			case "阴":
				imgtq4.setBackgroundResource(R.drawable.weather_small7);
				break;
			case "阵雨":
				imgtq4.setBackgroundResource(R.drawable.weather_small5);
				break;
			case "雷阵雨转中雨":
				imgtq4.setBackgroundResource(R.drawable.weather_small5);
				break;
			//依此类推 懒得加了。。。
			default:
				break;
			}
		//风向
			windshow.setText(wind);//显示到风向文本框
		//温度
			temperatureshow1.setText(alltemperature.get(0));
			temperatureshow2.setText(alltemperature.get(1));
			temperatureshow3.setText(alltemperature.get(2));
			temperatureshow4.setText(alltemperature.get(3));
			//提取今天温度
			String tag0=alldate.get(0);
			//提取第一个字符识别
			char tag1=tag0.charAt(14);
			String test1=String.valueOf(tag1);
			//提取第二个字符识别
			char tag2=tag0.charAt(15);
			String test2=String.valueOf(tag2);
			//提取第三个字符识别
			char tag3=tag0.charAt(16);
			String test3=String.valueOf(tag3);
			//提取字符串长度判断是否返回实时温度
			int a;
			a=tag0.length();
			//如果小于4说明只有三个字符，隐藏温度负号
			
			if(a!=18){
				imageView1.setVisibility(View.GONE);
				imageView2.setVisibility(View.GONE);
				imageView3.setVisibility(View.GONE);
				imageView4.setVisibility(View.GONE);
				//隐藏温度图1234显示提示<暂无实时温度>
				shishi.setVisibility(View.VISIBLE);
			}
			else{
				Log.d("识别反馈",test1+"."+test2+"."+test3+"."+a);
				if(test1!="-") {
					imageView1.setVisibility(View.GONE);
					switch (test1) {
					case "0":
						imageView2.setBackgroundResource(R.drawable.weather_degree0);
						break;
					case "1":
						imageView2.setBackgroundResource(R.drawable.weather_degree1);
						break;
					case "2":
						imageView2.setBackgroundResource(R.drawable.weather_degree2);
						break;
					case "3":
						imageView2.setBackgroundResource(R.drawable.weather_degree3);
						break;
					case "4":
						imageView2.setBackgroundResource(R.drawable.weather_degree4);
						break;
					default://imageView2.setVisibility(View.GONE);//为零则隐藏
						break;
					}
					switch (test2) {
					case "0":
						imageView3.setBackgroundResource(R.drawable.weather_degree0);
						break;
					case "1":
						imageView3.setBackgroundResource(R.drawable.weather_degree1);
						break;
					case "2":
						imageView3.setBackgroundResource(R.drawable.weather_degree2);
						break;
					case "3":
						imageView3.setBackgroundResource(R.drawable.weather_degree3);
						break;
					case "4":
						imageView3.setBackgroundResource(R.drawable.weather_degree4);
						break;
					case "5":
						imageView3.setBackgroundResource(R.drawable.weather_degree5);
						break;
					case "6":
						imageView3.setBackgroundResource(R.drawable.weather_degree6);
						break;
					case "7":
						imageView3.setBackgroundResource(R.drawable.weather_degree7);
						break;
					case "8":
						imageView3.setBackgroundResource(R.drawable.weather_degree8);
						break;
					case "9":
						imageView3.setBackgroundResource(R.drawable.weather_degree9);
						break;	
					default://imageView3.setVisibility(View.GONE);//为零则隐藏
						break;
					}
				}
				//否则无需隐藏温度负号
				else{
					switch (test2) {
					case "0":
						imageView2.setBackgroundResource(R.drawable.weather_degree0);
						break;
					case "1":
						imageView2.setBackgroundResource(R.drawable.weather_degree1);
						break;
					case "2":
						imageView2.setBackgroundResource(R.drawable.weather_degree2);
						break;
					case "3":
						imageView2.setBackgroundResource(R.drawable.weather_degree3);
						break;
					default:imageView2.setVisibility(View.GONE);//为零则隐藏
						break;
					}
					switch (test3) {
					case "0":
						imageView3.setBackgroundResource(R.drawable.weather_degree0);
						break;
					case "1":
						imageView3.setBackgroundResource(R.drawable.weather_degree1);
						break;
					case "2":
						imageView3.setBackgroundResource(R.drawable.weather_degree2);
						break;
					case "3":
						imageView3.setBackgroundResource(R.drawable.weather_degree3);
						break;
					default:imageView3.setVisibility(View.GONE);//为零则隐藏
						break;
					}
				}
			}
		}
		}
	};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//关联布局
		setContentView(R.layout.activity_weather);
		cityshow = (TextView) findViewById(R.id.city);
		windshow = (TextView) findViewById(R.id.wind);
		dateshow1= (TextView) findViewById(R.id.text_day);
		dateshow2= (TextView) findViewById(R.id.textrq2);
		dateshow3= (TextView) findViewById(R.id.textrq3);
		dateshow4= (TextView) findViewById(R.id.textrq4);
		weathershow1= (TextView) findViewById(R.id.texttq1);
		weathershow2= (TextView) findViewById(R.id.texttq2);
		weathershow3= (TextView) findViewById(R.id.texttq3);
		weathershow4= (TextView) findViewById(R.id.texttq4);
		temperatureshow1= (TextView) findViewById(R.id.textqw1);
		temperatureshow2= (TextView) findViewById(R.id.textqw2);
		temperatureshow3= (TextView) findViewById(R.id.textqw3);
		temperatureshow4= (TextView) findViewById(R.id.textqw4);
		imageView1= (ImageView) findViewById(R.id.imageView1);
		imageView2= (ImageView) findViewById(R.id.imageView2);
		imageView3= (ImageView) findViewById(R.id.imageView3);
		imageView4= (ImageView) findViewById(R.id.imageView4);
		tianqi= (ImageView) findViewById(R.id.tianqi);
		imgtq1= (ImageView) findViewById(R.id.imgtq1);
		imgtq2= (ImageView) findViewById(R.id.imgtq2);
		imgtq3= (ImageView) findViewById(R.id.imgtq3);
		imgtq4= (ImageView) findViewById(R.id.imgtq4);
		shishi= (TextView) findViewById(R.id.shishi);
		EditText = (EditText) findViewById(R.id.EditText);
		btn_Query=(Button) findViewById(R.id.btn_Query);
		btn_Query.setOnClickListener(Query);
		//向服务器发送请求
		new Thread(runNow).start();

		//Runnable接口重用线程
		Runnable run = new Runnable(){
			public void run(){
				}
		};
		Thread Q = new Thread(run);
		Q.start();		
	}
	
	OnClickListener Query=new OnClickListener() {
		
		public void onClick(View arg0) {
			//获取查询的城市名 
			editcity=EditText.getText().toString();
			//初始化数据
			alldate.clear();
			allweather.clear();
			alltemperature.clear();
			Log.d("查询的城市名", editcity.toString());
			
			//根据获得的Qcity向服务器发送请求
			//Qcity不为空值执行
			if(!editcity.equals("")){
				new Thread(runNow).start(); 
				
			}
		}
	};
	
	//新建一个新的线程:发送并解析当前城市的温度等信息
	Runnable runNow =new Runnable(){
		public void run() {
			
			URL url;
			HttpURLConnection connection;
			Reader read;
			BufferedReader bufferReader;
			try {
				//城市转码
				String urlcity = URLEncoder.encode(editcity, "UTF-8"); 
				
				//请求指令
				String path="http://api.map.baidu.com/telematics/v2/weather?location="+urlcity+"&output=xml&ak="+ak+"";
				url=new URL(path);
				
				//创建和服务端的连接
				connection =(HttpURLConnection) url.openConnection();
				
				//获取读取的方式
				read=new InputStreamReader(connection.getInputStream());
				bufferReader=new BufferedReader(read);
				
				//获取服务器返回的字符串
				String str;//读取每一行数据
				StringBuffer buffer=new StringBuffer();//接受全部数据
				while((str=bufferReader.readLine())!=null){
					buffer.append(str + "\n");
				}
				
				//关闭连接
				read.close();  
				connection.disconnect();
				
				//测试	
				Log.d("转码是否成功",urlcity.toString());
				Log.d("发出去的请求",path.toString());
				Log.d("读取来的数据",buffer.toString());

				//解析
				xml(buffer.toString());//调用
				Log.d("全部日期数据", alldate.toString());
				Log.d("全部天气数据", allweather.toString());
				Log.d("全部温度数据", alltemperature.toString());
				
				//异常处理	
				} catch (MalformedURLException e) {				
				e.printStackTrace();
				} catch (IOException e) {
				e.printStackTrace();
				}
			}
		
		//解析
		public void xml(String buffer){
			String code=buffer.toString();
			String wordkey="<status>success</status>";
			Pattern p=Pattern.compile(wordkey);
			Matcher m=p.matcher(code);
			if(m.find()){
				
				wordkey="<currentCity>(.*)</currentCity>";//匹配城市
				p=Pattern.compile(wordkey);
				m=p.matcher(code);
				m.find();
				city=m.group(1);//获取城市
				//handler.sendEmptyMessage(0);//返回数据
				Log.d("获取到的城市",city.toString());					
				
				wordkey="<date>(.*)</date>";//匹配日期
				p=Pattern.compile(wordkey);
				m=p.matcher(code);
				boolean result1 = m.find();
				while(result1) { 
				date=m.group(1);//获取日期
				alldate.add(date);//接受全部数据
				//handler.sendEmptyMessage(0);//返回数据
				Log.d("获取到的日期",date.toString());result1 = m.find();}
				
				wordkey="<weather>(.*)</weather>";//匹配天气
				p=Pattern.compile(wordkey);
				m=p.matcher(code);
				boolean result2 = m.find();
				while(result2) { 
				weather=m.group(1);//获取天气
				allweather.add(weather);//接受全部数据
				//handler.sendEmptyMessage(0);//返回数据
				Log.d("获取到的天气",weather.toString());result2 = m.find();}
				
				wordkey="<wind>(.*)</wind>";//匹配风向
				p=Pattern.compile(wordkey);
				m=p.matcher(code);
				m.find();
				//boolean result3 = m.find();
				//while(result3) { 
				wind=m.group(1);//获取风向
				//handler.sendEmptyMessage(0);//返回数据
				Log.d("获取到的风向",wind.toString());//result3 = m.find();}
				
				wordkey="<temperature>(.*)</temperature>";//匹配温度
				p=Pattern.compile(wordkey);
				m=p.matcher(code);
				boolean result4 = m.find();
				while(result4) { 
				temperature=m.group(1);//获取温度
				alltemperature.add(temperature);//接受全部数据
				handler.sendEmptyMessage(1);//返回数据
				Log.d("获取到的温度",temperature.toString());result4 = m.find();}	
			}
		}	
	};
}