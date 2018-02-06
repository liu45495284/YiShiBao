package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by JackLiu on 2018-02-05.
 */

public class FoundLinearLayoutManager extends LinearLayoutManager {
    public FoundLinearLayoutManager(Context context) {
        super(context);
    }

    public FoundLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {

        super(context, orientation, reverseLayout);
    }

    public FoundLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
