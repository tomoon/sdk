package com.tomoon.sample;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.tomoon.sdk.TMWatchConstant;
import com.tomoon.sdk.TMWatchSender;

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

				// 接收到通知消息
				if (msg.what != TMWatchConstant.REQ_OK) {
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
		TMWatchSender.sendAppRequest(this, "com.tomoon.sample.mobile", json);
	}

	private void onNotification(boolean ok) {
		if (ok) {
			mTVResult.setText("通知: " + mNoficationCnt);
			mNoficationCnt++;
		} else {
			mTVResult.setText("无法发送通知！ 手机程序可能未安装");

		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

}
