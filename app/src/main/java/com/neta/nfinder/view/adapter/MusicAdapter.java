package com.neta.nfinder.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.neta.nfinder.item.MusicItem;
import com.neta.nfinder.view.itemView.MusicItemListView;

/**
 * Created by Berfu on 4.12.2016.
 */

public class MusicAdapter extends CursorAdapter {
    public MusicAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return new MusicItemListView(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MusicItem musicItem = new MusicItem(cursor);
        MusicItemListView musicItemListView = (MusicItemListView) view;
        musicItemListView.setMusicItem(musicItem);
    }
}
