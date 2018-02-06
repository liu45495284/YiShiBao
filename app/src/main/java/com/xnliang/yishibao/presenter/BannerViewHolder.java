package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xnliang.yishibao.R;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

/**
 * Created by JackLiu on 2018-01-30.
 */

public class BannerViewHolder extends RecyclerView.ViewHolder {

    private final Context mContext;
    private Banner banner;

    public BannerViewHolder(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        banner = itemView.findViewById(R.id.banner);

    }

    //        public void setData(List<WomenBean.WomenData.ModuleBean.DataBean> module0data) {
//            //设置Banner的数据
//            //得到图片地址的集合
//            List<String> imageUrls=new ArrayList<>();
//            for (int i=0;i<module0data.size();i++){
//                String image=module0data.get(i).getImg();
//                imageUrls.add(image);
//            }
    public void setData(List module0data) {

        //新版的banner的使用----偷下懒的使用方法
        banner.setImages(module0data).setImageLoader(new GlideImageLoader()).start();

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