package com.neta.nfinder.view.itemView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neta.nfinder.R;
import com.neta.nfinder.util.Convert;

/**
 * Created by Berfu on 2.12.2016.
 */

public abstract class ItemGridView extends LinearLayout
{
    private TextView iconView;
    private TextView nameTextView;

    public ItemGridView(Context context)
    {
        super(context);
        createThis();
    }

    /* ItemGridView nesnesinin özelliklerini belirleyecek */
    private void createThis()
    {
        this.setOrientation(LinearLayout.VERTICAL);
        this.setPadding(Convert.dpToPx(5), Convert.dpToPx(5), Convert.dpToPx(5), Convert.dpToPx(5));
        this.setGravity(Gravity.CENTER_HORIZONTAL);
        this.setBackground(getResources().getDrawable(R.drawable.item_click));
        this.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                setOnClickAction();
            }
        });

        this.setOnLongClickListener(new OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                setOnLongClickAction();
                return true;
            }
        });
        createIconView();
        createNameView();
    }

    public void setIcon(Drawable d)
    {
        iconView.setBackground(d);
    }

    public void setText(String text)
    {
        nameTextView.setText(text);
    }

    public void setIconText(String text)
    {
        iconView.setText(text);
    }

    public String getIconText()
    {
        return iconView.getText().toString();
    }

    /* IconView nesnesini başlatacak ve özelliklerini oluşturacak */
    private void createIconView()
    {
        LinearLayout.LayoutParams params = new LayoutParams(Convert.dpToPx(36), Convert.dpToPx(36));
        params.setMargins(0, 0, 0, Convert.dpToPx(10));
        iconView = new TextView(getContext());
        iconView.setLayoutParams(params);
        iconView.setGravity(Gravity.CENTER);
        iconView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 8);
        this.addView(iconView);
    }

    /* NameView nesnesini başlatacak ve özelliklerini oluşturacak */
    private void createNameView()
    {
        nameTextView = new TextView(getContext());
        nameTextView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        nameTextView.setTextColor(Color.BLACK);
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_PT,6);
        nameTextView.setMaxLines(2);
        nameTextView.setGravity(Gravity.CENTER);
        nameTextView.setEllipsize(TextUtils.TruncateAt.END);
        this.addView(nameTextView);
    }

    protected abstract void setOnClickAction();
    protected abstract void setOnLongClickAction();
}
