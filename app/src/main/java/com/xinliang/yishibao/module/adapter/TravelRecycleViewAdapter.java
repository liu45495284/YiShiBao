package com.xinliang.yishibao.module.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xinliang.yishibao.R;
import com.xinliang.yishibao.presenter.BannerViewHolder;
import com.xinliang.yishibao.presenter.ItmeCallBackListener;
import com.xinliang.yishibao.view.MainActivity;
import com.xinliang.yishibao.view.TravelDetailActivity;
import com.xinliang.yishibao.view.fragment.TravelDetailFragment;
import com.xinliang.yishibao.view.fragment.TravelFragment;
import com.xinliang.yishibao.view.fragment.TravelFragmentContainer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.List;
import java.util.Objects;


/**
 * Created by JackLiu on 2018-02-01.
 */

public class TravelRecycleViewAdapter extends RecyclerView.Adapter {

    public static final int TRAVEL_ITEM = 0;
    private final MainActivity mActivity;
    private final TravelFragment mFragment;
    public int currentType = TRAVEL_ITEM;
    private final Context mContext;
    private final List moduleBeanList;
    private final LayoutInflater mLayoutInflater;
    private ViewHolder mHolder;
    private FragmentTransaction mFragmentTransaction;

    public TravelRecycleViewAdapter(Context mContext, List moduleBeanList, TravelFragment fragment) {
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
//        holder.setIsRecyclable(false);
        //将数据填充到具体的view中
//        mHolder.icon.setImageResource((int)moduleBeanList.get(position));
//        mHolder.title.setText(moduleBeanList.get(position));
//        mHolder.time.setText(moduleBeanList.get(position));
//        mHolder.jiFen.setText(moduleBeanList.get(position));

        if (mHolder.itemView != null) {
            mHolder.itemView.setOnClickListener(new View.OnClickListener() {
                public String BACKSTACK_TAG = "detail";
                public String CATEGORY_TAG = "category";

                @Override
                public void onClick(View v) {

//                    TravelDetailFragment detailFragment = new TravelDetailFragment();
//                    FragmentManager fragmentManager = mFragment.getFragmentManager();
//                    Bundle bundle = new Bundle();
////                    bundle.putSerializable(CATEGORY_TAG, moduleBeanList.get(position));
//                    detailFragment.setArguments(bundle);
//                    mFragmentTransaction =fragmentManager.beginTransaction();
//                    mFragmentTransaction.replace(R.id.fragment_container , detailFragment, "detail");
//                    mFragmentTransaction.addToBackStack(BACKSTACK_TAG);
//                    mFragmentTransaction.commitAllowingStateLoss();
                    Intent intent = new Intent(mActivity, TravelDetailActivity.class);
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
            //由于itemView是item的布局文件，我们需要的是里面的textView，因此利用itemView.findViewById获
            //取里面的textView实例，后面通过onBindViewHolder方法能直接填充数据到每一个textView了
            icon = (ImageView) itemView.findViewById(R.id.iv_travel_list);
            title = (TextView) itemView.findViewById(R.id.tv_travel_title);
            time = (TextView) itemView.findViewById(R.id.tv_travel_time);
            jiFen = (TextView) itemView.findViewById(R.id.tv_travel_jifen);

        }
        public void setData(List module0data , int position) {

            icon.setScaleType(ImageView.ScaleType.FIT_XY);

            //Glide 加载图片简单用法
            Glide.with(mContext).load(moduleBeanList.get(position)).into(icon);

        }
    }

}
