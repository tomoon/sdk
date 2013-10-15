package com.tomoon.sample.watch;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tomoon.watch.utils.TMLog;

public class MainActivity extends BaseActivity {

	public static final String sWeatherURL = "http://m.weather.com.cn/data/101010100.html";

	private static Class<?> sTestClasses[] = new Class<?>[] {

	TestHttpActivity.class,

	TestNotificationActivity.class, null, null

	};
	private static String sTestNames[] = new String[] {

	"测试Http",

	"测试App通信",

	"pebble通知", "pebble音乐",

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
					tv.setTextSize(18);
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
				if (arg2 == 2) {
					sendAlertToPebble();
				} else if (arg2 == 3) {
					sendMusicUpdateToPebble();
				} else {
					Intent i = new Intent();
					i.setClass(MainActivity.this, sTestClasses[arg2]);
					startActivity(i);
				}
			}

		});
		setContentView(lv);
	}

	private void sendMusicUpdateToPebble() {
		final Intent i = new Intent("com.getpebble.action.NOW_PLAYING");
		i.putExtra("artist", "Carly Rae Jepsen");
		i.putExtra("album", "Kiss");
		i.putExtra("track", "Call Me Maybe");

		sendBroadcast(i);
	}

	private void sendAlertToPebble() {
		final Intent i = new Intent("com.getpebble.action.SEND_NOTIFICATION");

		final Map data = new HashMap();
		data.put("title", "Test Message");
		data.put("body",
				"Whoever said nothing was impossible never tried to slam a revolving door.");
		final JSONObject jsonData = new JSONObject(data);
		final String notificationData = new JSONArray().put(jsonData)
				.toString();

		i.putExtra("messageType", "PEBBLE_ALERT");
		i.putExtra("sender", "MyAndroidApp");
		i.putExtra("notificationData", notificationData);

		TMLog.LOGD("About to send a modal alert to Pebble: " + notificationData);
		sendBroadcast(i);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
