package com.neta.nfinder.view.itemView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.neta.nfinder.R;
import com.neta.nfinder.database.FileExt;
import com.neta.nfinder.item.FileItem;
import com.neta.nfinder.util.Convert;
import com.neta.nfinder.util.Time;
import com.neta.nfinder.view.FileView;
import com.neta.nfinder.view.View;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Berfu on 6.12.2016.
 */

public class FileItemListView extends ItemListView
{
    private FileItem fileItem;
    private Context context;

    public FileItemListView(Context context)
    {
        super(context);
        this.context = context;
    }

    public FileItem getFileItem()
    {
        return fileItem;
    }

    public void setFileItem(FileItem fileItem)
    {
        this.fileItem = fileItem;
        setIcon();
        setRow1Text();
        setRow2Text();
        hideRow(3);
    }

    private void setIcon()
    {

        if(fileItem.getExt().equals(".apk"))
        {
            AsyncTask task = new AsyncTask()
            {
                Drawable drawable;
                @Override
                protected Object doInBackground(Object[] params)
                {
                    try
                    {
                        PackageManager pm = context.getPackageManager();
                        PackageInfo info = pm.getPackageArchiveInfo(fileItem.getData(), 0);
                        drawable = info.applicationInfo.loadIcon(pm);
                    }
                    catch (Exception e)
                    {
                        drawable = context.getResources().getDrawable(R.mipmap.ic_launcher);
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Object o)
                {
                    super.onPostExecute(o);
                    setIconText("");
                    setIcon(drawable);
                }
            };
            FileView.iconTaskList.add(task);
            task.execute();
        }
        else
        {
            int color = FileExt.getColor(fileItem.getExt());

            Drawable mDrawable = getResources().getDrawable(R.drawable.shape);
            mDrawable.setColorFilter(new
                    PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));

            getIconView().setTextSize(TypedValue.COMPLEX_UNIT_PT, 7);

            setIcon(mDrawable);
            setIconText(fileItem.getExt().substring(1)); /* Ex: .pdf yerine pdf yazısı gönderiliyor.*/
        }
    }

    private void setRow1Text()
    {
        String text = fileItem.getTitle();
        setRow1Text(text);
    }

    private void setRow2Text()
    {
        String text = Convert.byteToMb(fileItem.getSize()) + " | "
                + Time.timeStampToDate(fileItem.getDateModified());
        setRow2Text(text);
    }
}
