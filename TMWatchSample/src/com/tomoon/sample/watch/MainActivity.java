package com.tomoon.sample.watch;

import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tomoon.sdk.TMWatchReceiver;
import com.tomoon.sdk.TMWatchSender;
import com.tomoon.sdk.pebble.PebbleDictionary;
import com.tomoon.watch.sample.R;

public class MainActivity extends BaseActivity {

	public static final String sWeatherURL = "http://m.weather.com.cn/data/101010100.html";

	private static Class<?> sTestClasses[] = new Class<?>[] {

	TestHttpActivity.class,

	TestNotificationActivity.class, null, null, null

	};

	// receive pebble message from phone
	private TMWatchReceiver mReceiver = new TMWatchReceiver() {
		@Override
		protected void onPebbleData(Context ctx, int status, int transId,
				String data) {
			Toast.makeText(ctx, getResources().getString(R.string.t_watch_received_pebble_data) + (data == null ? "" : data),
					Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onPebbleAck(Context ctx, int status, int transId) {
			Toast.makeText(ctx, getResources().getString(R.string.t_watch_received_pebble_ack), Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onPebbleNack(Context ctx, int status, int transId) {
			Toast.makeText(ctx, getResources().getString(R.string.t_watch_received_pebble_nack), Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TMWatchSender.registerReceiver(this, mReceiver);

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
				tv.setText(getResources().getStringArray(R.array.watch_test_names)[arg0]);
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
							100, pd);

				} else if (arg2 == 3) { // pebble ack
					UUID uuid = UUID
							.fromString("ee4d768c-84c7-4352-8e4f-eef31194b183");
					TMWatchSender.sendPebbleAck(MainActivity.this, uuid, 100);
				} else if (arg2 == 4) { // pebble nack
					UUID uuid = UUID
							.fromString("ee4d768c-84c7-4352-8e4f-eef31194b183");
					TMWatchSender.sendPebbleNack(MainActivity.this, uuid, 100);
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
		unregisterReceiver(mReceiver);
	}

}
