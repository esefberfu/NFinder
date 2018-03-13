package com.neta.nfinder.view.itemView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.neta.nfinder.R;
import com.neta.nfinder.database._Cursor;
import com.neta.nfinder.item.MusicItem;
import com.neta.nfinder.util.Convert;
import com.neta.nfinder.util.Time;
import com.neta.nfinder.view.View;
import com.neta.nfinder.view.table.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Berfu on 4.12.2016.
 */

public class MusicItemListView extends ItemListView
{
	private MusicItem musicItem;
	private View view;

	public MusicItemListView(Context context)
	{
		super(context);
		this.view = view;
	}

	public MusicItem getMusicItem()
	{
		return musicItem;
	}

	public void setMusicItem(MusicItem musicItem)
	{
		this.musicItem = musicItem;
		setMusicIcon();
		setRow1Text();
		setRow2Text();
		hideRow(3);
	}

	public View getView()
	{
		return view;
	}

	public void setView(View view)
	{
		this.view = view;
	}

	private void setMusicIcon()
	{
		setIcon(getResources().getDrawable(R.drawable._mp3));
	}

	private void setRow1Text()
	{
		setRow1Text(musicItem.getTitle());
	}

	private void setRow2Text()
	{
		String text = musicItem.getAlbum() + " | " + musicItem.getArtist();
		setRow2Text(text);
	}
}
