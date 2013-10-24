package cn.tomoon.clockplugin;


import java.util.Calendar;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private TextView hour1TextView;
	private TextView dotTextView;
	private TextView min1TextView;
	private TextView hour2TextView;
	private TextView min2TextView;
	private Calendar mCalendar;
	
	private int HAND_REFRESH_VALUE = 1000;
	private boolean isShowSec = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		HAND_REFRESH_VALUE = isShowSec ? HAND_REFRESH_VALUE
				: HAND_REFRESH_VALUE * 60;
		Typeface fontFace = Typeface.createFromAsset(getAssets(),
                "fonts/Clockopia.ttf");
		hour1TextView = (TextView)findViewById(R.id.hour1TextView);
		hour2TextView = (TextView)findViewById(R.id.hour2TextView);
		dotTextView = (TextView)findViewById(R.id.dotTextView);
		min1TextView = (TextView)findViewById(R.id.min1TextView);
		min2TextView = (TextView)findViewById(R.id.min2TextView);
		hour1TextView.setTypeface(fontFace);
		hour2TextView.setTypeface(fontFace);
		dotTextView.setTypeface(fontFace);
		min1TextView.setTypeface(fontFace);
		min2TextView.setTypeface(fontFace);
		dotTextView.setText(":");
	}
	
	private Handler mDisplayHandler = new Handler();
	private Runnable mDisplayRunnable = new Runnable() {
		@Override
		public void run() {
			setTimeView(isShowSec);
			mDisplayHandler.postDelayed(mDisplayRunnable, HAND_REFRESH_VALUE);
		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		firstShowTime(isShowSec);
	}
	
	private void firstShowTime(boolean isShowSec) {
		setTimeView(isShowSec);
		if (isShowSec) {
			mDisplayHandler.removeCallbacks(mDisplayRunnable);
			mDisplayHandler.post(mDisplayRunnable);
			return;
		}
		mDisplayHandler.removeCallbacks(mDisplayRunnable);
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.setTimeInMillis(System.currentTimeMillis());
		long first_delay_time = (60 - mCalendar.get(Calendar.SECOND)) * 1000;
		first_delay_time = first_delay_time != 60 * 1000 ? first_delay_time : 0;
		mDisplayHandler.postDelayed(mDisplayRunnable, first_delay_time);
	}

	private void setTimeView(boolean isShowSec) {
		// TODO Auto-generated method stub
		long time = System.currentTimeMillis();
		mCalendar = Calendar.getInstance();
  	  	mCalendar.setTimeInMillis(time);
  	  	int mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
	  	int mMinute = mCalendar.get(Calendar.MINUTE);
	  	int mSecond = mCalendar.get(Calendar.SECOND);
	  	int H1, H2, M1, M2;
	  	H2 = mHour/10;
	  	H1 = mHour%10;
	  	M2 = mMinute/10;
	  	M1 = mMinute%10;
	  	if (isShowSec && mSecond % 2 == 0) {
	  		dotTextView.setVisibility(View.INVISIBLE);
		} else {
	  		dotTextView.setVisibility(View.VISIBLE);
		}
	  	hour1TextView.setText(H1+"");
	  	hour2TextView.setText(H2+"");
	  	min1TextView.setText(M1+"");
	  	min2TextView.setText(M2+"");
	}
	
}
