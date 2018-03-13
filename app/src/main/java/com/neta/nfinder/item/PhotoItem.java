package com.neta.nfinder.item;

import android.database.Cursor;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by Berfu on 1.12.2016.
 */

public class PhotoItem {

    private String _ID; // ID;
    private String tittle; // başlık
    private String mimeType; // tipi mp4, jpeg ...
    private String dateModified; // düzenlenme tarihi
    private String data; // bulunduğu dizin ve ismi
    private String width;
    private String height;

    public PhotoItem(Cursor cursor)
    {
        this._ID = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
        this.tittle = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE));
        this.mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));
        this.dateModified = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED));
        this.data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        this.width = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.WIDTH));
        this.height = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT));

    }

    public String getWidth()
    {
        return width;
    }

    public String getHeight()
    {
        return height;
    }

    public String get_ID() {
        return _ID;
    }

    public String getTittle() {
        return tittle;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getDateModified() {
        return dateModified;
    }

    public String getData() {
        return data;
    }

    public String getAlbum()
    {
        File f = new File(data).getParentFile();
        return f.getName();
    }
}

