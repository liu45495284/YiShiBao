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
 * Created by JackLiu on 2018-01-30.
 */

public class FoundItemRecycleViewAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final List mData;
    private MyViewHolder mHolder;

    public FoundItemRecycleViewAdapter(Context context, List data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.found_content, null);
        mHolder = new MyViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.setData(mData ,position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mHead;
        private final TextView mUserName;
        private final TextView mContent;
        private final TextView mTime;
        private final ImageView mImageContentOne;
        private final ImageView mImageContentTwo;

        public MyViewHolder(View itemView) {
            super(itemView);
        mHead = (ImageView) itemView.findViewById(R.id.iv_head);
        mUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
        mContent = (TextView) itemView.findViewById(R.id.tv_content);
        mTime = (TextView) itemView.findViewById(R.id.tv_time);
        mImageContentOne = itemView.findViewById(R.id.iv_content_1);
        mImageContentTwo = itemView.findViewById(R.id.iv_content_2);

        }

        public void setData(List moduledata,int position) {
            //使用Glide加载图片
            Glide.with(mContext)
                    .load(mData.get(position))
                    .into(mImageContentOne);
        }
    }

}
