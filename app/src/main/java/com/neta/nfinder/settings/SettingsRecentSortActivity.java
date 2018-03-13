package com.neta.nfinder.settings;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.neta.nfinder.R;
import com.neta.nfinder.settings.sort.Item;
import com.neta.nfinder.settings.sort.ItemSortView;

/**
 * Created by brefu on 24.12.2016.
 */

public class SettingsRecentSortActivity extends AppCompatActivity
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
		setContentView(R.layout.activity_settings_recent);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(getResources().getColor(R.color.blue));
		}

		parent = (LinearLayout) findViewById(R.id.parent);

		sortView = new ItemSortView(getApplicationContext());
		for(int i=0; i<5;i++)
		{
			/* sortView içinde boş viewlar oluşturuluyor.
			 * Daha sonra bunların içine farklı sıralarda nesneler eklenecek
			 */
			sortView.addView(new View(getApplicationContext()));
		}
		parent.addView(sortView);

		final Settings settings = new Settings(getApplicationContext());

		int doc_index = settings.getViewIndex(Settings.Key.RECENT_DOC);
		doc = new Item(getApplicationContext(), sortView, Settings.Key.RECENT_DOC, getResources().getString(R.string.doc));
		sortView.removeViewAt(doc_index);
		sortView.addView(doc, doc_index);

		int music_index = settings.getViewIndex(Settings.Key.RECENT_MUSIC);
		music = new Item(getApplicationContext(), sortView, Settings.Key.RECENT_MUSIC, getResources().getString(R.string.music));
		sortView.removeViewAt(music_index);
		sortView.addView(music, music_index);

		int photo_index = settings.getViewIndex(Settings.Key.RECENT_PHOTO);
		photo = new Item(getApplicationContext(), sortView, Settings.Key.RECENT_PHOTO, getResources().getString(R.string.photo));
		sortView.removeViewAt(photo_index);
		sortView.addView(photo, photo_index);

		int video_index = settings.getViewIndex(Settings.Key.RECENT_VIDEO);
		video = new Item(getApplicationContext(), sortView, Settings.Key.RECENT_VIDEO, getResources().getString(R.string.video));
		sortView.removeViewAt(video_index);
		sortView.addView(video, video_index);

		int file_index = settings.getViewIndex(Settings.Key.RECENT_FILE);
		file = new Item(getApplicationContext(), sortView, Settings.Key.RECENT_FILE, getResources().getString(R.string.file));
		sortView.removeViewAt(file_index);
		sortView.addView(file, file_index);


		CheckBox isDoc = (CheckBox) findViewById(R.id.checkbox_doc);
		isDoc.setChecked(settings.getBoolean(Settings.Key.IS_RECENT_DOC));
		isDoc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				settings.setBoolean(Settings.Key.IS_RECENT_DOC, isChecked);
			}
		});

		CheckBox isMusic = (CheckBox) findViewById(R.id.checkbox_music);
		isMusic.setChecked(settings.getBoolean(Settings.Key.IS_RECENT_MUSIC));
		isMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				settings.setBoolean(Settings.Key.IS_RECENT_MUSIC, isChecked);
			}
		});

		CheckBox isPhoto = (CheckBox) findViewById(R.id.checkbox_photo);
		isPhoto.setChecked(settings.getBoolean(Settings.Key.IS_RECENT_PHOTO));
		isPhoto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				settings.setBoolean(Settings.Key.IS_RECENT_PHOTO, isChecked);
			}
		});

		CheckBox isVideo = (CheckBox) findViewById(R.id.checkbox_video);
		isVideo.setChecked(settings.getBoolean(Settings.Key.IS_RECENT_VIDEO));
		isVideo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				settings.setBoolean(Settings.Key.IS_RECENT_VIDEO, isChecked);
			}
		});

		CheckBox isFile = (CheckBox) findViewById(R.id.checkbox_file);
		isFile.setChecked(settings.getBoolean(Settings.Key.IS_RECENT_FILE));
		isFile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				settings.setBoolean(Settings.Key.IS_RECENT_FILE, isChecked);
			}
		});

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
