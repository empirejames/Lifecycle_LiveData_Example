package com.viewsonic.lifecycleexample.sample5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LifecycleRegistryOwner;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.viewsonic.lifecycleexample.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private LifecycleRegistry lifecycle;
	private Button demo;
	private Button resume, stop;
	private MyActivityObserver mObserver;

	@Override
	public LifecycleRegistry getLifecycle() {
		if (lifecycle == null) {
			lifecycle = new LifecycleRegistry(this);
		}
		return lifecycle;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mObserver = new MyActivityObserver();
		lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
		initView();
		final Intent i = new Intent(this, FloatingViewService.class);
		demo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (checkDrawPermission()) {
					startService(i);
				}
			}
		});
		getLifecycle().addObserver(MyActivityObserver.getInstance());
	}

	@Override
	protected void onStart() {
		super.onStart();
		lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START);
	}

	@Override
	protected void onStop() {
		super.onStop();
		lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP);

	}

	@Override
	protected void onResume() {
		super.onResume();
		lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);

	}

	private boolean checkDrawPermission() {
		final int REQUEST_CODE_OVERLAY = 10;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
			Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
			intent.setData(Uri.parse("package:" + getPackageName()));
			startActivityForResult(intent, REQUEST_CODE_OVERLAY);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.resume:
				Log.e("Pan", "RESUME ....");
				lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
				break;
			case R.id.stop:
				Log.e("Pan", "STOP ....");
				lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
				break;
		}
	}

	void initView() {
		demo = findViewById(R.id.button);
		resume = findViewById(R.id.resume);
		stop = findViewById(R.id.stop);
		resume.setVisibility(View.VISIBLE);
		resume.setOnClickListener(this);
		stop.setVisibility(View.VISIBLE);
		stop.setOnClickListener(this);
	}
}