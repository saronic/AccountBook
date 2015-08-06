package com.zbc.soft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zbc.soft.util.ParamBase;
import com.zbc.soft.util.ProgressThread;

public class MyPayListActivity extends Activity {
	private Button pay_edit, pay_delete, pay_qut;
	private ListView lv;
	List<Map<String, Object>> lst = null;
	MyAdapter adapter;
	String id="";
	String pay_type="";
	Map<String,Object> map= new HashMap<String,Object>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_pay_list);
		
		Intent intent = getIntent();
		pay_type=intent.getExtras().getString("pay_type");
		lv = (ListView) this.findViewById(R.id.lv);
		pay_qut = (Button) this.findViewById(R.id.pay_qut);
		pay_qut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyPayListActivity.this,
						MainAppActivity.class);
				startActivity(intent);
				finish();
			}
		});
		pay_edit = (Button) this.findViewById(R.id.pay_edit);
		pay_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent =null;
				if(pay_type.trim().equals("0")){
					intent=new Intent(MyPayListActivity.this,MyPayActivity.class);
					
				}else{
					
					intent=new Intent(MyPayListActivity.this,IncomeActivity.class);
				}
				
				intent.putExtra("id", id);
				intent.putExtra("pay_item", map.get("pay_item").toString());
				intent.putExtra("pay_num", map.get("pay_num").toString());
				intent.putExtra("pay_desc", map.get("pay_desc")+"");
				startActivity(intent);
			}
		});
		pay_delete = (Button) this.findViewById(R.id.pay_delete);
		pay_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!id.trim().equals("")){
					
					String url = "http://120.27.46.196:8082/bianMeile/pay/deletePay.action";
					List<ParamBase> ls = new ArrayList<ParamBase>();
					ParamBase pb3 = new ParamBase();
					pb3.setParamNam("id");
					pb3.setParamVal(id);
					ls.add(pb3);
					new ProgressThread(payHandlerLst, ls, url, 2).start();	
				}else{
					
					Toast.makeText(MyPayListActivity.this, "«Î—°‘Ò ˝æ›£°",
							Toast.LENGTH_LONG).show();
				}
				
			}
		});
		String url = "http://120.27.46.196:8082/bianMeile/pay/getPay.action";
		List<ParamBase> ls = new ArrayList<ParamBase>();
		ParamBase pb3 = new ParamBase();
		pb3.setParamNam("pay_type");
		if(intent.getExtras()!=null && intent.getExtras().getString("pay_type").trim().equals("1")){
			
			pb3.setParamVal("1");
			ls.add(pb3);
		}else{
			pb3.setParamVal("0");
			ls.add(pb3);
		}
		
		new ProgressThread(payHandlerLst, ls, url, 1).start();
	}

	private Handler payHandlerLst = new Handler() {
		public void handleMessage(Message msg) {
			int state = msg.getData().getInt("state");
			String backStr = msg.getData().getString("backStr");
			if (backStr != null) {
				JSONObject jo;
				switch (state) {
				case 0:
					Log.i("title", "œ‘ æ¥ÌŒÛ");

					break;

				case 1:

					if (backStr.trim().equalsIgnoreCase("Õ¯¬Á¡¨Ω” ß∞‹")) {
						Toast.makeText(MyPayListActivity.this, "Õ¯¬Á¡¨Ω” ß∞‹£°",
								Toast.LENGTH_LONG).show();
						break;
					} else {
						try {
							jo = new JSONObject(backStr);
							JSONArray jsonArray = (JSONArray) jo.get("payLst");
							lst = new ArrayList<Map<String, Object>>();
							if (jsonArray != null && jsonArray.length() > 0) {

								for (int i = 0; i < jsonArray.length(); ++i) {
									JSONObject jsb = (JSONObject) jsonArray
											.get(i);
									Map<String, Object> map = new HashMap<String, Object>();
									if (jsb.has("id")) {

										map.put("id", jsb.get("id"));
									}
									if (jsb.has("pay_item")) {

										map.put("pay_item", jsb.get("pay_item"));
									}
									if (jsb.has("pay_num")) {

										map.put("pay_num", jsb.get("pay_num"));
									}
									if (jsb.has("pay_desc")) {

										map.put("pay_desc", jsb.get("pay_desc"));
									}

									lst.add(map);
								}

								adapter = new MyAdapter(MyPayListActivity.this);

								lv.setAdapter(adapter);
								lv.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										// TODO Auto-generated method stub
										adapter.setSelectItem(arg2);
										adapter.notifyDataSetInvalidated();
										adapter.notifyDataSetChanged();
										id=lst.get(arg2).get("id").toString();
										map=lst.get(arg2);
									}
									
								});

							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

						}
					}
					break;
				case 2:
					if (backStr.trim().equalsIgnoreCase("Õ¯¬Á¡¨Ω” ß∞‹")) {
						Toast.makeText(MyPayListActivity.this, "Õ¯¬Á¡¨Ω” ß∞‹£°",
								Toast.LENGTH_LONG).show();
						break;
					} else {
						Toast.makeText(MyPayListActivity.this, "…æ≥˝≥…π¶£°",
								Toast.LENGTH_LONG).show();
						id="";
						String url = "http://120.27.46.196:8082/bianMeile/pay/getPay.action";
						List<ParamBase> ls = new ArrayList<ParamBase>();
						ParamBase pb3 = new ParamBase();
						pb3.setParamNam("pay_type");
						if(pay_type.trim().equals("0")){
							pb3.setParamVal("0");
						}else{
							
							pb3.setParamVal("1");
						}
						ls.add(pb3);
						new ProgressThread(payHandlerLst, ls, url, 1).start();
					}
					
				default:
				}
			}
		}
	};
	/**
	 *   ≈‰∆˜ƒ£–Õ
	 * */
	 public final class ViewHolder{
		 public TextView txt1;
		 public TextView txt2;
		 public TextView txt3;
		 public TextView txt4;
		 public TextView txt5;
	 }
	 /**
	  *   ≈‰∆˜
	  * */
	 public class MyAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		private int selectItem=-1;
		public MyAdapter(Context context){
			this.mInflater = LayoutInflater.from(context);
		}
		public void setSelectItem(int selectItem) {
			// TODO Auto-generated method stub
			this.selectItem = selectItem;  
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return lst.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder=new ViewHolder();
				convertView = mInflater.inflate(R.layout.simpletst, null);
				holder.txt1 = (TextView)convertView.findViewById(R.id.txt1);
				holder.txt2 = (TextView)convertView.findViewById(R.id.txt2);
				holder.txt3 = (TextView)convertView.findViewById(R.id.txt3);
				holder.txt4 = (TextView)convertView.findViewById(R.id.txt4);
				holder.txt5 = (TextView)convertView.findViewById(R.id.txt5);
				convertView.setTag(holder);
				
			}else {  
				holder = (ViewHolder) convertView.getTag();  
			}  
			holder.txt1.setText(""+(position+1));
			if(lst.get(position).get("id")!=null){
			
				holder.txt5.setText(lst.get(position).get("id").toString());
			}else{
				
				holder.txt5.setText("");
			}
			if(lst.get(position).get("pay_item")!=null){
				holder.txt2.setText((String)lst.get(position).get("pay_item"));
			}else{
				
				holder.txt2.setText("");
			}
			if(lst.get(position).get("pay_num")!=null){
				
				holder.txt3.setText(lst.get(position).get("pay_num").toString());
			}else{
				
				holder.txt3.setText("");
			}

			if(lst.get(position).get("pay_desc")!=null){
				
				holder.txt4.setText((String)lst.get(position).get("pay_desc"));
			}else{
				
				holder.txt4.setText("");
			}
			
			if (this.selectItem == position) {  
				convertView.setBackgroundColor(Color.argb(255, 255, 111, 222));//—’…´…Ë÷√
			}else{
				
				convertView.setBackgroundColor(Color.argb(250, 255, 255, 255)); //—’…´…Ë÷√
			}

			return convertView;
		}
		 
	 }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_pay_list, menu);
		return true;
	}

}
