package com.zbc.soft;

import java.util.ArrayList;
import java.util.List;

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

public class IncomeActivity extends Activity {
	private EditText incomeName,incomeNum,incomeDesc;
	private Button income_ok,income_cancel;
	private String id="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		setContentView(R.layout.activity_income);
		
		incomeName=(EditText)this.findViewById(R.id.incomeName);
		if(intent.getStringExtra("pay_item")!=null){
			
			incomeName.setText(intent.getStringExtra("pay_item"));
		}
		incomeNum=(EditText)this.findViewById(R.id.incomeNum);
		if(intent.getStringExtra("pay_num")!=null){
			
			incomeNum.setText(intent.getStringExtra("pay_num"));
		}
		incomeDesc=(EditText)this.findViewById(R.id.incomeDesc);
		if(intent.getStringExtra("pay_desc")!=null){
			
			incomeDesc.setText(intent.getStringExtra("pay_desc"));
		}
		if(intent.getStringExtra("id")!=null){
			
			id=intent.getStringExtra("id");
		}
		income_ok=(Button)this.findViewById(R.id.income_ok);
		income_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String url="http://120.27.46.196:8082/bianMeile/pay/addPay.action";
				
				List<ParamBase> ls=new ArrayList<ParamBase>();
				ParamBase pb=new ParamBase();
				pb.setParamNam("pay_item");
				pb.setParamVal(incomeName.getText().toString());
				ls.add(pb);
				ParamBase pb2=new ParamBase();
				pb2.setParamNam("pay_num");
				pb2.setParamVal(incomeNum.getText().toString());
				ls.add(pb2);
				ParamBase pb3=new ParamBase();
				pb3.setParamNam("pay_type");
				pb3.setParamVal("1");
				ls.add(pb3);
				ParamBase pb4=new ParamBase();
				pb4.setParamNam("pay_desc");
				pb4.setParamVal(incomeDesc.getText().toString());
				ls.add(pb4);
				
				if(!id.trim().equals("")){
					
					url="http://120.27.46.196:8082/bianMeile/pay/updatePay.action";
					ParamBase pbid=new ParamBase();
					pbid.setParamNam("id");
					pbid.setParamVal(id);
					ls.add(pbid);
				}
				new ProgressThread(incomeHandler, ls, url, 1).start();
			}
		});
		income_cancel=(Button)this.findViewById(R.id.income_cancel);
		income_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(IncomeActivity.this,MainAppActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	private Handler incomeHandler = new Handler() {
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
						Toast.makeText(IncomeActivity.this, "网络连接失败！",
								Toast.LENGTH_LONG).show();
						break;
					}else{
						Toast.makeText(IncomeActivity.this, "保存成功！",Toast.LENGTH_LONG).show();
					}
				default:
				}
			}
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.income, menu);
		return true;
	}

}
