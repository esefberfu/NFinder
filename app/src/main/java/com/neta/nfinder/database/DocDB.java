package com.neta.nfinder.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by Berfu on 1.12.2016.
 */

public class DocDB
{
    private Context context;



    public DocDB(Context context)
    {
        this.context = context;
    }

    public Cursor getFound(String value)
    {
        Cursor cursor = context.getContentResolver().query(getUri(), getProjection(), getSelection(value), null,
                MediaStore.Files.FileColumns.TITLE + " ASC ");

        return cursor;
    }

    public Cursor getRecents()
    {
        final String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED + " DESC LIMIT 20 ";

        Cursor cursor = context.getContentResolver().query(getUri(), getProjection(), getSelection(), null, sortOrder);

        return cursor;
    }

    /* Kolon Listesi */
    private String[] getProjection()
    {
        String[] projection = new String[]{
                MediaStore.Files.FileColumns._ID, //id
                MediaStore.Files.FileColumns.TITLE, // isim
                MediaStore.Files.FileColumns.MIME_TYPE, // belge türü
                MediaStore.Files.FileColumns.DATE_MODIFIED, // düzenlenme tarihi
                MediaStore.Files.FileColumns.SIZE, // boyut
                MediaStore.Files.FileColumns.DATA // dizin ve dosya ismi
        };

        return projection;
    }

    private String getSelection(String value)
    {
        String selection = MediaStore.Files.FileColumns.TITLE + " LIKE '%" + value + "%' AND " +
                MediaStore.Files.FileColumns.SIZE + " >= 1000 AND" +
                getDocExt();
        return selection;
    }

    private String getSelection()
    {
        String selection = MediaStore.Files.FileColumns.SIZE + " >= 1000 AND " + getDocExt();
        return selection;
    }

    private Uri getUri()
    {
        Uri uri = MediaStore.Files.getContentUri("external");
        return uri;
    }

    private String getDocExt()
    {
        StringBuilder ext = new StringBuilder();

        ext.append(" ( ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.doc' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.docx' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.dot' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.dotx' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.wps' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.wpt' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.rtf' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.ppt' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.pptx' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.pps' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.ppsx' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.pot' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.potx' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.dps' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.dpt' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.xls' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.xlsx' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.xlt' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.xltx' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.et' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.ett' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.pdf' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.hwp' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.odt' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.docm' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.xlsm' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.pptm' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.ppsm' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.csv' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.ods' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.odp' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.epub' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.ibooks' OR ");
        ext.append(MediaStore.Files.FileColumns.DATA + " LIKE '%.xps' ");
        ext.append(" ) ");

        return ext.toString();
    }
}
