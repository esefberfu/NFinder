package com.neta.nfinder.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.neta.nfinder.R;
import com.neta.nfinder.item.FileItem;
import com.neta.nfinder.util.Convert;
import com.neta.nfinder.util.Time;
import com.neta.nfinder.view.itemView.FileItemListView;
import com.neta.nfinder.view.itemView.ItemDetailView;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brefu on 24.12.2016.
 */

public abstract class FileView extends View
{
	public static final List<AsyncTask> iconTaskList = new ArrayList<>();

	public FileView(Context context)
	{
		super(context);
	}

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
	public void onClickAction(android.view.View v)
	{
		FileItemListView item = (FileItemListView) v;
		FileItem fileItem = item.getFileItem();
		Uri uri = Uri.fromFile(new File(fileItem.getData()));

		Intent intent = new Intent(Intent.ACTION_VIEW);

        /* docItem.getExt().substring(1): .pdf --> pdf
         * Bazı dosyaların mime type'ı veritabanında null olarak gözüküyor
         * bu yüzden dosya uzantılarından mime type buldurup işlem yapıyoruz*/
		intent.setDataAndType(uri, MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileItem.getExt().substring(1)));
		try {
			getContext().startActivity(intent);
		} catch (Exception e) {
			Toast.makeText(getContext(),"No Application",Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onLongClickAction(android.view.View v)
	{
		final FileItemListView item = (FileItemListView) v;
		final FileItem fileItem = item.getFileItem();

		Map<String, String> values = new LinkedHashMap<>();
		values.put(getResources().getString(R.string.file_name), fileItem.getTitle());
		values.put(getResources().getString(R.string.file_path), fileItem.getData());
		values.put(getResources().getString(R.string.type), fileItem.getExt());
		values.put(getResources().getString(R.string.size), Convert.byteToMb(Long.valueOf(fileItem.getSize())));
		values.put(getResources().getString(R.string.date_modified), Time.timeStampToDate(Long.valueOf(fileItem.getDateModified())));

		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		AlertDialog dialog = builder.create();
		final ItemDetailView detailView = new ItemDetailView(getContext(), dialog, item.getIcon(), String.valueOf(fileItem.get_id()), values)
		{
			@Override
			public void onClickActionShare()
			{
				share(fileItem);
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

	private void share(FileItem fileItem)
	{
		File f=new File(fileItem.getData());
		Uri uri = Uri.parse("file://"+f.getAbsolutePath());
		Intent share = new Intent(Intent.ACTION_SEND);
		share.putExtra(Intent.EXTRA_STREAM, uri);
		share.setType("application/*");
		share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		getContext().startActivity(Intent.createChooser(share, "Share File"));
	}
}
