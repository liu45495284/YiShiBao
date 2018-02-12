package com.xnliang.yishibao.module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-11.
 */

public class CartItemViewAdapter extends RecyclerView.Adapter {


    private Context mContext;
    private List mData;
    private final LayoutInflater mLayoutInflater;
    private MyViewHolder mViewHolder;

    public CartItemViewAdapter(Context context, List data) {
        this.mContext = context;
        this.mData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.cart_item_view, parent, false);
        mViewHolder = new MyViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.setData(mData, position);
        }
    }

        @Override
        public int getItemCount () {
            return mData.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private final ImageView mItemPicture;
            private final TextView mItemName;
            private final TextView mItemType;
            private final TextView mItemIngegral;

            public MyViewHolder(View itemView) {
                super(itemView);
                mItemPicture = (ImageView) itemView.findViewById(R.id.iv_cart_picture);
                mItemName = (TextView) itemView.findViewById(R.id.tv_categry_title);
                mItemType = (TextView) itemView.findViewById(R.id.tv_categray_type);
                mItemIngegral = (TextView) itemView.findViewById(R.id.tv_categray_integray);

            }

            public void setData(List moduledata, int position) {
                //使用Glide加载图片
                Glide.with(mContext)
                        .load(mData.get(position))
                        .into(mItemPicture);
            }
        }
    }

