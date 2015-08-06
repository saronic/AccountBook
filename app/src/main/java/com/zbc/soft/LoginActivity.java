package com.zbc.soft;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zbc.soft.util.DBHandler;
import com.zbc.soft.util.Login_info;
import com.zbc.soft.util.ParamBase;
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

public class LoginActivity extends Activity {

    private Button ok, cancel;
    private EditText userName, pwd;
    boolean flag = false;
    DBHandler dbHandler;
    String path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText) this.findViewById(R.id.userName);
        pwd = (EditText) this.findViewById(R.id.pwd);
        path = this.getFilesDir().toString();
        dbHandler = new DBHandler();
        Map<String, Object> initUserInfo = dbHandler.getInit(dbHandler.getDB(path));
        if (initUserInfo!= null) {
            userName.setText(initUserInfo.get("user_nam").toString());
            flag = false;
        } else {
            dbHandler.createTable(dbHandler.getDB(path));
            flag = true;
        }

        ok = (Button) this.findViewById(R.id.ok);
        ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String url = "http://120.27.46.196:8082/bianMeile/user/getAllUser.action";
                List<ParamBase> ls = new ArrayList<>();
                ParamBase pb = new ParamBase();
                pb.setParamNam("username");
                pb.setParamVal(userName.getText().toString());
                ls.add(pb);
                if (flag == true) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("user_id", "1");
                    map.put("user_nam", userName.getText().toString());
                    dbHandler.addInit(dbHandler.getDB(path), map);
                }
                ParamBase pb2 = new ParamBase();
                pb2.setParamNam("password");
                pb2.setParamVal(pwd.getText().toString());
                ls.add(pb2);
                ProgressThread progressThread = new ProgressThread(loginHandler, ls, url, 1);
                progressThread.start();
            }
        });
        cancel = (Button) this.findViewById(R.id.cancel);
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    private Handler loginHandler = new MyHandler(LoginActivity.this);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    class MyHandler extends Handler {
        private final WeakReference<LoginActivity> mTarget;

        public MyHandler(LoginActivity target) {
            mTarget = new WeakReference<LoginActivity>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            int state = msg.getData().getInt("state");
            String backStr = msg.getData().getString("backStr");
            LoginActivity loginActivity = mTarget.get();
            if (loginActivity == null) return;
            if (backStr != null) {
                JSONObject jo;
                switch (state) {
                    case 0:
                        Log.i("title", "显示错误");

                        break;

                    case 1:

                        if (backStr.trim().equalsIgnoreCase("网络连接失败")) {
                            Toast.makeText(loginActivity, "网络连接失败！",
                                    Toast.LENGTH_LONG).show();
                            break;
                        } else {
                            try {
                                jo = new JSONObject(backStr);
                                if (jo.get("status").equals("-1")) {
                                    Toast.makeText(loginActivity, "用户名密码不匹配，请重新输入！",
                                            Toast.LENGTH_LONG).show();

                                } else {

                                    JSONArray jsonArray = (JSONArray) jo.get("data");
                                    if (jsonArray != null && jsonArray.length() > 0) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jo_tmp = (JSONObject) jsonArray.get(i);
                                            Login_info.ID = jo_tmp.getString("customer_id");
                                            Login_info.USERNAME = jo_tmp.getString("username");
                                        }


                                    }
                                    Intent intent = new Intent(loginActivity,
                                            MainAppActivity.class);
                                    loginActivity.startActivity(intent);

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
    }
}
