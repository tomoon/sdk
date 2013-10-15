package com.tomoon.sample;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tomoon.sdk.R;
import com.tomoon.sdk.TMWatchConstant;
import com.tomoon.sdk.TMWatchSender;

public class TestHttpActivity extends BaseActivity implements
		View.OnClickListener {

	private TextView mTVResult;
	private View mBtnWeather;

	//广播接收器，接收返回的http广播
	private SampleReceiver mReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_activity);
		mTVResult = (TextView) findViewById(R.id.tv_weather);
		mBtnWeather = findViewById(R.id.btn_weather);
		mBtnWeather.setOnClickListener(this);

		// 注册http返回的监听器，handler用来接收返回的消息
		mReceiver = new SampleReceiver(new Handler() {

			@Override
			public void handleMessage(Message msg) {
				JSONObject json = (JSONObject) msg.obj;
				mBtnWeather.setEnabled(true);
				TextView tv = mTVResult;
				try {
					String url = json.getString("url");
					if (!MainActivity.sWeatherURL.equals(url)) {
						return;
					}
					int result = json.getInt("httpResult");
					if (result == TMWatchConstant.REQ_HTTP_BAD_URL) {
						tv.setText("地址错误: " + url);
					} else if (result == TMWatchConstant.REQ_HTTP_NET_ERR) {
						tv.setText("网络错误");
					} else if (result != TMWatchConstant.REQ_OK) {
						tv.setText("未知错误");
					} else { // ok
						String ret = json.optString("httpRetString", null);
						if (TextUtils.isEmpty(ret)) {
							ret = "Failed!";
						}
						tv.setText(ret);
					}
				} catch (Exception e) {
					e.printStackTrace();
					tv.setText("error: " + e.toString());
				}
			}
		});
		TMWatchSender.registerReceiver(this, mReceiver);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// 取消广播，handler也不会接收回调了
		unregisterReceiver(mReceiver);
	}

	@Override
	public void onClick(View v) {
		// 等待返回消息，
		mBtnWeather.setEnabled(false);
		mTVResult.setText(null);
		// 发送请求
		TMWatchSender.sendHttpRequest(this, MainActivity.sWeatherURL);
	}

}
