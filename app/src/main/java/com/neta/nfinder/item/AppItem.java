package com.neta.nfinder.item;

import android.database.Cursor;
import android.util.Log;

import com.neta.nfinder.database.AppDB;
import com.neta.nfinder.database._Cursor;

/**
 * Created by Berfu on 1.12.2016.
 */

public class AppItem {
    private String appName; // Uygulama ismi
    private String packageName; // paket ismi(package name)
    private int starred;
    private int _id;

    public AppItem(Cursor cursor)
    {
        this._id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(AppDB._ID)));
        this.packageName = cursor.getString(cursor.getColumnIndex(AppDB.PACKAGE_NAME));
        this.appName = cursor.getString(cursor.getColumnIndex(AppDB.NAME));
        this.starred = Integer.valueOf(cursor.getString(cursor.getColumnIndex(AppDB.STARRED)));
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public int getStarred() {
        return starred;
    }

    public int get_id()
    {
        return _id;
    }
}
