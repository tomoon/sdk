package com.tomoon.sample.phone;

import android.app.Application;

public class TheApp extends Application {
	public static TheApp sInst;

	public void onCreate() {
		super.onCreate();
		sInst = this;
	}
}
