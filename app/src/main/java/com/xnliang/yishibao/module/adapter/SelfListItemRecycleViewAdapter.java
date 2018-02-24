package com.xnliang.yishibao.module.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.SelfListActivity;

import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by JackLiu on 2018-02-07.
 */

public class SelfListItemRecycleViewAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private MainActivity mActivity;
    private final List mData;
    private MyViewHolder mHolder;
    private WeakHashMap<Integer, Integer> icon;
    private WeakHashMap<Integer, Integer> title;
    private int iconPer[] = {R.mipmap.per_icon_order,R.mipmap.per_icon_team,R.mipmap.per_icon_recommend,
            R.mipmap.per_icon_add,R.mipmap.per_icon_help,R.mipmap.per_icon_us,R.mipmap.per_icon_key};
    private int titleName[] ={R.string.my_order,R.string.my_team,R.string.recommend_friend,R.string.address_management,
            R.string.help_center,R.string.about_us,R.string.modify_password};

    public SelfListItemRecycleViewAdapter(Context context, List data) {
        this.mContext = context;
        this.mActivity = (MainActivity) context;
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.self_list_item, null);
        mHolder = new MyViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder != null) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.setData(mData ,position);

        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mIcon;
        private final TextView mItemName;


        public MyViewHolder(final View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.iv_self_item);
            mItemName = (TextView) itemView.findViewById(R.id.tv_self_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("pos" , getAdapterPosition());
                    Intent intent = new Intent(mActivity , SelfListActivity.class);
                    intent.putExtras(bundle);
                    mActivity.startActivity(intent);
                }
            });

        }

        public void setData(List moduledata,int position) {
            initData();
            //使用Glide加载图片
            Glide.with(mContext)
                    .load(icon.get(position))
                    .into(mIcon);
            mItemName.setText(title.get(position));
        }


    public void initData(){

        icon = new WeakHashMap<>();
        title = new WeakHashMap<>();

        for (int i =0; i < iconPer.length; i++){
            icon.put(i , iconPer[i]);
            title.put(i, titleName[i]);
        }
    }

}
}
