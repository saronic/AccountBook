package com.zbc.soft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HttpClientActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_http_client);
		Button bt=(Button)this.findViewById(R.id.clientBt);
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Thread td=new Thread(new Runnable(){

					@Override
					public void run() {
						/**
						 * 1.创建httpclient
						 * 2.创建httpget
						 * 3.通过httpclient的execute发送请求
						 * 4.通过httpResponse的.getEntity()获取相应内容对象
						 * 5.获取相应内容
						 * */
						HttpClient cliet=new DefaultHttpClient();
//						HttpGet get=new HttpGet("http://120.27.46.196:8082/bianMeile/user/getAllUser.action?mobile=13053549598");
//				       
//				        try {
//				            HttpResponse response=cliet.execute(get);
//				            HttpEntity entity=response.getEntity();
//				            InputStream is=entity.getContent();
//				            BufferedReader br=new BufferedReader(new InputStreamReader(is));
//				            String line=null;
//				            StringBuffer sb=new StringBuffer();
//				            while((line=br.readLine())!=null){
//				                sb.append(line);
//				            }
//				            System.out.println(sb.toString());
//				        } catch (ClientProtocolException e) {
//				            e.printStackTrace();
//				        } catch (IOException e) {
//				            e.printStackTrace();
//				        }
						
						HttpPost post=new HttpPost("http://120.27.46.196:8082/bianMeile/user/getAllUser.action");
					        //创建默认的客户端对象
						
					        //用list封装要向服务器端发送的参数
					        List<BasicNameValuePair> pairs=new ArrayList<BasicNameValuePair>();
					        pairs.add(new BasicNameValuePair("mobile", "13053549598"));
					        try {
					            //用UrlEncodedFormEntity来封装List对象
					        	UrlEncodedFormEntity urlEntity=new UrlEncodedFormEntity(pairs);
					            //设置使用的Entity
					            post.setEntity(urlEntity);
					            try {
					                //客户端开始向指定的网址发送请求
					                HttpResponse response=cliet.execute(post);
					                
					                if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
					                	//获得请求的Entity
					                	 HttpEntity entity=response.getEntity();
							                InputStream is=entity.getContent();
							                //下面是读取数据的过程
							                BufferedReader br=new BufferedReader(new InputStreamReader(is));
							                String line=null;
							                StringBuffer sb=new StringBuffer();
							                while((line=br.readLine())!=null){
							                    sb.append(line);
							                }
							                System.out.println(sb.toString());
					                	
					                }
					               
					            } catch (ClientProtocolException e) {
					                e.printStackTrace();
					            } catch (IOException e) {
					                e.printStackTrace();
					            }
					        } catch (UnsupportedEncodingException e) {
					            e.printStackTrace();
					        }
						
					}
					
					
				});
				td.start();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.http_client, menu);
		return true;
	}

}
