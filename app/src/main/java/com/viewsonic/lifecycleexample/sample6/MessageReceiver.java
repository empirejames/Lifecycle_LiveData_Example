package com.viewsonic.lifecycleexample.sample6;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

public class MessageReceiver extends BroadcastReceiver {

	public final MutableLiveData<String> mReceiverData = new MutableLiveData<>();

	@Override
	public void onReceive(Context context, Intent intent) {
		String message = intent.getStringExtra("message");
		mReceiverData.setValue(message);
		Repository.instance().addDataSource(mReceiverData);
		Log.e("Pan", "Got message: " + message);
	}
}
