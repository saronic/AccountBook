package com.zbc.soft;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zbc.soft.util.Login_info;
import com.zbc.soft.util.ProgressThread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zbc.soft.util.*;
public class MyPayActivity extends Activity {

	private EditText payName,payNum,payDesc;
	private Button pay_ok,pay_cancel;
	String id="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_pay);
		Intent intent = getIntent();
		
		payName=(EditText)this.findViewById(R.id.payName);
		if(intent.getStringExtra("pay_item")!=null){
			
			payName.setText(intent.getStringExtra("pay_item"));
		}
		payNum=(EditText)this.findViewById(R.id.payNum);
		if(intent.getStringExtra("pay_num")!=null){
			
			payNum.setText(intent.getStringExtra("pay_num"));
		}
		payDesc=(EditText)this.findViewById(R.id.payDesc);
		if(intent.getStringExtra("pay_desc")!=null){
			
			payDesc.setText(intent.getStringExtra("pay_desc"));
		}
		if(intent.getStringExtra("id")!=null){
			
			id=intent.getStringExtra("id");
		}
		pay_ok=(Button)this.findViewById(R.id.pay_ok);
		pay_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String url="http://120.27.46.196:8082/bianMeile/pay/addPay.action";
				
				List<ParamBase> ls=new ArrayList<ParamBase>();
				ParamBase pb=new ParamBase();
				pb.setParamNam("pay_item");
				pb.setParamVal(payName.getText().toString());
				ls.add(pb);
				ParamBase pb2=new ParamBase();
				pb2.setParamNam("pay_num");
				pb2.setParamVal(payNum.getText().toString());
				ls.add(pb2);
				ParamBase pb3=new ParamBase();
				pb3.setParamNam("pay_type");
				pb3.setParamVal("0");
				ls.add(pb3);
				ParamBase pb4=new ParamBase();
				pb4.setParamNam("pay_desc");
				pb4.setParamVal(payDesc.getText().toString());
				ls.add(pb4);
				
				if(!id.trim().equals("")){
					
					url="http://120.27.46.196:8082/bianMeile/pay/updatePay.action";
					ParamBase pbid=new ParamBase();
					pbid.setParamNam("id");
					pbid.setParamVal(id);
					ls.add(pbid);
				}
				new ProgressThread(payHandler, ls, url, 1).start();
			}
		});
		pay_cancel=(Button)this.findViewById(R.id.pay_cancel);
		pay_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyPayActivity.this,MainAppActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	private Handler payHandler = new Handler() {
		public void handleMessage(Message msg) {
			int state = msg.getData().getInt("state");
			String backStr = msg.getData().getString("backStr");
			if (backStr != null) {
				JSONObject jo;
				switch (state) {
				case 0:
					Log.i("title", "显示错误");

					break;

				case 1:

					if (backStr.trim().equalsIgnoreCase("网络连接失败")) {
						Toast.makeText(MyPayActivity.this, "网络连接失败！",
								Toast.LENGTH_LONG).show();
						break;
					}else{
						Toast.makeText(MyPayActivity.this, "保存成功！",Toast.LENGTH_LONG).show();
					}
				default:
				}
			}
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_pay, menu);
		return true;
	}

}
