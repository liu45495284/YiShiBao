package com.xnliang.yishibao.module.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.TravelListBean;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.TravelDetailActivity;
import com.xnliang.yishibao.view.fragment.TravelFragment;


import java.util.List;


/**
 * Created by JackLiu on 2018-02-01.
 */

public class TravelRecycleViewAdapter extends RecyclerView.Adapter {

    public static final int TRAVEL_ITEM = 0;
    private final MainActivity mActivity;
    private final TravelFragment mFragment;
    public int currentType = TRAVEL_ITEM;
    private final Context mContext;
    private final List<TravelListBean.DataBeanX.DataBean> moduleBeanList;
    private final LayoutInflater mLayoutInflater;
    private ViewHolder mHolder;
    private FragmentTransaction mFragmentTransaction;

    public TravelRecycleViewAdapter(Context mContext, List<TravelListBean.DataBeanX.DataBean> moduleBeanList, TravelFragment fragment) {
        this.mContext = mContext;
        this.moduleBeanList = moduleBeanList;
        this.mFragment = fragment;
        mActivity = (MainActivity)mContext;
        //以后用它来初始化布局
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.travel_list_item, parent, false);
        mHolder = new ViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder= (ViewHolder) holder;
        viewHolder.setData(moduleBeanList , position);

        if (mHolder.itemView != null) {
            mHolder.itemView.setOnClickListener(new View.OnClickListener() {
                public String BACKSTACK_TAG = "detail";
                public String CATEGORY_TAG = "category";

                @Override
                public void onClick(View v) {
                    int id = moduleBeanList.get(position).getId();
                    String title = moduleBeanList.get(position).getTitle();
                    String score = moduleBeanList.get(position).getPrice();
                    Intent intent = new Intent(mActivity, TravelDetailActivity.class);
                    intent.putExtra("detail" , id);
                    intent.putExtra("title" , title);
                    intent.putExtra("price" , score);
                    mActivity.startActivity(intent);

                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return moduleBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView icon;
        public TextView title;
        public TextView time;
        public TextView jiFen;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.iv_travel_list);
            title = (TextView) itemView.findViewById(R.id.tv_travel_title);
//            time = (TextView) itemView.findViewById(R.id.tv_travel_time);
            jiFen = (TextView) itemView.findViewById(R.id.tv_travel_jifen);

        }
        public void setData(List<TravelListBean.DataBeanX.DataBean> data , int position) {
            icon.setScaleType(ImageView.ScaleType.FIT_XY);

            //Glide 加载图片简单用法
            Glide.with(mContext).load(data.get(position).getImage()).into(icon);
            title.setText(data.get(position).getTitle());
            jiFen.setText(data.get(position).getPrice());
        }
    }

}
