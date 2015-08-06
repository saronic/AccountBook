package com.zbc.soft;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zbc.soft.util.ParamBase;
import com.zbc.soft.util.ProgressThread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PayIncomeActivity extends Activity {

	EditText res_pay,res_income,res_num;
	Button result_cancel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_income);
		res_pay=(EditText)this.findViewById(R.id.res_pay);
		res_income=(EditText)this.findViewById(R.id.res_income);
		res_num=(EditText)this.findViewById(R.id.res_num);
		result_cancel=(Button)this.findViewById(R.id.result_cancel);
		result_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PayIncomeActivity.this,MainAppActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		String url = "http://120.27.46.196:8082/bianMeile/pay/getPayAll.action";
		List<ParamBase> ls = new ArrayList<ParamBase>();
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
					Log.i("title", "ÏÔÊ¾´íÎó");

					break;

				case 1:

					if (backStr.trim().equalsIgnoreCase("ÍøÂçÁ¬½ÓÊ§°Ü")) {
						Toast.makeText(PayIncomeActivity.this, "ÍøÂçÁ¬½ÓÊ§°Ü£¡",
								Toast.LENGTH_LONG).show();
						break;
					} else {
						try {
							jo = new JSONObject(backStr);
							JSONArray jsonArray = (JSONArray) jo.get("payLstAll");
							if(jsonArray!=null&&jsonArray.length()>0){
								for(int i=0;i<jsonArray.length();i++){
									JSONObject jsb = (JSONObject) jsonArray
											.get(i);
									if(jsb.get("total")!=null){
										if(jsb.get("pay_type").toString().trim().equals("0")){
											
											res_pay.setText(jsb.get("total").toString());
										}else{
											
											res_income.setText(jsb.get("total").toString());
										}
										
									}
								}
								res_num.setText((Double.parseDouble(res_income.getText().toString())-Double.parseDouble(res_pay.getText().toString()))+"");
								break;
							}
						
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

						}
					}
					break;
					default:
				}
			}
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pay_income, menu);
		return true;
	}

}
