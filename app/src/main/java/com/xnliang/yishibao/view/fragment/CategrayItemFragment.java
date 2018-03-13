package com.xnliang.yishibao.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.CategrayItemAdapter;
import com.xnliang.yishibao.module.bean.ShoppingCartBean;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.SearchViewActivity;
import com.xnliang.yishibao.view.ShoppingCartActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackLiu on 2018-02-08.
 */

public class CategrayItemFragment extends BaseFragment implements View.OnClickListener {


    private View mView;
    private MainActivity mActivity;
    public List moduleBeanList = new ArrayList();
    private PullRefreshLayout mPullRefreshLayout;
    private ImageView mNoramalArrow;
    private ImageView mSalesUpArrow;
    private ImageView mPriceUpArrow;
    public static final String SHOP_CATEGORY_TAG = "shop_category";
    public static final String CATEGORY_TAG = "category";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (MainActivity)getActivity();
        mView = inflater.inflate(R.layout.fragment_categray , container ,false);
        initView();
        initData();
        return mView;
    }

    private void initView() {
        ImageButton itemBack = mView.findViewById(R.id.ib_categray_back);
        RelativeLayout searchLayout = mView.findViewById(R.id.rl_categray_search);
        ImageButton cateCrat = mView.findViewById(R.id.ib_categray_cart);

        itemBack.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        cateCrat.setOnClickListener(this);

        TextView normalSort = mView.findViewById(R.id.tv_normal);
        mNoramalArrow = mView.findViewById(R.id.iv_normal_arrow_up);
        mNoramalArrow.setSelected(true);
        TextView salesSort = mView.findViewById(R.id.tv_sales);
        mSalesUpArrow = mView.findViewById(R.id.iv_sales_arrow_up);
//        ImageView downArrow = mView.findViewById(R.id.iv_sales_arrow_down);
        TextView priceSort = mView.findViewById(R.id.tv_price);
        mPriceUpArrow = mView.findViewById(R.id.iv_price_arrow_up);
//        ImageView priceDownArrow = mView.findViewById(R.id.iv_price_arrow_down);

        normalSort.setOnClickListener(new NormalOnClickListener());
        salesSort.setOnClickListener(new SalesOnClickListener());
        priceSort.setOnClickListener(new PriceOnClickListener());

        mPullRefreshLayout = mView.findViewById(R.id.categray_refresh);
        mPullRefreshLayout.setOnRefreshListener(new CateRefreshListener());
        mPullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        RecyclerView cateView = mView.findViewById(R.id.rv_categray_item);
        CategrayItemAdapter catgrayItemAdapter = new CategrayItemAdapter(mActivity , moduleBeanList);
        cateView.setAdapter(catgrayItemAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(mActivity , LinearLayoutManager.VERTICAL ,false);
        cateView.setLayoutManager(manager);
        cateView.addItemDecoration(new ListDecoration(mActivity , ListDecoration.VERTICAL_LIST , R.drawable.list_divide));

    }

    public void initData() {
        moduleBeanList.clear();
        int iconBanner[] = { R.mipmap.i4, R.mipmap.i2, R.mipmap.i3,R.mipmap.i4, R.mipmap.i2,
                R.mipmap.i3,R.mipmap.i4, R.mipmap.i2};
        ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
        for (int i = 0 ; i < iconBanner.length ; i++) {
//            shoppingCartBean.setId(iconBanner[i]);
            moduleBeanList.add(iconBanner[i]);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_categray_back:
                onBackPressed();
                break;
            case R.id.rl_categray_search:
                if (mActivity != null) {
                    Intent intent = new Intent(mActivity, SearchViewActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ib_categray_cart:
                if (mActivity != null) {
                    Intent intent = new Intent(mActivity, ShoppingCartActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    public class CateRefreshListener implements PullRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPullRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "更新了1条数据...", Toast.LENGTH_SHORT).show();
                }
            } , 1000);
        }
    }

    public class NormalOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mNoramalArrow.setSelected(true);
            mSalesUpArrow.setSelected(false);
            mPriceUpArrow.setSelected(false);
        }
    }

    public class SalesOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mNoramalArrow.setSelected(false);
            mSalesUpArrow.setSelected(true);
            mPriceUpArrow.setSelected(false);
        }
    }

    public class PriceOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mNoramalArrow.setSelected(false);
            mSalesUpArrow.setSelected(false);
            mPriceUpArrow.setSelected(true);
        }
    }

    @Override
    public boolean onBackPressed() {
        int flag = getArguments().getInt("flag");
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (flag == 0) {
            HomeFragment homeFragment = (HomeFragment) manager.findFragmentByTag("home");
            transaction.hide(this);
            transaction.show(homeFragment);
            transaction.commit();
        }else if(flag == 1){
            ShopFragment shopFragment = (ShopFragment) manager.findFragmentByTag("shop");
            transaction.hide(this);
            transaction.show(shopFragment);
            transaction.commit();
        }
        return true;
    }
}
