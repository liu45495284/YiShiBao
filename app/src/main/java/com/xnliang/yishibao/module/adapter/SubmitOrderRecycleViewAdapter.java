package com.xnliang.yishibao.module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.ShoppingCartBean;
import com.xnliang.yishibao.presenter.CartItemViewHolder;
import com.xnliang.yishibao.presenter.CartReItemViewHolder;
import com.xnliang.yishibao.presenter.CartRecommendViewHolder;
import com.xnliang.yishibao.presenter.SubmitItemViewHolder;
import com.xnliang.yishibao.presenter.SubmitPathViewHolder;
import com.xnliang.yishibao.presenter.SubmitTotalViewHolder;
import com.xnliang.yishibao.view.OrderSubmitActivity;
import com.xnliang.yishibao.view.ShoppingCartActivity;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-11.
 */

public class SubmitOrderRecycleViewAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private OrderSubmitActivity submitActivity;
    private List mData;
    private static final int SUBMIT_PATH = 0;
    private static final int SUBMIT_ITEM = 1;
    private static final int SUBMIT_TOTAL = 2;
    private int CURRENT_TYPE = SUBMIT_PATH;
    private RecyclerView.ViewHolder mViewHolder;
    private final LayoutInflater mInflater;

    public SubmitOrderRecycleViewAdapter(Context context , List data) {
        this.mContext = context;
        this.submitActivity = (OrderSubmitActivity) context;
        this.mData = data;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SUBMIT_PATH:
                mViewHolder = new SubmitPathViewHolder(mContext ,mInflater.inflate(R.layout.submit_path, null));
                break;
            case SUBMIT_ITEM:
                mViewHolder = new SubmitItemViewHolder(mContext ,mInflater.inflate(R.layout.submit_item_recycle_view, null));
                break;
            case SUBMIT_TOTAL:
                mViewHolder = new SubmitTotalViewHolder(mContext ,mInflater.inflate(R.layout.submit_total, null));
                break;
        }
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case SUBMIT_PATH:
                SubmitPathViewHolder pahtViewHolder = (SubmitPathViewHolder) holder;
                break;
            case SUBMIT_ITEM:
                SubmitItemViewHolder submitItemViewHolder = (SubmitItemViewHolder) holder;
                submitItemViewHolder.setData(mData);
                break;
            case SUBMIT_TOTAL:
                SubmitTotalViewHolder submitTotalViewHolder = (SubmitTotalViewHolder) holder;
                submitTotalViewHolder.setData(mData);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case SUBMIT_PATH:
                CURRENT_TYPE = SUBMIT_PATH;
                break;
            case SUBMIT_ITEM:
                CURRENT_TYPE = SUBMIT_ITEM;
                break;
            case SUBMIT_TOTAL:
                CURRENT_TYPE = SUBMIT_TOTAL;
                break;
        }
        return CURRENT_TYPE;
    }
}
