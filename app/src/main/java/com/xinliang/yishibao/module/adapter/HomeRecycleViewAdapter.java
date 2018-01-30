package com.xinliang.yishibao.module.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xinliang.yishibao.R;
import com.xinliang.yishibao.module.utils.DividerGridItemDecoration;
import com.xinliang.yishibao.presenter.BannerViewHolder;
import com.xinliang.yishibao.view.customview.CategrayGridView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by JackLiu on 2018-01-29.
 */

public class HomeRecycleViewAdapter extends RecyclerView.Adapter {

    /**
     * 5种类型
     */
    /**
     * 类型1：--使用banner实现
     */
    public static final int BLACK_5_BANNER0 = 0;
    /**
     *类型2：--使用GridView实现
     */
    public static final int TODAY_NEW_GV1 = 1;
    /**
     *类型3：--使用GridView实现
     */
    public static final int CATEGRAY_NEW_GV2 = 2;
    /**
     * 类型4：--使用ImageView实现
     */
    public static final int PIN_PAI_IV3=3;

    /**
     * 类型5：--使用RecyclerView实现
     */
    public static final int DAPEIQS_GV4 =4;

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
            return new BannerViewHolder(mContext, mLayoutInflater.inflate(R.layout.banner_viewpager, null));
        }else if(viewType==TODAY_NEW_GV1){
            return new TODAYViewHolder(mContext,mLayoutInflater.inflate(R.layout.gv_channel,null));
        }else if(viewType==CATEGRAY_NEW_GV2){
            return new CategrayViewHolder(mContext,mLayoutInflater.inflate(R.layout.gv_channel_categray,null));
        }else if(viewType==PIN_PAI_IV3) {
            return new PINPAIViewHolder(mContext, mLayoutInflater.inflate(R.layout.iv_pinpai, null));
        }else if(viewType== DAPEIQS_GV4) {
            return new DaPeiViewHolder(mContext, mLayoutInflater.inflate(R.layout.dapeiqs_rv, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BLACK_5_BANNER0) {
            BannerViewHolder bannerViewHolder= (BannerViewHolder) holder;
//            List<WomenBean.WomenData.ModuleBean.DataBean> module0data = moduleBeanList.get(0).getData();
//            bbnViewHolder.setData(module0data);
            bannerViewHolder.setData(moduleBeanList);
        }else if(getItemViewType(position)==TODAY_NEW_GV1) {
            TODAYViewHolder todayViewHolder= (TODAYViewHolder) holder;
//            List<WomenBean.WomenData.ModuleBean.DataBean> module1data = moduleBeanList.get(1).getData();
            todayViewHolder.setData(moduleBeanList);
        }else if(getItemViewType(position)==CATEGRAY_NEW_GV2) {
            CategrayViewHolder categrayViewHolder= (CategrayViewHolder) holder;
//            List<WomenBean.WomenData.ModuleBean.DataBean> module1data = moduleBeanList.get(1).getData();
            categrayViewHolder.setData(moduleBeanList);
        }else if(getItemViewType(position)==PIN_PAI_IV3) {
            PINPAIViewHolder pinpaiViewHolder = (PINPAIViewHolder) holder;
//            List<WomenBean.WomenData.ModuleBean.DataBean> pinpai2data = moduleBeanList.get(2).getData();
            pinpaiViewHolder.setData(moduleBeanList);
        }else if(getItemViewType(position)== DAPEIQS_GV4) {
            DaPeiViewHolder dapeiViewHolder = (DaPeiViewHolder) holder;
//            List<WomenBean.WomenData.ModuleBean.DataBean> dapeiqs6data = moduleBeanList.get(6).getData();
            dapeiViewHolder.setData(moduleBeanList);
        }


    }

    @Override
    public int getItemCount() {
        return 5;
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
            case CATEGRAY_NEW_GV2:
                currentType = CATEGRAY_NEW_GV2;
                break;
            case PIN_PAI_IV3:
                currentType = PIN_PAI_IV3;
                break;
            case DAPEIQS_GV4:
                currentType = DAPEIQS_GV4;
                break;
        }
        return currentType;
    }

    public  class BBNViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Context mContext;
        private Banner banner;

        public BBNViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            banner = (Banner) itemView.findViewById(R.id.banner);

            itemView.setOnClickListener(this);
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
                    Toast.makeText(mContext, "position=="+position, Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public void onClick(View v) {

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
        private CategrayGridView gridView;

        public TODAYViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            gridView = itemView.findViewById(R.id.gv_channel);
        }

        //        public void setData(List<WomenBean.WomenData.ModuleBean.DataBean> module1data) {
        public void setData (List module0data){
            //已得到数据了
            //设置适配器
            TodayGVAdapter adapter = new TodayGVAdapter(mContext, module0data);
            gridView.setAdapter(adapter);
        }
    }

    class CategrayViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        private CategrayGridView gridView;

        public CategrayViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            gridView = itemView.findViewById(R.id.gv_channel_categray);
        }

        //        public void setData(List<WomenBean.WomenData.ModuleBean.DataBean> module1data) {
        public void setData (List module0data){
            //已得到数据了
            //设置适配器
            CategrayAdapter adapter = new CategrayAdapter(mContext, module0data);
            gridView.setAdapter(adapter);
        }
    }

    class PINPAIViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        @Bind(R.id.iv_pinpai)
        ImageView ivNewChok;

        PINPAIViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            ButterKnife.bind(this, itemView);
            ivNewChok = (ImageView) itemView.findViewById(R.id.iv_pinpai);
        }

//        public void setData(List<WomenBean.WomenData.ModuleBean.DataBean> pinpai2data) {
          public void setData(List pinpai2data) {
            //使用Glide加载图片
            Glide.with(mContext)
                    .load(R.mipmap.guide3)
                    .into(ivNewChok);
        }
    }

    class DaPeiViewHolder extends RecyclerView.ViewHolder{

        private final Context mContext;
        private RecyclerView dapeiqs_rv;

        public DaPeiViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext=mContext;
            dapeiqs_rv= itemView.findViewById(R.id.dapeiqs_rv);
        }

        public void setData(List dapeiqs6data) {
            //1.已有数据
            //2.设置适配器：-->设置文本和recycleView的数据
            DaPeiQSRecycleViewAdapter adapter=new DaPeiQSRecycleViewAdapter(mContext,dapeiqs6data);
            //设置adapter
            dapeiqs_rv.setAdapter(adapter);
            dapeiqs_rv.addItemDecoration(new DividerGridItemDecoration(mContext));

        //recycleView不仅要设置适配器还要设置布局管理者,否则图片不显示
            GridLayoutManager manager= new GridLayoutManager(mContext,2);
            dapeiqs_rv.setLayoutManager(manager);

        }
    }

}
