package com.neta.nfinder.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Berfu on 2.12.2016.
 */

public class Settings {
    public static class Key
    {
        public static final String RECENT_CONTACT= "recentContact";
        public static final String RECENT_APP= "recentApp";
        public static final String RECENT_DOC= "recentDoc";
        public static final String RECENT_MUSIC= "recentMusic";
        public static final String RECENT_PHOTO= "recentPhoto";
        public static final String RECENT_VIDEO= "recentVideo";
        public static final String RECENT_FILE= "recentFile";

        public static final String IS_RECENT_CONTACT= "isRecentContact";
        public static final String IS_RECENT_APP= "isRecentApp";
        public static final String IS_RECENT_DOC= "isRecentDoc";
        public static final String IS_RECENT_MUSIC= "isRecentMusic";
        public static final String IS_RECENT_PHOTO= "isRecentPhoto";
        public static final String IS_RECENT_VIDEO= "isRecentVideo";
        public static final String IS_RECENT_FILE= "isRecentFile";

        public static final String FINDER_CONTACT = "finderConatct";
        public static final String FINDER_APP = "finderApp";
        public static final String FINDER_DOC = "finderDoc";
        public static final String FINDER_MUSIC = "finderMusic";
        public static final String FINDER_PHOTO = "finderPhoto";
        public static final String FINDER_VIDEO = "finderVideo";
        public static final String FINDER_FILE = "finderFile";

        public static final String IS_NOTIFICATION = "isNotification";

    }
    public static final String SD_CARD_PATH = Environment.getExternalStorageDirectory().getPath()+"/NFinder";
    private int FIRST_OPEN; // ilk açılış bilgisi

    private Context context;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public Settings(Context context)
    {
        this.context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();

        FIRST_OPEN = pref.getInt("FIRST_OPEN",1);
        /* if bloğu FIRST_OPEN değerini ayarlardan bakacak eğerki 1 değeri dönerse uygulama ilk defa açılıyordur
         * ilk defa açıldığından MainActivity ilk başlangıcında yapması gerekn bazı işlemleri gerçekleştirecek
         * Buarada if bloğu içinde FIRST_OPEN 0 olarak değiştiriliyor ve bundan sonraki uygulama açılışlarında
         * bu ayarların yapılmış olduğu belirtiliyor.
         */
        if(FIRST_OPEN == 1) {
            FIRST_OPEN = 1;
            editor.putInt("FIRST_OPEN",0);
            editor.putInt("_ID", 0);
            /* isFindGroup Aramada nelerin aranıp aranmayacağıı tutar */
            editor.putBoolean("isFindApp",true);
            editor.putBoolean("isFindContact",true);
            editor.putBoolean("isFindMusic",true);
            editor.putBoolean("isFindVideo",true);
            editor.putBoolean("isFindPhoto",true);
            editor.putBoolean("isFindFile",true);
            editor.putBoolean("isFindDoc",true);

            /* Recent Settings recent fragmentinda kategorilerin hangi sırada gözükeceği bilgisini tutar. */
            editor.putInt(Key.RECENT_DOC, 0);
            editor.putInt(Key.RECENT_MUSIC, 1);
            editor.putInt(Key.RECENT_PHOTO, 2);
            editor.putInt(Key.RECENT_VIDEO, 3);
            editor.putInt(Key.RECENT_FILE, 4);

            /* Finder Settings finder fragmentinda kategorilerin hangi sırada gözükeceği bilgisini tutar. */
            editor.putInt(Key.FINDER_CONTACT, 0);
            editor.putInt(Key.FINDER_APP, 1);
            editor.putInt(Key.FINDER_DOC, 2);
            editor.putInt(Key.FINDER_MUSIC, 3);
            editor.putInt(Key.FINDER_PHOTO, 4);
            editor.putInt(Key.FINDER_VIDEO, 5);
            editor.putInt(Key.FINDER_FILE, 6);
            editor.putBoolean("oneTouchCall", true);
            editor.putBoolean(Key.IS_NOTIFICATION, false);


            editor.commit();
        }
    }

    public int getFirstOpen() {
        return FIRST_OPEN;
    }

    public void setFirstOpen(int firstOpen) {
        FIRST_OPEN = firstOpen;
    }

    public int getID()
    {
        int id = pref.getInt("_ID",0);
        int next_id = id + 1;
        editor.putInt("_ID", next_id );
        editor.commit();
        return id;
    }



    /* isFINDGROUP ***********************************************************************/
    public boolean isFindApp()
    {
        return pref.getBoolean("isFindApp",true);
    }

    public boolean isFindContact()
    {
        return pref.getBoolean("isFindContact",true);
    }

    public boolean isFindMusic()
    {
        return pref.getBoolean("isFindMusic",true);
    }

    public boolean isFindPhoto()
    {
        return pref.getBoolean("isFindPhoto",true);
    }

    public boolean isFindVideo()
    {
        return pref.getBoolean("isFindVideo",true);
    }

    public boolean isFindFile()
    {
        return pref.getBoolean("isFindFile",true);
    }

    public boolean isFindDoc()
    {
        return pref.getBoolean("isFindDoc",true);
    }

    public void setFindApp(boolean is)
    {
        editor.putBoolean("isFindApp", is);
        editor.commit();
    }

    public void setFindContact(boolean is)
    {
        editor.putBoolean("isFindContact", is);
        editor.commit();
    }

    public void setFindMusic(boolean is)
    {
        editor.putBoolean("isFindMusic", is);
        editor.commit();
    }

    public void setFindPhoto(boolean is)
    {
        editor.putBoolean("isFindPhoto", is);
        editor.commit();
    }

    public void setFindVideo(boolean is)
    {
        editor.putBoolean("isFindVideo", is);
        editor.commit();
    }

    public void setFindFile(boolean is)
    {
        editor.putBoolean("isFindFile", is);
        editor.commit();
    }

    public void setFindDoc(boolean is)
    {
        editor.putBoolean("isFindDoc",is);
        editor.commit();
    }
    /* END isFINDGROUP ***********************************************************************/


    /* SETTINGS */
    public int getViewIndex(String key)
    {
        return pref.getInt(key, -1);
    }

    public void setViewIndex(String key, int index)
    {
        editor.putInt(key, index);
        editor.commit();
    }

    public void setBoolean(String key, boolean is)
    {
        editor.putBoolean(key, is);
        editor.commit();
    }

    public boolean getBoolean(String key)
    {
        return pref.getBoolean(key, true);
    }

    public boolean isOneTouchCall()
    {
        return pref.getBoolean("oneTouchCall", true);
    }

    public void setOneTouchCall(boolean is)
    {
        editor.putBoolean("oneTouchCall", is);
        editor.commit();
    }
}
