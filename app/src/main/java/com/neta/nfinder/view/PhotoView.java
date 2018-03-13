package com.neta.nfinder.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.AdapterView;
import android.widget.Toast;

import com.neta.nfinder.R;
import com.neta.nfinder.item.PhotoItem;
import com.neta.nfinder.util.Convert;
import com.neta.nfinder.util.Time;
import com.neta.nfinder.view.itemView.ItemDetailView;
import com.neta.nfinder.view.itemView.PhotoItemListView;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Berfu on 6.12.2016.
 */

public abstract class PhotoView extends View
{
    public static final List<AsyncTask> iconTaskList = new ArrayList<>();

    public PhotoView(Context context)
    {
        super(context);
    }

    /* execute methodu ovveride edildi. Çünkü yeni bir iş başlatılmadan önce bu sınıftati taskList içinde görevler durdurulmalı. */
    @Override
    public void execute(boolean isRun)
    {
        for(int i = 0; i< iconTaskList.size(); i++)
        {
            AsyncTask task = iconTaskList.get(i);
            task.cancel(true);
        }
        super.execute(isRun);
    }

    @Override
    public void onClickAction(android.view.View view)
    {
        PhotoItemListView item = (PhotoItemListView) view;
        PhotoItem photoItem = item.getPhotoItem();

        Uri uri = Uri.fromFile(new File(photoItem.getData()));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, photoItem.getMimeType());
        try
        {
            getContext().startActivity(intent);
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(),"No Application",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLongClickAction(android.view.View view)
    {
        final PhotoItemListView item = (PhotoItemListView) view;
        final PhotoItem photoItem = item.getPhotoItem();

        File f = new File(photoItem.getData());
        Map<String, String> values = new LinkedHashMap<>();
        values.put(getResources().getString(R.string.file_name), photoItem.getTittle());
        values.put(getResources().getString(R.string.file_path), photoItem.getData());
        values.put(getResources().getString(R.string.album), photoItem.getAlbum());
        values.put(getResources().getString(R.string.size), photoItem.getWidth() + " x " + photoItem.getHeight());
        values.put(getResources().getString(R.string.file) + " " +getResources().getString(R.string.size), Convert.byteToMb(f.length()));
        values.put(getResources().getString(R.string.date_modified), Time.timeStampToDate(Long.valueOf(photoItem.getDateModified())));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog dialog = builder.create();

        final ItemDetailView detailView = new ItemDetailView(getContext(), dialog, item.getIcon(), photoItem.get_ID(), values)
        {
            @Override
            public void onClickActionShare()
            {
                share(photoItem);
            }
        };


        dialog.setView(detailView);
        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener()
        {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                if(detailView.isChanged)
                {
                    if(detailView.fName != null)
                    {
                        item.setRow1Text(detailView.fName);
                    }
                }
            }
        });
    }

    private void share(PhotoItem photoItem)
    {
        File f=new File(photoItem.getData());
        Uri uri = Uri.parse("file://"+f.getAbsolutePath());
        Intent share = new Intent(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.setType("image/*");
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        getContext().startActivity(Intent.createChooser(share, "Share File"));
    }
}
