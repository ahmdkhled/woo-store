package com.example.woocommerce.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.woocommerce.ui.OverviewFrag;
import com.example.woocommerce.ui.ReviewsFrag;

public class DetailsAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments ={new OverviewFrag(),new ReviewsFrag()};
    private String[] tabs={"overview","reviews"};

    public DetailsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
