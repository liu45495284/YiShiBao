package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.SettingActivity;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-07.
 */

public class SelfDetailViewHolder extends BaseViewHolder {

    private final Context mContext;
    private ImageView mHeadPicture;
    private ImageView mSetting;
    private MainActivity mActivity;

    public SelfDetailViewHolder(Context context ,View itemView) {
        super(itemView);
        this.mContext = context;
        this.mActivity = (MainActivity) context;
        mHeadPicture = itemView.findViewById(R.id.iv_self_picture);
        mSetting = itemView.findViewById(R.id.self_setting);

        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    Intent intent = new Intent(mActivity, SettingActivity.class);
                    mActivity.startActivity(intent);
                }
            }
        });
    }

    public void setData(List data) {
        mHeadPicture.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mContext).load(R.mipmap.guide3).into(mHeadPicture);
    }

}
