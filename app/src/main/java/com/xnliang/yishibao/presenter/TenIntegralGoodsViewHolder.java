package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.ShopOneIntegralAdapter;
import com.xnliang.yishibao.module.adapter.ShopTenIntegralAdapter;
import com.xnliang.yishibao.module.bean.ShopIndexBean;
import com.xnliang.yishibao.module.utils.ListDecoration;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-07.
 */

public class TenIntegralGoodsViewHolder extends BaseViewHolder {

    private final Context mContext;
    private final RecyclerView mRecyclerView;
    private ListDecoration mDecoration;

    public TenIntegralGoodsViewHolder(Context context , View itemView) {
        super(itemView);
        this.mContext = context;
        mRecyclerView = itemView.findViewById(R.id.rv_one_integral);
    }

    public void setData(List<ShopIndexBean.DataBean.SjfListsBean> data) {
        ShopTenIntegralAdapter shopCategray = new ShopTenIntegralAdapter(mContext , data);
        mRecyclerView.setAdapter(shopCategray);

        if (mDecoration == null) {
            mDecoration = new ListDecoration(mContext, ListDecoration.HORIZONTAL_LIST, R.drawable.list_divide);
        }
        mRecyclerView.addItemDecoration(mDecoration);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext ,LinearLayoutManager.HORIZONTAL , false);
        mRecyclerView.setLayoutManager(layoutManager);

    }
}
