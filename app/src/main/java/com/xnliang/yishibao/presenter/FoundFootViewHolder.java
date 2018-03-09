package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;

import java.util.LinkedHashMap;

/**
 * Created by JackLiu on 2018-02-05.
 */

public class FoundFootViewHolder extends BaseViewHolder {

    private final Context mContext;
//    private final ProgressBar mLoadingView;
    private TextView mNoData;

    public FoundFootViewHolder(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
//        mLoadingView = itemView.findViewById(R.id.pb_loading);
        mNoData = itemView.findViewById(R.id.tv_no_data);

    }

}
