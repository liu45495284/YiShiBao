package com.xnliang.yishibao.module.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.ShopIndexBean;
import com.xnliang.yishibao.view.fragment.CategrayItemFragment;
import com.xnliang.yishibao.view.fragment.ShopFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by JackLiu on 2018-01-30.
 */

public class ShopCategrayRecycleViewAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final List<ShopIndexBean.DataBean.CateBean> mData;
    private WeakHashMap<Integer,Integer> hashMap;
    private WeakHashMap<Integer,String> nameHashMap;
    public static final String CATEGORY_TAG = "category";
    private ShopFragment mShopFragment;
    private int mCateId;
    private List idList = new ArrayList();

    public ShopCategrayRecycleViewAdapter(Context context, List<ShopIndexBean.DataBean.CateBean> data , ShopFragment shopFragment) {
        this.mContext = context;
        this.mData = data;
        this.mShopFragment = shopFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.shop_categray_item, null));
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
//        return data.size();
        return 10;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mShopCategray;
        private TextView mCategrayName;

        public MyViewHolder(View itemView) {
            super(itemView);
            mShopCategray = itemView.findViewById(R.id.img_shop_categray);
            mCategrayName = itemView.findViewById(R.id.tv_shop_categray);

            itemView.setOnClickListener(this);

        }

        public void setData(int position) {
//            tempData();
            //使用Glide加载图片
            Glide.with(mContext)
                    .load(mData.get(position).getApp_icon())
                    .into(mShopCategray);

            mCategrayName.setText(mData.get(position).getName());
            mCateId = mData.get(position).getId();
            idList.add(mCateId);
        }

        @Override
        public void onClick(View v) {
            int id = (int)idList.get(getAdapterPosition());
            CategrayItemFragment itemFragment = new CategrayItemFragment();
            FragmentManager manager = mShopFragment.getFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putSerializable(CATEGORY_TAG, getAdapterPosition());
            bundle.putInt("flag" , 1);
            bundle.putInt("id" , id);
            itemFragment.setArguments(bundle);
            FragmentTransaction transaction = manager.beginTransaction();
            ShopFragment shopFragment = (ShopFragment)manager.findFragmentByTag("shop");
            transaction.add(R.id.fragment_home_container ,itemFragment ,"category");
            transaction.hide(shopFragment);
            transaction.addToBackStack("categray");
            transaction.commit();
        }
    }

    public void tempData() {
        int tempData[] = {R.mipmap.categray_chuju,R.mipmap.categray_kouhong,R.mipmap.categray_rihua,
                            R.mipmap.categray_xihu,R.mipmap.categray_xiyi,R.mipmap.categray_chuju,
                            R.mipmap.categray_kouhong,R.mipmap.categray_rihua,R.mipmap.categray_kouhong,R.mipmap.categray_rihua};
        String tempName[] ={"厨具" ,"口红","日化","洗护","衣服","盆栽" ,"纸品","洗漱","洗发水","梳子"};
        hashMap = new WeakHashMap();
        nameHashMap = new WeakHashMap();
        for (int i = 0 ; i < tempData.length ; i++) {
            hashMap.put(i,tempData[i]);
            nameHashMap.put(i,tempName[i]);
        }
    }

}
