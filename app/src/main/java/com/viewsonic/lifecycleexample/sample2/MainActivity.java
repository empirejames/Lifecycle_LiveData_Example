package com.viewsonic.lifecycleexample.sample2;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.viewsonic.lifecycleexample.R;

public class MainActivity extends AppCompatActivity {

	MessageReceiver mMessageReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mMessageReceiver = new MessageReceiver();
		Button button = findViewById(R.id.button);
		MessageReceiver.register(this, mMessageReceiver);
		final TextView textView = findViewById(R.id.textView);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				sendMessage();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						textView.setText(mMessageReceiver.data);
					}
				}, 1);
			}
		});
	}

	@Override
	protected void onDestroy() {
		// Unregister since the activity is about to be closed.
		MessageReceiver.unregister(this, mMessageReceiver);
		super.onDestroy();
	}

	private void sendMessage() {
		Intent intent = new Intent("getData");
		intent.putExtra("message", "This is my message!");
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}
}