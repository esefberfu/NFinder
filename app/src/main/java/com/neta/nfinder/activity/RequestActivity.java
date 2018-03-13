package com.neta.nfinder.activity;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;

import com.neta.nfinder.R;
import com.neta.nfinder.settings.Settings;

import java.util.Set;


/**
 * Created by brefu on 23.12.2016.
 */

public class RequestActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(getResources().getColor(R.color.blue));
		}

		requestPerm();

		Button btn = (Button) findViewById(R.id.start_app);
		btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) == -1
						|| ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == -1
						|| ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == -1
						|| ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_NETWORK_STATE) == -1)
				{
					requestPerm();
				}
				else
				{
					Intent intent = new Intent(getApplicationContext(), MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					finish();
				}
			}
		});

		final Settings settings = new Settings(getApplicationContext());
		final Switch showNotification = (Switch) findViewById(R.id.create_notification);
		showNotification.setChecked(false);
		showNotification.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				boolean bool = showNotification.isChecked();
				settings.setBoolean(Settings.Key.IS_NOTIFICATION, bool);
				com.neta.nfinder.settings.Notification notification = new com.neta.nfinder.settings.Notification(getApplicationContext());
				if(bool)
				{

					notification.createNotif();
				}
				else
				{
					notification.cancelNotif();
				}
			}
		});

		Button defaultApp = (Button) findViewById(R.id.default_assist);
		defaultApp.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(android.provider.Settings.ACTION_VOICE_INPUT_SETTINGS);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
			}
		});

		Button tutorial = (Button) findViewById(R.id.start_tutorial);
		tutorial.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(getApplicationContext(), TutorialActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
			}
		});

	}

	private void requestPerm()
	{
		ActivityCompat.requestPermissions(this, new String[]
				{
						Manifest.permission.READ_CONTACTS,
						Manifest.permission.CALL_PHONE,
						Manifest.permission.WRITE_EXTERNAL_STORAGE,
						Manifest.permission.ACCESS_NETWORK_STATE
				}, 1);
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		finish();
	}
}
