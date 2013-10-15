package cn.tomoon.clockplugin;


import java.util.Calendar;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public static TextView hour1TextView;
	public static TextView dotTextView;
	public static TextView min1TextView;
	public static TextView hour2TextView;
	public static TextView min2TextView;
	protected static boolean CLOCK_START = true;
	public static Calendar mCalendar;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
	  	int H1, H2, M1, M2;
	  	H2 = mHour/10;
	  	H1 = mHour%10;
	  	M2 = mMinute/10;
	  	M1 = mMinute%10;
	  	if (mSecond % 2 == 0) {
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
