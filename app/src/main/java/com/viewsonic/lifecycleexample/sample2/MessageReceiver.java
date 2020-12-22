package com.viewsonic.lifecycleexample.sample2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MessageReceiver extends BroadcastReceiver {
	String data;

	@Override
	public void onReceive(Context context, Intent intent) {
		String message = intent.getStringExtra("message");
		data = message;
		Log.e("Pan", "Get message: " + message);
	}

	public static void register(Context context, MessageReceiver mMessageReceiver) {
		LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver, new IntentFilter("getData"));
	}

	public static void unregister(Context context, MessageReceiver mMessageReceiver) {
		LocalBroadcastManager.getInstance(context).unregisterReceiver(mMessageReceiver);
	}

}
