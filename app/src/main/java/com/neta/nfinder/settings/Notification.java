package com.neta.nfinder.settings;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.neta.nfinder.R;
import com.neta.nfinder.activity.MainActivity;

/**
 * Created by brefu on 25.12.2016.
 */

public class Notification
{
	private Context context;
	private NotificationManager manager;

	public Notification(Context context)
	{
		this.context = context;
		manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public void createNotif()
	{
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
		builder.setLargeIcon(b);
		builder.setSmallIcon(R.drawable.icon);
		builder.setContentTitle(context.getString(R.string.app_name));
		builder.setContentText(context.getString(R.string.find_everything));
		builder.setAutoCancel(false);
		builder.setOngoing(true);

		Intent notificationIntent = new Intent(context, MainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(contentIntent);

		// Add as notification
		manager.notify(0, builder.build());
	}

	public void cancelNotif()
	{
		manager.cancel(0);
	}
}
