package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.ShopOneIntegralAdapter;
import com.xnliang.yishibao.module.adapter.ShopTeamIntegralAdapter;
import com.xnliang.yishibao.module.bean.ShopIndexBean;
import com.xnliang.yishibao.module.utils.DividerGridItemDecoration;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-07.
 */

public class TeamBuyGoodsViewHolder extends BaseViewHolder {

    private final Context mContext;
    private RecyclerView mTeamBuy;
    private RecyclerView.ItemDecoration mDecoration = null;

    public TeamBuyGoodsViewHolder(Context context , View itemView) {
        super(itemView);
        this.mContext = context;
        mTeamBuy = itemView.findViewById(R.id.rv_team_buy);
    }

    public void setData(List<ShopIndexBean.DataBean.TgListsBean> data) {
        ShopTeamIntegralAdapter shopCategray = new ShopTeamIntegralAdapter(mContext , data);
        mTeamBuy.setAdapter(shopCategray);
        if (mDecoration == null) {
            mDecoration = new DividerGridItemDecoration(mContext);
            mTeamBuy.addItemDecoration(mDecoration);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext ,2);
        mTeamBuy.setLayoutManager(gridLayoutManager);

    }
}
