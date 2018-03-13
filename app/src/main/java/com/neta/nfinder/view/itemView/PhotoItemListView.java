package com.neta.nfinder.view.itemView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.neta.nfinder.R;
import com.neta.nfinder.settings.Settings;
import com.neta.nfinder.item.PhotoItem;
import com.neta.nfinder.util.Convert;
import com.neta.nfinder.util.Time;
import com.neta.nfinder.view.PhotoView;
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

public class PhotoItemListView extends ItemListView
{
    private PhotoItem photoItem;

    public PhotoItemListView(Context context)
    {
        super(context);
    }

    public PhotoItem getPhotoItem()
    {
        return photoItem;
    }

    public void setPhotoItem(PhotoItem photoItem)
    {
        this.photoItem = photoItem;
        setPhotoIcon();
        setRow1Text();
        setRow2Text();
        hideRow(3);
    }

    /* Resimler için ufak resim oluşturuluyor ve bu işlem Asynctasklar yardımı ile yapılıyor. */
    private void setPhotoIcon()
    {
        setIcon(getResources().getDrawable(R.drawable._pictures));
        AsyncTask iconTask = new AsyncTask()
        {
            Bitmap bitmap;
            @Override
            protected Object doInBackground(Object[] objects)
            {
                try
                {
                    File thumbnail = new File(Settings.SD_CARD_PATH+"/PhotoDB", photoItem.get_ID()+".cache");
                    if(thumbnail.exists())
                    {
                        FileInputStream fis = new FileInputStream(thumbnail);
                        bitmap = BitmapFactory.decodeStream(fis);
                        fis.close();
                    }
                    else
                    {
                        /* Orjinal resim okunuyor. */
                        FileInputStream fis = new FileInputStream(photoItem.getData());
                        Bitmap imageBitmap = BitmapFactory.decodeStream(fis);

                        bitmap = Bitmap.createScaledBitmap(imageBitmap, Convert.dpToPx(36), Convert.dpToPx(36), false);

                        /* Resmin küçük kopyasi sd carda kaydedilecek. */
                        File path = new File(Settings.SD_CARD_PATH + "/PhotoDB");
                        if (!path.exists())
                        {
                            path.mkdirs();
                        }

                        File outputThumbnail = new File(path.getAbsolutePath(), photoItem.get_ID()+".cache");
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
        /* Oluşturulan task PhotoView sınıfındaki static taskListe ekleniyor. İstenilen durumda durdurulabilecekler. */
        PhotoView.iconTaskList.add(iconTask);
        iconTask.execute();

    }

    private void setRow1Text()
    {
        super.setRow1Text(photoItem.getTittle());
    }

    private void setRow2Text()
    {
        String text = photoItem.getAlbum() + " | " + Time.timeStampToDate(Long.valueOf(photoItem.getDateModified()));
        super.setRow2Text(text);
    }
}
