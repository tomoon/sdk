package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
	private static final String TAG = "MyService";
	private static final String CLOCK_RES_NAME_HEADER = "clock_bg_mj_";
	private File imageFile;
	private String path = "/sdcard/.tomoon/skin/digital_clock_1/clock_bg_mj_3.jpg";
	private Bitmap mResultBmp;
	private HashMap<String, Integer> hashMap;
	private int image_size;
	private int[] defaultImgIds;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
//		Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onCreate");
		imageFile = new File(path);
		hashMap = new HashMap<String, Integer>();
		Field[] fields = R.drawable.class.getDeclaredFields();
		for (Field field : fields) {
			if ((field.getName().startsWith(CLOCK_RES_NAME_HEADER))) {
				int index = 0;
				try {
					index = field.getInt(R.drawable.class);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				hashMap.put(field.getName(), index);
			}
		}
		image_size = hashMap.size();
		defaultImgIds = new int[image_size];
		for (int i = 1; i <= image_size; i++) {
			defaultImgIds[i - 1] = hashMap.get(CLOCK_RES_NAME_HEADER + i);
		}
		
	}

	@Override
	public void onDestroy() {
//		Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onDestroy");
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
//		Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onStart");
		Random random1 = new Random();
		int index = Math.abs(random1.nextInt())%image_size;
		InputStream is = this.getResources().openRawResource(defaultImgIds[index]);
		mResultBmp = BitmapFactory.decodeStream(is); 
		try {
			FileOutputStream fos = new FileOutputStream(imageFile);
			boolean re = mResultBmp.compress(CompressFormat.JPEG, 90, fos);
			Log.d(TAG, "re=" + re +", index=" + index + ", path="+imageFile.getAbsolutePath());

			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.stopSelf();
	}
}
