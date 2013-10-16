package com.tomoon.sample.watch;

import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.tomoon.sdk.TMWatchConstant;
import com.tomoon.sdk.TMWatchReceiver;

public class SampleReceiver extends TMWatchReceiver {
	private Handler mHandler;

	public SampleReceiver(Handler h) {
		mHandler = h;
	}

	@Override
	protected void onHttpResponse(Context ctx, JSONObject json) {
		// 发回给Activity
		if (json == null || mHandler == null) {
			return;
		}
		Message msg = mHandler.obtainMessage();
		msg.obj = json;
		mHandler.sendMessage(msg);
	}

	@Override
	protected void onAppResponse(Context ctx, int status, JSONObject json) {
		if (mHandler == null) {
			return;
		}
		mHandler.sendEmptyMessage(status);
	}

}
