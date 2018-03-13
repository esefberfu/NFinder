package com.neta.nfinder.item;

import android.database.Cursor;
import android.provider.MediaStore;

/**
 * Created by Berfu on 16.11.2016.
 * Sorgu sonucu media bilgilerini tutan sınıf.
 */

public class VideoItem {

    private String _ID; // ID;
    private String tittle; // başlık
    private String album; // albüm
    private String mimeType; // tipi mp4, jpeg ...
    private String dateModified; // düzenlenme tarihi
    private String data; // bulunduğu dizin ve ismi
    private String width;
    private String height;
    private String artist;

    public VideoItem(Cursor cursor)
    {
        this._ID = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID));
        this.tittle = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
        this.album = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.ALBUM));
        this.mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE));
        this.dateModified = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED));
        this.data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        this.width = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.WIDTH));
        this.height = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.HEIGHT));
        this.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.ARTIST));
    }

    public String get_ID() {
        return _ID;
    }

    public String getTittle() {
        return tittle;
    }

    public String getAlbum() {
        return album;
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

    public String getWidth()
    {
        return width;
    }

    public String getHeight()
    {
        return height;
    }

    public String getArtist()
    {
        return artist;
    }
}
