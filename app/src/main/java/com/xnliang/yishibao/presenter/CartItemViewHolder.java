package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.CartItemViewAdapter;
import com.xnliang.yishibao.module.bean.CartBean;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.view.ShoppingCartActivity;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-11.
 */

public class CartItemViewHolder extends BaseViewHolder implements ShoppingCartActivity.CartItemListener{

    public RecyclerView mView;
    public Context mContext;
    public ShoppingCartActivity cartActivity;
    private CartItemViewAdapter mItemViewAdapter;

    public CartItemViewHolder(Context context ,View itemView) {
        super(itemView);
        this.mContext = context;
        this.cartActivity = (ShoppingCartActivity) context;
        mView = itemView.findViewById(R.id.rv_cart_item);
    }

    public void setData(List<CartBean.DataBean.ListsBean> data){
        mItemViewAdapter = new CartItemViewAdapter(mContext , data);
        mItemViewAdapter.setCheckInterface(cartActivity);
        mView.setAdapter(mItemViewAdapter);

        mView.addItemDecoration(new ListDecoration(mContext , ListDecoration.VERTICAL_LIST ,R.drawable.cart_list_divide));
        LinearLayoutManager manager = new LinearLayoutManager(mContext ,LinearLayoutManager.VERTICAL , false);
        mView.setLayoutManager(manager);


    }

    @Override
    public void cartItem() {
        mItemViewAdapter.notifyDataSetChanged();
    }
}
