package com.tomoon.weather;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.tomoon.sdk.Emulator;
import com.tomoon.sdk.TMWatchReceiver;
import com.tomoon.sdk.TMWatchSender;
import com.tomoon.watch.utils.TMLog;

public class WeatherActivity extends Activity {

	private TextView mWeather;

	private TMWatchReceiver mTMWatchReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_activity_layout);
		init();
		Emulator.configure(getWindow());
		mTMWatchReceiver = new TMWatchReceiver() {

			@Override
			protected void onPebbleData(Context ctx, int transId,
					String jsonData) {
				super.onPebbleData(ctx, transId, jsonData);
				/**
				 * data structure watch received from pebble app
				 * [
				 *    {"value":0,"length":1,"type":"uint","key":0},
				 *    {"value":"17��C","length":0,"type":"string","key":1}
				 * ]
				 */
				JSONArray data;
				try {
					data = new JSONArray(jsonData);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				String temp = data.optJSONObject(1).optString("value");
				mWeather.setText(temp);
			}
		};
		TMWatchSender.registerReceiver(this, mTMWatchReceiver);
	}

	private void init() {
		mWeather = (TextView) findViewById(R.id.weather);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		TMLog.LOGD("unregiste weather receiver");
		unregisterReceiver(mTMWatchReceiver);
	}

}
