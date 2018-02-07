package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.ShopCategrayRecycleViewAdapter;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-06.
 */

public class GoodsCategrayViewHolder extends BaseViewHolder {

    private final Context mContext;
    private final RecyclerView mRecyclerView;

    public  GoodsCategrayViewHolder(Context context , View itemView) {
        super(itemView);
        this.mContext = context;
        mRecyclerView = itemView.findViewById(R.id.rv_shop_categray);
    }

    public void setData(List data) {
        ShopCategrayRecycleViewAdapter shopCategray = new ShopCategrayRecycleViewAdapter(mContext , data);
        mRecyclerView.setAdapter(shopCategray);

        GridLayoutManager layoutManager = new GridLayoutManager(mContext ,5);
        mRecyclerView.setLayoutManager(layoutManager);
    }
}
