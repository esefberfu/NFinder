package com.neta.nfinder.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.neta.nfinder.item.DocItem;
import com.neta.nfinder.view.itemView.DocItemListView;

/**
 * Created by Berfu on 4.12.2016.
 */

public class DocAdapter extends CursorAdapter {

    public DocAdapter(Context context, Cursor c)
    {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup)
    {
        return new DocItemListView(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        DocItem docItem = new DocItem(cursor);
        DocItemListView docItemListView = (DocItemListView) view;
        docItemListView.setDocItem(docItem);
    }
}
