package com.dd.musicplayerdemo.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.dd.musicplayerdemo.R;
import com.dd.musicplayerdemo.adapters.MainFragmentPagerAdapter;
import com.dd.musicplayerdemo.fragments.ArtistFragment;
import com.dd.musicplayerdemo.fragments.ChannelFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private FragmentPagerAdapter mAdapter;

    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initTabLayout();

        initData();

        initViewPager();
    }

    private void initData() {
        mFragments.add(new ChannelFragment());
        mFragments.add(new ArtistFragment());
    }

    private void initViewPager() {
        mAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(),mFragments);

        mViewPager.setAdapter(mAdapter);
    }

    private void initTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
    }
}
