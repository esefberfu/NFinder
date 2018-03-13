package com.neta.nfinder.view.itemView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.neta.nfinder.R;
import com.neta.nfinder.util.Convert;

/**
 * Created by Berfu on 2.12.2016.
 */

public abstract class ItemListView extends LinearLayout
{

	private LinearLayout column1;

	private LinearLayout row1;
	private LinearLayout row2;
	private LinearLayout row3;

	private TextView iconView;
	private TextView row1TextView;
	private TextView row2TextView;
	private TextView row3TextView;

	public ItemListView(Context context)
	{
		super(context);
		createThis();
	}

	public void setIcon(Drawable drawable)
	{
		iconView.setBackground(drawable);
	}

	public void setIconText(String text)
	{
		iconView.setText(text);
	}

	public void setRow1Text(String text)
	{
		row1TextView.setText(text);
	}

	public void setRow2Text(String text)
	{
		row2TextView.setText(text);
	}

	public void setRow3Text(String text)
	{
		row3TextView.setText(text);
	}

	public TextView getIconView()
	{
		return iconView;
	}

	public void hideRow(int rowNumber)
	{
		if(rowNumber == 1)
		{
			row1.setVisibility(LinearLayout.GONE);
		}
		else if(rowNumber == 2)
		{
			row2.setVisibility(LinearLayout.GONE);
		}
		else if(rowNumber == 3)
		{
			row3.setVisibility(LinearLayout.GONE);
		}
	}

	public Drawable getIcon()
	{
		iconView.setDrawingCacheEnabled(true);
		Drawable drawable = new BitmapDrawable(iconView.getDrawingCache());
		return drawable;
	}



	private void createThis()
	{
		this.setOrientation(HORIZONTAL);
		this.setPadding(Convert.dpToPx(5), Convert.dpToPx(5), Convert.dpToPx(5), Convert.dpToPx(5));
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, Convert.dpToPx(60)));
		this.setGravity(Gravity.CENTER_VERTICAL);

		createIconView();
		createColumn1();
	}

	private void createIconView()
	{
		LinearLayout.LayoutParams params = new LayoutParams(Convert.dpToPx(48), Convert.dpToPx(48));
		params.setMargins(0, 0, Convert.dpToPx(10), 0);
		iconView = new TextView(getContext());
		iconView.setLayoutParams(params);
		iconView.setGravity(Gravity.CENTER);
		iconView.setMaxLines(1);
		iconView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 8);
		this.addView(iconView);
	}

	private void createColumn1()
	{
		column1 = new LinearLayout(getContext());
		column1.setOrientation(VERTICAL);
		column1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this.addView(column1);
		createRow1();
		createRow2();
		createRow3();
	}


	private void createRow1()
	{
		row1 = new LinearLayout(getContext());
		row1.setOrientation(VERTICAL);
		row1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		column1.addView(row1);
		createRow1TextView();
	}

	private void createRow2()
	{
		row2 = new LinearLayout(getContext());
		row2.setOrientation(VERTICAL);
		row2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		column1.addView(row2);
		createRow2TextView();
	}

	private void createRow3()
	{
		row3 = new LinearLayout(getContext());
		row3.setOrientation(VERTICAL);
		row3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		column1.addView(row3);
		createRow3TextView();
	}

	private void createRow1TextView()
	{
		row1TextView = new TextView(getContext());
		row1TextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		row1TextView.setTextColor(Color.BLACK);
		row1TextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
		row1TextView.setMaxLines(1);
		row1TextView.setEllipsize(TextUtils.TruncateAt.END);
		row1.addView(row1TextView);
	}

	private void createRow2TextView()
	{
		row2TextView = new TextView(getContext());
		row2TextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		row2TextView.setTextColor(getResources().getColor(R.color.view_name_and_sub_text));
		row2TextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 5);
		row2TextView.setMaxLines(1);
		row2TextView.setEllipsize(TextUtils.TruncateAt.START);
		row2.addView(row2TextView);
	}

	private void createRow3TextView()
	{
		row3TextView = new TextView(getContext());
		row3TextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		row3TextView.setTextColor(getResources().getColor(R.color.view_name_and_sub_text));
		row3TextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 5);
		row3TextView.setMaxLines(1);
		row3TextView.setEllipsize(TextUtils.TruncateAt.END);
		row3.addView(row3TextView);
	}

}
