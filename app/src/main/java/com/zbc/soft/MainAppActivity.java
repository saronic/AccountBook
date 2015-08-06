package com.zbc.soft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainAppActivity extends Activity {

	private Button pay,payLst,income,incomeLst,income_pay,pwd_change;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_app);
		pwd_change=(Button)this.findViewById(R.id.pwd_change);
		pwd_change.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainAppActivity.this,ResetActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		pay=(Button)this.findViewById(R.id.pay);
		pay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainAppActivity.this,MyPayActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		income=(Button)this.findViewById(R.id.income);
		income.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainAppActivity.this,IncomeActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		incomeLst=(Button)this.findViewById(R.id.incomeLst);
		incomeLst.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainAppActivity.this,MyPayListActivity.class);
				intent.putExtra("pay_type", "1");
				startActivity(intent);
				finish();
			}
		});
		
		payLst=(Button)this.findViewById(R.id.payLst);
		payLst.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainAppActivity.this,MyPayListActivity.class);
				intent.putExtra("pay_type", "0");
				startActivity(intent);
				finish();
			}
		});
		income_pay=(Button)this.findViewById(R.id.income_pay);
		income_pay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainAppActivity.this,PayIncomeActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_app, menu);
		return true;
	}

}
