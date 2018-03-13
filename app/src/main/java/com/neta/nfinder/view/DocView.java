package com.neta.nfinder.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.neta.nfinder.R;
import com.neta.nfinder.item.DocItem;
import com.neta.nfinder.util.Convert;
import com.neta.nfinder.util.Time;
import com.neta.nfinder.view.itemView.DocItemListView;
import com.neta.nfinder.view.itemView.ItemDetailView;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by brefu on 25.12.2016.
 */

public abstract class DocView extends View
{

	public DocView(Context context)
	{
		super(context);
	}

	@Override
	public void onClickAction(android.view.View v)
	{
		DocItemListView item = (DocItemListView) v;
		DocItem docItem = item.getDocItem();

		Uri uri = Uri.fromFile(new File(docItem.getData()));

		Intent intent = new Intent(Intent.ACTION_VIEW);

        /* docItem.getExt().substring(1): .pdf --> pdf
         * Bazı dosyaların mime type'ı veritabanında null olarak gözüküyor
         * bu yüzden dosya uzantılarından mime type buldurup işlem yapıyoruz*/
		intent.setDataAndType(uri, MimeTypeMap.getSingleton().getMimeTypeFromExtension(docItem.getExt().substring(1)));
		try {
			getContext().startActivity(intent);
		} catch (Exception e) {
			Toast.makeText(getContext(),"No Application",Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onLongClickAction(android.view.View v)
	{
		final DocItemListView item = (DocItemListView) v;
		final DocItem docItem = item.getDocItem();

		Map<String, String> values = new LinkedHashMap<>();
		values.put(getResources().getString(R.string.file_name), docItem.getTitle());
		values.put(getResources().getString(R.string.file_path), docItem.getData());
		values.put(getResources().getString(R.string.type), docItem.getExt());
		values.put(getResources().getString(R.string.size), Convert.byteToMb(Long.valueOf(docItem.getSize())));
		values.put(getResources().getString(R.string.date_modified), Time.timeStampToDate(Long.valueOf(docItem.getDateModified())));

		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		AlertDialog dialog = builder.create();

		final ItemDetailView detailView = new ItemDetailView(getContext(), dialog, item.getIcon(), docItem.get_ID(), values)
		{
			@Override
			public void onClickActionShare()
			{
				share(docItem);
			}
		};

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

		dialog.setView(detailView);
		dialog.show();
	}

	private void share(DocItem docItem)
	{
		File f=new File(docItem.getData());
		Uri uri = Uri.parse("file://"+f.getAbsolutePath());
		Intent share = new Intent(Intent.ACTION_SEND);
		share.putExtra(Intent.EXTRA_STREAM, uri);
		share.setType("application/*");
		share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		getContext().startActivity(Intent.createChooser(share, "Share File"));
	}
}
