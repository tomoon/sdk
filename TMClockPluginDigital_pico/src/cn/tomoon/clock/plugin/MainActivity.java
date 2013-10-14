package cn.tomoon.clock.plugin;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class MainActivity extends Activity {
	
	public static Calendar mCalendar;
	public static int mMinute;
	public static int mHour;
	public static int mSecond;
	  
	private static String mFormat;
	private  static int H1;
	private static int H2;
	private static int M1;
	private static int M2;
	private static int S1;
	private static int S2;
//	  private static Thread mClockThread;
	  private static ScaleableView hour1ImageView;
	  private static ScaleableView hour2ImageView;
	  private static ScaleableView min1ImageView;
	  private static ScaleableView min2ImageView;
	  private static ScaleableView bigDotImageView;
	  private static int color = 3;

	protected static boolean CLOCK_START = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mFormat = "kk:mm";
		mFormat = "h:mm aa";
		initialFindViewById();
//		Emulator.configure(getWindow());
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	private void displayClock() {
		// TODO Auto-generated method stub
		initLEDClock();
		mDisplayHandler.removeCallbacks(mDisplayRunnable);
        mDisplayHandler.post(mDisplayRunnable);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		displayClock();
	}

	private void initialFindViewById() {
		// TODO Auto-generated method stub
		hour1ImageView = (ScaleableView)findViewById(R.id.hour1);
        hour2ImageView = (ScaleableView)findViewById(R.id.hour2);
        min1ImageView = (ScaleableView)findViewById(R.id.minute1);
        min2ImageView = (ScaleableView)findViewById(R.id.minute2);
        bigDotImageView = (ScaleableView)findViewById(R.id.bigdot);
        
//        mNextAlarmView = (ScaleableView)findViewById(R.id.nextAlarm);
	}
	private static Handler mDisplayHandler = new Handler();
	private static Runnable mDisplayRunnable = new Runnable() {
		@Override
		public void run() {
			initLEDClock();
			if(CLOCK_START)
				mDisplayHandler.postDelayed(mDisplayRunnable,993);
		}
	};
	protected static void initLEDClock() {
		// TODO Auto-generated method stub
		long time = System.currentTimeMillis();
		mCalendar = Calendar.getInstance();
  	  	mCalendar.setTimeInMillis(time);
  	  	mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
	  	mMinute = mCalendar.get(Calendar.MINUTE);
	  	mSecond = mCalendar.get(Calendar.SECOND);
		if(mFormat == "h:mm aa" ){
		  	H1 = mHour/10;
		  	H2 = mHour%10;
	  	}else if(mFormat == "kk:mm"){
	  		if (mHour==0) {
				H1 = 1;
				H2 = 2;
			}else if(mHour<=12){
			  	H1 = mHour/10;
			  	H2 = mHour%10;
	  		}else{
	  			H1 = (mHour-12)/10;
			  	H2 = (mHour-12)%10;
	  		}
	  	}

	  	M1 = mMinute/10;
	  	M2 = mMinute%10;
	  	S1 = mSecond/10;
	  	S2 = mSecond%10;
	  	if(H1==0){
	  		hour1ImageView.setImageSrc(ClockResourcesSelect.getBigNumResId(10, color));
	  	}
	  	else {
	  		hour1ImageView.setImageSrc(ClockResourcesSelect.getBigNumResId(H1, color));
		}
	  	hour2ImageView.setImageSrc(ClockResourcesSelect.getBigNumResId(H2, color));
	  	min1ImageView.setImageSrc(ClockResourcesSelect.getBigNumResId(M1, color));
	  	min2ImageView.setImageSrc(ClockResourcesSelect.getBigNumResId(M2, color));
	  	
	  	if(S2%2==1){
	  		bigDotImageView.setVisibility(View.VISIBLE);
	  		}
	  	else {
	  		bigDotImageView.setVisibility(View.INVISIBLE);
		}
	}


}
