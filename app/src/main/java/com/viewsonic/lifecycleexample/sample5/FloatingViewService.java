package com.viewsonic.lifecycleexample.sample5;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;

import com.viewsonic.lifecycleexample.R;

public class FloatingViewService extends Service implements View.OnTouchListener, LifecycleObserver {
	WindowManager mWindowManager;
	private WindowManager.LayoutParams mParams;
	private View mWindowView;
	private ImageButton mImgBtn;
	private TextView mTextView;
	private int mStartX, mInitialX;
	private int mStartY, mInitialY;
	RelativeLayout mRelativeLayout;
	LifecycleOwner mLifecycleOwner;
	Context mContext;


	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();

		MyActivityObserver.getInstance().status.observeForever(new Observer<String>() {
			@Override
			public void onChanged(String s) {
				if (s.equals("ON_STOP")) {
					stopSelf();
					if (mWindowView != null)
						mWindowManager.removeView(mWindowView);
					Log.e("Pan", "service get ON_STOP");
				} else {
					Log.e("Pan", "service get " + s);
				}
			}
		});
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		initWindowParams();
		initView();
		mWindowManager.addView(mWindowView, mParams);
		return super.onStartCommand(intent, flags, startId);
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void initView() {
		mWindowView = LayoutInflater.from(getApplication()).inflate(R.layout.activity_main_demo5, null);
		mImgBtn = mWindowView.findViewById(R.id.btn);
		mImgBtn.setOnTouchListener(this);
		mTextView = mWindowView.findViewById(R.id.txtPin);
		mRelativeLayout = mWindowView.findViewById(R.id.ry_main);
		mRelativeLayout.getLayoutParams().height = 200;
		mRelativeLayout.getLayoutParams().width = 200;
	}

	private void initWindowParams() {
		mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		mWindowManager.getDefaultDisplay().getMetrics(dm);
		int mScreenWidth = dm.widthPixels;
		int mScreenHeight = dm.heightPixels;
		mParams = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) ?
						WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
						| WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
				PixelFormat.TRANSLUCENT);
		mParams.x = mScreenWidth / 4;
		mParams.y = mScreenHeight / 4;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mInitialX = mParams.x;
				mInitialY = mParams.y;
				mStartX = (int) event.getRawX();
				mStartY = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				mParams.x = mInitialX + (int) event.getRawX() - mStartX;
				mParams.y = mInitialY + (int) event.getRawY() - mStartY;
				mWindowManager.updateViewLayout(mWindowView, mParams);
				break;
			default:
				break;
		}
		return true;
	}

}
