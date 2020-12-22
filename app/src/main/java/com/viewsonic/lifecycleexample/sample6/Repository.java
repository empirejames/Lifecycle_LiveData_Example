package com.viewsonic.lifecycleexample.sample6;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

public class Repository {
	private static final Repository INSTANCE = new Repository();
	private final MediatorLiveData<String> mData = new MediatorLiveData<>();

	private Repository() {
	}

	public static Repository instance() {
		return INSTANCE;
	}

	public LiveData<String> getData() {
		return mData;
	}

	public void addDataSource(LiveData<String> data) {
		mData.addSource(data, new Observer<String>() {
			@Override
			public void onChanged(String s) {
				mData.setValue(s);
			}
		});
	}

	public void removeDataSource(LiveData<String> data) {
		mData.removeSource(data);
	}
}
