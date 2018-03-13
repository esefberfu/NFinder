package com.neta.nfinder.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.neta.nfinder.R;
import com.neta.nfinder.item.MusicItem;
import com.neta.nfinder.util.Convert;
import com.neta.nfinder.util.Time;
import com.neta.nfinder.view.itemView.ItemDetailView;
import com.neta.nfinder.view.itemView.MusicItemListView;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by brefu on 25.12.2016.
 */

public abstract class MusicVİew extends View
{
	public MusicVİew(Context context)
	{
		super(context);
	}

	@Override
	public void onClickAction(android.view.View v)
	{
		MusicItemListView item = (MusicItemListView) v;
		MusicItem musicItem = item.getMusicItem();

		Uri uri = Uri.fromFile(new File(musicItem.getData()));

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.setDataAndType(uri, musicItem.getMimeType());
		try
		{
			getContext().startActivity(intent);
		} catch (Exception e)
		{
			Toast.makeText(getContext(), "No Application", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onLongClickAction(android.view.View v)
	{
		final MusicItemListView item = (MusicItemListView) v;
		final MusicItem musicItem = item.getMusicItem();

		Map<String, String> values = new LinkedHashMap<>();
		values.put(getResources().getString(R.string.file_name), musicItem.getTitle());
		values.put(getResources().getString(R.string.file_path), musicItem.getData());
		values.put(getResources().getString(R.string.album), musicItem.getAlbum());
		values.put(getResources().getString(R.string.artist),musicItem.getArtist());
		values.put(getResources().getString(R.string.composer), musicItem.getComposer());
		values.put(getResources().getString(R.string.track), musicItem.getTrack());
		values.put(getResources().getString(R.string.year), musicItem.getYear());
		values.put(getResources().getString(R.string.duration), Time.milisecondToMinute(Long.valueOf(musicItem.getDuration())));
		values.put(getResources().getString(R.string.size), Convert.byteToMb(Long.valueOf(musicItem.getSize())));
		values.put(getResources().getString(R.string.date_modified), Time.timeStampToDate(Long.valueOf(musicItem.getDateModified())));

		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		AlertDialog dialog = builder.create();

		final ItemDetailView detailView = new ItemDetailView(getContext(), dialog, item.getIcon(), musicItem.get_ID(), values)
		{
			@Override
			public void onClickActionShare()
			{
				share(musicItem);
			}
		};


		dialog.setView(detailView);
		dialog.show();

		dialog.setOnDismissListener(new DialogInterface.OnDismissListener()
		{
			@Override
			public void onDismiss(DialogInterface dialog)
			{
				if(detailView.isChanged)
				{
					if(detailView.fName != null)
					{
						item.setRow1Text(detailView.fName);
					}
				}
			}
		});
	}

	private void share(MusicItem musicItem)
	{
		File f=new File(musicItem.getData());
		Uri uri = Uri.parse("file://"+f.getAbsolutePath());
		Intent share = new Intent(Intent.ACTION_SEND);
		share.putExtra(Intent.EXTRA_STREAM, uri);
		share.setType("audio/*");
		share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		getContext().startActivity(Intent.createChooser(share, "Share File"));
	}
}
