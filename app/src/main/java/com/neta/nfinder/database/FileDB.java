package com.neta.nfinder.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Path;
import android.graphics.drawable.shapes.PathShape;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Berfu on 1.12.2016.
 */

public class FileDB
{
	Context context;

	public static final int VIDEO = 0;
	public static final int PHOTO = 1;

	public FileDB(Context context)
	{
		this.context = context;
	}

	public Cursor getFound(String value)
	{
		Cursor cursor = context.getContentResolver().query(getUri(), getProjection(), getSelection(value), null, getTitleSort());
		return cursor;
	}

	public Cursor getRecent()
	{
		Cursor cursor = context.getContentResolver().query(getUri(), getProjection(), getSelection(), null, getDateSort());
		return cursor;
	}

	private Uri getUri()
	{
		return MediaStore.Files.getContentUri("external");
	}

	private String[] getProjection()
	{
		String projection[] =
				{
						MediaStore.Files.FileColumns._ID,
						MediaStore.Files.FileColumns.TITLE,
						MediaStore.Files.FileColumns.DATE_MODIFIED,
						MediaStore.Files.FileColumns.DATA,
						MediaStore.Files.FileColumns.MIME_TYPE,
						MediaStore.Files.FileColumns.SIZE
				};
		return projection;
	}

	private String getTitleSort()
	{
		String sort = MediaStore.Files.FileColumns.TITLE + " ASC ";
		return sort;
	}

	private String getDateSort()
	{
		String sort = MediaStore.Files.FileColumns.DATE_MODIFIED + " DESC ";
		return sort;
	}

	private String getSelection(String value)
	{
		String selection = MediaStore.Files.FileColumns.TITLE + " LIKE '%" + value + "%' AND " +
				MediaStore.Files.FileColumns.SIZE + " > 1000 AND " +
				" ( " + getFileExt() + " ) ";
		return selection;
	}

	private String getSelection()
	{
		String selection = getFileExt();
		Log.e("SQL", selection);
		return selection;
	}

	private String getFileExt()
	{
		StringBuilder ext = new StringBuilder();

		for (int i = 0; i < fileExtList.length; i++)
		{
			if (i == fileExtList.length - 1)
			{
				ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%" + fileExtList[i] + "' ");
				continue;
			}
			ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%" + fileExtList[i] + "' OR ");
		}

		return ext.toString();
	}

	public void deleteFile(final String _id, final String path)
	{
		AsyncTask task = new AsyncTask()
		{
			@Override
			protected Object doInBackground(Object[] params)
			{
				String where = MediaStore.Files.FileColumns._ID + " = " + _id;
				ContentResolver contentResolver = context.getContentResolver();

				contentResolver.delete(getUri(), where, null);
				return null;
			}
		};
		task.execute();
		/*
        File f = new File(path);
        f.delete();
        */
	}

	public void renameFile(final String _id, final String path, final String name, final String ext)
	{
		File source = new File(path);
		File dest = new File(source.getParent()+"/"+name+ext);
		source.renameTo(dest);

		ContentResolver contentResolver = context.getContentResolver();

		ContentValues values = new ContentValues();
		values.put(MediaStore.Files.FileColumns.TITLE, name);
		values.put(MediaStore.Files.FileColumns.DATA, dest.getAbsolutePath());

		String where = MediaStore.Files.FileColumns._ID + " = " + _id;
		contentResolver.update(getUri(), values, where, null);
	}


	private String fileExtList[] =
			{
					".zip",
					".rar",
					".7z",
					".tar",
					".tar.gz",
					".exe",
					".msi",
					".app",
					".apk",
					".iso",
					".img",
					".bin",
					".cue",
					".iso",
					".mdf",
					".dmg",
					".3dm",
					".3ds",
					".max",
					".obj",
					".psd",
					".dds",
					".ai",
					".eps",
					".ps",
					".svg",
					".indd",
					".pct",
					".sql",
					".dwg",
					".dxf",
					".gpx",
					".kml",
					".kmz",
					".html",
					".css",
					".js",
					".jsp",
					".php",
					".c",
					".cpp",
					".cp",
					".java",
					".jar",
					".pl",
					".py",
					".torrent",
					".est",
					".wgom",
					".skp",
					".max",
					".sldprt",
					".dwfx",
					".rvt",
					".3ds",
					".c4d",
					".obj",
					".brd",
					".edrw",
					".shx",
					".idw",
					".cad",
					".cdr",
					".swf",
					".cdx",
					".ait"
			};
}
