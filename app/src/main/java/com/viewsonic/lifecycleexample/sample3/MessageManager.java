package com.viewsonic.lifecycleexample.sample3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MessageManager {

	public static void bindMessageReceiverIn(LifecycleOwner lifecycleOwner,
											 BroadcastReceiver receiver, Context context) {
		new MessageReceiver(lifecycleOwner, receiver, context);
	}

	static class MessageReceiver implements LifecycleObserver {
		private final Context mContext;
		private BroadcastReceiver mReceiver;

		public MessageReceiver(LifecycleOwner lifecycleOwner,
							   BroadcastReceiver receiver, Context context) {
			mContext = context;
			mReceiver = receiver;
			lifecycleOwner.getLifecycle().addObserver(this);
		}

		@OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
		void addReceiver() {
			Log.e("Pan", "RESUME");
			LocalBroadcastManager.getInstance(mContext).registerReceiver(mReceiver, new IntentFilter("getData"));
		}

		@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
		void removeReceiver() {
			Log.e("Pan", "DESTROY");
			LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mReceiver);
		}
	}

}
