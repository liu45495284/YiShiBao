package com.xnliang.yishibao.module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.bean.ShopIndexBean;
import com.xnliang.yishibao.presenter.GoodsCategrayViewHolder;
import com.xnliang.yishibao.presenter.OneIntegralBuyViewHolder;
import com.xnliang.yishibao.presenter.IntegralGoodsViewHolder;
import com.xnliang.yishibao.presenter.ShopViewHolder;
import com.xnliang.yishibao.presenter.TeamBuyGoodsViewHolder;
import com.xnliang.yishibao.presenter.TeamBuyViewHolder;
import com.xnliang.yishibao.presenter.TenIntegralBuyViewHolder;
import com.xnliang.yishibao.presenter.TenIntegralGoodsViewHolder;
import com.xnliang.yishibao.view.fragment.ShopFragment;

import java.util.LinkedHashMap;
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
    private static final int SHOP_ITEM_COUNT = 8;
    /**
     * 以后用它来初始化布局
     */
    private final LayoutInflater mLayoutInflater;

    private final Context mContext;
    private final JSONObject mData;
    private RecyclerView.ViewHolder holder = null;
    private ShopFragment mShopFragment;

    public ShopRecycleViewAdapter(Context context , JSONObject data , ShopFragment shopFragment) {
        this.mContext = context;
        this.mData = data;
        this.mShopFragment = shopFragment;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SHOP_BANNER0:
                holder = new ShopViewHolder(mContext, mLayoutInflater.inflate(R.layout.shop_banner_viewpager, null));
                break;
            case GOODS_CATEGRAY_GV1:
                holder = new GoodsCategrayViewHolder(mContext, mLayoutInflater.inflate(R.layout.shop_goods_categray, null) , mShopFragment);
                break;
            case ONE_INTEGRAL_IV2:
                holder = new OneIntegralBuyViewHolder(mContext, mLayoutInflater.inflate(R.layout.shop_integral_buy_title, null));
                break;
            case ONE_INTEGRAL_GOODS_RV3:
                holder = new IntegralGoodsViewHolder(mContext, mLayoutInflater.inflate(R.layout.shop_one_integral_goods, null));
                break;
            case TEN_INTEGRAL_IV4:
                holder = new TenIntegralBuyViewHolder(mContext, mLayoutInflater.inflate(R.layout.shop_integral_buy_title, null));
                break;
            case TEN_INTEGRAL_GOODS_RV5:
                holder = new TenIntegralGoodsViewHolder(mContext, mLayoutInflater.inflate(R.layout.shop_one_integral_goods, null));
                break;
            case TEAM_BUY_IV6:
                holder = new TeamBuyViewHolder(mContext, mLayoutInflater.inflate(R.layout.shop_integral_buy_title, null));
                break;
            case TEAM_BUY_DETAIL_RV7:
                holder = new TeamBuyGoodsViewHolder(mContext, mLayoutInflater.inflate(R.layout.shop_team_buy_goods, null));
                break;
        }
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case SHOP_BANNER0:
                ShopViewHolder shopViewHolder = (ShopViewHolder) holder;
                List<ShopIndexBean.DataBean.SlidesBean> slidesBeans = JSON.parseObject(mData.getString("slides"),
                        new TypeReference<List<ShopIndexBean.DataBean.SlidesBean>>(){}.getType());
                shopViewHolder.setData(slidesBeans , position);
                break;
            case GOODS_CATEGRAY_GV1:
                GoodsCategrayViewHolder goodsCategrayViewHolder = (GoodsCategrayViewHolder) holder;
                List<ShopIndexBean.DataBean.CateBean> cateBeans = JSON.parseObject(mData.getString("cate"),
                        new TypeReference<List<ShopIndexBean.DataBean.CateBean>>(){}.getType());
                goodsCategrayViewHolder.setData(cateBeans);
                break;
            case ONE_INTEGRAL_IV2:
                OneIntegralBuyViewHolder oneIntegralBuyViewHolder = (OneIntegralBuyViewHolder) holder;
                oneIntegralBuyViewHolder.setData();
                break;
            case ONE_INTEGRAL_GOODS_RV3:
                IntegralGoodsViewHolder oneIntegralViewHolder = (IntegralGoodsViewHolder) holder;
                List<ShopIndexBean.DataBean.YjfListsBean> yjfListsBeans = JSON.parseObject(mData.getString("yjf_lists"),
                        new TypeReference<List<ShopIndexBean.DataBean.YjfListsBean>>(){}.getType());
                oneIntegralViewHolder.setData(yjfListsBeans);
                break;
            case TEN_INTEGRAL_IV4:
                TenIntegralBuyViewHolder integralBuyViewHolder = (TenIntegralBuyViewHolder) holder;
                integralBuyViewHolder.setData();
                break;
            case TEN_INTEGRAL_GOODS_RV5:
                TenIntegralGoodsViewHolder tenIntegralViewHolder = (TenIntegralGoodsViewHolder) holder;
                List<ShopIndexBean.DataBean.SjfListsBean> sjfListsBeans = JSON.parseObject(mData.getString("sjf_lists"),
                        new TypeReference<List<ShopIndexBean.DataBean.SjfListsBean>>(){}.getType());
                tenIntegralViewHolder.setData(sjfListsBeans);
                break;
            case TEAM_BUY_IV6:
                TeamBuyViewHolder teamBuyViewHolder = (TeamBuyViewHolder) holder;
                teamBuyViewHolder.setData();
                break;
            case TEAM_BUY_DETAIL_RV7:
                TeamBuyGoodsViewHolder teamBuyGoodsViewHolder = (TeamBuyGoodsViewHolder) holder;
                List<ShopIndexBean.DataBean.TgListsBean> tgListsBeans = JSON.parseObject(mData.getString("tg_lists"),
                        new TypeReference<List<ShopIndexBean.DataBean.TgListsBean>>(){}.getType());
                teamBuyGoodsViewHolder.setData(tgListsBeans);
                break;
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
