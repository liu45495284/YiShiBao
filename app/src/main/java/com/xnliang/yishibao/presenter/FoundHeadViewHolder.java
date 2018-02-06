package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-05.
 */

public class FoundHeadViewHolder extends BaseViewHolder {

    private final Context mContext;
    private final ImageView mHeadView;

    public FoundHeadViewHolder(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        mHeadView = itemView.findViewById(R.id.iv_found_head);
        //fix recyclerView auto scroll bug
        mHeadView.setFocusableInTouchMode(true);
        mHeadView.requestFocus();

    }

    public void setData(List pinpai2data) {
        //使用Glide加载图片
        Glide.with(mContext)
                .load(R.mipmap.guide4)
                .into(mHeadView);
    }
}
