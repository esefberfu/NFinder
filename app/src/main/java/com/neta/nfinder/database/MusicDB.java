package com.neta.nfinder.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by Berfu on 1.12.2016.
 */

public class MusicDB
{
    private Context context;
    private String value;

    public MusicDB(Context context) {
        this.context = context;
    }

    public Cursor getFound(String value)
    {
        final String sortOrder = MediaStore.Audio.AudioColumns.TITLE
                + " COLLATE LOCALIZED ASC";

        Cursor cursor = context.getContentResolver().query(getUri(),
                getProjection(), getSelection(value), null, sortOrder);

        return cursor;
    }

    public Cursor getRecents()
    {
        final String sortOrder = MediaStore.Audio.AudioColumns.DATE_MODIFIED
                + " COLLATE LOCALIZED DESC LIMIT 20";

        Cursor cursor = context.getContentResolver().query(getUri(),
                getProjection(), getSelection(), null, sortOrder);

        return cursor;
    }

    private String[] getProjection()
    {
        /* Projection veritabanından gelecek kolonları tutuyor.*/
        final String[] projection = new String[] {
                MediaStore.Audio.Media._ID, //id
                MediaStore.Audio.Media.TITLE, //isim
                MediaStore.Audio.Media.ALBUM, // albüm
                MediaStore.Audio.Media.ARTIST, // sanatçı
                MediaStore.Audio.Media.DATA, // dizin ve dosya ismi
                MediaStore.Audio.Media.COMPOSER,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.TRACK,
                MediaStore.Audio.Media.YEAR,
                MediaStore.Audio.Media.DATE_MODIFIED
        };
        return projection;
    }

    private String getSelection(String value)
    {
        /* selection sorgu sonuçlarını filtreliyor. */
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
                + " AND " + MediaStore.Audio.Media.MIME_TYPE + "= 'audio/mpeg'"
                + " AND " + MediaStore.Audio.Media.DURATION + " > 60000"
                + " AND " + MediaStore.Audio.Media.TITLE + " LIKE '%"+value+"%' " +
                " OR " + MediaStore.Audio.Media.ALBUM + " LIKE '%"+value+"%' " +
                " OR " + MediaStore.Audio.Media.ARTIST + " LIKE '%"+value+"%' ";
        return selection;
    }

    private String getSelection()
    {
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
                + " AND " + MediaStore.Audio.Media.MIME_TYPE + "= 'audio/mpeg'"
                + " AND " + MediaStore.Audio.Media.DURATION + " > 60000";

        return selection;
    }

    private Uri getUri()
    {
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }

}
