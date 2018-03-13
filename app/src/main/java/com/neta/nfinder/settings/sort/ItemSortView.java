package com.neta.nfinder.settings.sort;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import com.neta.nfinder.R;
import com.neta.nfinder.settings.Settings;

/**
 * Created by brefu on 24.12.2016.
 */

public class ItemSortView extends LinearLayout
{
	private Settings settings;

	public ItemSortView(Context context)
	{
		super(context);
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		this.setOrientation(VERTICAL);
		this.setDividerDrawable(getResources().getDrawable(R.drawable.divider));
		this.setShowDividers(SHOW_DIVIDER_MIDDLE);
		settings = new Settings(getContext());
	}

	public int onUpItem(Item item)
	{
		int index = settings.getViewIndex(item.getKey());

		if(index == 0)
			return 0;

		this.removeView(item);

		Item temp = (Item) getChildAt(index-1);
		this.removeView(temp);

		this.addView(item, index-1);
		this.addView(temp, index);

		temp.setTextIndex(index);

		settings.setViewIndex(item.getKey(), index-1);
		settings.setViewIndex(temp.getKey(), index);
		return index-1;
	}

	public int onDownItem(Item item)
	{
		int index = settings.getViewIndex(item.getKey());
		int replace_index = index+1;

		if(index == this.getChildCount()-1)
			return this.getChildCount()-1;

		Item temp = (Item) getChildAt(replace_index);
		this.removeView(temp);
		this.removeView(item);

		this.addView(temp, index);
		this.addView(item, replace_index);

		temp.setTextIndex(index);

		Log.e("INDEX OLD", String.valueOf(index));
		Log.e("INDEX NEW", String.valueOf(replace_index));

		settings.setViewIndex(item.getKey(), replace_index);
		settings.setViewIndex(temp.getKey(), index);
		return index+1;
	}
}
