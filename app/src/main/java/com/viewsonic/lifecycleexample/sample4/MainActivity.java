package com.viewsonic.lifecycleexample.sample4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
		bindMessageManager();
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				sendMessage();
			}
		});
		subscribe();
	}

	private void subscribe() {
		mReceiver.mData.observe(this, new Observer<String>() {
			@Override
			public void onChanged(String s) {
				((TextView) findViewById(R.id.textView)).setText(s);
			}
		});
	}

	private void bindMessageManager() {
		MessageManager.bindMessageReceiverIn(this, mReceiver, getApplicationContext());
	}

	private void sendMessage() {
		Log.e("Pan", "Broadcasting message");
		Intent intent = new Intent("custom-event-name");
		// You can also include some extra data.
		intent.putExtra("message", "This is my message!");
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

}