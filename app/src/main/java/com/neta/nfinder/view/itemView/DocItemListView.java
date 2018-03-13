package com.neta.nfinder.view.itemView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.neta.nfinder.R;
import com.neta.nfinder.database.FileExt;
import com.neta.nfinder.item.DocItem;
import com.neta.nfinder.util.Convert;
import com.neta.nfinder.util.Time;
import com.neta.nfinder.view.View;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Berfu on 4.12.2016.
 */

public class DocItemListView extends ItemListView
{
    private DocItem docItem; /* Belge bilgilerini tutan nesne */

    public DocItemListView(Context context)
    {
        super(context);
    }

    public void setDocItem(DocItem docItem)
    {
        this.docItem = docItem;
        createThis();
    }

    public DocItem getDocItem()
    {
        return docItem;
    }

    /* Bu view'ın özellikleri oluşturuluyor */
    private void createThis()
    {
        createDocIcon();
        createRow1Text();
        createRow2Text();
        hideRow(3);
    }

    /* Icon oluşturuluyor */
    private void createDocIcon()
    {
        String ext = docItem.getExt();

        //mimeType değerine göre nesne ikonu değişiklik gösterecek.
        if (ext.equals(".pdf"))
        {
            this.setIcon(getResources().getDrawable(R.drawable._pdf));
        }
        else if (ext.equals(".doc"))
        {
            this.setIcon(getResources().getDrawable(R.drawable._doc));
        }
        else if (ext.equals(".docx"))
        {
            this.setIcon(getResources().getDrawable(R.drawable._doc));
        }
        else if (ext.equals(".xls"))
        {
            this.setIcon(getResources().getDrawable(R.drawable._xls));
        }
        else if (ext.equals(".xlsx"))
        {
            this.setIcon(getResources().getDrawable(R.drawable._xls));
        }
        else if (ext.equals(".txt"))
        {
            this.setIcon(getResources().getDrawable(R.drawable._txt));
        }
        else if (ext.equals(".ppt"))
        {
            this.setIcon(getResources().getDrawable(R.drawable._ppt));
        }
        else if (ext.equals(".pptx"))
        {
            this.setIcon(getResources().getDrawable(R.drawable._ppt));
        }
        else
        {
            int color = FileExt.getColor(ext);

            GradientDrawable shape = new GradientDrawable();
            shape.setColor(color);
            shape.setCornerRadius(Convert.dpToPx(15));
            shape.setShape(GradientDrawable.RECTANGLE);

            setIcon(shape);
            setIconText(ext.substring(1)); /* Ex: .pdf yerine pdf yazısı gönderiliyor.*/

        }
    }

    /* 1.satırda yazacak yazılar oluşturuluyor */
    private void createRow1Text()
    {
        super.setRow1Text(docItem.getTitle());
    }

    /* 2.satırda yazacak yazılar oluşturuluyor */
    private void createRow2Text()
    {
        String text = Convert.byteToMb(Long.valueOf(docItem.getSize())) + " | " +
                Time.timeStampToDate(Long.valueOf(docItem.getDateModified()));
        super.setRow2Text(text);
    }
}
