package com.neta.nfinder.view.table;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.neta.nfinder.R;
import com.neta.nfinder.util.Convert;

import java.util.TimerTask;

/**
 * Created by Berfu on 2.12.2016.
 */

public class ListView extends android.widget.ListView {

    private int wrap_height = Convert.dpToPx(60);
    private int count;

    public ListView(Context context)
    {
        super(context);
        createThis();
    }

    private void createThis()
    {
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        this.setBackgroundColor(getResources().getColor(R.color.white));
        this.setDivider(new ColorDrawable(getResources().getColor(R.color.white_dark)));
        this.setDividerHeight(2);
    }

    @Override
    public void setAdapter(ListAdapter adapter)
    {
        super.setAdapter(adapter);
        count = adapter.getCount();
        this.setParams(count);
    }

    /* setLayoutParams fonksiyonu listView'in içindeki nesne boyutu ve saysına göre ayarlanacak */
    private void setParams(int count)
    {
        /* 46dp = 36dp icon + 5dp padding top + 5dp padding bottom */
        if (count < 6)
        {
            ((LinearLayout.LayoutParams) this.getLayoutParams()).height = count * wrap_height;
            //this.setLayoutParams( new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ()) );
        }
        else
        {
            ((LinearLayout.LayoutParams) this.getLayoutParams()).height = 5 * wrap_height;
            //this.setLayoutParams( new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (5*height)) );
        }
    }

    public void setMatchParent()
    {
        Log.e("MatchParent, Count ", String.valueOf(count));
        if(count > 5)
        {
            final LinearLayout.LayoutParams params = ((LinearLayout.LayoutParams) this.getLayoutParams());
            final int max = ((count * wrap_height) + Convert.dpToPx(count));
            params.height = max;
            this.setLayoutParams(params);
        }
    }

    public void setWrapContent()
    {
        Log.e("WrapContent, Count ", String.valueOf(count));
        if(count > 5)
        {
            LinearLayout.LayoutParams params = ((LinearLayout.LayoutParams) this.getLayoutParams());
            params.height =( 5 * wrap_height );
            this.setLayoutParams(params);
        }
    }
}
