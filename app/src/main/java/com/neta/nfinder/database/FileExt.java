package com.neta.nfinder.database;

import android.graphics.Color;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by Berfu on 7.12.2016.
 */

public class FileExt
{
    private static final String[] fileExt =
            {
                    ".rar",
                    ".zip",
                    ".gzip",
                    ".bzip",
                    ".tar",
                    ".iso",
                    ".mdf",
                    ".bin",
                    ".nrg",
                    ".img",
                    ".xml",
                    ".html",
                    ".apk",
                    ".7z",
                    ".tgz",
                    ".tar.gz",
                    ".js",
                    ".java",
                    ".c",
                    ".cpp",
                    ".py",
                    ".jsp",
                    ".jsf",
                    ".php",
                    ".sql",
                    ".jar",
                    ".torrent",
                    ".pdf",

                    /* SPREADSHHET */
                    ".ots",
                    ".sxc",
                    ".stc",
                    ".dif",
                    ".xls",
                    ".xlsx",
                    ".xlt",
                    ".slk",
                    ".csv",
                    ".xml",

                    /* TEXT */
                    ".odt",
                    ".ott",
                    ".sxw",
                    ".stw",
                    ".doc",
                    ".docx",
                    ".rtf",
                    ".uot",

                    /* PPOINT*/
                    ".ppt",
                    ".pptx",
                    ".odp",
                    ".otp",
                    ".sxi",
                    ".sti",
                    ".pot",
                    ".sxd",
                    ".uop",
                    ".odf",

                    /* DRAW */
                    ".odf",
                    ".otg",
                    ".std"
            };

    public static int getColor(String ext)
    {
        return Color.parseColor("#FFFFFF");
    }

}
