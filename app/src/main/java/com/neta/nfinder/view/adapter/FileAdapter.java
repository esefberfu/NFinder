package com.neta.nfinder.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.neta.nfinder.item.FileItem;
import com.neta.nfinder.view.itemView.FileItemListView;

/**
 * Created by Berfu on 6.12.2016.
 */

public class FileAdapter extends CursorAdapter
{
    public FileAdapter(Context context, Cursor c)
    {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup)
    {
        return new FileItemListView(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        FileItem fileItem = new FileItem(cursor);

        FileItemListView fileItemListView = (FileItemListView) view;
        fileItemListView.setFileItem(fileItem);
    }
}
