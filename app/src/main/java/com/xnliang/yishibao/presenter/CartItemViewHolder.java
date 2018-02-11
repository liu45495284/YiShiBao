package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.CartItemViewAdapter;
import com.xnliang.yishibao.module.utils.ListDecoration;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-11.
 */

public class CartItemViewHolder extends BaseViewHolder{

    public RecyclerView mView;
    public Context mContext;
    public CartItemViewHolder(Context context ,View itemView) {
        super(itemView);
        this.mContext = context;
        mView = itemView.findViewById(R.id.rv_cart_item);
    }

    public void setData(List data){
        CartItemViewAdapter itemViewAdapter = new CartItemViewAdapter();
        mView.setAdapter(itemViewAdapter);

        mView.addItemDecoration(new ListDecoration(mContext , ListDecoration.VERTICAL_LIST ,R.drawable.list_divide));
        LinearLayoutManager manager = new LinearLayoutManager(mContext ,LinearLayoutManager.VERTICAL , false);
        mView.setLayoutManager(manager);

    }
}
