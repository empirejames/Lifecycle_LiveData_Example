package com.viewsonic.lifecycleexample.sample1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.viewsonic.lifecycleexample.R;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button b = findViewById(R.id.button);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				sendMessage();
			}
		});
		LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
				new IntentFilter("getData"));
	}

	private void sendMessage() {
		Log.e("Pan", "Broadcasting message");
		Intent intent = new Intent("getData");
		intent.putExtra("message", "This is my message!");
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String message = intent.getStringExtra("message");
			((TextView) findViewById(R.id.textView)).setText(message);
			Log.e("Pan", "Got message: " + message);
		}
	};

	@Override
	protected void onDestroy() {
		// Unregister since the activity is about to be closed.
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
		super.onDestroy();
	}
}