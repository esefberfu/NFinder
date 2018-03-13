package com.neta.nfinder.item;

import android.database.Cursor;
import android.provider.MediaStore;

/**
 * Created by Berfu on 14.11.2016.
 * Sorgu sonucu müzik bilgilerini tutan sınıf.
 */

public class MusicItem {
    private String _ID; //Id
    private String title; // başlık
    private String album; // albüm
    private String artist; // sanatçı
    private String data; // dosya dizini ve ismi
    private String composer;
    private String duration;
    private String size;
    private String track;
    private String year;
    private String dateModified;
    private final String mimeType = "audio/mpeg"; // tipi

    public MusicItem(Cursor cursor) {
        this._ID = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
        this.title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        this.album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
        this.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        this.data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        this.composer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
        this.duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
        this.size = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
        this.track = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
        this.year = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
        this.dateModified = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED));

    }

    public String get_ID() {
        return _ID;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getData() {
        return data;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getComposer()
    {
        return composer;
    }

    public String getDuration()
    {
        return duration;
    }

    public String getSize()
    {
        return size;
    }

    public String getTrack()
    {
        return track;
    }

    public String getYear()
    {
        return year;
    }

    public String getDateModified()
    {
        return dateModified;
    }
}
