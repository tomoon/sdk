package com.tomoon.sample.watch;

import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.tomoon.sdk.TMWatchReceiver;

public class SampleReceiver extends TMWatchReceiver {
	

	private Handler mHandler;

	public SampleReceiver(Handler h) {
		mHandler = h;
	}

	@Override
	protected void onHttpResponse(Context ctx, int status, int transId,
			JSONObject json) {
		if (10 != transId) {
			Log.w("sample", "Got bad trnasation xx: " + transId);
			return;
		}

		// 发回给Activity
		if (json == null || mHandler == null) {
			return;
		}
		Message msg = mHandler.obtainMessage();
		msg.obj = json;
		msg.what = status;
		mHandler.sendMessage(msg);
	}

	@Override
	protected void onAppResponse(Context ctx, int status, int transId,
			JSONObject json) {
		if (10 != transId) {
			Log.w("sample", "Got bad trnasation xxxx: " + transId);
			return;
		}
		if (mHandler == null) {
			return;
		}

		mHandler.sendEmptyMessage(status);
	}

}
