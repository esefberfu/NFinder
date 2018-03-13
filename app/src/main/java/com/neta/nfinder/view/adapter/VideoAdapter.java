package com.neta.nfinder.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.neta.nfinder.item.VideoItem;
import com.neta.nfinder.view.itemView.VideoItemListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Berfu on 4.12.2016.
 */

public class VideoAdapter extends CursorAdapter
{
    public VideoAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup)
    {
        return new VideoItemListView(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        VideoItem videoItem = new VideoItem(cursor);

        VideoItemListView videoItemListView = (VideoItemListView) view;
        videoItemListView.setVideoItem(videoItem);
    }
}
