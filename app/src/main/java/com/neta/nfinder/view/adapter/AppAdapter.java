package com.neta.nfinder.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.neta.nfinder.item.AppItem;
import com.neta.nfinder.view.itemView.AppItemGridView;

/**
 * Created by Berfu on 15.11.2009.
 */

public class AppAdapter extends CursorAdapter
{

    public AppAdapter(Context context, Cursor c)
    {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup)
    {
        return new AppItemGridView(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        AppItem appItem = new AppItem( cursor );

        AppItemGridView appItemGridView = (AppItemGridView) view;
        appItemGridView.setAppItem(appItem);
    }
}
