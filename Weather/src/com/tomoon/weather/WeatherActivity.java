package com.tomoon.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.tomoon.sdk.TMWatchReceiver;
import com.tomoon.sdk.TMWatchSender;

public class WeatherActivity extends Activity {
	
	private TextView mWeather;
	
	private TMWatchReceiver mTMWatchReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_activity_layout);
		init();
		mTMWatchReceiver = new TMWatchReceiver() {

			@Override
			protected void onPebbleRequest(Context ctx, int transId,
					String jsonData) {
				// TODO Auto-generated method stub
				super.onPebbleRequest(ctx, transId, jsonData);
				/**
				 * pebble手机app发送给手表的天气数据格式
				 * [
				 *    {"value":0,"length":1,"type":"uint","key":0},
				 *    {"value":"17°C","length":0,"type":"string","key":1}
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
		mWeather = (TextView)findViewById(R.id.weather);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mTMWatchReceiver);
	}
	
	

}
