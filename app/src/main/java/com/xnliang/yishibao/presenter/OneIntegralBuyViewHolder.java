package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xnliang.yishibao.R;

/**
 * Created by JackLiu on 2018-02-07.
 */

public class OneIntegralBuyViewHolder extends BaseViewHolder {

    private final TextView mTitleView;

    public OneIntegralBuyViewHolder(Context context , View itemView) {
        super(itemView);
        mTitleView = itemView.findViewById(R.id.tv_title);
    }

    public void setData(){
        mTitleView.setText(R.string.one_integral);
    }
}
