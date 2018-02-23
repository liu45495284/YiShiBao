package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xnliang.yishibao.R;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-23.
 */

public class SubmitTotalViewHolder extends BaseViewHolder {
    private Context mContext;
    private TextView mIntegral;
    private TextView mFreight;
    public SubmitTotalViewHolder(Context context ,View itemView) {
        super(itemView);
        this.mContext = context;
        mIntegral = itemView.findViewById(R.id.tv_submit_total_integral);
        mFreight = itemView.findViewById(R.id.tv_submit_total_freight);
    }

    public void setData(List data){

    }
}
