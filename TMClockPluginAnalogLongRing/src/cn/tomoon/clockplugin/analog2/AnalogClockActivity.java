package cn.tomoon.clockplugin.analog2;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import cn.tomoon.clockplugin.analog2.widget.HandImageView;

public class AnalogClockActivity extends Activity {

	private HandImageView mHourHandImageView;
	private HandImageView mMinHandImageView;
	private HandImageView mSecHandImageView;
	int secAngle = 0;
	int lastSecAngle;
	int hourAngle = 0;
	int lastHourAngle;
	int minAngle = 0;
	int lastMinAngle;
	int HAND_REFRESH_VALUE = 1000;
	private boolean isShowSec = false;
	private Runnable mRunnable;
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_analog_clock);
		HAND_REFRESH_VALUE = isShowSec ? HAND_REFRESH_VALUE : HAND_REFRESH_VALUE*60;
		mMinHandImageView = (HandImageView) findViewById(R.id.min_hand);
		mSecHandImageView = (HandImageView)findViewById(R.id.sec_hand);
		mHourHandImageView = (HandImageView) findViewById(R.id.hour_hand);
		mRunnable = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				showTimeView(isShowSec);
				mHandler.postDelayed(mRunnable, HAND_REFRESH_VALUE);
			}
		};
		mHandler = new Handler();
		firstShowTime(isShowSec);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		firstShowTime(isShowSec);
	}
	
	private void firstShowTime(boolean isShowSec) {
		showTimeView(isShowSec);
		if (isShowSec) {
			mHandler.removeCallbacks(mRunnable);
			mHandler.post(mRunnable);
			return;
		}
		mHandler.removeCallbacks(mRunnable);
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.setTimeInMillis(System.currentTimeMillis());
		long first_delay_time = (60 - mCalendar.get(Calendar.SECOND)) * 1000;
		first_delay_time = first_delay_time != 60*1000 ? first_delay_time : 0;
		mHandler.postDelayed(mRunnable, first_delay_time);
	}

	private void showTimeView(boolean isShowSec) {
		long time = System.currentTimeMillis();
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.setTimeInMillis(time);
		secAngle = mCalendar.get(Calendar.SECOND) * 6;
		minAngle = mCalendar.get(Calendar.MINUTE) * 6;
		hourAngle = mCalendar.get(Calendar.HOUR) * 30 + minAngle / 12;
		// secAngle = (secAngle > 348 ? 0 : secAngle + 6);
		if (!isShowSec) {
			mSecHandImageView.setVisibility(View.INVISIBLE);
		}
		if (isShowSec && secAngle != lastSecAngle) {
			mSecHandImageView.PostRotateHanderWithAngle(secAngle);
			lastSecAngle = secAngle;
		}
		if (lastMinAngle != minAngle || lastHourAngle != hourAngle) {
			mHourHandImageView.PostRotateHanderWithAngle(hourAngle);
			mMinHandImageView.PostRotateHanderWithAngle(minAngle);
			lastMinAngle = minAngle;
			lastHourAngle = hourAngle;
		}
	}

}
