package com.neta.nfinder.item;

import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by Berfu on 1.12.2016.
 */

public class FileItem
{
    private int _id;
    private String title;
    private String mimeType;
    private Long dateModified;
    private String data;
    private Long size;

    public FileItem(Cursor cursor)
    {
        _id = cursor.getInt(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID));
        title = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE));
        data = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
        mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE));
        dateModified = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_MODIFIED));
        size = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE));
    }

    public int get_id()
    {
        return _id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getMimeType()
    {
        return mimeType;
    }

    public Long getDateModified()
    {
        return dateModified;
    }

    public String getData()
    {
        return data;
    }

    public Long getSize()
    {
        return size;
    }

    public String getExt()
    {
        String ext = "";
        int l = data.length();
        for(int i = l-1; i>0; i--)
        {
            if(data.charAt(i) == '.')
            {
                ext = data.substring(i).toLowerCase();
                break;
            }
        }
        return ext;
    }
}
