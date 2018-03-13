package com.neta.nfinder.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.neta.nfinder.item.PhotoItem;
import com.neta.nfinder.view.itemView.PhotoItemListView;

/**
 * Created by Berfu on 4.12.2016.
 */

public class PhotoAdapter extends CursorAdapter {
    public PhotoAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup)
    {
        return new PhotoItemListView(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        PhotoItem photoItem = new PhotoItem(cursor);

        PhotoItemListView photoItemListView = (PhotoItemListView) view;
        photoItemListView.setPhotoItem(photoItem);

    }
}
