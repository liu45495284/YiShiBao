package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by JackLiu on 2018-02-02.
 */

/**
 * for fix "java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid item position" bug
 */
public class TravelLinearLayoutManager extends LinearLayoutManager {
    public TravelLinearLayoutManager(Context context) {
        super(context);
    }

    public TravelLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {

        super(context, orientation, reverseLayout);
    }

    public TravelLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }
}
