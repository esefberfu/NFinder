package com.neta.nfinder.view.itemView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neta.nfinder.R;
import com.neta.nfinder.database.ContactDB;
import com.neta.nfinder.item.ContactItem;
import com.neta.nfinder.util.Convert;

import java.util.List;

/**
 * Created by brefu on 25.12.2016.
 */

public class ContactItemDetailView extends LinearLayout
{
	private LinearLayout parent;
	private ContactItemGridView itemView;
	private ContactItem contactItem;
	private TextView iconView;
	private TextView nameView;
	private TextView showInContact;
	private ImageView share;

	private List<String> wpList;

	public ContactItemDetailView(Context context, ContactItemGridView itemView)
	{
		super(context);
		this.itemView = itemView;
		this.contactItem = itemView.getContactItem();
		createThis();
	}

	private void createThis()
	{
		parent = (LinearLayout) inflate(getContext(), R.layout.dialog_contact_dialog, null);
		this.setOrientation(VERTICAL);
		this.addView(parent);
		parent.setDividerDrawable(getResources().getDrawable(R.drawable.divider));
		parent.setShowDividers(SHOW_DIVIDER_MIDDLE);

		iconView = (TextView) parent.findViewById(R.id.contact_icon);
		iconView.setBackground(getResources().getDrawable(R.drawable.contact_icon));
		iconView.setText(itemView.getIconText());


		nameView = (TextView) parent.findViewById(R.id.contact_name);
		nameView.setText(contactItem.getContactName());

		showInContact = (TextView) parent.findViewById(R.id.show_in_contact);
		showInContact.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(Intent.ACTION_VIEW);
				Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(contactItem.getContact_ID()));
				intent.setData(uri);
				getContext().startActivity(intent);
			}
		});

		share = (ImageView) parent.findViewById(R.id.share_button);
		share.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setDataAndType(uri, "tel:05348985976;05326542525" );
				getContext().startActivity(intent);
			}
		});

		createRow();
	}

	private void createRow()
	{
		ContactDB db = new ContactDB(getContext());
		wpList = db.getWhatsAppNumber(contactItem.getContact_ID());
		Log.e("WPNUMBER", wpList.toString());
		final List<String> phoneList = db.getPhoneList(contactItem.getContact_ID());
		for(int i=0; i<phoneList.size(); i++)
		{
			String phone = phoneList.get(i).split(";")[0];
			this.addView(createNumberRow(phone));
		}
	}

	private LinearLayout createNumberRow(final String phone)
	{
		LinearLayout layout = new LinearLayout(getContext());
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, Convert.dpToPx(48)));
		layout.setOrientation(HORIZONTAL);
		layout.setPadding(Convert.dpToPx(10), 0, Convert.dpToPx(10), 0);
		layout.setGravity(Gravity.CENTER);

		TextView numberView = new TextView(getContext());
		numberView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, Convert.dpToPx(48), 1.0f));
		numberView.setText(phone);
		numberView.setBackground(getResources().getDrawable(R.drawable.item_click));
		numberView.setGravity(Gravity.CENTER_VERTICAL);
		numberView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 7);
		numberView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				call(phone);
			}
		});
		layout.addView(numberView);

		ContactDB db = new ContactDB(getContext());
		for (int i=0; i<wpList.size(); i++)
		{
			if(db.isPhoneEqual(phone, wpList.get(i)))
			{
				ImageView wpButton = new ImageView(getContext());
				wpButton.setLayoutParams(new LayoutParams(Convert.dpToPx(48), Convert.dpToPx(36)));
				//wpButton.setPadding(Convert.dpToPx(5), Convert.dpToPx(5), Convert.dpToPx(5), Convert.dpToPx(5));
				wpButton.setImageDrawable(getResources().getDrawable(R.drawable._whatsapp));
				wpButton.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Uri uri = Uri.parse("smsto:" + phone);
						Intent i = new Intent(Intent.ACTION_SENDTO, uri);
						i.setPackage("com.whatsapp");
						getContext().startActivity(i);
					}
				});
				layout.addView(wpButton);
				break;
			}
		}

		TextView smsButton = new TextView(getContext());
		smsButton.setLayoutParams(new LayoutParams(Convert.dpToPx(48), Convert.dpToPx(36)));
		smsButton.setPadding(Convert.dpToPx(5), Convert.dpToPx(5), Convert.dpToPx(5), Convert.dpToPx(5));
		smsButton.setText("SMS");
		smsButton.setGravity(Gravity.CENTER);
		smsButton.setTextColor(getResources().getColor(R.color.view_name_and_sub_text));
		smsButton.setBackground(getResources().getDrawable(R.drawable.item_click));
		//smsButton.setImageDrawable(getResources().getDrawable(R.drawable._sms_3));
		smsButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "sms:" + phone));
				getContext().startActivity(intent);
			}
		});
		layout.addView(smsButton);

		return layout;
	}

	private void call(String phone)
	{
		try
		{
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel: " + phone));
			getContext().startActivity(intent);
		}
		catch (Exception e)
		{

		}
	}

}
