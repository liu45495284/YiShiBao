package com.xnliang.yishibao.module.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.CategroyListBean;
import com.xnliang.yishibao.view.GoodsDetailActivity;
import com.xnliang.yishibao.view.MainActivity;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-09.
 */

public class CategrayItemAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private final List<CategroyListBean.DataBeanX.DataBean> mData;
    private MyViewHolder mHolder;
    private MainActivity mActivity;

    public CategrayItemAdapter(Context context, List<CategroyListBean.DataBeanX.DataBean> data) {
        this.mContext = context;
        this.mData = data;
        this.mActivity = (MainActivity) context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categray_list_item, null);
        mHolder = new MyViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
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
        private final TextView mItemType;
        private final TextView mItemIngegral;

        public MyViewHolder(View itemView) {
            super(itemView);
            mItemPicture = (ImageView) itemView.findViewById(R.id.iv_categray_list);
            mItemName = (TextView) itemView.findViewById(R.id.tv_categry_title);
            mItemType = (TextView) itemView.findViewById(R.id.tv_categray_type);
            mItemIngegral = (TextView) itemView.findViewById(R.id.tv_categray_integray);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = Integer.valueOf(mData.get(getAdapterPosition()).getId());
                    Bundle bundle = new Bundle();
                    bundle.putInt("detail" , id);
                    Intent intent = new Intent(mActivity , GoodsDetailActivity.class);
                    intent.putExtras(bundle);
                    mActivity.startActivity(intent);
                }
            });
        }

        public void setData(List<CategroyListBean.DataBeanX.DataBean> moduledata, int position) {

            String image = moduledata.get(position).getImage();
            //使用Glide加载图片
            Glide.with(mContext)
                    .load(image)
                    .into(mItemPicture);

            mItemName.setText(moduledata.get(position).getGoods_name());
            mItemIngegral.setText(moduledata.get(position).getShop_price());
        }
    }
}
