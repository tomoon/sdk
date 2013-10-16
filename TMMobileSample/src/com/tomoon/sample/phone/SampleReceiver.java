package com.tomoon.sample.phone;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.tomoon.sdk.TMPhoneReceiver;
import com.tomoon.sdk.TMPhoneSender;

public class SampleReceiver extends TMPhoneReceiver {
	private static Handler sHandler;
	public static int sReceiverCount = 0;

	public static void setHandler(Handler h) {
		sHandler = h;
	}

	// 用于向手表发送通知
	private static Map<String, NotificationTask> mNotificaitonMap = new HashMap<String, NotificationTask>();

	private static class NotificationTask implements Runnable {
		int count = 10;
		String senderPkg;
		Handler mHandler;

		NotificationTask(String pkg) {
			senderPkg = pkg;
			// 如果使用TMWatchConstant.sTheApp ，
			// 需要在app onCreate 中调用 TMWatchConstant.init
			mHandler = new Handler(TheApp.sInst.getMainLooper());
		}

		@Override
		public void run() {
			mHandler.removeCallbacks(this);
			if (count < 0) {
				mNotificaitonMap.remove(senderPkg);
				return;
			}
			count--;

			TMPhoneSender.sendAppResponse(TheApp.sInst, senderPkg, null);
			mHandler.postDelayed(this, 1000);
		}
	}

	@Override
	protected void onAppRequest(String senderPackage, JSONObject json) {
		sReceiverCount++;
		//Log.d("tomoon", "received request: " + sReceiverCount);
		if (sHandler != null) {
			sHandler.sendEmptyMessage(0);
		}

		Notification noti = new Notification();
		noti.defaults = 0;
		noti.flags = Notification.FLAG_AUTO_CANCEL;
		noti.icon = android.R.drawable.ic_dialog_alert;
		noti.tickerText = "收到手表请求！";
		Intent i = new Intent(TheApp.sInst, MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent contentIntent = PendingIntent.getActivity(TheApp.sInst,
				android.R.string.ok, i, PendingIntent.FLAG_UPDATE_CURRENT);
		noti.setLatestEventInfo(TheApp.sInst, "", noti.tickerText,
				contentIntent);
		NotificationManager nm = (NotificationManager) TheApp.sInst
				.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(0, noti);

		// 是否持续发送通知？
		boolean sendNotification = json.optBoolean("sendNotification", false);
		if (!sendNotification) {
			return;
		}
		NotificationTask task = mNotificaitonMap.get(senderPackage);
		if (task != null) {
			if (task.count < 0) {
				mNotificaitonMap.remove(senderPackage);
			} else {
				return; // 不要重复发送
			}
		}
		task = new NotificationTask(senderPackage);
		mNotificaitonMap.put(senderPackage, task);
		task.mHandler.post(task);
	}
}
