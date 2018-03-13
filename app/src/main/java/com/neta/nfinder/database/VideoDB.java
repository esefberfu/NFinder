package com.neta.nfinder.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by Berfu on 1.12.2016.
 */

public class VideoDB
{
    private Context context;

    public VideoDB(Context context) {
        this.context = context;
    }

    public Cursor getFound(String value)
    {
        /* selection sorgu sonuçlarını filtreliyor. */
        final String selection = MediaStore.Video.Media.TITLE + " LIKE '%"+ value +"%' " +
                " OR " + MediaStore.Video.Media.ALBUM + " LIKE '%"+value+"%' " +
                "AND " + MediaStore.Video.Media.SIZE + " >= 100000";
        final String sort = MediaStore.Video.Media.DATE_MODIFIED + " DESC ";

        Cursor cursor = context.getContentResolver().query(getUri(), getProjection(), selection, null, sort);
        return cursor;
    }

    public Cursor getRecents()
    {
        final String selection = MediaStore.Video.Media.SIZE + " >= 100000";
        final String sort = MediaStore.Video.Media.DATE_MODIFIED + " DESC LIMIT 20";

        Cursor cursor = context.getContentResolver().query(getUri(), getProjection(), selection, null, sort);
        return cursor;
    }

    private String[] getProjection()
    {
        final String[] projection = new String[]{
                MediaStore.Video.Media._ID, //id
                MediaStore.Video.Media.TITLE, // isim
                MediaStore.Video.Media.ALBUM,
                MediaStore.Video.Media.MIME_TYPE, // video
                MediaStore.Video.Media.DATE_MODIFIED, // düzenlenme tarihi
                MediaStore.Video.Media.DATA,// dizin ve dosya ismi
                MediaStore.Video.Media.WIDTH,
                MediaStore.Video.Media.HEIGHT,
                MediaStore.Video.Media.ARTIST
        };
        return projection;
    }

    private Uri getUri()
    {
        return MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    }
}
