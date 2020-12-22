package com.viewsonic.lifecycleexample.sample5;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

public class MyActivityObserver implements LifecycleObserver {

	static MyActivityObserver mObserver;
	MutableLiveData<String> status = new MutableLiveData<>();

	public static MyActivityObserver getInstance() {
		if (mObserver == null) {
			mObserver = new MyActivityObserver();
		}
		return mObserver;
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
	public void ON_RESUME() {
		status.postValue("ON_RESUME");
		Log.e("Pan", "OBSERVER_RESUME");
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_STOP)
	public void ON_STOP() {
		status.postValue("ON_STOP");
		Log.e("Pan", "OBSERVER_ON_STOP");
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	public void ON_START() {
		status.postValue("ON_START");
		Log.e("Pan", "OBSERVER_ON_START");
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
	public void ON_DESTROY() {
		status.postValue("ON_DESTROY");
		Log.e("Pan", "OBSERVER_ON_DESTROY");
	}
}
