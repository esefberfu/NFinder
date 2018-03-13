package com.neta.nfinder.database;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Berfu on 2.12.2016.
 * Bu Sınıf soyut Cursor sınıfı implement ediyor.
 * Bu şekilde cursor nesnesinden satır kolon şeklinde gelen kayıtlar bu sınıfa aktarılıyor
 * ve gerektiği durumlarda bu kayıtlardan istenilen satır silinebiliyor
 * veya istenilen hücre değeri değiştirilebiliyor
 * ve bu şekilde yeni bir cursor nesnesi elde edilmiş oluyor.
 */

public class _Cursor implements Cursor
{
    /* Satır<kolon> şeklinde tanımlanmıştır.
     * her row = values.get bir satır getirir.
     * ve  row.get istenilen kolonla eşlenşen hücredeki bilgiyi getirir.
     * */
    private List<List<String>> values;
    private int cRow = 0;/* cursor nesnesinin bulunduğu mevcut satır bilgisini tutuyor. */
    private List<String> columnList; /* kolon listesini tutuyor */

    public _Cursor(List<String> columnList)
    {
        this.columnList = columnList;
        values = new ArrayList<List<String>>();
        cRow = 0;
    }

    public _Cursor(Cursor cursor)
    {
        /* cursor nesnesindeki hiçbir değişiklik yapmadan kolon bilgisini bu sınıfa aktar. */
        columnList = new ArrayList<String>();
        String cList[] = cursor.getColumnNames();
        for (String s : cList)
        {
            columnList.add(s);
        }

        /* değerleri tutacak values nesnesini başlat */
        values = new ArrayList<List<String>>();

        /* Cursor nesnesindeki tüm satırlar bu tabloya aktarılıyor. */
        while (cursor.moveToNext())
        {
            addRow(cursor);
        }
    }

    /* cursor nesnesinin bulunduğu mevcut satır bu tabloya ekleniyor. */
    public void addRow(Cursor cursor)
    {
        /* Yeni satır tanımlaması yapılıyor. */
        List<String> value = new ArrayList<String>();

        /* değerler gelen cursor nesnesinden alınıyor. */
        int columnCount = cursor.getColumnCount();
        for (int i = 0; i < columnCount; i++)
        {
            value.add(i, cursor.getString(i));
        }
        values.add(value);/*satır tabloya ekleniyor */
    }

    /* String dizisindeki değerler bu tabloya ekleniyor */
    public void addRow(String[] row)
    {
        List<String> value = new ArrayList<String>();
        for (int i = 0; i < getColumnCount(); i++)
        {
            value.add(i, row[i]);
        }
        values.add(value);
    }

    /*  Hücredeki bilgi value nesnesindeki bilgi ile güncelleniyor */
    public void updateCell(int row, int column, String value)
    {
        values.get(row).set(column, value);
    }

    public void deleteRow(int index)
    {
        values.remove(index);
    }

    /* hücredeki verinin value değişkenindeki veriyle aynı olup olmadığı kontrol ediliyor. */
    public boolean equals(int row, int column, String value)
    {
        return values.get(row).get(column).equals(value);
    }

    /* Hücredeki veri getiriliyor. */
    public String getString(int row, int column)
    {
        return values.get(row).get(column);
    }

    @Override
    public int getCount()
    {
        return values.size();
    }

    @Override
    public int getPosition()
    {
        return cRow;
    }

    @Override
    public boolean move(int i)
    {
        if (i >= 0 && i < getCount())
        {
            cRow = i;
            return true;
        }
        return false;
    }

    @Override
    public boolean moveToPosition(int i)
    {
        if (i >= 0 && i < getCount())
        {
            cRow = i;
            return true;
        }
        return false;
    }

    @Override
    public boolean moveToFirst()
    {
        cRow = 1;
        return true;
    }

    @Override
    public boolean moveToLast()
    {
        cRow = getCount() - 1;
        return true;
    }

    @Override
    public boolean moveToNext()
    {
        /* cRow+1 = getCount ise cRow son satırdadır ve arttırılamaz. */
        if ((cRow + 1) < getCount())
        {
            cRow++;
            return true;
        }
        return false;
    }

    @Override
    public boolean moveToPrevious()
    {
        if (cRow > 0)
        {
            cRow--;
            return true;
        }
        return false;
    }

    @Override
    public boolean isFirst()
    {
        if (cRow == 1) return true;
        return false;
    }

    @Override
    public boolean isLast()
    {
        if (cRow == getCount() - 1) return true;
        return false;
    }

    @Override
    public boolean isBeforeFirst()
    {
        if (cRow == 2) return true;
        return false;
    }

    @Override
    public boolean isAfterLast()
    {
        if (cRow == getCount() - 2) return true;
        return false;
    }

    @Override
    public int getColumnIndex(String s)
    {
        return columnList.indexOf(s);
    }

    @Override
    public int getColumnIndexOrThrow(String s) throws IllegalArgumentException
    {
        return 0;
    }

    @Override
    public String getColumnName(int i)
    {
        return columnList.get(i);
    }

    /***/
    @Override
    public String[] getColumnNames()
    {
        String[] list = new String[getColumnCount()];
        for (int i = 0; i < getColumnCount(); i++)
        {
            list[i] = getColumnName(i);
        }
        return new String[0];
    }

    @Override
    public int getColumnCount()
    {
        return columnList.size();
    }

    @Override
    public byte[] getBlob(int i)
    {
        return new byte[0];
    }

    @Override
    public String getString(int i)
    {
        return values.get(cRow).get(i);
    }

    @Override
    public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer)
    {

    }

    @Override
    public short getShort(int i)
    {
        return 0;
    }

    @Override
    public int getInt(int i)
    {
        return Integer.valueOf(values.get(cRow).get(i));
    }

    public int getInt(int row, int column)
    {
        return Integer.valueOf(values.get(row).get(column));
    }

    @Override
    public long getLong(int i)
    {
        return Long.valueOf(values.get(cRow).get(i));
    }

    @Override
    public float getFloat(int i)
    {
        return Float.valueOf(values.get(cRow).get(i));
    }

    @Override
    public double getDouble(int i)
    {
        return Double.valueOf(values.get(cRow).get(i));
    }

    @Override
    public int getType(int i)
    {
        return 0;
    }

    @Override
    public boolean isNull(int i)
    {
        return false;
    }

    @Override
    public void deactivate()
    {

    }

    @Override
    public boolean requery()
    {
        return false;
    }

    @Override
    public void close()
    {
        values = null;
        columnList = null;
        cRow = 0;
    }

    @Override
    public boolean isClosed()
    {
        return false;
    }

    @Override
    public void registerContentObserver(ContentObserver contentObserver)
    {

    }

    @Override
    public void unregisterContentObserver(ContentObserver contentObserver)
    {

    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver)
    {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver)
    {

    }

    @Override
    public void setNotificationUri(ContentResolver contentResolver, Uri uri)
    {

    }

    @Override
    public Uri getNotificationUri()
    {
        return null;
    }

    @Override
    public boolean getWantsAllOnMoveCalls()
    {
        return false;
    }

    @Override
    public void setExtras(Bundle bundle)
    {

    }

    @Override
    public Bundle getExtras()
    {
        return null;
    }

    @Override
    public Bundle respond(Bundle bundle)
    {
        return null;
    }

    @Override
    public String toString()
    {
        for (int i = 0; i < getCount(); i++)
        {
            Log.e(String.valueOf(i), values.get(i).toString());
        }
        return null;
    }
}
