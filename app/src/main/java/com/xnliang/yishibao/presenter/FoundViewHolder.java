package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.view.MainActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-01-31.
 */

public class FoundViewHolder extends BaseViewHolder implements View.OnClickListener{

    private final Context mContext;
    private final MainActivity mActivity;
    private ItmeCallBackListener mListener;
    @Bind(R.id.iv_found)
    ImageView ivFound;

    public FoundViewHolder(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        mActivity = (MainActivity)mContext;
        ButterKnife.bind(this, itemView);
        ivFound = (ImageView) itemView.findViewById(R.id.iv_found);
        ivFound.setOnClickListener(this);
        setFoundClickListener(mActivity);
    }

    public void setData() {
        //使用Glide加载图片
        Glide.with(mContext)
                .load(R.mipmap.found_banner)
                .into(ivFound);
    }

    @Override
    public void onClick(View v) {
        mListener.ivFoundClickListener();
    }

    public void setFoundClickListener(ItmeCallBackListener listener) {
        mListener = listener;
    }
}
