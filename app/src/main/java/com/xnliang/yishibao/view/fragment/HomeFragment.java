package com.xnliang.yishibao.view.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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
import com.alibaba.fastjson.JSONObject;
import com.baoyz.widget.PullRefreshLayout;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.HomeRecycleViewAdapter;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.SearchViewActivity;
import com.xnliang.yishibao.view.ShoppingCartActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private MainActivity mActivity;
    public List moduleBeanList = new ArrayList();
    private PullRefreshLayout mPullRefreshLayout;
    private View mView;
    private static final String mIndex ="http://ysb.appxinliang.cn/api/index";
    private static final int SUCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private JSONObject mIndexData;
    protected static final int SUCEESS = 0;
    private RecyclerView mRecyclerView;
    private HomeFragment mFragment;
    private HomeRecycleViewAdapter homeRecycleAdapter;
    private View tempView;


    public HomeFragment() {
        // Required empty public constructor
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            if(msg.what == SUCEESS){
                mRecyclerView = mView.findViewById(R.id.rv_home);
                if(homeRecycleAdapter == null){
                    homeRecycleAdapter = new HomeRecycleViewAdapter(mActivity, mIndexData , mFragment);
                    mRecyclerView.setAdapter(homeRecycleAdapter);
                }

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
                mRecyclerView.setLayoutManager(manager);
                mRecyclerView.getAdapter().getItemViewType(0);
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (null != mIndexData && null != homeRecycleAdapter) {//解决切换后闪屏已经无数据显示问题
            homeRecycleAdapter.notifyDataSetChanged();
        }
        Log.i("test" , "onActivityCreated");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = (MainActivity)getActivity();
        mFragment = this;

        // Inflate the layout for this fragment
        if(tempView == null) {
            mView = inflater.inflate(R.layout.fragment_home, container, false);
            initView();
        }else {
            mView = tempView;
        }
//        initData();

        Log.i("test" , "onCreateView");
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

    }

    @Override
    public void onStart() {
        super.onStart();
        if (null == mIndexData && getUserVisibleHint()) {
            dataFromNet();//解决第一个fragment无法加载数据问题
        }else{
            handler.obtainMessage(SUCEESS).sendToTarget();
        }
        Log.i("test" , "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("test" , "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("test" , "onPause");
    }



    public void initData() {
        moduleBeanList.clear();
        int iconBanner[] = { R.mipmap.i4, R.mipmap.i2, R.mipmap.i3,R.mipmap.i4, R.mipmap.i2,
                R.mipmap.i3,R.mipmap.i4, R.mipmap.i2};
        for (int i = 0 ; i < iconBanner.length ; i++) {
            moduleBeanList.add(iconBanner[i]);
        }

        dataFromNet();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible()) {//视图可见并且控件准备好了，每次都会调用
            if (null == mIndexData) {//如果数据为空了，则需要重新联网请求
                dataFromNet();
            }
        }
    }

    public void dataFromNet(){
        OkHttpUtils
                .get()
                .url(mIndex)
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
        handler.obtainMessage(SUCEESS).sendToTarget();

        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(code) == SUCESSFUL_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
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
    public void onDestroyView() {
        super.onDestroyView();
        tempView = mView;
        Log.i("test" , "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("test" , "onDestroy");
    }

    @Override
    public boolean onBackPressed() {
        if (mActivity != null) {
            mActivity.finish();
        }
        return true;
    }
}
