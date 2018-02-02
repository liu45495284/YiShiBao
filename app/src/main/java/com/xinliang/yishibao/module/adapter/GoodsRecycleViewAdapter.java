package com.xinliang.yishibao.module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xinliang.yishibao.R;

import java.util.List;

/**
 * Created by JackLiu on 2018-01-30.
 */

public class GoodsRecycleViewAdapter extends RecyclerView.Adapter {
    private final Context mContext;
//    private final List<WomenBean.WomenData.ModuleBean.DataBean> dapeiqs6data;
    private final List dapeiqs6data;

    public GoodsRecycleViewAdapter(Context mContext, List dapeiqs6data) {
        this.mContext = mContext;
        this.dapeiqs6data = dapeiqs6data;
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
        return dapeiqs6data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_figure;
        private TextView tv_name;
        private TextView tv_jifen;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_figure = itemView.findViewById(R.id.iv_shangpin);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_jifen = itemView.findViewById(R.id.tv_jifen);

        }

        public void setData(int position) {
//            WomenBean.WomenData.ModuleBean.DataBean dapeiBean = dapeiqs6data.get(position);
            //使用Glide加载图片
            Glide.with(mContext)
                    .load(dapeiqs6data.get(position))
                    .into(iv_figure);

            tv_name.setText("梦想");
            tv_jifen.setText("10000积分");
        }
    }

}
