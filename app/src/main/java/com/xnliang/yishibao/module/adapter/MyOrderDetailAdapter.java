package com.xnliang.yishibao.module.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xnliang.yishibao.view.fragment.MyOrderDetailFragment;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-24.
 */

public class MyOrderDetailAdapter extends FragmentPagerAdapter {

    private List<MyOrderDetailFragment> mFragmentList;
    private List<String> mTilteLis;

    public MyOrderDetailAdapter(FragmentManager fm, List<MyOrderDetailFragment> fragmentList, List<String> tilteLis) {
        super(fm);
        mFragmentList = fragmentList;
        mTilteLis = tilteLis;

    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTilteLis.get(position);
    }


}
