package com.tomoon.sample;

import com.tomoon.sdk.Emulator;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

	private TextView mTextView;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			onMessage();
		}

	};

	private void onMessage() {
		mTextView.setText("get " + SampleReceiver.sReceiverCount + " requests");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTextView = new TextView(this);
		setContentView(mTextView);
		onMessage();
		SampleReceiver.setHandler(mHandler);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		SampleReceiver.setHandler(null);
	}

}
