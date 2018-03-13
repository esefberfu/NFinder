package com.neta.nfinder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Berfu on 1.12.2016.
 */

public class _Database extends SQLiteOpenHelper {
    private static final String DBNAME = "DBNFINDER"; // Veritabanı ismi
    private static final int DBVERSION = 1;
    private Context context;

    public _Database(Context context)
    {
        super(context, DBNAME, null, DBVERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        createAppRecentTable(sqLiteDatabase);
        createAppStarredTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }

    /* Favori uygulamalar listesini tutacak tabloyu oluşturacak */
    private void createAppStarredTable(SQLiteDatabase sqLiteDatabase)
    {
        String sql = "create table " +
                AppDB.TABLE_NAME_STARRED + " (" +
                AppDB._ID + " integer, " +
                AppDB.PACKAGE_NAME + " text primary key, " +
                AppDB.NAME + " text, " +
                AppDB.STARRED + " integer" +
                ");";

        Log.e("SQL", sql);
        sqLiteDatabase.execSQL(sql);
    }

    /* Son Kullanılan uygulamalar listesini tutacak tablo oluşturacak */
    private void createAppRecentTable(SQLiteDatabase sqLiteDatabase)
    {
        String sql = "create table " +
                AppDB.TABLE_NAME_RECENT + " (" +
                AppDB._ID + " integer , " +
                AppDB.PACKAGE_NAME + " text primary key, " +
                AppDB.NAME + " text, " +
                AppDB.STARRED + " integer );";
        Log.e("SQL", sql);
        sqLiteDatabase.execSQL(sql);
    }

}
