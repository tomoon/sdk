package cn.tomoon.clockplugin.analog;

import java.util.Calendar;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import cn.tomoon.clockplugin.analog.widget.LongHandImageView;

import com.tomoon.sdk.Emulator;

public class AnalogClockActivity extends Activity {

	private boolean ClockRunning = true;
	private GetRunHandTask mGetRunHandTask;
	
	private LongHandImageView mHourLongHandImageView;
	private LongHandImageView mMinLongHandImageView;
	private LongHandImageView mSecLongHandImageView;
	int secAngle=0;
	int lastSecAngle;
	int hourAngle=0;
	int lastHourAngle;
	int minAngle=0;
	int lastMinAngle;
	private final int SEC_HAND_REFRESH_VALUE=1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_analog_clock);
		Emulator.configure(getWindow());
//		RelativeLayout contentView = (RelativeLayout)findViewById(R.id.contentView);
        mMinLongHandImageView = (LongHandImageView)findViewById(R.id.min_hand);
        mSecLongHandImageView = (LongHandImageView)findViewById(R.id.sec_hand);
        mHourLongHandImageView = (LongHandImageView)findViewById(R.id.hour_hand);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
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
				mSecLongHandImageView.PostRotateHanderWithAngle(secAngle);
				lastSecAngle=secAngle;
//				Log.i("jike@ secAngle " + secAngle);
			}
			if (lastMinAngle!=minAngle) {
				mHourLongHandImageView.PostRotateHanderWithAngle(hourAngle);
				mMinLongHandImageView.PostRotateHanderWithAngle(minAngle);
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
