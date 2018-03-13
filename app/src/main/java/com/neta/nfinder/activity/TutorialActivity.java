package com.neta.nfinder.activity;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.neta.nfinder.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by brefu on 26.12.2016.
 */

public class TutorialActivity extends AppCompatActivity
{

	int current = 0;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(getResources().getColor(R.color.blue));
		}

		final ImageView tutorial = (ImageView) findViewById(R.id.tutorial_page);

		Configuration configuration = getResources().getConfiguration();

		final List<Integer> imageList = new ArrayList<>();
		if(Locale.getDefault().getLanguage().equals("tr"))
		{
			imageList.add(R.drawable.tr_1);
			imageList.add(R.drawable.tr_1_2);
			imageList.add(R.drawable.tr_2);
			imageList.add(R.drawable.tr_3);
			imageList.add(R.drawable.tr_4);
			imageList.add(R.drawable.tr_5);
			imageList.add(R.drawable.tr_6);
			imageList.add(R.drawable.tr_7);
		}
		else
		{
			imageList.add(R.drawable.en_1);
			imageList.add(R.drawable.en_1_2);
			imageList.add(R.drawable.en_2);
			imageList.add(R.drawable.en_3);
			imageList.add(R.drawable.en_4);
			imageList.add(R.drawable.en_5);
			imageList.add(R.drawable.en_6);
			imageList.add(R.drawable.en_7);
		}

		tutorial.setImageDrawable(getResources().getDrawable(imageList.get(0)));

		final Button next = (Button) findViewById(R.id.next);
		next.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				next.setText(getString(R.string.next));
				current += 1;
				if(current == 7)
				{
					next.setText(getString(R.string.finish));
				}
				if (current == 8)
				{
					finish();
				}
				else
				{

					tutorial.setImageDrawable(getResources().getDrawable(imageList.get(current)));
				}
			}
		});

		Button before = (Button) findViewById(R.id.before);
		before.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (current != 0)
				{
					current -= 1;
					next.setText(getString(R.string.next));
					tutorial.setImageDrawable(getResources().getDrawable(imageList.get(current)));
				}
			}
		});
	}
}
