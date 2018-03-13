package com.neta.nfinder.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by Berfu on 1.12.2016.
 */

public class PhotoDB {
    private Context context;

    public PhotoDB(Context context)
    {
        this.context = context;
    }

    public Cursor getFound(String value)
    {
        Cursor cursor = null;
        /* selection sorgu sonuçlarını filtreliyor. */
        final String selection = MediaStore.Images.Media.TITLE + " LIKE '%"+ value +"%' " +
                " OR " + MediaStore.Images.Media.DATA + " LIKE '%"+value+"%' " +
                " AND " + MediaStore.Images.Media.SIZE + " >= 100000";

        final String sort = MediaStore.Images.Media.DATE_MODIFIED + " DESC ";

        cursor = context.getContentResolver().query(getUri(), getProjection(), selection, null, sort);

        return cursor;
    }

    public Cursor getRecents()
    {
        Cursor cursor = null;

        /* selection sorgu sonuçlarını filtreliyor. */
        final String selection = MediaStore.Images.Media.SIZE + " >= 100000";
        final String sort = MediaStore.Images.Media.DATE_MODIFIED + " DESC LIMIT 20";

        cursor = context.getContentResolver().query(getUri(), getProjection(), selection, null, sort);

        return cursor;
    }

    private String[] getProjection()
    {
        final String[] projection = new String[] {
                MediaStore.Images.Media._ID, //id
                MediaStore.Images.Media.TITLE, // isim
                MediaStore.Images.Media.MIME_TYPE, // video
                MediaStore.Images.Media.DATE_MODIFIED, // düzenlenme tarihi
                MediaStore.Images.Media.DATA,// dizin ve dosya ismi
                MediaStore.Images.Media.WIDTH,
                MediaStore.Images.Media.HEIGHT
        };
        return projection;
    }

    private Uri getUri()
    {
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }
}
