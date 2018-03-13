package com.xnliang.yishibao.view.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baoyz.widget.PullRefreshLayout;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.ShopRecycleViewAdapter;
import com.xnliang.yishibao.module.bean.FoundListBean;
import com.xnliang.yishibao.module.bean.ShopIndexBean;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.SearchViewActivity;
import com.xnliang.yishibao.view.ShoppingCartActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends BaseFragment implements View.OnClickListener{


    private MainActivity mActivity;
    private View mView;
    public List moduleBeanList = new ArrayList();
    private PullRefreshLayout mPullRefreshLayout;
    private static final String mShopIndex = "http://ysb.appxinliang.cn/api/shop/index";
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private JSONObject mIndexData;
    private JSONArray mSlidesData;
    private JSONArray mCateData;
    private JSONArray mYjfData;
    private JSONArray mSjfData;
    private JSONArray mTgData;
    private LinkedHashMap<String ,JSONArray> hashMap = new LinkedHashMap<>();
    private ArrayList<ShopIndexBean.DataBean.SlidesBean> mDatasBean = new ArrayList();
    private static final int SHOP_INDEX = 0;
    private ShopFragment mShopFragment;

    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = (MainActivity)getActivity();
        mShopFragment = this;
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_shop, container, false);
        initView();
        initData();
        return mView;
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mActivity.setSupportActionBar(toolbar);

        RelativeLayout searchLayout = mView.findViewById(R.id.rl_shop_search);
        searchLayout.setOnClickListener(this);

        ImageButton shoppingButton = (ImageButton) mView.findViewById(R.id.shopping_cart);
        shoppingButton.setOnClickListener(this);
        mPullRefreshLayout = mView.findViewById(R.id.shop_swipeRefresh);
        mPullRefreshLayout.setOnRefreshListener(new ShopRefreshListener());
        mPullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
    }


    public void initData() {
        moduleBeanList.clear();
//        int iconBanner[] = { R.mipmap.i4, R.mipmap.i2, R.mipmap.i3,R.mipmap.i4, R.mipmap.i2,
//                R.mipmap.i3,R.mipmap.i4, R.mipmap.i2};
//        for (int i = 0 ; i < iconBanner.length ; i++) {
//            moduleBeanList.add(iconBanner[i]);
//        }
        getDataFromNet(mShopIndex);
    }

    public void getDataFromNet(String url){
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                        Toast.makeText(mActivity ,R.string.register_failure ,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==" + response);
                        if (response.isEmpty()){
                            return;
                        }
                        processData(response);
                    }
                });

    }

    private void processData(String json) {
        JSONObject jsonObject = JSON.parseObject(json);

        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");
        mIndexData = jsonObject.getJSONObject("data");
//        mSlidesData = mIndexData.getJSONArray("slides");
//        mCateData = mIndexData.getJSONArray("cate");
//        mYjfData = mIndexData.getJSONArray("yjf_lists");
//        mSjfData = mIndexData.getJSONArray("sjf_lists");
//        mTgData = mIndexData.getJSONArray("tg_lists");
//
//        hashMap.put("slide" , mSlidesData);
//        hashMap.put("cate" , mCateData);
//        hashMap.put("yjf_lists" , mYjfData);
//        hashMap.put("sjf_lists" , mSjfData);
//        hashMap.put("tg_lists" , mTgData);
//
//        mDatasBean = JSON.parseObject(mIndexData.getString("slides"),
//                new TypeReference<ArrayList<ShopIndexBean.DataBean.SlidesBean>>(){}.getType());

        handler.obtainMessage(SHOP_INDEX).sendToTarget();

        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(code) == SUCCESSFUL_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_shop_search:
                Intent intentSearch = new Intent(mActivity,SearchViewActivity.class);
                startActivity(intentSearch);
                break;
            case R.id.shopping_cart:
                Intent intentCart = new Intent(mActivity,ShoppingCartActivity.class);
                startActivity(intentCart);
                break;
        }
    }

    private class ShopRefreshListener implements PullRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPullRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "更新了3条数据...", Toast.LENGTH_SHORT).show();
                }
            } , 1000);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SHOP_INDEX:
                    RecyclerView recyclerView = mView.findViewById(R.id.rv_shop);
                    ShopRecycleViewAdapter shopRecycleAdapter = new ShopRecycleViewAdapter(mActivity, mIndexData , mShopFragment);
                    recyclerView.setAdapter(shopRecycleAdapter);
                    recyclerView.setItemAnimator(null);
                    //recycleView不仅要设置适配器还要设置布局管理者,否则图片不显示
                    //第一个参数是上下文，第二个参数是只有一列
                    /*
                     * fix recycleview item width not match_parent
                     */
                    LinearLayoutManager manager = new LinearLayoutManager(mActivity){
                        @Override
                        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                            return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                        }
                    };
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(manager);

                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                        }

                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                        }
                    });
                    break;
            }
        }
    };
}
