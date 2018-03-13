package com.neta.nfinder.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Berfu on 1.12.2016.
 */

public class ContactDB
{
	private Context context;

	public ContactDB(Context context)
	{
		this.context = context;
		//print();
	}

	private void print()
	{
		String[] projection = {
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Phone.NUMBER,
				ContactsContract.CommonDataKinds.Phone.ACCOUNT_TYPE_AND_DATA_SET
		};
		Cursor cursor = context.getContentResolver().query(getUri(), projection, null, null, getSort());
		while (cursor.moveToNext())
		{
			Log.e("CONTACT", "ISIM: "+cursor.getString(0)+" - NUMARA: "+cursor.getString(1)+" - GRUP: "+cursor.getString(2));
		}
	}

	public Cursor getFound(String value)
	{
		String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " LIKE '%" + value + "%' " +
				" OR " + ContactsContract.CommonDataKinds.Phone.NUMBER + " LIKE '%"+value+"%' ";
		Cursor cursor = context.getContentResolver().query(getUri(), getProjection(), selection, null, getSort());
		_Cursor c = clearSameRecord(cursor);
		return c;
	}

	public Cursor getStarred()
	{
		String selection = ContactsContract.CommonDataKinds.Phone.STARRED + " = 1";
		Cursor cursor = context.getContentResolver().query(getUri(), getProjection(), selection, null, getSort());
		_Cursor c = clearSameRecord(cursor);
		return c;
	}

	public List<String> getPhoneList(String contact_id)
	{
		List<String> phoneList = new ArrayList<>();

		String projection[] = {ContactsContract.CommonDataKinds.Phone.NUMBER,
				ContactsContract.CommonDataKinds.Phone.IS_SUPER_PRIMARY};

		String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contact_id;
		Cursor cursor = context.getContentResolver().query(getUri(), projection, selection, null, null);
		while (cursor.moveToNext())
		{
			StringBuilder builder = new StringBuilder();
			builder.append(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
			builder.append(";");
			builder.append(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.IS_SUPER_PRIMARY)));

			phoneList.add(builder.toString());
		}

		List<String> result = clearSamePhone(phoneList);
		Log.e("RESULT", result.toString());
		return result;
	}

	public List<String > getWhatsAppNumber(String contact_id)
	{
		List<String> list = new ArrayList<>();
		String[] projection = {
				ContactsContract.CommonDataKinds.Phone.NUMBER
		};

		String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contact_id + "" +
				" AND " + ContactsContract.CommonDataKinds.Phone.ACCOUNT_TYPE_AND_DATA_SET + " = \"com.whatsapp\" ";

		Cursor cursor = context.getContentResolver().query(getUri(), projection, selection, null, null);
		while (cursor.moveToNext())
		{
			list.add(cursor.getString(0));
		}

		return list;
	}

	/* Kişiye ait gelen aynı numaraları temizliyor. */
	private List<String> clearSamePhone(List<String> list1)
	{

		List<String> list2 = new ArrayList<>();
		if(list1.size() <1)
		{
			return list2;
		}
		list2.add(list1.get(0));

		for (int i = 0; i < list1.size(); i++)
		{
			String array_p1[] = list1.get(i).split(";");
			String p1 = array_p1[0].replaceAll(" ", "");

			for (int a = 0; a < list2.size(); a++)
			{
				String array_p2[] = list2.get(a).split(";");
				String p2 = array_p2[0].replaceAll(" ", "");

				if (p1.contains(p2) || p2.contains(p1))
				{
					break;
				} else
				{
					if (a == list2.size() - 1)
					{
						list2.add(list1.get(i));
					}
					continue;
				}
			}
		}

		return list2;
	}

	public boolean isPhoneEqual(String p1, String p2)
	{
		int l1 = p1.length();
		int l2 = p2.length();

		p1 = p1.replaceAll(" ", "");
		p2 = p2.replaceAll(" ", "");

		if(l1>l2)
		{
			if(p1.contains(p2))
				return true;
		}
		else
		{
			if (p2.contains(p1))
				return true;
		}

		return false;

	}

	private String getSort()
	{
		String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC ";
		return sort;
	}

	private String[] getProjection()
	{
		String[] projection = {
				ContactsContract.CommonDataKinds.Phone._ID,
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
		};
		return projection;
	}

	private List<String> getProjectionList()
	{
		List<String> cList = new ArrayList<>();
		cList.add(ContactsContract.CommonDataKinds.Phone._ID);
		cList.add(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
		cList.add(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
		return cList;
	}

	private Uri getUri()
	{
		return ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
	}

	/* Sonuçlarda çıkan aynı isimleri kaldırıyor */
	private _Cursor clearSameRecord(Cursor cursor)
	{
		_Cursor c = new _Cursor(getProjectionList());
		List<Integer> id_list = new ArrayList<>();

		while (cursor.moveToNext())
		{
			int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
			if (!id_list.contains(id))
			{
				c.addRow(cursor);
				id_list.add(id);
			}
		}
		return c;
	}
}
