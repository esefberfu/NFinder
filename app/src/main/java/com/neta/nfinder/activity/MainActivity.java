package com.neta.nfinder.activity;

import android.Manifest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.neta.nfinder.R;
import com.neta.nfinder.pager.PagerAdapter;
import com.neta.nfinder.settings.Notification;
import com.neta.nfinder.settings.Settings;
import com.neta.nfinder.settings.SettingsAboutActivity;
import com.neta.nfinder.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity
{
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.e("ONCREATE","MAINACTIVITY-START");
		super.onCreate(savedInstanceState);
		setStatusBarColor();
		setContentView(R.layout.activity_main);
		createThis();
		Log.e("ONCREATE","MAINACTIVITY-FINISH");
	}

	private void createThis()
	{
        /* tabLayout sekmelerin listelendiği yer.
		 * Burada view isimleri listelenecek
         */
		if(requestPermission())
		{
			Settings settings = new Settings(getApplicationContext());
			Notification notification = new Notification(getApplicationContext());
			if(settings.getBoolean(Settings.Key.IS_NOTIFICATION))
			{
				notification.createNotif();
			}

			final ImageView optionButton = (ImageView) findViewById(R.id.button_options);
			optionButton.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					PopupMenu popup = new PopupMenu(getApplicationContext(), v);
					popup.inflate(R.menu.popup_menu);
					popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
					{
						@Override
						public boolean onMenuItemClick(MenuItem item)
						{
							if (item.getTitle().equals(getResources().getString(R.string.settings)))
							{
								Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivityForResult(intent, 100);
								finish();
							}
							else if (item.getTitle().equals(getResources().getString(R.string.about)))
							{
								Intent intent = new Intent(getApplicationContext(), SettingsAboutActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(intent);
							}
							else if(item.getTitle().equals(getString(R.string.share_app)))
							{
								Intent intent = new Intent(Intent.ACTION_SEND);
								intent.setType("text/plain");
								String link = "https://play.google.com/store/apps/details?id=com.neta.nfinder";
								intent.putExtra(Intent.EXTRA_TEXT, link);
								startActivity(Intent.createChooser(intent, "Share App Link"));
							}
							return true;
						}
					});
					popup.show();
				}
			});

			final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
			tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.finder)));
			tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.recents)));
			tabLayout.setTabTextColors(Color.parseColor("#60FFFFFF"), Color.parseColor("#BBFFFFFF"));
			tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
			tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#BBFFFFFF"));


			viewPager = (ViewPager) findViewById(R.id.pager);
			final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
			viewPager.setAdapter(adapter);

			viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
			tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
			{
				@Override
				public void onTabSelected(TabLayout.Tab tab)
				{
					viewPager.setCurrentItem(tab.getPosition());
				}

				@Override
				public void onTabUnselected(TabLayout.Tab tab)
				{

				}

				@Override
				public void onTabReselected(TabLayout.Tab tab)
				{
				}
			});
		}


        /*
        * View pager ekranda kaydırarak sayfalarda geçiş yapmayı sağlar.
        * */

	}

	private boolean requestPermission()
	{

				if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == -1
						|| ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == -1
						|| ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == -1
						|| ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_NETWORK_STATE) == -1)
				{
					Intent intent = new Intent(this, RequestActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					finish();
					return false;
				}
				else
				{
					Settings settings = new Settings(getApplicationContext());
					if(settings.getFirstOpen() == 1)
					{
						Intent intent = new Intent(this, RequestActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						finish();
						return false;
					}
				}
		return true;
	}

	private void setStatusBarColor()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(getResources().getColor(R.color.blue));
		}
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		finish();
	}
}
