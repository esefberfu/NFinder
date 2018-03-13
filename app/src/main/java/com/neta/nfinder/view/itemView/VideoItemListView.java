package com.neta.nfinder.view.itemView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.neta.nfinder.R;
import com.neta.nfinder.settings.Settings;
import com.neta.nfinder.item.VideoItem;
import com.neta.nfinder.util.Convert;
import com.neta.nfinder.util.Time;
import com.neta.nfinder.view.VideoView;
import com.neta.nfinder.view.View;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Berfu on 4.12.2016.
 */

public class VideoItemListView extends ItemListView {
    private VideoItem videoItem;

    public VideoItemListView(Context context)
    {
        super(context);
    }

    public VideoItem getVideoItem()
    {
        return videoItem;
    }

    public void setVideoItem(VideoItem videoItem)
    {
        this.videoItem = videoItem;
        setVideoIcon();
        setRow1Text();
        setRow2Text();
        hideRow(3);
    }

    private void setVideoIcon()
    {
        setIcon(getResources().getDrawable(R.drawable._video_player));
        AsyncTask task = new AsyncTask()
        {
            Bitmap bitmap;
            @Override
            protected Object doInBackground(Object[] objects)
            {
                try
                {
                    File thumbnail = new File(Settings.SD_CARD_PATH+"/VideoDB", videoItem.get_ID()+".cache");
                    if(thumbnail.exists())
                    {
                        FileInputStream fis = new FileInputStream(thumbnail);
                        bitmap = BitmapFactory.decodeStream(fis);
                        fis.close();
                    }
                    else
                    {
                        /* Orjinal resim okunuyor. */
                        String data = videoItem.getData();
                        bitmap = ThumbnailUtils.createVideoThumbnail(data, MediaStore.Images.Thumbnails.MICRO_KIND);

                        /* Resmin küçük kopyasi sd carda kaydedilecek. */
                        File path = new File(Settings.SD_CARD_PATH + "/VideoDB");
                        if (!path.exists())
                        {
                            path.mkdirs();
                        }

                        File outputThumbnail = new File(path.getAbsolutePath(), videoItem.get_ID()+".cache");
                        if (!outputThumbnail.exists())
                        {
                            outputThumbnail.createNewFile();
                        }

                        Log.e("PATH", outputThumbnail.getAbsolutePath());
                        FileOutputStream fos = new FileOutputStream(outputThumbnail);
                        BufferedOutputStream bos = new BufferedOutputStream(fos);

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);

                        bos.flush();
                        bos.close();

                    }
                }
                catch (Exception ex)
                {
                    Log.e("PHOTO_THUMBNAIL_IOERROR", ex.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o)
            {
                setIcon(new BitmapDrawable(bitmap));
                super.onPostExecute(o);
            }
        };
        VideoView.iconTaskList.add(task);
        task.execute();
    }

    private void setRow1Text()
    {
        super.setRow1Text(videoItem.getTittle());
    }

    private void setRow2Text()
    {
        String text = videoItem.getAlbum() + " | " + Time.timeStampToDate(Long.valueOf(videoItem.getDateModified()));
        super.setRow2Text(text);
    }

}
