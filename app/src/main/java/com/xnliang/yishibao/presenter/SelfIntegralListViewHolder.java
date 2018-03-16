package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.SelfListActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-02-08.
 */

public class SelfIntegralListViewHolder extends BaseViewHolder implements View.OnClickListener {
    private Context mContext;
    private final MainActivity mActivity;
    @Bind(R.id.rl_self_integral)
    RelativeLayout mSelfIntegral;
    @Bind(R.id.rl_self_shop_integral)
    RelativeLayout mShopIntegral;
    @Bind(R.id.rl_self_cash)
    RelativeLayout mSelfCash;

    public SelfIntegralListViewHolder(Context context , View itemView) {
        super(itemView);
        this.mContext = context;
        mActivity = (MainActivity)mContext;
        ButterKnife.bind(this ,itemView);

        mShopIntegral.setOnClickListener(this);
        mSelfIntegral.setOnClickListener(this);
        mSelfCash.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos" , 9);
        Intent intent = new Intent(mActivity , SelfListActivity.class);
        intent.putExtras(bundle);
        switch (v.getId()){
            case R.id.rl_self_integral:
                intent.putExtra("list" , 1);
                break;
            case R.id.rl_self_shop_integral:
                intent.putExtra("list" , 2);
                break;
            case R.id.rl_self_cash:
                intent.putExtra("list" , 3);
                break;
        }
        mActivity.startActivity(intent);
    }
}
