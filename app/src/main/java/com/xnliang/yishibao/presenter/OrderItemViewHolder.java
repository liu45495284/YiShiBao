package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.OrderBean;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-26.
 */

public class OrderItemViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    private final ImageView mPic;
    private final TextView mTitle;
    private final TextView mIntegral;
    private final TextView mNum;

    public OrderItemViewHolder(Context context ,View itemView) {
            super(itemView);
            this.mContext = context;
        mPic = itemView.findViewById(R.id.iv_order_picture);
        mTitle = itemView.findViewById(R.id.tv_order_item_title);
        mIntegral = itemView.findViewById(R.id.tv_order_integral);
        mNum = itemView.findViewById(R.id.tv_order_num);
        }

        public void setData(List<OrderBean.DataBeanX.DataBean.ValueBean> data ,int position){
            String image = data.get(position).getGoods_image();
            String title = data.get(position).getGoods_name();
            String price = data.get(position).getGoods_price();
            int num = data.get(position).getGoods_num();

            Glide.with(mContext)
                    .load(image)
                    .into(mPic);

            mTitle.setText(title);
            mIntegral.setText(price);
            mNum.setText(num+"");

        }
    }
