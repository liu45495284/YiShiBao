package com.xnliang.yishibao.module.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.xnliang.yishibao.module.bean.OrderBean;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.view.OrderPayActivity;
import com.xnliang.yishibao.view.SelfListActivity;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-24.
 */

public class OrderDetailRecycleViewAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<OrderBean.DataBeanX.DataBean> mData;
    private LayoutInflater mLayoutInflater;
    private OrderNumberItemAdapter mNumAdaptger;
    private ListDecoration mDecoration;
    private SelfListActivity mActivity;

    public OrderDetailRecycleViewAdapter(Context context , List<OrderBean.DataBeanX.DataBean> data) {
        this.mContext = context;
        this.mActivity = (SelfListActivity)context;
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
            myViewHolder.setData(mData ,position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
/**
 * order_sn : 2018022849994910
 * create_time : 2018-02-28
 * total_amount : 304.00
 * pay_status : 0
 * order_status : 0*/

        public void setData(List<OrderBean.DataBeanX.DataBean> data , int position) {
            List<OrderBean.DataBeanX.DataBean.ValueBean> valueBeans = data.get(position).getValue();
            if (mNumAdaptger == null){
                mNumAdaptger = new OrderNumberItemAdapter(mContext , valueBeans);
                mItemView.setAdapter(mNumAdaptger);
            }

            if(mDecoration == null){
                mDecoration = new ListDecoration(mContext , ListDecoration.VERTICAL_LIST , R.drawable.list_divide);
                mItemView.addItemDecoration(mDecoration);
            }
            mItemView.setItemAnimator(null);
            LinearLayoutManager manager = new LinearLayoutManager(mContext , LinearLayoutManager.VERTICAL ,false);
            mItemView.setLayoutManager(manager);

            String orderSn = data.get(position).getOrder_sn();
            String time = data.get(position).getCreate_time();
            String totalAmount = data.get(position).getTotal_amount();
            int payStatus = data.get(position).getPay_status();
            int orderStatus = data.get(position).getOrder_status();

            mNumView.setText(orderSn);
            mTimeView.setText(time);
            mTotalView.setText(totalAmount);
            if(payStatus == 0){
                mGoPay.setText(R.string.my_order_go_pay);
                mGoPay.setOnClickListener(this);
            }else if(payStatus == 1){
                mGoPay.setText(R.string.my_order_complete);
                mGoPay.setOnClickListener(null);
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mActivity , OrderPayActivity.class);
            mActivity.startActivity(intent);
        }
    }
}
