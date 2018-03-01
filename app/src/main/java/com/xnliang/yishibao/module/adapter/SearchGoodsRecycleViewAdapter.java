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
 * Created by JackLiu on 2018-02-07.
 */

public class SearchGoodsRecycleViewAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final List data;
    public SearchGoodsRecycleViewAdapter(Context context , List data) {
        this.mContext = context;
        this.data = data;
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
        return data.size();
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
//            WomenBean.WomenData.ModuleBean.DataBean dapeiBean = dapeiqs6data.get(position);
            //使用Glide加载图片
            Glide.with(mContext)
                    .load(data.get(position))
                    .into(mShopGoods);

            mShopName.setText("洗面奶");
            mShopIntegral.setText("10积分");
        }
    }
}
