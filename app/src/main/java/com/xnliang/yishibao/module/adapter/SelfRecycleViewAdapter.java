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

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.presenter.SelfDetailViewHolder;
import com.xnliang.yishibao.presenter.SelfListViewHolder;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.TravelDetailActivity;
import com.xnliang.yishibao.view.fragment.SelfFragment;
import com.xnliang.yishibao.view.fragment.TravelFragment;

import java.lang.ref.PhantomReference;
import java.util.List;
import java.util.WeakHashMap;


/**
 * Created by JackLiu on 2018-02-01.
 */

public class SelfRecycleViewAdapter extends RecyclerView.Adapter {

    private final MainActivity mActivity;
    private final SelfFragment mFragment;
    private final Context mContext;
    private final List moduleBeanList;
    private final LayoutInflater mLayoutInflater;
    private FragmentTransaction mFragmentTransaction;
    private WeakHashMap<Integer, Integer> icon;
    private WeakHashMap<Integer, Integer> title;
    public int currentType = SELF_DETAIL;
    private static final int SELF_DETAIL = 0;
    private static final int SELF_LIST = 1;
    RecyclerView.ViewHolder mHolder = null;

    public SelfRecycleViewAdapter(Context mContext, List moduleBeanList, SelfFragment fragment) {
        this.mContext = mContext;
        this.moduleBeanList = moduleBeanList;
        this.mFragment = fragment;
        mActivity = (MainActivity)mContext;
        //以后用它来初始化布局
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SELF_DETAIL:
                mHolder = new SelfDetailViewHolder(mContext, mLayoutInflater.inflate(R.layout.self_detail, null));
                break;
            case SELF_LIST:
                mHolder = new SelfListViewHolder(mContext, mLayoutInflater.inflate(R.layout.self_list, null));
                break;
        }
        return mHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case SELF_DETAIL:
                SelfDetailViewHolder selfDetailViewHolder = (SelfDetailViewHolder) holder;
                selfDetailViewHolder.setData(moduleBeanList);
                break;
            case SELF_LIST:
                SelfListViewHolder selfListViewHolder = (SelfListViewHolder) holder;
                selfListViewHolder.setData(moduleBeanList);
                break;
        }
//        ViewHolder viewHolder= (ViewHolder) holder;
////        viewHolder.setData(moduleBeanList , position);
//        viewHolder.initData();
//        //将数据填充到具体的view中
//        mHolder.mIcon.setImageResource(icon.get(position));
//        mHolder.mName.setText(title.get(position));
//
//        if (mHolder.itemView != null) {
//            mHolder.itemView.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case SELF_DETAIL:
                currentType = SELF_DETAIL;
                break;
            case SELF_LIST:
                currentType = SELF_LIST;
                break;
        }
        return currentType;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mIcon;
        public TextView mName;
        public ImageView mInto;

        public ViewHolder(View itemView) {
            super(itemView);
            //由于itemView是item的布局文件，我们需要的是里面的textView，因此利用itemView.findViewById获
            //取里面的textView实例，后面通过onBindViewHolder方法能直接填充数据到每一个textView了
            mIcon = (ImageView) itemView.findViewById(R.id.iv_self_item);
            mName = (TextView) itemView.findViewById(R.id.tv_self_item);
            mInto = (ImageView) itemView.findViewById(R.id.iv_self_into);

        }
        public void setData(List module0data , int position) {

            mIcon.setScaleType(ImageView.ScaleType.FIT_XY);

            //Glide 加载图片简单用法
            Glide.with(mContext).load(moduleBeanList.get(position)).into(mIcon);

        }


    }

}
