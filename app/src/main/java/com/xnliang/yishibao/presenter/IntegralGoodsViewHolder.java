package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.ShopOneIntegralAdapter;
import com.xnliang.yishibao.module.utils.DividerGridItemDecoration;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-07.
 */

public class IntegralGoodsViewHolder extends BaseViewHolder {

    private final Context mContext;
    private final RecyclerView mRecyclerView;

    public IntegralGoodsViewHolder(Context context , View itemView) {
        super(itemView);
        this.mContext = context;
        mRecyclerView = itemView.findViewById(R.id.rv_one_integral);
    }

    public void setData(List data) {
        ShopOneIntegralAdapter shopCategray = new ShopOneIntegralAdapter(mContext , data);
        mRecyclerView.setAdapter(shopCategray);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext ,LinearLayoutManager.HORIZONTAL , false);
        mRecyclerView.setLayoutManager(layoutManager);

    }
}
