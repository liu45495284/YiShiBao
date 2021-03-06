package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-01-31.
 */

public class AdvertisingViewHolder extends BaseViewHolder {

    private final Context mContext;
    @Bind(R.id.iv_pinpai)
    ImageView ivNewChok;

    public AdvertisingViewHolder(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        ButterKnife.bind(this, itemView);
        ivNewChok = (ImageView) itemView.findViewById(R.id.iv_pinpai);
    }

    public void setData(String data) {
        //使用Glide加载图片
        Glide.with(mContext)
                .load(R.mipmap.banner_turntable)
                .into(ivNewChok);
    }
}
