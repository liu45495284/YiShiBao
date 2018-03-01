package com.xnliang.yishibao.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.HomeRecycleViewAdapter;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.SearchViewActivity;
import com.xnliang.yishibao.view.ShoppingCartActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private MainActivity mActivity;
    public List moduleBeanList = new ArrayList();
    private PullRefreshLayout mPullRefreshLayout;
    private View mView;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = (MainActivity)getActivity();
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        initData();

        return mView;
    }

    public void initView() {

        Toolbar toolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mActivity.setSupportActionBar(toolbar);

        RelativeLayout searchLayout = (RelativeLayout)mView.findViewById(R.id.rl_home_search);
        searchLayout.setOnClickListener(this);

        ImageButton shoppingButton = (ImageButton) mView.findViewById(R.id.shopping_cart);
        shoppingButton.setOnClickListener(this);
        mPullRefreshLayout = mView.findViewById(R.id.swipeRefreshLayout);
        mPullRefreshLayout.setOnRefreshListener(new myRefreshListener());
        mPullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        RecyclerView recyclerView = mView.findViewById(R.id.rv_home);
        HomeRecycleViewAdapter homeRecycleAdapter = new HomeRecycleViewAdapter(mActivity, moduleBeanList , this);
        recyclerView.setAdapter(homeRecycleAdapter);

        //recycleView不仅要设置适配器还要设置布局管理者,否则图片不显示
        //第一个参数是上下文，第二个参数是只有一列
        LinearLayoutManager manager = new LinearLayoutManager(mActivity){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.getAdapter().getItemViewType(0);

    }

    public void initData() {
        moduleBeanList.clear();
        int iconBanner[] = { R.mipmap.i4, R.mipmap.i2, R.mipmap.i3,R.mipmap.i4, R.mipmap.i2,
                R.mipmap.i3,R.mipmap.i4, R.mipmap.i2};
        for (int i = 0 ; i < iconBanner.length ; i++) {
            moduleBeanList.add(iconBanner[i]);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_home_search:
                Intent intentSearch = new Intent(mActivity,SearchViewActivity.class);
                startActivity(intentSearch);
                break;
            case R.id.shopping_cart:
                Intent intentCart = new Intent(mActivity,ShoppingCartActivity.class);
                startActivity(intentCart);
                break;
        }
    }

    public class myRefreshListener implements PullRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            new Handler ().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPullRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "更新了五条数据...", Toast.LENGTH_SHORT).show();
                }
            } , 3000);
        }
    }

    @Override
    public boolean onBackPressed() {
        if (mActivity != null) {
            mActivity.finish();
        }
        return true;
    }
}
