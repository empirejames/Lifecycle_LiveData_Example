package com.viewsonic.lifecycleexample.sample6;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MessageViewModel extends ViewModel {

	public LiveData<String> getData() {
		return Repository.instance().getData();
	}

}
