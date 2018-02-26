package com.xnliang.yishibao.view.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.MyOrderDetailAdapter;
import com.xnliang.yishibao.view.customview.OrderDetailViewPager;

import java.lang.reflect.Field;
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

    @Bind(R.id.rl_order_back)
    RelativeLayout mBack;
    @Bind(R.id.tb_my_order)
    TabLayout mTabLayout;
    @Bind(R.id.vp_my_order)
    OrderDetailViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.my_order , container ,false);
        ButterKnife.bind(this, mView);
        initTitile();
        initFragment();
        initView();

        mViewPager.setAdapter(new MyOrderDetailAdapter(getActivity().getSupportFragmentManager(), mFragmentList, mTitleList));
        //将tablayout与fragment关联
        mTabLayout.setupWithViewPager(mViewPager);

        return mView;
    }

    private void initView() {
//        TabLayout mTabLayout = mView.findViewById(R.id.tb_my_order);
//        OrderDetailViewPager mViewPager = mView.findViewById(R.id.vp_my_order);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
        mFragmentList.add(MyOrderDetailFragment.newInstance(0));
        mFragmentList.add(MyOrderDetailFragment.newInstance(1));
        mFragmentList.add(MyOrderDetailFragment.newInstance(2));
        mFragmentList.add(MyOrderDetailFragment.newInstance(3));

    }

    /**
     * modify tablayout indicator line width
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public void setIndicator (TabLayout tabs,int leftDip,int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(mTabLayout,getResources().getDimensionPixelSize(R.dimen.order_tab_indicator),
                        getResources().getDimensionPixelSize(R.dimen.order_tab_indicator));
            }
        });
    }
}
