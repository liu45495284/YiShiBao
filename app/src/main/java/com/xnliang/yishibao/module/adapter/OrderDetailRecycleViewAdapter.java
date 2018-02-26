package com.xnliang.yishibao.module.adapter;

import android.content.Context;
import android.mtp.MtpEvent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.utils.ListDecoration;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-24.
 */

public class OrderDetailRecycleViewAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List mData;
    private LayoutInflater mLayoutInflater;

    public OrderDetailRecycleViewAdapter(Context context , List data) {
        this.mContext = context;
        this.mData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.order_num_item ,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.setData(mData);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView mItemView;
        private TextView mNumView;
        private TextView mTimeView;
        private TextView mTotalView;
        private TextView mGoPay;

        public MyViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView.findViewById(R.id.rv_order_num);
            mNumView = itemView.findViewById(R.id.tv_order_num);
            mTimeView = itemView.findViewById(R.id.tv_order_time);
            mTotalView = itemView.findViewById(R.id.tv_order_total);
            mGoPay = itemView.findViewById(R.id.tv_order_go_pay);
        }

        public void setData(List data) {
            OrderNumberItemAdapter numAdaptger = new OrderNumberItemAdapter(mContext , data);
            mItemView.setAdapter(numAdaptger);

            mItemView.addItemDecoration(new ListDecoration(mContext , ListDecoration.VERTICAL_LIST ,R.drawable.list_divide));
            LinearLayoutManager manager = new LinearLayoutManager(mContext , LinearLayoutManager.VERTICAL ,false);
            mItemView.setLayoutManager(manager);

            mGoPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext ,"付款" , Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
