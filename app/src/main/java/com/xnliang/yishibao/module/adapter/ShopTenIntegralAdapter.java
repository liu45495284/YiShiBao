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
import com.xnliang.yishibao.module.bean.ShopIndexBean;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-07.
 */

public class ShopTenIntegralAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final List<ShopIndexBean.DataBean.SjfListsBean> mData;
    public ShopTenIntegralAdapter(Context context , List<ShopIndexBean.DataBean.SjfListsBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_shop_goods, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.setData(position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mShopGoods;
        private TextView mShopName;
        private TextView mShopIntegral;

        public MyViewHolder(View itemView) {
            super(itemView);
            mShopGoods = itemView.findViewById(R.id.iv_shop_goods);
            mShopName = itemView.findViewById(R.id.tv_shop_name);
            mShopIntegral = itemView.findViewById(R.id.tv_shop_jifen);

        }

        public void setData(int position) {
            //使用Glide加载图片
            Glide.with(mContext)
                    .load(mData.get(position).getImage())
                    .into(mShopGoods);

            mShopName.setText(mData.get(position).getGoods_name());
            mShopIntegral.setText(mData.get(position).getShop_price());
        }
    }
}
