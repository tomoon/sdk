package com.tomoon.sample;

import com.tomoon.sdk.Emulator;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 调整为手表屏幕大小
		super.onCreate(savedInstanceState);
		Emulator.configureWindow(getWindow());
	}
}
