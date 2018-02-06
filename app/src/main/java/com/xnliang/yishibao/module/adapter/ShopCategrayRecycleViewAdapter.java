package com.xnliang.yishibao.module.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by JackLiu on 2018-01-30.
 */

public class ShopCategrayRecycleViewAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final List data;
    private WeakHashMap<Integer,Integer> hashMap;
    private WeakHashMap<Integer,String> nameHashMap;

    public ShopCategrayRecycleViewAdapter(Context mContext, List data) {
        this.mContext = mContext;
        this.data = data;
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

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mShopCategray;
        private TextView mCategrayName;

        public MyViewHolder(View itemView) {
            super(itemView);
            mShopCategray = itemView.findViewById(R.id.img_shop_categray);
            mCategrayName = itemView.findViewById(R.id.tv_shop_categray);

        }

        public void setData(int position) {
            tempData();
            //使用Glide加载图片
            Glide.with(mContext)
                    .load(hashMap.get(position))
                    .into(mShopCategray);

            mCategrayName.setText(nameHashMap.get(position));
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
