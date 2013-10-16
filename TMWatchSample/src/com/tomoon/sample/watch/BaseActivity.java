package com.tomoon.sample.watch;

import com.tomoon.sdk.Emulator;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 调整为手表屏幕大小
		super.onCreate(savedInstanceState);
		Emulator.configure(getWindow());
	}
}
