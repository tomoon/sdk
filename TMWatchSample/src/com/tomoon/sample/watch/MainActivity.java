package com.tomoon.sample.watch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tomoon.sdk.TMWatchSender;
import com.tomoon.sdk.pebble.PebbleDictionary;
import com.tomoon.watch.utils.TMLog;

public class MainActivity extends BaseActivity {

	public static final String sWeatherURL = "http://m.weather.com.cn/data/101010100.html";

	private static Class<?> sTestClasses[] = new Class<?>[] {

	TestHttpActivity.class,

	TestNotificationActivity.class, null, null, null

	};
	private static String sTestNames[] = new String[] {

	"测试Http",

	"测试App通信",

	"发送Pebble消息", "发送PebbleAck", "发送PebbleNack",

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ListView lv = new ListView(this);
		lv.setBackgroundColor(0x88000000);
		lv.setScrollingCacheEnabled(false);
		lv.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) {
				TextView tv = (TextView) arg1;
				if (null == tv) {
					tv = new TextView(MainActivity.this);
					tv.setTextSize(22);
					tv.setPadding(10, 10, 0, 10);
				}
				tv.setText(sTestNames[arg0]);
				return tv;
			}

			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return sTestClasses.length;
			}
		});
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 2) { // pebble msg
					UUID uuid = UUID
							.fromString("ee4d768c-84c7-4352-8e4f-eef31194b183");

					PebbleDictionary pd = new PebbleDictionary();
					pd.addString(100, "value");
					TMWatchSender.sendPebbleMessage(MainActivity.this, uuid,
							-1, pd);

				} else if (arg2 == 3) { // pebble ack
					UUID uuid = UUID
							.fromString("ee4d768c-84c7-4352-8e4f-eef31194b183");
					TMWatchSender.sendPebbleAck(MainActivity.this, uuid, -1);
				} else if (arg2 == 4) { // pebble nack
					UUID uuid = UUID
							.fromString("ee4d768c-84c7-4352-8e4f-eef31194b183");
					TMWatchSender.sendPebbleNack(MainActivity.this, uuid, -1);
				} else {
					Intent i = new Intent();
					i.setClass(MainActivity.this, sTestClasses[arg2]);
					startActivity(i);
				}
			}

		});
		setContentView(lv);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
