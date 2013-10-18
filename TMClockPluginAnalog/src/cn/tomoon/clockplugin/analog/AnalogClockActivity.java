package cn.tomoon.clockplugin.analog;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import cn.tomoon.clockplugin.analog.widget.HandImageView;

public class AnalogClockActivity extends Activity {

	public static int mRotateCenterX = 120; // 120 * 1.325;
	public static int mRotateCenterY = 160; // 160 * 1.325;
	private boolean ClockRunning = true;
	private GetRunHandTask mGetRunHandTask;
	
	private HandImageView mHourHandImageView;
	private HandImageView mMinHandImageView;
	private HandImageView mSecHandImageView;
	int secAngle=0;
	int lastSecAngle;
	int hourAngle=0;
	int lastHourAngle;
	int minAngle=0;
	int lastMinAngle;
	private final int SEC_HAND_REFRESH_VALUE=1000;
	private float WATCH_SCALE_DENSITY = 1.445f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		DisplayMetrics dm = new DisplayMetrics();    
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		mRotateCenterX=dm.widthPixels/2;
//        mRotateCenterY=dm.heightPixels/2;
		final Resources res = this.getResources();
		final DisplayMetrics dm = res.getDisplayMetrics();
		final float scale = dm.scaledDensity / WATCH_SCALE_DENSITY ;
		int CenterX = 120; // 120 * 1.325;
		int CenterY = 160; // 160 * 1.325;
		mRotateCenterX = (int) (CenterX * scale);
		mRotateCenterY = (int) (CenterY * scale);
		setContentView(R.layout.activity_analog_clock);
		RelativeLayout contentView = (RelativeLayout)findViewById(R.id.contentView);
//		mRotateCenterY = contentView.getHeight() / 2;
//		mRotateCenterX = contentView.getWidth() / 2;
		Log.d("-------onCreate", mRotateCenterX + " " + mRotateCenterY);
        mMinHandImageView = (HandImageView)findViewById(R.id.min_hand);
        mSecHandImageView = (HandImageView)findViewById(R.id.sec_hand);
        mHourHandImageView = (HandImageView)findViewById(R.id.hour_hand);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mGetRunHandTask =new GetRunHandTask();
		ClockRunning=true;
		mGetRunHandTask.execute("");
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ClockRunning=false;
//		Debug.stopMethodTracing();
	}
	class GetRunHandTask extends AsyncTask<String, Boolean, Boolean>{

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			while(ClockRunning){
			long time = System.currentTimeMillis();
			Calendar mCalendar = Calendar.getInstance();
			mCalendar.setTimeInMillis(time);
			// if (!on_off) {
			// return;
			// }
			secAngle = mCalendar.get(Calendar.SECOND) * 6;
			minAngle = mCalendar.get(Calendar.MINUTE) * 6;
			hourAngle = mCalendar.get(Calendar.HOUR) * 30+minAngle/12;
//			secAngle = (secAngle > 348 ? 0 : secAngle + 6);
			if (secAngle!=lastSecAngle) {
				mSecHandImageView.PostRotateHanderWithAngle(secAngle);
				lastSecAngle=secAngle;
//				Log.i("jike@ secAngle " + secAngle);
			}
			if (lastMinAngle!=minAngle) {
				mHourHandImageView.PostRotateHanderWithAngle(hourAngle);
				mMinHandImageView.PostRotateHanderWithAngle(minAngle);
				lastMinAngle=minAngle;
			}
			try {
				Thread.sleep(SEC_HAND_REFRESH_VALUE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			return ClockRunning;
		}
	}

}
