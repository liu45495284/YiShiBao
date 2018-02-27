package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.view.IntegralTopUpActivity;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.SelfListActivity;
import com.xnliang.yishibao.view.SettingActivity;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-07.
 */

public class SelfDetailViewHolder extends BaseViewHolder implements View.OnClickListener {

    private final Context mContext;
    private ImageView mHeadPicture;
    private ImageView mSetting;
    private MainActivity mActivity;
    private RelativeLayout mIntegralT;
    private RelativeLayout mIntegralC;
    private Bundle mBundle;

    public SelfDetailViewHolder(Context context ,View itemView) {
        super(itemView);
        this.mContext = context;
        this.mActivity = (MainActivity) context;
        mHeadPicture = itemView.findViewById(R.id.iv_self_picture);
        mSetting = itemView.findViewById(R.id.self_setting);
        mIntegralC = itemView.findViewById(R.id.rl_detail_integral_c);
        mIntegralT = itemView.findViewById(R.id.rl_detail_integral_t);
    }

    public void setData(List data) {
        mHeadPicture.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mContext).load(R.mipmap.guide3).into(mHeadPicture);

        mHeadPicture.setOnClickListener(this);
        mSetting.setOnClickListener(this);
        mIntegralC.setOnClickListener(this);
        mIntegralT.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mBundle = new Bundle();
        switch (v.getId()) {
            case R.id.self_setting:
                Intent intent = new Intent(mActivity, SettingActivity.class);
                mActivity.startActivity(intent);
                break;
            case R.id.iv_self_picture:
                mBundle.putInt("pos" , 7);
                Intent personIntent = new Intent(mActivity , SelfListActivity.class);
                personIntent.putExtras(mBundle);
                mActivity.startActivity(personIntent);
                break;
            case R.id.rl_detail_integral_c:
                Intent cIntent = new Intent(mActivity , IntegralTopUpActivity.class);
                mActivity.startActivity(cIntent);
                break;
            case R.id.rl_detail_integral_t:
                Bundle bundle = new Bundle();
                bundle.putInt("pos" , 8);
                Intent tixianIntent = new Intent(mActivity , SelfListActivity.class);
                tixianIntent.putExtras(bundle);
                mActivity.startActivity(tixianIntent);
                break;
        }
    }
}
