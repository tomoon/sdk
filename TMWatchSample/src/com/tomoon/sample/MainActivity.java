package com.tomoon.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

	public static final String sWeatherURL = "http://m.weather.com.cn/data/101010100.html";

	private static Class<?> sTestClasses[] = new Class<?>[] {

	TestHttpActivity.class,

	TestNotificationActivity.class,

	};
	private static String sTestNames[] = new String[] {

	"测试Http",

	"测试通知"

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
				Intent i = new Intent();
				i.setClass(MainActivity.this, sTestClasses[arg2]);
				startActivity(i);
			}

		});
		setContentView(lv);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
