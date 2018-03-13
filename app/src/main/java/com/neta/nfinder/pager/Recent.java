package com.neta.nfinder.pager;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.neta.nfinder.R;
import com.neta.nfinder.database.DocDB;
import com.neta.nfinder.database.FileDB;
import com.neta.nfinder.database.MusicDB;
import com.neta.nfinder.database.PhotoDB;
import com.neta.nfinder.database.VideoDB;
import com.neta.nfinder.settings.Settings;
import com.neta.nfinder.view.adapter.DocAdapter;
import com.neta.nfinder.view.adapter.FileAdapter;
import com.neta.nfinder.view.adapter.MusicAdapter;
import com.neta.nfinder.view.adapter.PhotoAdapter;
import com.neta.nfinder.view.adapter.VideoAdapter;

/**
 * Created by Berfu on 4.12.2016.
 */

public class Recent extends Fragment
{
    private View fragmentRecent;
    private LinearLayout parentLayout;
    private Settings settings;

    private com.neta.nfinder.view.View docView;
    private com.neta.nfinder.view.View musicView;
    private com.neta.nfinder.view.View photoView;
    private com.neta.nfinder.view.View videoView;
    private com.neta.nfinder.view.View appView;
    private com.neta.nfinder.view.View fileView;

    public Recent()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View fragmentRecent = inflater.inflate(R.layout.fragment_recents, null);
        parentLayout = (LinearLayout) fragmentRecent.findViewById(R.id.parent);
        for(int i=0; i<5; i++)
        {
            parentLayout.addView(new View(getContext()));
        }

        settings = new Settings(getContext());
        createDocView();
        createMusicView();
        createPhotoView();
        createVideoView();
        createFileView();
        execute();
        return fragmentRecent;
    }


    private void createDocView()
    {
        docView = new com.neta.nfinder.view.DocView(getContext())
        {
            Cursor cursor;

            @Override
            public void doIn()
            {
                DocDB db = new DocDB(getContext());
                cursor = db.getRecents();
            }

            @Override
            public void onPost()
            {
                DocAdapter adapter = new DocAdapter(getContext(), cursor);
                setAdapter(adapter);
                docView.setViewName(getString(R.string.document) + " ( " + adapter.getCount() + " )");
            }
        };
        int index = settings.getViewIndex(Settings.Key.RECENT_DOC);
        docView.setViewType(com.neta.nfinder.view.View.LIST);
        parentLayout.removeViewAt(index);
        parentLayout.addView(docView, index);
    }

    private void createMusicView()
    {
        musicView = new com.neta.nfinder.view.MusicVİew(getContext())
        {
            Cursor cursor;

            @Override
            public void doIn()
            {
                MusicDB db = new MusicDB(getContext());
                cursor = db.getRecents();
            }

            @Override
            public void onPost()
            {
                MusicAdapter adapter = new MusicAdapter(getContext(), cursor);
                setAdapter(adapter);
                musicView.setViewName(getString(R.string.last_added_music) + " ( " + adapter.getCount() + " )");
            }
        };
        int index = settings.getViewIndex(Settings.Key.RECENT_MUSIC);
        musicView.setViewType(com.neta.nfinder.view.View.LIST);
        parentLayout.removeViewAt(index);
        parentLayout.addView(musicView, index);
    }

    private void createPhotoView()
    {
        photoView = new com.neta.nfinder.view.PhotoView(getContext())
        {
            Cursor cursor;
            AsyncTask task = null;
            @Override
            public void doIn()
            {
                PhotoDB db = new PhotoDB(getContext());
                cursor = db.getRecents();
            }

            @Override
            public void onPost()
            {
                final PhotoAdapter adapter = new PhotoAdapter(getContext(), cursor);
                setAdapter(adapter);
                photoView.setViewName(getString(R.string.photo) + " ( " + adapter.getCount() + " )");
            }
        };
        int index = settings.getViewIndex(Settings.Key.RECENT_PHOTO);
        photoView.setViewType(com.neta.nfinder.view.View.LIST);
        parentLayout.removeViewAt(index);
        parentLayout.addView(photoView, index);
    }

    private void createVideoView()
    {
        videoView = new com.neta.nfinder.view.VideoView(getContext())
        {
            Cursor cursor;

            @Override
            public void doIn()
            {
                VideoDB db = new VideoDB(getContext());
                cursor = db.getRecents();
            }

            @Override
            public void onPost()
            {
                final VideoAdapter adapter = new VideoAdapter(getContext(), cursor);
                setAdapter(adapter);
                videoView.setViewName(getString(R.string.video) + " ( " + adapter.getCount() + " )");
            }
        };
        int index = settings.getViewIndex(Settings.Key.RECENT_VIDEO);
        videoView.setViewType(com.neta.nfinder.view.View.LIST);
        parentLayout.removeViewAt(index);
        parentLayout.addView(videoView, index);
    }

    private void createFileView()
    {
        fileView = new com.neta.nfinder.view.FileView(getContext())
        {
            Cursor cursor;
            @Override
            public void doIn()
            {
                FileDB db = new FileDB(getContext());
                cursor = db.getRecent();
            }

            @Override
            public void onPost()
            {
                FileAdapter adapter = new FileAdapter(getContext(), cursor);
                setAdapter(adapter);
                fileView.setViewName(getString(R.string.files));
            }
        };
        int index = settings.getViewIndex(Settings.Key.RECENT_FILE);
        fileView.setViewType(com.neta.nfinder.view.View.LIST);
        parentLayout.removeViewAt(index);
        parentLayout.addView(fileView, index);
    }

    private void execute()
    {
        docView.execute(settings.getBoolean(Settings.Key.IS_RECENT_DOC));
        musicView.execute(settings.getBoolean(Settings.Key.IS_RECENT_MUSIC));
        photoView.execute(settings.getBoolean(Settings.Key.IS_RECENT_PHOTO));
        videoView.execute(settings.getBoolean(Settings.Key.IS_RECENT_VIDEO));
        fileView.execute(settings.getBoolean(Settings.Key.IS_RECENT_FILE));
    }
}
