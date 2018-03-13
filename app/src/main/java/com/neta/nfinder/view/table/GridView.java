package com.neta.nfinder.view.table;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.neta.nfinder.R;
import com.neta.nfinder.util.Convert;

/**
 * Created by Berfu on 2.12.2016.
 */

public class GridView extends android.widget.GridView
{
	private int row_height = Convert.dpToPx(95);
	private int count;

	public GridView(Context context)
	{
		super(context);
		createThis();
	}

	/* Bu sınıfın özellikleri tanımlanıyor */
	private void createThis()
	{
		this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Convert.dpToPx(95)));
		this.setNumColumns(4);
		this.setVerticalSpacing(Convert.dpToPx(10));
		this.setHorizontalSpacing(Convert.dpToPx(10));
		this.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		this.setGravity(Gravity.CENTER);
		this.setPadding(Convert.dpToPx(5), Convert.dpToPx(5), Convert.dpToPx(5), Convert.dpToPx(5));
		this.setBackgroundColor(getResources().getColor(R.color.white));
	}

	/* adapter nesnesinin nesne sayısına göre dinamik olarak yüksekliği değiştirilecek. */
	@Override
	public void setAdapter(ListAdapter adapter)
	{
		super.setAdapter(adapter);
		this.count = adapter.getCount();
	}


	public void setMatchParent()
	{
		if (count > 4)
		{
			LinearLayout.LayoutParams params = ((LinearLayout.LayoutParams) this.getLayoutParams());

			if(count % 4 != 0)
				params.height = (( ((count / 4)+1) * row_height) + Convert.dpToPx(count));
			else
				params.height = (( (count / 4) * row_height) + Convert.dpToPx(count));
			this.setLayoutParams(params);
		}
	}

	public void setWrapContent()
	{
		if (count > 4)
		{
			LinearLayout.LayoutParams params = ((LinearLayout.LayoutParams) this.getLayoutParams());
			params.height = (row_height);
			this.setLayoutParams(params);
		}
	}
}
