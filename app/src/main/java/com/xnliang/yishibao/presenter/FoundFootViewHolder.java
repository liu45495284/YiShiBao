package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.view.fragment.FoundFragment;

import java.util.LinkedHashMap;

/**
 * Created by JackLiu on 2018-02-05.
 */

public class FoundFootViewHolder extends BaseViewHolder {

    private final Context mContext;
//    private final ProgressBar mLoadingView;
    private TextView mNoData;
    private TextView mLoading;
    private int mPage = 1;
    private String mLast;

    public FoundFootViewHolder(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
//        mLoadingView = itemView.findViewById(R.id.pb_loading);
        mNoData = itemView.findViewById(R.id.tv_no_data);
        mLoading = itemView.findViewById(R.id.tv_loading_data);
    }

    public void setData(LinkedHashMap<String ,String> moduleBeanList){
        String current = moduleBeanList.get("current");
        mLast = moduleBeanList.get("last");
        if (Integer.valueOf(current) > Integer.valueOf(mLast)){
            mNoData.setVisibility(View.VISIBLE);
            mLoading.setVisibility(View.INVISIBLE);
        }else {
            mNoData.setVisibility(View.INVISIBLE);
            mLoading.setVisibility(View.VISIBLE);
        }

    }

}
