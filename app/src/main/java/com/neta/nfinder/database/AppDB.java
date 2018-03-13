package com.neta.nfinder.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.neta.nfinder.settings.Settings;
import com.neta.nfinder.item.AppItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Berfu on 1.12.2016.
 */

public class AppDB {
    public static final String TABLE_NAME_STARRED = "app_starred";
    public static final String TABLE_NAME_RECENT = "app_recent";
    public static final String PACKAGE_NAME = "package_name"; /*package name kolonu*/
    public static final String NAME = "name";/*uygulama ismi kolonu*/
    public static final String STARRED = "starred"; /* favori kolonu */
    public static final String _ID = "_id";

    public static final int NOT_STARRED = 0;
    public static final int IS_STARRED = 1;

    private Context context;
    public AppDB(Context context)
    {
        this.context = context;
    }

    /* uygulamayı favoriler tablosuna ekle */
    public void addToStarred(AppItem appItem)
    {
        Settings settings = new Settings(context);

        ContentValues values = new ContentValues();
        values.put(_ID,String.valueOf(settings.getID()));
        values.put(PACKAGE_NAME,appItem.getPackageName());
        values.put(NAME,appItem.getAppName());
        values.put(STARRED,1);
        getWritableDatabase().insert(TABLE_NAME_STARRED, null, values);
    }

    /* uygulamayı son kullanılanar tablosuna ekle */
    public void addToRecent(AppItem appItem)
    {
        Settings settings = new Settings(context);

        ContentValues values = new ContentValues();
        values.put( _ID, String.valueOf(settings.getID()) );
        values.put( PACKAGE_NAME, appItem.getPackageName() );
        values.put( NAME, appItem.getAppName() );
        values.put( STARRED, appItem.getStarred() );
        getWritableDatabase().insert(TABLE_NAME_RECENT, null, values);
    }

    /* uygulamayı favoriler tablosundan kaldır */
    public void removeAppFromStarred(String packageName)
    {
        String sql = "delete from "+TABLE_NAME_STARRED+" where "+
                PACKAGE_NAME + " = '" + packageName + "';";
        getWritableDatabase().execSQL(sql);
    }

    /* uygulamayı son kullanılanlar tablosundan kaldır */
    public void removeAppFromRecent(String packageName)
    {
        String sql = "delete from "+TABLE_NAME_RECENT+" where "+
                PACKAGE_NAME + " = '" + packageName + "';";
        getWritableDatabase().execSQL(sql);
    }

    public Cursor getFound(String value)
    {
        List<String> cList = new ArrayList<>();
        cList.add(_ID);
        cList.add(PACKAGE_NAME);
        cList.add(NAME);
        cList.add(STARRED);

        _Cursor cursor = new _Cursor(cList);

        int id=0;
        for(ResolveInfo info : getAppList())
        {
            String name = info.loadLabel( getPackageManager() ).toString().toLowerCase(Locale.ENGLISH);
            Log.e("APPNAME",name);
            value.toLowerCase();
            if(name.contains(value))
            {
                String[] values = new String[cList.size()];
                values[0] = String.valueOf(id);
                values[1] = info.activityInfo.packageName;
                values[2] = info.loadLabel(getPackageManager()).toString();
                values[3] = "0";
                cursor.addRow(values);
                id++;
            }
        }

        return cursor;
    }

    /* Favorilere eklenen uygulama listesini getirir */
    public Cursor getStarred()
    {
        String sql = "select " +
                _ID + ", " +
                PACKAGE_NAME + ", " +
                NAME + ", " +
                STARRED + " " +
                " from "+TABLE_NAME_STARRED;
        _Cursor cursor = new _Cursor( getReadableDatabase().rawQuery(sql, null) );
        clearNotFoundApps(cursor);
        return cursor;
    }

    /* Son Kullanılan uygulama listesini getirir */
    public Cursor getRecent()
    {
        String sql = "select " +
                _ID + ", " +
                PACKAGE_NAME + ", " +
                NAME + ", " +
                STARRED + " " +
                " from "+TABLE_NAME_RECENT;
        _Cursor cursor = new _Cursor( getReadableDatabase().rawQuery(sql, null) );
        clearNotFoundApps(cursor);
        return cursor;
    }

    /* Son yüklenen uygulamaları getirir. */
    public Cursor getRecentInstalled()
    {
        List<String> cList = new ArrayList<>();
        cList.add(_ID);
        cList.add(PACKAGE_NAME);
        cList.add(NAME);
        cList.add(STARRED);

        List<ResolveInfo> appList = getAppList();
        _Cursor cursor = new _Cursor(cList);
        String[][] values = new String[appList.size()][4];

        int id=0;
        for (ResolveInfo info : appList)
        {
            values[id][0] = String.valueOf(id);
            values[id][1] = info.activityInfo.packageName;
            values[id][2] = info.loadLabel(getPackageManager()).toString();
            values[id][3] = "0";
            cursor.addRow(values[id]);
            id++;
        }

        return null;
    }

    /* Sistemde yüklü olmayan uygulama kayıtlarını siler. */
    private void clearNotFoundApps(_Cursor cursor)
    {
        while (cursor.moveToNext())
        {
            int i = cursor.getPosition();
            String pName = cursor.getString(cursor.getColumnIndex(PACKAGE_NAME));
            if(!isInstalled(pName))
            {
                removeAppFromStarred(pName);
                removeAppFromRecent(pName);
                cursor.deleteRow(i);
            }
        }
        cursor.move(0);
    }

    /* uygulamanın yüklü olup olmadığını sorgular */
    private boolean isInstalled(String packageName)
    {
        try
        {
            getPackageManager().getApplicationIcon(packageName);
            return true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }

    /* Package Manager nesnesini döndürür */
    private PackageManager getPackageManager()
    {
        return context.getPackageManager();
    }

    /* Uygulama listesini döndürür */
    private List<ResolveInfo> getAppList()
    {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return getPackageManager().queryIntentActivities(intent, PackageManager.GET_META_DATA);
    }

    private SQLiteDatabase getWritableDatabase()
    {
        return new _Database(context).getWritableDatabase();
    }

    private SQLiteDatabase getReadableDatabase()
    {
        return new _Database(context).getWritableDatabase();
    }


}
