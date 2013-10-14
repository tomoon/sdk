package cn.tomoon.clockplugin;


import java.util.Calendar;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public static TextView hourTextView;
	public static TextView dotTextView;
	public static TextView minTextView;
	protected static boolean CLOCK_START = true;
	public static Calendar mCalendar;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Typeface fontFace = Typeface.createFromAsset(getAssets(),
                "fonts/radioland.ttf");
		hourTextView = (TextView)findViewById(R.id.hourTextView);
		dotTextView = (TextView)findViewById(R.id.dotTextView);
		minTextView = (TextView)findViewById(R.id.minTextView);
		hourTextView.setTypeface(fontFace);
		dotTextView.setTypeface(fontFace);
		minTextView.setTypeface(fontFace);
		dotTextView.setText(":");
	}
	private static Handler mDisplayHandler = new Handler();
	private static Runnable mDisplayRunnable = new Runnable() {
		@Override
		public void run() {
			setTimeView();
			if(CLOCK_START )
				mDisplayHandler.postDelayed(mDisplayRunnable,1000);
		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mDisplayHandler.post(mDisplayRunnable);
	}

	protected static void setTimeView() {
		// TODO Auto-generated method stub
		long time = System.currentTimeMillis();
		mCalendar = Calendar.getInstance();
  	  	mCalendar.setTimeInMillis(time);
  	  	int mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
	  	int mMinute = mCalendar.get(Calendar.MINUTE);
	  	int mSecond = mCalendar.get(Calendar.SECOND);
	  	String hourStr = mHour > 9 ? "" + mHour : "0" + mHour;
	  	String minStr = mMinute > 9 ? "" + mMinute : "0" + mMinute;
	  	if (mSecond % 2 == 0) {
	  		dotTextView.setVisibility(View.INVISIBLE);
		} else {
	  		dotTextView.setVisibility(View.VISIBLE);
		}
	  	hourTextView.setText(hourStr);
	  	minTextView.setText(minStr);
	  	
	}


}
