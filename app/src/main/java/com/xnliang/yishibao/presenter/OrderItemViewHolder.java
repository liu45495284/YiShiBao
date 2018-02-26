package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xnliang.yishibao.R;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-26.
 */

public class OrderItemViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;

        public OrderItemViewHolder(Context context ,View itemView) {
            super(itemView);
            this.mContext = context;
        }

        public void setData(List data){

        }
    }
