package com.xnliang.yishibao.module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.presenter.CartItemViewHolder;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-11.
 */

public class CartRecycleViewAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List mData;
    private static final int CART_ITEM_LIST = 0;
    private static final int CART_RECOMMEND = 1;
    private static final int CART_RECOMMEND_ITEM = 2;
    private int CURRENT_TYPE = CART_ITEM_LIST;
    private RecyclerView.ViewHolder mViewHolder;
    private final LayoutInflater mInflater;

    public CartRecycleViewAdapter(Context context , List data) {
        this.mContext = context;
        this.mData = data;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case CART_ITEM_LIST:
                mViewHolder = new CartItemViewHolder(mContext ,mInflater.inflate(R.layout.cart_item_recycle_view, null));
                break;
        }
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case CART_ITEM_LIST:
                CartItemViewHolder itemViewHolder = (CartItemViewHolder) holder;
                itemViewHolder.setData(mData);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case CART_ITEM_LIST:
                CURRENT_TYPE = CART_ITEM_LIST;
                break;
            case CART_RECOMMEND:
                CURRENT_TYPE = CART_RECOMMEND;
                break;
            case CART_RECOMMEND_ITEM:
                CURRENT_TYPE = CART_RECOMMEND_ITEM;
                break;
        }
        return CURRENT_TYPE;
    }
}
