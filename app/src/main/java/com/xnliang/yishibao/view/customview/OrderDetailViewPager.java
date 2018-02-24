package com.xnliang.yishibao.view.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by JackLiu on 2018-02-24.
 */

public class OrderDetailViewPager extends ViewPager {
    public OrderDetailViewPager(Context context) {
        super(context);
    }

    public OrderDetailViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item , false);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }
}
