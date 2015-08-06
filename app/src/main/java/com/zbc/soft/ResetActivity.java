package com.zbc.soft;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zbc.soft.util.Login_info;
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

public class ResetActivity extends Activity {

	private Button reset_cancel,reset_ok;
	private EditText new_pwd,userName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset);
		new_pwd=(EditText)this.findViewById(R.id.new_pwd);
		userName=(EditText)this.findViewById(R.id.userName);
		userName.setText(Login_info.USERNAME);
		reset_ok=(Button)this.findViewById(R.id.reset_ok);
		reset_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(new_pwd.getText()==null||new_pwd.getText().toString().trim().equals("")){
					Toast.makeText(ResetActivity.this, "密码不能为空！",
							Toast.LENGTH_LONG).show();
					
				}else{
					
					String url = "http://120.27.46.196:8082/bianMeile/user/updateUser.action";
					List<ParamBase> ls = new ArrayList<ParamBase>();
					ParamBase pb = new ParamBase();
					pb.setParamNam("id");
					pb.setParamVal(Login_info.ID);
					ls.add(pb);
					ParamBase pb2 = new ParamBase();
					pb2.setParamNam("password");
					pb2.setParamVal(new_pwd.getText().toString());
					ls.add(pb2);
					new ProgressThread(resetHandler, ls, url, 1).start();
				}
			}
		});
		reset_cancel=(Button)this.findViewById(R.id.reset_cancel);
		reset_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ResetActivity.this,MainAppActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	private Handler resetHandler = new Handler() {
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
						Toast.makeText(ResetActivity.this, "网络连接失败！",
								Toast.LENGTH_LONG).show();
						break;
					}else{
						try {
							jo = new JSONObject(backStr);
							if(jo.get("status").equals("-1")){
								Toast.makeText(ResetActivity.this, "密码修改失败，请联系管理员！",
										Toast.LENGTH_LONG).show();
								
							}else{
								
								
								Toast.makeText(ResetActivity.this, "密码修改成功！",
										Toast.LENGTH_LONG).show();
							
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				default:
				}
			}
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reset, menu);
		return true;
	}

}
