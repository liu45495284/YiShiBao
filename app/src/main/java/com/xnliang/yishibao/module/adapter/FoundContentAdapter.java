package com.xnliang.yishibao.module.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.FoundListBean;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.PicturePreviewActivity;

import java.util.List;

/**
 * Created by JackLiu on 2018-03-09.
 */

public class FoundContentAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private MainActivity mActivity;
    private List mData;
    private LayoutInflater mLayoutInflater;
    private static final String mFoundIndex ="http://ysb.appxinliang.cn/api/discover";

    public FoundContentAdapter(Context context , List data) {
        this.mContext = context;
        this.mActivity = (MainActivity)context;
        this.mData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(mLayoutInflater.inflate(R.layout.found_content_image  , null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder != null){
            MyViewHolder viewHolder = (MyViewHolder) holder;
            viewHolder.setData(mData, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView mContentView;
        public MyViewHolder(View itemView ) {
            super(itemView);
            mContentView = itemView.findViewById(R.id.iv_content);
            mContentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, PicturePreviewActivity.class);
                    intent.putExtra("url", mData.get(getAdapterPosition()).toString());
                    mActivity.startActivity(intent);
                }
            });
        }

        public void setData(List mData , int position) {
            String image = mData.get(position).toString();
            if (TextUtils.isEmpty(image)){
                return;
            }
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.loading_default)
                    .error(R.mipmap.loading_failure)
                    .priority(Priority.HIGH);

            Glide.with(mContext)
                    .load(image)
                    .apply(options)
                    .into(mContentView);
        }
    }
}
