package com.zbc.soft.util;

import java.io.File;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ProgressThread extends Thread {
	private Handler handler;
	private List<ParamBase> ls;////参数列表
	private String url;////对应的url操作
	private String backStr; ////返回支付串
	private int type = 0; ///处理类型
	File file; ////传输类型是否为文件格式

	public ProgressThread(Handler handler, List<ParamBase> ls2, String url,
						  int type) {
		this.handler = handler;
		this.ls = ls2;
		this.url = url;
		this.type = type;
	}

	public ProgressThread(Handler handler, List<ParamBase> ls, String url,
						  int type, File file) {
		this.handler = handler;
		this.ls = ls;
		this.url = url;
		this.type = type;
		this.file = file;
	}

	@Override
	public void run() {
		if (file != null) {
			backStr = HttpUtil.queryPicForGet(url, file);

		} else {

			//backStr = HttpUtil.queryStringForPost(url, ls);
			backStr = HttpUtil.queryStringForUrlConnPost(url, ls);
		}

		Message msg = handler.obtainMessage();
		Bundle state = new Bundle();

		state.putInt("state", type);
		state.putString("backStr", backStr);
		msg.setData(state);
		handler.sendMessage(msg);
	}
}
