package com.zbc.soft.util;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class HttpUtil {

	
	public static HttpGet getHttpGet(String url) {
		HttpGet request = new HttpGet(url);
		return request;
	}

	
	public static HttpPost getHttpPost(String url) {
		HttpPost request = new HttpPost(url);
		return request;
	}

	
	public static HttpResponse getHttpResponse(HttpGet request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}


	public static HttpResponse getHttpResponse(HttpPost request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}


	public static String queryStringForUrlConnPost(String urlStr, List<ParamBase> parameBases) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			//conn.setUseCaches(false);
			List<NameValuePair> params = new ArrayList<>();
			if (parameBases != null && parameBases.size() > 0) {
				for (ParamBase pb : parameBases) {
                    params.add(new BasicNameValuePair(pb.getParamNam(), pb.getParamVal()));
                }
			}

			OutputStream os = conn.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			writer.write(getQueryString(params));
			writer.flush();
			writer.close();
			os.close();

			conn.connect();

			InputStream inputStream = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			Log.d("lsx", "xxxx " + sb.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getQueryString(List<NameValuePair> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (NameValuePair pair : params) {
			if (first) {
				first = false;
			} else {
				result.append("&");
			}
			result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
		}

		return result.toString();
	}

	public static String queryStringForPost(String url,List<ParamBase> ls) {
		String result = null;
		try {
		
			/*建立HTTP Post联机*/  
			HttpPost httpRequest = new HttpPost(url);  
			List <NameValuePair> params = new ArrayList <NameValuePair>();
			if(ls!=null && ls.size()>0){
				for(int i=0;i<ls.size();i++){
					ParamBase param=ls.get(i);
					params.add(new BasicNameValuePair(param.getParamNam(),param.getParamVal()));
				}
				
			}
			
			 /*发出HTTP request*/  
			httpRequest.setEntity((HttpEntity) new UrlEncodedFormEntity(params, HTTP.UTF_8));   
			/*取得HTTP response*/  
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);   

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				StringBuilder builder = new StringBuilder();  
				 BufferedReader bufferedReader2 = new BufferedReader(  
				new InputStreamReader(httpResponse.getEntity().getContent()));  
				for (String s = bufferedReader2.readLine();s!= null;s = bufferedReader2.readLine()) {  
					builder.append(s);  
				} 
				return builder.toString();
				
			}else{
				result = "网络连接失败";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络连接失败";
			
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络连接失败";
			
		}
		return result;
	}

	
	public static String queryStringForGet(String url) {
		String target = url;
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter("http.socket.timeout", 3000);
		HttpGet httpRequest = new HttpGet(target);
		HttpResponse httpResponse;
		String result="";
		try {
			httpResponse = httpclient.execute(httpRequest);	
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				StringBuilder builder = new StringBuilder();  
				 BufferedReader bufferedReader2 = new BufferedReader(  
				new InputStreamReader(httpResponse.getEntity().getContent()));  
				String str2 = "";  
				for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2.readLine()) {  
					builder.append(s);  
				} 
				return builder.toString();
			}else{
				result="网络连接失败";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result ="网络连接失败";
		} catch (IOException e) {
			e.printStackTrace();
			result ="网络连接失败";
		}catch(Exception e)
		{
			e.printStackTrace();
			result="网络连接失败";
		}
		return result;
	}
	
	/**
	 * 图片方式流处理
	 * **/
	public static String queryPicForGet(String url,File file) {
		 String target = url;
		 HttpPost request = new HttpPost(url); 
		 HttpClient httpClient = new DefaultHttpClient();
		 FileEntity entity = new FileEntity(file,"binary/octet-stream");
		HttpResponse httpResponse;
		String result="";
		try {
			request.setEntity(entity); 
            entity.setContentEncoding("binary/octet-stream"); 
            httpResponse = httpClient.execute(request); 
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				StringBuilder builder = new StringBuilder();  
				 BufferedReader bufferedReader2 = new BufferedReader(  
							new InputStreamReader(httpResponse.getEntity().getContent()));  
							for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2.readLine()) {  
								builder.append(s);  
							} 
							return builder.toString();
			}else{
				result="网络连接失败";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result ="网络连接失败";
		} catch (IOException e) {
			e.printStackTrace();
			result ="网络连接失败";
		}catch(Exception e)
		{
			e.printStackTrace();
			result="网络连接失败";
		}
		return result;
	}
}
