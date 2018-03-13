package com.neta.nfinder.item;

import android.database.Cursor;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

/**
 * Created by Berfu on 13.11.2016.
 * Sorgu sonucu belge bilgilerini tutan sınıf.
 */

public class DocItem {

    private String _ID; // ID
    private String title; // başlık, isim
    private String mimeType; // dosya tipi (_pdf, _doc ...)
    private String size;  // boyut bilgisi kb olarak
    private String dateModified; // düzenlenme tarihi
    private String data; // dosya dizini ve ismi
    private String ext; // extension - dosya uzantısı

    public DocItem(Cursor cursor)
    {
        this._ID = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID));
        this.title = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE));
        this.mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE));
        this.size = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE));
        this.dateModified = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_MODIFIED));
        this.data = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));

        ext = "";

        int l = data.length();
        for(int i = l-1; i>0; i--)
        {
            if(data.charAt(i) == '.')
            {
                ext = data.substring(i).toLowerCase();
                break;
            }
        }

    }

    public String get_ID() {
        return _ID;
    }

    public String getTitle() {
        return title;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getSize() {
        return size;
    }

    public String getDateModified() {
        return dateModified;
    }

    public String getData() {
        return data;
    }

    public String getExt()
    {
        return ext;
    }
}
