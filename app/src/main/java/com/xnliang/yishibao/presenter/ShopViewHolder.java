package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.ShopIndexBean;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackLiu on 2018-02-06.
 */

public class ShopViewHolder extends BaseViewHolder {

    private final Context mContext;
    private Banner banner;
    private ArrayList list = new ArrayList();


    public ShopViewHolder(Context context ,View itemView) {
        super(itemView);
        this.mContext = context;
        banner = itemView.findViewById(R.id.shop_banner);
    }

    public void setData(List<ShopIndexBean.DataBean.SlidesBean> data , int position) {
        for (int i = 0 ; i < data.size() ;i++ ){
            list.add(data.get(i).getImage());
        }
        banner.setImages(list).setImageLoader(new GlideImageLoader()).start();

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
