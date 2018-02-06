package com.xnliang.yishibao.module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.presenter.GoodsCategrayViewHolder;
import com.xnliang.yishibao.presenter.ShopViewHolder;

import java.util.List;

/**
 * Created by JackLiu on 2018-02-06.
 */

public class ShopRecycleViewAdapter extends RecyclerView.Adapter {

    /**
     * 8种类型
     */
    /**
     * 类型1：--使用banner实现
     */
    public static final int SHOP_BANNER0 = 0;
    /**
     *类型2：--使用GridView实现
     */
    public static final int GOODS_CATEGRAY_GV1 = 1;
    /**
     *类型3：--使用ImageView实现
     */
    public static final int ONE_INTEGRAL_IV2 = 2;
    /**
     * 类型4：--使用RecyclerView实现
     */
    public static final int ONE_INTEGRAL_GOODS_RV3=3;
    /**
     * 类型5：--使用ImageView实现
     */
    public static final int TEN_INTEGRAL_IV4 =4;
    /**
     * 类型6：--使用RecyclerView实现
     */
    public static final int TEN_INTEGRAL_GOODS_RV5=5;
    /**
     * 类型7：--使用ImageView实现
     */
    public static final int TEAM_BUY_IV6=6;
    /**
     * 类型8：--使用RecyclerView实现
     */
    public static final int TEAM_BUY_DETAIL_RV7=7;
    /**
     * 当前类型
     */
    public int currentType = SHOP_BANNER0;
    private static final int SHOP_ITEM_COUNT = 2;
    /**
     * 以后用它来初始化布局
     */
    private final LayoutInflater mLayoutInflater;

    private final Context mContext;
    private final List mData;

    public ShopRecycleViewAdapter(Context context , List data) {
        this.mContext = context;
        this.mData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case SHOP_BANNER0:
                holder = new ShopViewHolder(mContext, mLayoutInflater.inflate(R.layout.shop_banner_viewpager, null));
                break;
            case GOODS_CATEGRAY_GV1:
                holder = new GoodsCategrayViewHolder(mContext, mLayoutInflater.inflate(R.layout.shop_goods_categray, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case SHOP_BANNER0:
                ShopViewHolder shopViewHolder = (ShopViewHolder) holder;
                shopViewHolder.setData(mData);
                break;
            case GOODS_CATEGRAY_GV1:
                GoodsCategrayViewHolder goodsCategrayViewHolder = (GoodsCategrayViewHolder) holder;
                goodsCategrayViewHolder.setData(mData);
        }
    }

    @Override
    public int getItemCount() {
        return SHOP_ITEM_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case SHOP_BANNER0:
                currentType = SHOP_BANNER0;
                break;
            case GOODS_CATEGRAY_GV1:
                currentType = GOODS_CATEGRAY_GV1;
                break;
            case ONE_INTEGRAL_IV2:
                currentType = ONE_INTEGRAL_IV2;
                break;
            case ONE_INTEGRAL_GOODS_RV3:
                currentType = ONE_INTEGRAL_GOODS_RV3;
                break;
            case TEN_INTEGRAL_IV4:
                currentType = TEN_INTEGRAL_IV4;
                break;
            case TEN_INTEGRAL_GOODS_RV5:
                currentType = TEN_INTEGRAL_GOODS_RV5;
                break;
            case TEAM_BUY_IV6:
                currentType = TEAM_BUY_IV6;
                break;
            case TEAM_BUY_DETAIL_RV7:
                currentType = TEAM_BUY_DETAIL_RV7;
                break;
        }
        return currentType;
    }
}
