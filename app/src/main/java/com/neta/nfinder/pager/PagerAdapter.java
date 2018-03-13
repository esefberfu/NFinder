package com.neta.nfinder.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by PC on 20.11.2016.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int count;
    private Finder finder = null;
    private Recent recent = null;
    public PagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                finder = new Finder();
                return finder;
            case 1:
                recent = new Recent();
                return recent;
        }

        return null;
    }

    @Override
    public int getCount() {
        return count;
    }




}
