package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.CartRecommendAdapter;
import com.xnliang.yishibao.module.adapter.ShopOneIntegralAdapter;
import com.xnliang.yishibao.module.bean.CartBean;
import com.xnliang.yishibao.module.utils.DividerGridItemDecoration;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-22.
 */

public class CartReItemViewHolder extends BaseViewHolder {

    public Context mContext;
    public RecyclerView mView;
    private RecyclerView.ItemDecoration mDecoration = null;

    public CartReItemViewHolder(Context context , View itemView) {
        super(itemView);
        this.mContext = context;
        mView = itemView.findViewById(R.id.rv_cart_re_item);
    }

    public void setData(List<CartBean.DataBean.ListsBean> data){
        CartRecommendAdapter shopCategray = new CartRecommendAdapter(mContext , data);
        mView.setAdapter(shopCategray);
        if (mDecoration == null) {
            mDecoration = new DividerGridItemDecoration(mContext);
            mView.addItemDecoration(mDecoration);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext ,2);
        mView.setLayoutManager(gridLayoutManager);
    }
}
