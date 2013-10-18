package com.tomoon.weather;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.tomoon.sdk.Emulator;
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
		Emulator.configure(getWindow());

		mTMWatchReceiver = new TMWatchReceiver() {

			@Override
			protected void onPebbleData(Context ctx,int status, int transId,
					String jsonData) {
				super.onPebbleData(ctx,status, transId, jsonData);
				/**
				 * weather data sent from pebble watch
				 * [
				 *    {"value":0,"length":1,"type":"uint","key":0},
				 *    {"value":"17C","length":0,"type":"string","key":1}
				 * ]
				 */
				JSONArray data;
				try {
					data = new JSONArray(jsonData);
				} catch (JSONException e) {
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
		unregisterReceiver(mTMWatchReceiver);
		super.onDestroy();
	}

}
