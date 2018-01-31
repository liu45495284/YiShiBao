package com.xinliang.yishibao.module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.xinliang.yishibao.R;
import com.xinliang.yishibao.presenter.AdvertisingViewHolder;
import com.xinliang.yishibao.presenter.BannerViewHolder;
import com.xinliang.yishibao.presenter.CategrayViewHolder;
import com.xinliang.yishibao.presenter.GoodsViewHolder;
import com.xinliang.yishibao.presenter.TravelViewHolder;
import java.util.List;
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
    public static final int GOODS_BANNER0 = 0;
    /**
     *类型2：--使用GridView实现
     */
    public static final int TRAVEL_CITY_GV1 = 1;
    /**
     *类型3：--使用GridView实现
     */
    public static final int CATEGRAY_NEW_GV2 = 2;
    /**
     * 类型4：--使用ImageView实现
     */
    public static final int ADVERTISING_IV3=3;
    /**
     * 类型5：--使用RecyclerView实现
     */
    public static final int GOODS_LIST_RV4 =4;
    /**
     * 当前类型
     */
    public int currentType = GOODS_BANNER0;
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
        if (viewType == GOODS_BANNER0) {
            return new BannerViewHolder(mContext, mLayoutInflater.inflate(R.layout.banner_viewpager, null));
        }else if(viewType==TRAVEL_CITY_GV1){
            return new TravelViewHolder(mContext, mLayoutInflater.inflate(R.layout.gv_channel,null));
        }else if(viewType==CATEGRAY_NEW_GV2){
            return new CategrayViewHolder(mContext, mLayoutInflater.inflate(R.layout.gv_channel_categray,null));
        }else if(viewType==ADVERTISING_IV3) {
            return new AdvertisingViewHolder(mContext, mLayoutInflater.inflate(R.layout.iv_pinpai, null));
        }else if(viewType== GOODS_LIST_RV4) {
            return new GoodsViewHolder(mContext, mLayoutInflater.inflate(R.layout.dapeiqs_rv, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == GOODS_BANNER0) {
            BannerViewHolder bannerViewHolder= (BannerViewHolder) holder;
//            List<WomenBean.WomenData.ModuleBean.DataBean> module0data = moduleBeanList.get(0).getData();
//            bbnViewHolder.setData(module0data);
            bannerViewHolder.setData(moduleBeanList);
        }else if(getItemViewType(position)==TRAVEL_CITY_GV1) {
            TravelViewHolder travelViewHolder= (TravelViewHolder) holder;
//            List<WomenBean.WomenData.ModuleBean.DataBean> module1data = moduleBeanList.get(1).getData();
            travelViewHolder.setData(moduleBeanList);
        }else if(getItemViewType(position)==CATEGRAY_NEW_GV2) {
            CategrayViewHolder categrayViewHolder= (CategrayViewHolder) holder;
//            List<WomenBean.WomenData.ModuleBean.DataBean> module1data = moduleBeanList.get(1).getData();
            categrayViewHolder.setData(moduleBeanList);
        }else if(getItemViewType(position)==ADVERTISING_IV3) {
            AdvertisingViewHolder advertisingViewHolder = (AdvertisingViewHolder) holder;
//            List<WomenBean.WomenData.ModuleBean.DataBean> pinpai2data = moduleBeanList.get(2).getData();
            advertisingViewHolder.setData(moduleBeanList);
        }else if(getItemViewType(position)== GOODS_LIST_RV4) {
            GoodsViewHolder goodsViewHolder = (GoodsViewHolder) holder;
//            List<WomenBean.WomenData.ModuleBean.DataBean> dapeiqs6data = moduleBeanList.get(6).getData();
            goodsViewHolder.setData(moduleBeanList);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case GOODS_BANNER0:
                currentType = GOODS_BANNER0;
                break;
            case TRAVEL_CITY_GV1:
                currentType = TRAVEL_CITY_GV1;
                break;
            case CATEGRAY_NEW_GV2:
                currentType = CATEGRAY_NEW_GV2;
                break;
            case ADVERTISING_IV3:
                currentType = ADVERTISING_IV3;
                break;
            case GOODS_LIST_RV4:
                currentType = GOODS_LIST_RV4;
                break;
        }
        return currentType;
    }
}
