package com.xinliang.yishibao.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xinliang.yishibao.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-01-31.
 */

public class FoundViewHolder extends BaseViewHolder {

    private final Context mContext;
    @Bind(R.id.iv_found)
    ImageView ivNewChok;

    public FoundViewHolder(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        ButterKnife.bind(this, itemView);
        ivNewChok = (ImageView) itemView.findViewById(R.id.iv_found);
    }

    //        public void setData(List<WomenBean.WomenData.ModuleBean.DataBean> pinpai2data) {
    public void setData(List pinpai2data) {
        //使用Glide加载图片
        Glide.with(mContext)
                .load(R.mipmap.guide3)
                .into(ivNewChok);
    }
}
