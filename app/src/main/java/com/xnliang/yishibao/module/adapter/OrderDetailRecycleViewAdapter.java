package com.xnliang.yishibao.module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xnliang.yishibao.R;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-24.
 */

public class OrderDetailRecycleViewAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List mData;
    private LayoutInflater mLayoutInflater;

    public OrderDetailRecycleViewAdapter(Context context , List data) {
        this.mContext = context;
        this.mData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        mLayoutInflater.inflate(R.layout.)
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
