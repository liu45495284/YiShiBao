package com.xnliang.yishibao.module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.OrderBean;
import com.xnliang.yishibao.presenter.OrderItemViewHolder;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-26.
 */

public class OrderNumberItemAdapter extends RecyclerView.Adapter {

    public Context mContext;
    public List<OrderBean.DataBeanX.DataBean.ValueBean> mData;
    private final LayoutInflater mLayoutInflater;
    RecyclerView.ViewHolder holder = null;

    public OrderNumberItemAdapter(Context context, List<OrderBean.DataBeanX.DataBean.ValueBean> data) {
        this.mContext = context;
        this.mData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        holder = new OrderItemViewHolder(mContext ,mLayoutInflater.inflate(R.layout.order_item_content , null));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderItemViewHolder itemViewHolder = (OrderItemViewHolder)holder;

        itemViewHolder.setData(mData ,position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
