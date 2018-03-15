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

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.CategroyListBean;
import com.xnliang.yishibao.view.GoodsDetailActivity;
import com.xnliang.yishibao.view.SearchViewActivity;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-07.
 */

public class SearchGoodsRecycleViewAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final List<CategroyListBean.DataBeanX.DataBean> data;
    private SearchViewActivity mActivity;
    public SearchGoodsRecycleViewAdapter(Context context , List<CategroyListBean.DataBeanX.DataBean> data) {
        this.mContext = context;
        this.mActivity = (SearchViewActivity) context;
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

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mShopGoods;
        private TextView mShopName;
        private TextView mShopIntegral;

        public MyViewHolder(View itemView) {
            super(itemView);
            mShopGoods = itemView.findViewById(R.id.iv_shop_goods);
            mShopName = itemView.findViewById(R.id.tv_shop_name);
            mShopIntegral = itemView.findViewById(R.id.tv_shop_jifen);

            itemView.setOnClickListener(this);

        }

        public void setData(int position) {
            String image = data.get(position).getImage();
            String name = data.get(position).getGoods_name();
            String integral = data.get(position).getShop_price();
            int id = data.get(position).getId();
            //使用Glide加载图片
            Glide.with(mContext)
                    .load(image)
                    .into(mShopGoods);

            mShopName.setText(name);
            mShopIntegral.setText(integral);
        }

        @Override
        public void onClick(View v) {
            int id = data.get(getAdapterPosition()).getId();
            Bundle bundle = new Bundle();
            bundle.putInt("detail" , id);
            Intent intent = new Intent(mActivity , GoodsDetailActivity.class);
            intent.putExtras(bundle);
            mActivity.startActivity(intent);
        }
    }
}
