package com.xnliang.yishibao.module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.view.OrderSubmitActivity;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-23.
 */

public class SubmitListItemAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List mData;
    private final LayoutInflater mLayoutInflater;
    public OrderSubmitActivity submitActivity;
    private MyViewHolder myViewHolder;

    public SubmitListItemAdapter(Context context , List data) {
        this.mContext = context;
        this.submitActivity = (OrderSubmitActivity) context;
        this.mData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.submit_order_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            myViewHolder = (MyViewHolder) holder;
            myViewHolder.setData(mData, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mItemPicture;
        private final TextView mItemName;
        private final TextView mItemIngegral;
        private final TextView mItemNum;

        public MyViewHolder(View itemView) {
            super(itemView);
            mItemPicture = (ImageView) itemView.findViewById(R.id.iv_submit_picture);
            mItemName = (TextView) itemView.findViewById(R.id.tv_submit_item_title);
            mItemIngegral = (TextView) itemView.findViewById(R.id.tv_submit_integral);
            mItemNum = (TextView) itemView.findViewById(R.id.tv_submit_num);
        }

        public void setData(List moduledata, int position) {
            //使用Glide加载图片
            Glide.with(mContext)
                    .load(mData.get(position))
                    .into(mItemPicture);
        }
    }
}
