package com.csto.bluelife.wirelessfax.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.csto.bluelife.wirelessfax.model.TiffImages;

/**
 * Created by slomka.jin on 2016/11/15.
 */

public class TiffPagerAdapter extends FragmentPagerAdapter {
    public TiffPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        TiffImageFfragment fragment=TiffImageFfragment.getInstance();
        fragment.setIndex(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return TiffImages.getInstance().getImages().size();
    }
}
