package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.MyOrderDetailAdapter;
import com.xnliang.yishibao.view.customview.OrderDetailViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-02-24.
 */

public class MyOrderFragment extends BaseFragment {

    private View mView;
    private List<String> mTitleList;
    private List<MyOrderDetailFragment> mFragmentList;

    @Bind(R.id.tb_my_order)
    TabLayout mTabLayout;
    @Bind(R.id.vp_my_order)
    OrderDetailViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.my_order , container ,false);
        ButterKnife.bind(this, mView);
//        initView();
        initTitile();
        initFragment();

        mViewPager.setAdapter(new MyOrderDetailAdapter(getActivity().getSupportFragmentManager(), mFragmentList, mTitleList));
        //将tablayout与fragment关联
        mTabLayout.setupWithViewPager(mViewPager);

        return mView;
    }

    private void initView() {
//        TabLayout tabLayout = mView.findViewById(R.id.tb_my_order);
//        ViewPager viewPager = mView.findViewById(R.id.vp_my_order);
    }

    private void initTitile() {
        mTitleList = new ArrayList<>();
        mTitleList.add(getString(R.string.my_order_all));
        mTitleList.add(getString(R.string.my_order_no_pay));
        mTitleList.add(getString(R.string.my_order_pay));
        mTitleList.add(getString(R.string.my_order_complete));
        //设置tablayout模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //tablayout获取集合中的名称
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(3)));

    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(MyOrderDetailFragment.newInstance(mTitleList.get(0)));
        mFragmentList.add(MyOrderDetailFragment.newInstance(mTitleList.get(1)));
        mFragmentList.add(MyOrderDetailFragment.newInstance(mTitleList.get(2)));
        mFragmentList.add(MyOrderDetailFragment.newInstance(mTitleList.get(3)));

    }

}
