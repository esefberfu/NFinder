package com.neta.nfinder.settings.sort;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neta.nfinder.R;
import com.neta.nfinder.settings.Settings;
import com.neta.nfinder.util.Convert;

/**
 * Created by brefu on 24.12.2016.
 * Sıralama için kullanılacak item oluşturuluyor
 */

public class Item extends LinearLayout
{
	private TextView itemName;
	private ImageView upButton;
	private ImageView downButton;

	private String key;
	private String viewName;
	private ItemSortView listView;


	public Item(Context context, ItemSortView listView, String key, String viewName)
	{
		super(context);
		this.key = key;
		this.listView = listView;
		this.viewName = viewName;

		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this.setOrientation(HORIZONTAL);
		this.setPadding(Convert.dpToPx(10), Convert.dpToPx(10), Convert.dpToPx(10), Convert.dpToPx(10));
		this.setGravity(Gravity.CENTER_VERTICAL);

		Settings settings = new Settings(getContext());

		itemName = new TextView(getContext());
		itemName.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, Convert.dpToPx(48), 2.0f));
		itemName.setGravity(Gravity.CENTER_VERTICAL);
		itemName.setTextColor(Color.BLACK);
		itemName.setText(String.valueOf(settings.getViewIndex(key)+1) + ". " + viewName);
		this.addView(itemName);

		upButton = new ImageView(getContext());
		upButton.setLayoutParams(new LayoutParams(Convert.dpToPx(72), Convert.dpToPx(36), 1.0f));
		upButton.setPadding(Convert.dpToPx(10), Convert.dpToPx(10), Convert.dpToPx(10), Convert.dpToPx(10));
		upButton.setClickable(true);
		upButton.setBackground(getResources().getDrawable(R.drawable.item_click));
		upButton.setImageDrawable(getResources().getDrawable(R.drawable.arrow_up));
		upButton.setColorFilter(getResources().getColor(R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
		upButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onUp();
			}
		});
		this.addView(upButton);

		downButton = new ImageView(getContext());
		downButton.setLayoutParams(new LayoutParams(Convert.dpToPx(72), Convert.dpToPx(36), 1.0f));
		downButton.setPadding(Convert.dpToPx(10), Convert.dpToPx(10), Convert.dpToPx(10), Convert.dpToPx(10));
		downButton.setClickable(true);
		downButton.setBackground(getResources().getDrawable(R.drawable.item_click));
		downButton.setImageDrawable(getResources().getDrawable(R.drawable.arrow_down));
		downButton.setColorFilter(getResources().getColor(R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
		downButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onDown();
			}
		});
		this.addView(downButton);

	}

	public String getKey()
	{
		return key;
	}

	public void setTextIndex(int index)
	{
		itemName.setText(String.valueOf(index+1) + ". " + viewName);
	}

	private void onUp()
	{
		int index = listView.onUpItem(this);
		setTextIndex(index);
	}

	private void onDown()
	{
		int index = listView.onDownItem(this);
		setTextIndex(index);
	}

}
