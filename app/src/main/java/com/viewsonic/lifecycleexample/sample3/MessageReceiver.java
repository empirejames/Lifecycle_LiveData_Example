package com.viewsonic.lifecycleexample.sample3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MessageReceiver extends BroadcastReceiver {
	String data;

	@Override
	public void onReceive(Context context, Intent intent) {
		String message = intent.getStringExtra("message");
		data = message;
		Log.e("Pan", "Got message: " + message);
	}
}
