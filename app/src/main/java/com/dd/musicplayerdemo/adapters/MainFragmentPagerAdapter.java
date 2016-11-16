package com.dd.musicplayerdemo.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dd.musicplayerdemo.constants.Uri;

import java.util.List;

/**
 * Created by my on 2016/11/5.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public MainFragmentPagerAdapter(FragmentManager supportFragmentManager, List<Fragment> fragments) {
        super(supportFragmentManager);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments==null?0:fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Uri.TABS[position];
    }
}
