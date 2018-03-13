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
import com.neta.nfinder.item.VideoItem;
import com.neta.nfinder.util.Convert;
import com.neta.nfinder.util.Time;
import com.neta.nfinder.view.itemView.ItemDetailView;
import com.neta.nfinder.view.itemView.VideoItemListView;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Berfu on 6.12.2016.
 */

public abstract class VideoView extends View
{
    public static final List<AsyncTask> iconTaskList = new ArrayList<>();

    public VideoView(Context context)
    {
        super(context);
    }

    @Override
    public void execute(boolean isRun)
    {
        for(int i=0; i<iconTaskList.size(); i++)
        {
            AsyncTask task = iconTaskList.get(i);
            task.cancel(true);
        }
        super.execute(isRun);
    }

    @Override
    public void onClickAction(android.view.View v)
    {
        VideoItemListView item = (VideoItemListView) v;
        VideoItem videoItem = item.getVideoItem();

        Uri uri = Uri.fromFile(new File(videoItem.getData()));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, videoItem.getMimeType());
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
    public void onLongClickAction(final android.view.View v)
    {
        final VideoItemListView item = (VideoItemListView) v;
        final VideoItem videoItem = item.getVideoItem();

        File f = new File(videoItem.getData());
        Map<String, String> values = new LinkedHashMap<>();
        values.put(getResources().getString(R.string.file_name), videoItem.getTittle());
        values.put(getResources().getString(R.string.file_path), videoItem.getData());
        values.put(getResources().getString(R.string.album), videoItem.getAlbum());
        values.put(getResources().getString(R.string.artist), videoItem.getArtist());
        values.put(getResources().getString(R.string.size), videoItem.getWidth() + " x " + videoItem.getHeight());
        values.put(getResources().getString(R.string.file) + " " +getResources().getString(R.string.size), Convert.byteToMb(f.length()));
        values.put(getResources().getString(R.string.date_modified), Time.timeStampToDate(Long.valueOf(videoItem.getDateModified())));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog dialog = builder.create();

        final ItemDetailView detailView = new ItemDetailView(getContext(), dialog, item.getIcon(), videoItem.get_ID(), values)
        {
            @Override
            public void onClickActionShare()
            {
                share(videoItem);
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

    private void share(VideoItem videoItem)
    {
        File f=new File(videoItem.getData());
        Uri uri = Uri.parse("file://"+f.getAbsolutePath());
        Intent share = new Intent(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.setType("video/*");
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        getContext().startActivity(Intent.createChooser(share, "Share File"));
    }
}
