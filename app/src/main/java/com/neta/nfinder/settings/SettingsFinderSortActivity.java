package com.neta.nfinder.settings;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.neta.nfinder.R;
import com.neta.nfinder.settings.sort.Item;
import com.neta.nfinder.settings.sort.ItemSortView;

/**
 * Created by brefu on 24.12.2016.
 */

public class SettingsFinderSortActivity extends AppCompatActivity
{
	LinearLayout parent;
	ItemSortView sortView;
	Item contact;
	Item app;
	Item doc;
	Item music;
	Item photo;
	Item video;
	Item file;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_finder);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(getResources().getColor(R.color.blue));
		}

		parent = (LinearLayout) findViewById(R.id.parent);

		sortView = new ItemSortView(getApplicationContext());
		for(int i=0; i<7;i++)
		{
			/* sortView içinde boş viewlar oluşturuluyor.
			 * Daha sonra bunların içine farklı sıralarda nesneler eklenecek
			 */
			sortView.addView(new View(getApplicationContext()));
		}
		parent.addView(sortView);

		Settings settings = new Settings(getApplicationContext());

		int contact_index = settings.getViewIndex(Settings.Key.FINDER_CONTACT);
		contact = new Item(getApplicationContext(), sortView, Settings.Key.FINDER_CONTACT, getResources().getString(R.string.contacts));
		sortView.removeViewAt(contact_index);
		sortView.addView(contact, contact_index);

		int app_index = settings.getViewIndex(Settings.Key.FINDER_APP);
		app = new Item(getApplicationContext(), sortView, Settings.Key.FINDER_APP, getResources().getString(R.string.app));
		sortView.removeViewAt(app_index);
		sortView.addView(app, app_index);

		int doc_index = settings.getViewIndex(Settings.Key.FINDER_DOC);
		doc = new Item(getApplicationContext(), sortView, Settings.Key.FINDER_DOC, getResources().getString(R.string.doc));
		sortView.removeViewAt(doc_index);
		sortView.addView(doc, doc_index);

		int music_index = settings.getViewIndex(Settings.Key.FINDER_MUSIC);
		music = new Item(getApplicationContext(), sortView, Settings.Key.FINDER_MUSIC, getResources().getString(R.string.music));
		sortView.removeViewAt(music_index);
		sortView.addView(music, music_index);

		int photo_index = settings.getViewIndex(Settings.Key.FINDER_PHOTO);
		photo = new Item(getApplicationContext(), sortView, Settings.Key.FINDER_PHOTO, getResources().getString(R.string.photo));
		sortView.removeViewAt(photo_index);
		sortView.addView(photo, photo_index);

		int video_index = settings.getViewIndex(Settings.Key.FINDER_VIDEO);
		video = new Item(getApplicationContext(), sortView, Settings.Key.FINDER_VIDEO, getResources().getString(R.string.video));
		sortView.removeViewAt(video_index);
		sortView.addView(video, video_index);

		int file_index = settings.getViewIndex(Settings.Key.FINDER_FILE);
		file = new Item(getApplicationContext(), sortView, Settings.Key.FINDER_FILE, getResources().getString(R.string.file));
		sortView.removeViewAt(file_index);
		sortView.addView(file, file_index);


	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		finish();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		finish();
	}
}
