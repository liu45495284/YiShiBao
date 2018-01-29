package com.xinliang.yishibao.module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xinliang.yishibao.R;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;


/**
 * Created by JackLiu on 2018-01-29.
 */

public class HomeRecycleViewAdapter extends RecyclerView.Adapter {

    /**
     * 4种类型
     */
    /**
     * 类型1：黑色星期五--使用banner实现
     */
    public static final int BLACK_5_BANNER0 = 0;
    /**
     *类型2：今日新品--使用GridView实现
     */
    public static final int TODAY_NEW_GV1 = 1;
    /**
     * 类型3：品牌福利--使用ImageView实现
     */
    public static final int PIN_PAI_IV2=2;

    /**
     * 类型4：搭配趋势--使用RecyclerView实现
     */
    public static final int DAPEIQS_GV3 =3;


    /**
     * 当前类型
     */
    public int currentType = BLACK_5_BANNER0;

    private final Context mContext;
    private final List moduleBeanList;


    /**
     * 以后用它来初始化布局
     */
    private final LayoutInflater mLayoutInflater;

    public HomeRecycleViewAdapter(Context mContext, List moduleBeanList) {
        this.mContext = mContext;
        this.moduleBeanList = moduleBeanList;
        //以后用它来初始化布局
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BLACK_5_BANNER0) {
            return new BBNViewHolder(mContext, mLayoutInflater.inflate(R.layout.banner_viewpager, null));
        }else if(viewType==TODAY_NEW_GV1){
            return new TODAYViewHolder(mContext,mLayoutInflater.inflate(R.layout.gv_channel,null));
//        }else if(viewType==PIN_PAI_IV2) {
//            return new PINPAIViewHolder(mContext, mLayoutInflater.inflate(R.layout.iv_pinpai, null));
//        }else if(viewType== DAPEIQS_GV3) {
//            return new DaPeiViewHolder(mContext, mLayoutInflater.inflate(R.layout.dapeiqs_rv, null));
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BLACK_5_BANNER0) {
            BBNViewHolder bbnViewHolder= (BBNViewHolder) holder;
//            List<WomenBean.WomenData.ModuleBean.DataBean> module0data = moduleBeanList.get(0).getData();
//            bbnViewHolder.setData(module0data);
              bbnViewHolder.setData(moduleBeanList);
        }else if(getItemViewType(position)==TODAY_NEW_GV1) {
            TODAYViewHolder todayViewHolder= (TODAYViewHolder) holder;
//            List<WomenBean.WomenData.ModuleBean.DataBean> module1data = moduleBeanList.get(1).getData();
            todayViewHolder.setData(moduleBeanList);
        }
// else if(getItemViewType(position)==PIN_PAI_IV2) {
//            PINPAIViewHolder pinpaiViewHolder = (PINPAIViewHolder) holder;
//            List<WomenBean.WomenData.ModuleBean.DataBean> pinpai2data = moduleBeanList.get(2).getData();
//            pinpaiViewHolder.setData(pinpai2data);
//        }else if(getItemViewType(position)== DAPEIQS_GV3) {
//            DaPeiViewHolder dapeiViewHolder = (DaPeiViewHolder) holder;
//            List<WomenBean.WomenData.ModuleBean.DataBean> dapeiqs6data = moduleBeanList.get(6).getData();
//            dapeiViewHolder.setData(dapeiqs6data);
//        }


    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BLACK_5_BANNER0:
                currentType = BLACK_5_BANNER0;
                break;
            case TODAY_NEW_GV1:
                currentType = TODAY_NEW_GV1;
                break;
            case PIN_PAI_IV2:
                currentType = PIN_PAI_IV2;
                break;
            case DAPEIQS_GV3:
                currentType = DAPEIQS_GV3;
                break;
        }
        return currentType;
    }

    public  class BBNViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        private Banner banner;

        public BBNViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            banner = (Banner) itemView.findViewById(R.id.banner);
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
                List imageUrls=new ArrayList<>();
                WeakHashMap weakHashMap = new WeakHashMap();
                weakHashMap = ((WeakHashMap)module0data.get(0));
                    Set<String> keys = weakHashMap.keySet() ;// 得到全部的key
                    Iterator<String> iter = keys.iterator() ;
                    while(iter.hasNext()) {
                        String str = iter.next();
                        if (str.substring(0,4).equals("bann")) {
                            imageUrls.add(weakHashMap.get("bann0"));
                            imageUrls.add(weakHashMap.get("bann1"));
                            imageUrls.add(weakHashMap.get("bann2"));
                        }
                    }


            //新版的banner的使用----偷下懒的使用方法
            banner.setImages(imageUrls).setImageLoader(new GlideImageLoader()).start();

            //设置item的点击事件
            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    //注意这里的position是从1开始的
                    Toast.makeText(mContext, "position=="+position, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
        }
    }


    class TODAYViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        private GridView gridView;

        public TODAYViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            gridView = (GridView) itemView.findViewById(R.id.gv_channel);
        }

//        public void setData(List<WomenBean.WomenData.ModuleBean.DataBean> module1data) {
            public void setData (List module0data){
                //已得到数据了
                //设置适配器
                TodayGVAdapter adapter = new TodayGVAdapter(mContext, module0data);
                gridView.setAdapter(adapter);
            }
        }


}
