package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.FoundListBean;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by JackLiu on 2018-02-05.
 */

public class FoundHeadViewHolder extends BaseViewHolder {

    private final Context mContext;
    private final Banner banner;

    public FoundHeadViewHolder(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        banner = itemView.findViewById(R.id.banner_found);
        //fix recyclerView auto scroll bug
        banner.setFocusableInTouchMode(true);
        banner.requestFocus();

    }

    public void setData(LinkedHashMap<String , List> data) {
        List<FoundListBean.DataBeanX.SlidesBean> slideList = data.get("slide");
        ArrayList list = new ArrayList();
        for (int i= 0 ; i< slideList.size() ; i++){
            list.add(slideList.get(i).getImage());
        }
        //使用Glide加载图片
        //新版的banner的使用----偷下懒的使用方法
        banner.setImages(list).setImageLoader(new GlideImageLoader()).start();

        //设置item的点击事件
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                //注意这里的position是从1开始的
                Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
        }
    }
}
