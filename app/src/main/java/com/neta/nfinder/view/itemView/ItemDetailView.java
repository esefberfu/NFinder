package com.neta.nfinder.view.itemView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.neta.nfinder.R;
import com.neta.nfinder.database.FileDB;
import com.neta.nfinder.util.Convert;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by brefu on 18.12.2016.
 */

public abstract class ItemDetailView extends LinearLayout
{

	private List<TextView> textViewList;

	private TextView titleView;
	private ImageView iconView;
	private LinearLayout horizontal;
	private TableLayout tableLayout;
	private android.app.AlertDialog parentDialog;

	private LinearLayout buttonLayout;
	private LinearLayout proccessLayout;
	private Map<String, String > val;
	private String _id;

	private Drawable icon;

	public boolean isChanged = false;
	public String fName = null;

	public ItemDetailView(Context context, android.app.AlertDialog parentDialog, Drawable icon, String _id, Map<String, String> map)
	{
		super(context);
		this.icon = icon;
		this.setOrientation(VERTICAL);
		this.val = map;
		this._id = _id;
		this.parentDialog = parentDialog;
		textViewList = new ArrayList<>();

		createThis();
		createTitleView();
		createHorizontal();
		createTableLayout();
		createTableRows(map);
		createButtonLayout();
	}

	private void createThis()
	{
		this.setDividerDrawable(getResources().getDrawable(R.drawable.divider));
		this.setShowDividers(SHOW_DIVIDER_MIDDLE);
		this.setBackgroundColor(Color.WHITE);
	}

	private void createTitleView()
	{
		titleView = new TextView(getContext());
		titleView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, Convert.dpToPx(40)));
		titleView.setPadding(Convert.dpToPx(10), Convert.dpToPx(5), Convert.dpToPx(10), Convert.dpToPx(5));
		titleView.setBackgroundColor(getResources().getColor(R.color.white_dark));
		titleView.setGravity(Gravity.BOTTOM);
		titleView.setText("Details");
		titleView.setTextColor(getResources().getColor(R.color.detail_text));
		this.addView(titleView);
	}

	private void createHorizontal()
	{
		horizontal = new LinearLayout(getContext());
		horizontal.setOrientation(LinearLayout.HORIZONTAL);
		horizontal.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		horizontal.setGravity(Gravity.CENTER_VERTICAL);
		horizontal.setBackgroundColor(Color.WHITE);
		this.addView(horizontal);
	}

	private void createIconView(String name, String value)
	{


		iconView = new ImageView(getContext());;
		iconView.setLayoutParams(new LinearLayout.LayoutParams(Convert.dpToPx(64), Convert.dpToPx(64)));
		iconView.setBackgroundColor(Color.WHITE);
		iconView.setImageDrawable(icon);
		iconView.setPadding(0,Convert.dpToPx(10), 0, Convert.dpToPx(10));
		horizontal.addView(iconView);

		LinearLayout vertical = new LinearLayout(getContext());
		vertical.setOrientation(LinearLayout.VERTICAL);
		vertical.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		horizontal.addView(vertical);

		TextView nameView = createNameView(name);
		nameView.setPadding(Convert.dpToPx(10), Convert.dpToPx(5), 0, Convert.dpToPx(5));
		vertical.addView( nameView );
		vertical.addView( createValueView(value) );


	}

	private void createTableLayout()
	{
		tableLayout = new TableLayout(getContext());
		tableLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		tableLayout.setPadding(0, 0, 0, Convert.dpToPx(5));
		tableLayout.setBackgroundColor(Color.WHITE);
		tableLayout.setDividerDrawable(getResources().getDrawable(R.drawable.divider));
		tableLayout.setShowDividers(SHOW_DIVIDER_MIDDLE);
		this.addView(tableLayout);
	}

	private void createTableRows(Map<String, String> map)
	{
		Set<String> keys = map.keySet();
		Iterator<String> iter = keys.iterator();

		for(int i=0; i<keys.size(); i++)
		{
			String key = iter.next();

			String name = key;
			String value = map.get(key);

			if(i == 0)
			{
				createIconView(name +" :", value);
				continue;
			}

			TableRow row = new TableRow(getContext());
			row.setPadding(Convert.dpToPx(10), Convert.dpToPx(5), Convert.dpToPx(10), 0);
			row.setGravity(Gravity.CENTER_VERTICAL);
			tableLayout.addView(row);

			row.addView( createNameView(name + " :") );
			row.addView( createValueView(value) );

		}

	}

	private void createButtonLayout()
	{
		buttonLayout = new LinearLayout(getContext());
		buttonLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, Convert.dpToPx(48)));
		buttonLayout.setOrientation(VERTICAL);
		buttonLayout.setBackgroundColor(getResources().getColor(R.color.white_dark));
		buttonLayout.setDividerDrawable(getResources().getDrawable(R.drawable.divider));
		buttonLayout.setShowDividers(SHOW_DIVIDER_MIDDLE);
		this.addView(buttonLayout);
		createProccessLayout();
	}

	private void createProccessLayout()
	{
		proccessLayout = new LinearLayout(getContext());
		proccessLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, Convert.dpToPx(48)));
		proccessLayout.setOrientation(HORIZONTAL);
		proccessLayout.setBackgroundColor(getResources().getColor(R.color.white_dark));
		buttonLayout.addView(proccessLayout);
		createRenameButton();
		createShareButton();
		createDeleteButton();
	}

	private void createRenameButton()
	{
		final String path = textViewList.get(1).getText().toString();
		File source = new File(path);

		final String name = textViewList.get(0).getText().toString();
		final String fullName = source.getName();
		final String ext = fullName.replace(name, "");

		ImageView rename = new ImageView(getContext());
		rename.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, Convert.dpToPx(48)));
		Drawable open = getResources().getDrawable(R.drawable._rename);
		open.setColorFilter(new
				PorterDuffColorFilter(getResources().getColor(R.color.blue_gray), PorterDuff.Mode.MULTIPLY));
		rename.setImageDrawable(open);
		rename.setBackground(getResources().getDrawable(R.drawable.share_button_click));
		rename.setPadding(0, Convert.dpToPx(10), 0, Convert.dpToPx(10));
		rename.setClickable(true);
		proccessLayout.addView(rename);

		rename.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
				final EditText edittext = new EditText(getContext());
				edittext.setText(name);
				alert.setMessage("File Name");
				alert.setView(edittext);

				alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String fileName = edittext.getText().toString();
						fName = fileName;

						try
						{
							File f = new File(path);
							File f2 = new File(f.getParent()+"/"+fileName+ext);

							FileDB db = new FileDB(getContext());
							db.renameFile(_id, path, fileName, ext);
							textViewList.get(0).setText(fileName);
							textViewList.get(1).setText(f2.getPath());
							isChanged = true;
						}
						catch (Exception e)
						{
							Toast.makeText(getContext(), "Dosya Mevcut", Toast.LENGTH_SHORT).show();
						}
					}
				});

				alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});

				alert.show();
			}
		});
	}

	private void createDeleteButton()
	{
		final ImageView delete = new ImageView(getContext());
		delete.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, Convert.dpToPx(48)));

		Drawable open = getResources().getDrawable(R.drawable._rubbish);
		open.setColorFilter(new
				PorterDuffColorFilter(getResources().getColor(R.color.blue_gray), PorterDuff.Mode.MULTIPLY));

		delete.setImageDrawable(open);
		delete.setBackground(getResources().getDrawable(R.drawable.share_button_click));
		delete.setPadding(0, Convert.dpToPx(15), 0, Convert.dpToPx(15));
		delete.setClickable(true);
		proccessLayout.addView(delete);

		delete.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
				alert.setMessage("Dosyayı silmek istiyor musunuz ?");

				alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						FileDB db = new FileDB(getContext());
						db.deleteFile(_id,textViewList.get(1).getText().toString());
						parentDialog.dismiss();
					}
				});

				alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});

				alert.show();

			}
		});
	}

	private void createShareButton()
	{

		ImageView button = new ImageView(getContext());
		button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, Convert.dpToPx(48), 1.0f));
		button.setImageDrawable(getResources().getDrawable(R.drawable.share_color_filter));
		button.setBackground(getResources().getDrawable(R.drawable.share_button_click));
		button.setPadding(0, Convert.dpToPx(10), 0, Convert.dpToPx(10));
		button.setClickable(true);
		proccessLayout.addView(button);

		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Log.e("SHARE","BUTTON CLICK");
				onClickActionShare();
			}
		});
	}

	private TextView createNameView(String text)
	{
		TextView textView = new TextView(getContext());
		textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, Convert.dpToPx(30)));
		textView.setTextColor(Color.BLACK);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
		textView.setText(text);
		textView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

		return textView;
	}

	private TextView createValueView(String text)
	{
		TextView textView = new TextView(getContext());
		textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
		textView.setTextColor(getResources().getColor(R.color.detail_text));
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
		textView.setText(text);
		textView.setHorizontallyScrolling(false);
		textView.setPadding(Convert.dpToPx(10), 0, 0, 0);
		textView.setGravity(Gravity.CENTER_VERTICAL);
		textViewList.add(textView);
		return textView;
	}


	public abstract void onClickActionShare();

}
