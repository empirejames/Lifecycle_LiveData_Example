package com.viewsonic.lifecycleexample.sample3;

import android.content.Intent;
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

	MessageManager mMessageManager;
	MessageReceiver mReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mMessageManager = new MessageManager();
		mReceiver = new MessageReceiver();
		Button b = findViewById(R.id.button);
		bindLocationListener();
		final TextView textView = findViewById(R.id.textView);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				sendMessage();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						textView.setText(mReceiver.data);
					}
				}, 1);
			}
		});
	}

	private void bindLocationListener() {
		MessageManager.bindMessageReceiverIn(this, mReceiver, getApplicationContext());
	}

	private void sendMessage() {
		Log.e("Pan", "Broadcasting message");
		Intent intent = new Intent("getData");
		intent.putExtra("message", "This is my message!");
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

}