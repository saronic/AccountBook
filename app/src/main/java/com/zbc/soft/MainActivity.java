package com.zbc.soft;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;


public class MainActivity extends Activity implements Runnable{

	private WebView webView1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/**
		 * 判断网络是否连接正常
		 * */
		ConnectivityManager connManager  = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo ni=connManager.getActiveNetworkInfo();
		if(ni.isAvailable()){///判断网络是否可用
			
			Toast.makeText(this, "网络连接正常"+ni.getTypeName(), Toast.LENGTH_LONG).show();
			
		}else{
			Toast.makeText(MainActivity.this, "网络连接异常，请连接网络", Toast.LENGTH_LONG).show();
			 startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));  
			//startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));  
		}
		
		  State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();  
		  if(State.CONNECTED==state){  
		   Log.i("通知", "GPRS网络已连接");  
		  }  
		    
		  state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();  
		  if(State.CONNECTED==state){  
		   Log.i("通知", "WIFI网络已连接");  
		  }  
		    
//		   if(ni.getType()==ConnectivityManager.TYPE_WIFI){  
//				// 跳转到无限wifi网络设置界面  
//				startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));  
//			}else if(ni.getType()==ConnectivityManager.TYPE_MOBILE) {  
//			
//				// 跳转到无线网络设置界面  
//				startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));  
//			}  
		 

		
		webView1=(WebView)this.findViewById(R.id.webView1);
		webView1.loadUrl("http://www.baidu.com");
		webView1.getSettings().setJavaScriptEnabled(true);
//		Thread td=new Thread(MainActivity.this);
//		td.start();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("======================");
		InputStream is;
		try {
			is = new URL("http://www.baidu.com").openStream();
			BufferedReader br= new BufferedReader(new InputStreamReader(is));
			String str=null;
			StringBuffer sb=new StringBuffer();
			while((str=br.readLine())!=null){
				
				sb.append(str);
			}
			System.out.println(sb.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
