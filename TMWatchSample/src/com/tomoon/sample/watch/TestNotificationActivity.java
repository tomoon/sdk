package com.tomoon.sample.watch;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.tomoon.sdk.TMWatchConstant;
import com.tomoon.sdk.TMWatchSender;
import com.tomoon.watch.sample.R;

public class TestNotificationActivity extends BaseActivity {

	private int mNoficationCnt = 0;
	private TextView mTVResult;

	private SampleReceiver mReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTVResult = new TextView(this);
		setContentView(mTVResult);

		mReceiver = new SampleReceiver(new Handler() {

			@Override
			public void handleMessage(Message msg) {

				// received notification
				if (msg.what != TMWatchConstant.STATUS_OK) {
					onNotification(false);
				} else {
					onNotification(true);
				}
			}
		});
		TMWatchSender.registerReceiver(this, mReceiver);

		// 向手机第3方应用发送一个请求，手机应用收到后会每秒发送一个通知，持续10秒
		JSONObject json = new JSONObject();
		try {
			json.put("sendNotification", true);
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		TMWatchSender.sendAppRequest(this, 10, "com.tomoon.sample.phone", json);
	}

	private void onNotification(boolean ok) {
		if (ok) {
			mTVResult.setText(getResources().getString(R.string.notification) + mNoficationCnt);
			mNoficationCnt++;
		} else {
			mTVResult.setText(getResources().getString(R.string.notification_err));

		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

}
