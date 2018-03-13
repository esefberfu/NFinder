package com.neta.nfinder.settings;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.neta.nfinder.R;
import com.neta.nfinder.activity.MainActivity;
import com.neta.nfinder.activity.TutorialActivity;

/**
 * Created by brefu on 24.12.2016.
 */

public class SettingsActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		final Settings settings = new Settings(getApplicationContext());

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(getResources().getColor(R.color.blue));
		}

		TextView recentSettings = (TextView) findViewById(R.id.recent_settings);
		recentSettings.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), SettingsRecentSortActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});

		TextView finderSettings = (TextView) findViewById(R.id.finder_settings);
		finderSettings.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), SettingsFinderSortActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});

		final Switch oneClickCall = (Switch) findViewById(R.id.one_click_call);
		oneClickCall.setChecked(settings.isOneTouchCall());
		oneClickCall.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				settings.setOneTouchCall(oneClickCall.isChecked());
			}
		});

		final Switch showNotification = (Switch) findViewById(R.id.create_notification);
		showNotification.setChecked(settings.getBoolean(Settings.Key.IS_NOTIFICATION));
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

		LinearLayout assist = (LinearLayout) findViewById(R.id.default_assist);
		assist.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				Intent i = new Intent(android.provider.Settings.ACTION_VOICE_INPUT_SETTINGS);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);

			}
		});

		TextView tutorial = (TextView) findViewById(R.id.start_tutorial);
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

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}
}
