package com.neta.nfinder.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neta.nfinder.R;
import com.neta.nfinder.util.Convert;
import com.neta.nfinder.view.table.GridView;
import com.neta.nfinder.view.table.ListView;

import java.util.regex.Pattern;

/**
 * Created by Berfu on 2.12.2016.
 */

public abstract class View extends LinearLayout
{
	public static final int GRID = 0;
	public static final int LIST = 1;

	private int WRAP_CONTENT = 0;
	private int MATCH_PARENT = 1;


	private LinearLayout row1;
	private LinearLayout row2;
	private TextView viewName;
	private TextView viewMoreButton;
	private ImageView viewExecute;
	private GridView gridView;
	private ListView listView;
	private int screenMode = WRAP_CONTENT;

	private AsyncTask task = null;

	private int viewType;

	public View(Context context)
	{
		super(context);
		createThis();
	}






    /* PUBLIC ******************************************************************************************/

	/* View ismi ayarlanıyor. */
	public void setViewName(String viewName)
	{
		this.viewName.setText(viewName);
	}

	/* viewType'ın değerine göre ListView veya GridView nesnesine adapter bağlanacak */
	public void setAdapter(CursorAdapter adapter)
	{
		if (adapter.getCount() == 0)
		{
			this.setVisibility(LinearLayout.GONE);
		} else
		{
			this.setVisibility(LinearLayout.VISIBLE);
			if (viewType == GRID)
			{
				gridView.setAdapter(adapter);
			} else if (viewType == LIST)
			{
				listView.setAdapter(adapter);
			}
		}
	}

	/* Bu view'ın nesneleri hangi şekilde göstereceği ayarlanıyor ve viewType değerine göre gridview veya listview oluşturuluyor */
	public void setViewType(int viewType)
	{
		this.viewType = viewType;
		if (viewType == GRID)
		{
			createGridView();
		} else if (viewType == LIST)
		{
			createListView();
		}
	}

	public android.widget.ListView getListView()
	{
		return listView;
	}

	/* AsyncTask oluşturulup başlatacak */
	/* isRun = true ise AsyncTask çalışacak */
    /* isRun = false ise  AsyncTask çalışmayacak ve View gizlenecek */
    /* Belli şartlara göre execute fonksiyonu her zaman çalıştırılmak istenmeyebilir. */
    /* Bu yüzde isRun parametresi kontrol sağlanmaktadır. */
	public void execute(boolean isRun)
	{
		if (task != null)
		{
			task.cancel(true);
		}

		if (isRun)
		{
			this.setVisibility(VISIBLE);

			task = new AsyncTask()
			{
				@Override
				protected Object doInBackground(Object[] objects)
				{
					doIn();
					return null;
				}

				@Override
				protected void onPostExecute(Object o)
				{
					super.onPostExecute(o);
					viewMoreButton.performLongClick();
					onPost();
				}
			};
			task.execute();
		} else if (!isRun)
		{
			Log.e(viewName.getText().toString() + " - BOOLEAN", "false");
			this.setVisibility(GONE);
		}
	}

	public void cancelTask()
	{
		if(task!=null)
		{
			task.cancel(true);
			task = null;
		}
	}
    /* END PUBLIC ******************************************************************************************/






    /* PRIVATE ******************************************************************************************/

	private void createThis()
	{
		this.setOrientation(VERTICAL);
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		createRow1();
		createRow2();
	}

	/* Bu View satır olarak ayrılmıştır. İlk satırda ViewName ve ViewMore butonları veya eklenecek diğer butonlar olacak */
	private void createRow1()
	{
		row1 = new LinearLayout(getContext());
		row1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		row1.setOrientation(HORIZONTAL);
		row1.setPadding(Convert.dpToPx(10), Convert.dpToPx(5), Convert.dpToPx(10), Convert.dpToPx(5));
		row1.setBackgroundColor(getResources().getColor(R.color.white_dark));
		this.addView(row1);
		createViewExecute();
		createViewName();
		createViewMoreButton();
	}

	/* Row2 satırı içinde ListView veya GridView bulunacak. */
	private void createRow2()
	{
		row2 = new LinearLayout(getContext());
		row2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		row2.setOrientation(HORIZONTAL);
		this.addView(row2);

	}

	/* Viewname'i gösterecek view oluşturuluyor */
	private void createViewName()
	{
		viewName = new TextView(getContext());
		viewName.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, Convert.dpToPx(30), 1.0f));
		viewName.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
		viewName.setTextColor(getResources().getColor(R.color.view_name_and_sub_text));
		viewName.setGravity(Gravity.BOTTOM);
		row1.addView(viewName);
	}

	/*  */
	private void createViewMoreButton()
	{
		viewMoreButton = new TextView(getContext());
		viewMoreButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, Convert.dpToPx(30), 1.0f));
		viewMoreButton.setText(getResources().getString(R.string.show_more));
		viewMoreButton.setTextColor(getResources().getColor(R.color.view_name_and_sub_text));
		viewMoreButton.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
		viewMoreButton.setGravity(Gravity.END | Gravity.BOTTOM);
		viewMoreButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(android.view.View view)
			{
				if (viewType == LIST)
				{
					changeModeListView();
				} else if (viewType == GRID)
				{
					changeModeGridView();
				}
			}
		});
		row1.addView(viewMoreButton);
	}

	private void createViewExecute()
	{
		viewExecute = new ImageView(getContext());
		viewExecute.setLayoutParams(new LayoutParams(Convert.dpToPx(30), Convert.dpToPx(30) ));
		viewExecute.setImageDrawable(getResources().getDrawable(R.drawable.refresh_color_filter));
		viewExecute.setBackground(getResources().getDrawable(R.drawable.cancel_button));
		viewExecute.setPadding(Convert.dpToPx(10), Convert.dpToPx(10), Convert.dpToPx(10), Convert.dpToPx(-5));
		viewExecute.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(android.view.View view)
			{
				execute(true);
			}
		});
		row1.addView(viewExecute);
	}
	/* Itemleri grid şeklinde listeleyecek view oluşturuluyor */
	private void createGridView()
	{
		gridView = new GridView(getContext());
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id)
			{
				onClickAction(view);
			}
		});

		gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, android.view.View view, int position, long id)
			{
				onLongClickAction(view);
				return true;
			}
		});
		row2.addView(gridView);

	}

	/* Itemleri liste şeklinde listeleyecek view oluşturuluyor */
	private void createListView()
	{
		listView = new ListView(getContext());
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id)
			{
				onClickAction(view);
			}
		});

		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, android.view.View view, int position, long id)
			{
				onLongClickAction(view);
				return true;
			}
		});
		row2.addView(listView);
	}

	/* GridView nesnesinin wrap_content ise match parent; match_parent ise wrap content olmasını sağlayacak */
	protected void changeModeGridView()
	{
		if (screenMode == WRAP_CONTENT)
		{
			screenMode = MATCH_PARENT;
			gridView.setMatchParent();
			viewMoreButton.setText(getResources().getString(R.string.show_less));
		} else if (screenMode == MATCH_PARENT)
		{
			screenMode = WRAP_CONTENT;
			gridView.setWrapContent();
			viewMoreButton.setText(getResources().getString(R.string.show_more));
		}
	}

	/* ListView nesnesinin wrap_content ise match parent; match_parent ise wrap content olmasını sağlayacak */
	protected void changeModeListView()
	{
		if (screenMode == WRAP_CONTENT)
		{
			screenMode = MATCH_PARENT;
			listView.setMatchParent();
			viewMoreButton.setText(getResources().getString(R.string.show_less));
		} else if (screenMode == MATCH_PARENT)
		{
			screenMode = WRAP_CONTENT;
			listView.setWrapContent();
			viewMoreButton.setText(getResources().getString(R.string.show_more));
		}
	}

    /* END PRIVATE ******************************************************************************************/







    /* ABSTRACT ******************************************************************************************/

	/* doIn ve onPost fonksiyonları sadece execute içinde çağrılacak. */
    /* doIn: arkaplanda işlemleri yapacak */
	public abstract void doIn();

	/* onPost arkaplanda grafiksel olarak işlem yapacak */
	public abstract void onPost();

	/* table'ların tıklama olayları */
	public abstract void onClickAction(android.view.View v);
	public abstract void onLongClickAction(android.view.View v);
    /* END ABSTRACT ******************************************************************************************/

}

