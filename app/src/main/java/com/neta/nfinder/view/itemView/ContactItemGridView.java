package com.neta.nfinder.view.itemView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.neta.nfinder.R;
import com.neta.nfinder.database.ContactDB;
import com.neta.nfinder.item.ContactItem;
import com.neta.nfinder.settings.Settings;

import java.util.List;
import java.util.Set;

/**
 * Created by Berfu on 6.12.2016.
 */

public class ContactItemGridView extends ItemGridView
{
	private ContactItem contactItem;

	public ContactItemGridView(Context context)
	{
		super(context);
	}

	public ContactItem getContactItem()
	{
		return contactItem;
	}

	public void setContactItem(ContactItem contactItem)
	{
		this.contactItem = contactItem;
		setIcon();
		setText();
	}

	private void setIcon()
	{
		setIcon(getResources().getDrawable(R.drawable.contact_icon));
		String names[] = contactItem.getContactName().split(" ");
		String iconText = "";
		for (int i = 0; i < names.length; i++)
		{
			if (i == 2)
			{
				break;
			} else
			{
				iconText += names[i].substring(0, 1).toUpperCase();
			}
		}
		setIconText(iconText);
	}

	private void setText()
	{
		super.setText(contactItem.getContactName());
	}

	@Override
	protected void setOnClickAction()
	{
		Settings settings = new Settings(getContext());
		if(!settings.isOneTouchCall())
		{
			this.performLongClick();
			return;
		}

		ContactDB db = new ContactDB(getContext());
		final List<String> phones = db.getPhoneList(contactItem.getContact_ID());
		/* phoneList.get(i) --> tel;isSuperPrimary --> 05365651099;0 */
		/* superPrimary çoklu numaralarda varsayılan numarayı belirtir. */
		if(phones.size() == 0)
		{

		}
		else if (phones.size() == 1)
		{
			String number = phones.get(0).split(";")[0];
			call(number);
		}
		else
		{

			CharSequence items[] = new CharSequence[phones.size()];
			for(int i=0; i<phones.size(); i++)
			{
				String array[] = phones.get(i).split(";");
				if(array[1].equals("1"))
				{
					call(array[0]);
					return;
				}
				items[i] = getResources().getString(R.string.call) + ":  " + array[0];
			}

			AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
			builder.setItems(items, new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					String number = phones.get(which).split(";")[0];
					call(number);
				}
			});
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}

	@Override
	protected void setOnLongClickAction()
	{

		Intent intent = new Intent(Intent.ACTION_VIEW);
		Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(contactItem.getContact_ID()));
		intent.setData(uri);
		getContext().startActivity(intent);


		/*
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setView(new ContactItemDetailView(getContext(), this));

		AlertDialog dialog = builder.create();
		dialog.show();
		*/
	}


	private void call(String number)
	{
		try
		{
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel: " + number));
			getContext().startActivity(intent);
		}
		catch (Exception e)
		{

		}
	}
}
