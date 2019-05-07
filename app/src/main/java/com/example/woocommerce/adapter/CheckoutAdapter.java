package com.example.woocommerce.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.woocommerce.ui.AddressFrag;
import com.example.woocommerce.ui.PaymentFrag;

public class CheckoutAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments={new AddressFrag(),new PaymentFrag()};
    String[] tabs={"address","payment"};

    public CheckoutAdapter(FragmentManager fm) {
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
        return super.getPageTitle(position);
    }
}
