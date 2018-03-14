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

import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.view.GoodsDetailActivity;
import com.xnliang.yishibao.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackLiu on 2018-01-30.
 */

public class GoodsRecycleViewAdapter extends RecyclerView.Adapter  {
    private final Context mContext;
    private final MainActivity mActivity;
    private final JSONArray data;
    private List idList = new ArrayList();

    public GoodsRecycleViewAdapter(Context context, JSONArray data) {
        this.mContext = context;
        this.mActivity = (MainActivity) context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_goods, null));
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
        private ImageView iv_figure;
        private TextView tv_name;
        private TextView tv_jifen;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_figure = itemView.findViewById(R.id.iv_shangpin);
            tv_name = itemView.findViewById(R.id.tv_name);
//            tv_jifen = itemView.findViewById(R.id.tv_jifen);

            itemView.setOnClickListener(this);
        }

        public void setData(int position) {

            String url = data.getJSONObject(position).getString("image");
            String name = data.getJSONObject(position).getString("goods_name");
            String id = data.getJSONObject(position).getString("id");
            idList.add(id);
//            String price = data.getJSONObject(position).getString("shop_price");

            //使用Glide加载图片
            Glide.with(mContext)
                    .load(url)
                    .into(iv_figure);

            tv_name.setText(name);
//            tv_jifen.setText(price);
        }

        @Override
        public void onClick(View v) {
            int id = Integer.valueOf(idList.get(getAdapterPosition()).toString());
            Bundle bundle = new Bundle();
            bundle.putInt("detail" , id);
            Intent intent = new Intent(mActivity , GoodsDetailActivity.class);
            intent.putExtras(bundle);
            mActivity.startActivity(intent);
        }
    }

}
