package com.zbc.soft;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HttpURLConnectActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_http_urlconnect);
		Button bt=(Button)this.findViewById(R.id.bt);
		final TextView textView1=(TextView)this.findViewById(R.id.textView1);
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Thread td = new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						String urlStr = "http://120.27.46.196:8082/bianMeile/user/getAllUser.action?mobile=13053549598";
//						try {
//							URL url=new URL(urlStr);
//							HttpURLConnection conn;
//							try {
//								conn = (HttpURLConnection) url.openConnection();
//								 //连接服务器
//			                     conn.connect();
//			                     /**读入服务器数据的过程**/
//			                     //得到输入流
//			                     InputStream is=conn.getInputStream();
//			                     //创建包装流
//			                     BufferedReader br=new BufferedReader(new InputStreamReader(is));
//			                     //定义String类型用于储存单行数据
//			                     String line=null;
//			                     //创建StringBuffer对象用于存储所有数据
//			                     StringBuffer sb=new StringBuffer();
//			                     while((line=br.readLine())!=null){
//			                         sb.append(line);
//			                     }
//			                    
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//		                    
//						} catch (MalformedURLException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
						
						try {
							URL url=new URL(urlStr);
							HttpURLConnection conn;
		                    try {
		                        //打开服务器
		                        conn=(HttpURLConnection) url.openConnection();
		                        //设置输入输出流
		                        conn.setDoOutput(true);///向连接中写入数据
		                        conn.setDoInput(true);////向连接中读取数据
		                        //设置请求的方法为Post
		                        conn.setRequestMethod("POST");
		                        //Post方式不能缓存数据，则需要手动设置使用缓存的值为false
		                        conn.setUseCaches(false);
		                        //连接数据库
		                        conn.connect();
		                        /**写入参数**/
		                        OutputStream os=conn.getOutputStream();
		                        //封装写给服务器的数据（这里是要传递的参数）
		                        DataOutputStream dos=new DataOutputStream(os);
		                        //写方法：name是key值不能变，编码方式使用UTF-8可以用中文
	//	                        dos.writeBytes("name="+URLEncoder.encode("你好", "UTF-8"));
		                        dos.writeBytes("mobile=13053549598");
		                        //关闭外包装流
		                        dos.close();
		                        /**读服务器数据**/
		                        InputStream is=conn.getInputStream();
		                        BufferedReader br=new BufferedReader(new InputStreamReader(is));
		                        String line=null;
		                        StringBuffer sb=new StringBuffer();
		                        while((line=br.readLine())!=null){
		                            sb.append(line);
		                        }
		                    } catch (IOException e) {
		                        e.printStackTrace();
		                    }
		                } catch (MalformedURLException e) {
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
		getMenuInflater().inflate(R.menu.http_urlconnect, menu);
		return true;
	}

}
