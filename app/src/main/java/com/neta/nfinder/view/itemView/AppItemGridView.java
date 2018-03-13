package com.neta.nfinder.view.itemView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.neta.nfinder.database.AppDB;
import com.neta.nfinder.item.AppItem;

/**
 * Created by Berfu on 15.11.2009.
 */

public class AppItemGridView extends ItemGridView
{
    private AppItem appItem;

    public AppItemGridView(Context context)
    {
        super(context);
    }


    public AppItem getAppItem()
    {
        return appItem;
    }

    public void setAppItem(AppItem appItem)
    {
        this.appItem = appItem;
        setIcon();
        setRow1Text();
    }

    private void setIcon()
    {
        try
        {
            Drawable d = getContext().getPackageManager().getApplicationIcon(appItem.getPackageName());
            super.setIcon(d);
        }
        catch (Exception e)
        {
            Log.e("AppItemGridView", e.getMessage());
        }
    }

    private void setRow1Text()
    {
        setText(appItem.getAppName());
    }


    @Override
    protected void setOnClickAction()
    {
        try
        {
            AppDB db = new AppDB(getContext());
            db.addToRecent(appItem);
            Intent intent = getContext().getPackageManager().getLaunchIntentForPackage(appItem.getPackageName());
            getContext().startActivity(intent);
        }
        catch (Exception e)
        {
            Log.e("App", e.getMessage());
        }
    }

    @Override
    protected void setOnLongClickAction()
    {
        try {
            //Open the specific App Info page:
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + appItem.getPackageName()));
            getContext().startActivity(intent);

        } catch ( ActivityNotFoundException e ) {
            //Open the generic Apps page:
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
            getContext().startActivity(intent);

        }
    }
}
