package com.neta.nfinder.item;

import android.database.Cursor;
import android.provider.ContactsContract;

/**
 * Created by Berfu on 1.12.2016.
 */

public class ContactItem {
    private String _ID; // ID
    private String contact_ID;
    private String contactName;

    public ContactItem(Cursor cursor) {
        this._ID = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
        this.contact_ID = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
        this.contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
    }

    public String getContact_ID() {
        return contact_ID;
    }

    public String getContactName() {
        return contactName;
    }
}
