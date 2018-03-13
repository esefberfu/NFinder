package com.neta.nfinder.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.neta.nfinder.item.ContactItem;
import com.neta.nfinder.view.itemView.ContactItemGridView;

/**
 * Created by Berfu on 6.12.2016.
 */

public class ContactAdapter extends CursorAdapter {
    public ContactAdapter(Context context, Cursor c)
    {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup)
    {
        return new ContactItemGridView(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        ContactItem contactItem = new ContactItem(cursor);

        ContactItemGridView contactItemGridView = (ContactItemGridView) view;
        contactItemGridView.setContactItem(contactItem);
    }
}
