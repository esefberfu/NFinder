package com.neta.nfinder.util;

import android.content.Loader;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by Berfu on 2.12.2016.
 */

public final class Convert
{
	public static int pxToDp(int px)
	{
		return (int) (px / Resources.getSystem().getDisplayMetrics().density);
	}

	//dp olarak girilen değeri pixel'e çeviren fonksiyon
	public static int dpToPx(int dp)
	{
		return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
	}

	public static String byteToMb(Long bytes)
	{
		String type = null;
		double size = Double.valueOf(bytes);
		Double mb_size = 0.0;

		if (bytes < 1000)
		{
			type = "byte";
			mb_size = size;
		}

		if (bytes < 1000 * 1000)
		{
			type = "Kb";
			mb_size = size / (1000);
		}
		else if (bytes < 1000 * 1000 * 1000)
		{
			type = "Mb";
			mb_size = size / (1000 * 1000);
			;
		}
		else if (bytes < 1000 * 1000 * 1000 * 1000)
		{
			type = "Gb";
			mb_size = size / (1000 * 1000 * 1000);
			;
		}


		DecimalFormat format = new DecimalFormat("#0.00");
		String ssize = format.format(mb_size) + " " + type; /* s(tring)size */
		return ssize;
	}
}
