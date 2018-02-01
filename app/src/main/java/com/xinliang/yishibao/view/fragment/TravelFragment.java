package com.xinliang.yishibao.view.fragment;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.xinliang.yishibao.R;
import com.xinliang.yishibao.module.adapter.HomeRecycleViewAdapter;
import com.xinliang.yishibao.module.adapter.TravelRecycleViewAdapter;
import com.xinliang.yishibao.presenter.ItmeCallBackListener;
import com.xinliang.yishibao.view.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TravelFragment extends BaseFragment {

    private MainActivity mActivity;
    private View mView;
    private PullRefreshLayout mPullRefreshLayout;
    private TravelRecycleViewAdapter mTravelRecycleAdapter;
    public List moduleBeanList = new ArrayList();
    public List freeList = new ArrayList();
    public List highList = new ArrayList();
    private Button mFreeButton;
    private Button mSelfButton;
    private ItmeCallBackListener mListener;
    private RecyclerView mRecyclerView;
    private static final int FREE_HIGH = 0;
    private static final int GOOD_SELF = 1;

    public TravelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mActivity = (MainActivity)getActivity();
        mView = inflater.inflate(R.layout.fragment_travel, container, false);
        initDate();
        initView();
        setBackListener(mActivity);
        return mView;
    }

    public void initView() {
        Toolbar toolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mActivity.setSupportActionBar(toolbar);
        ImageButton back = mView.findViewById(R.id.ib_back);
        back.setOnClickListener(new backListener());

        mFreeButton = mView.findViewById(R.id.free_high);
        mSelfButton = mView.findViewById(R.id.good_self);
        mFreeButton.setOnClickListener(new FreeHighListener());
        mFreeButton.setBackgroundResource(R.drawable.travel_btn_bg);
        mFreeButton.setTextColor(getResources().getColor(R.color.white));
        mSelfButton.setOnClickListener(new GoodSelfListener());

        mPullRefreshLayout = mView.findViewById(R.id.tr_swipeRefreshLayout);
        mPullRefreshLayout.setOnRefreshListener(new myRefreshListener());
        mPullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        mRecyclerView = mView.findViewById(R.id.rv_travel);
        mTravelRecycleAdapter = new TravelRecycleViewAdapter(mActivity, moduleBeanList);
        mRecyclerView.setAdapter(mTravelRecycleAdapter);

        //recycleView不仅要设置适配器还要设置布局管理者,否则图片不显示
        //第一个参数是上下文，第二个参数是只有一列
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mPullRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });
    }

    public void initDate() {

        int itemFree[] = { R.mipmap.i3, R.mipmap.i3, R.mipmap.i3,R.mipmap.i3, R.mipmap.i3,
                R.mipmap.i3,R.mipmap.i3, R.mipmap.i3, R.mipmap.i3,R.mipmap.i3, R.mipmap.i3, R.mipmap.i3};
        int itemHigh[] = { R.mipmap.i4, R.mipmap.i4,R.mipmap.i4, R.mipmap.i4,R.mipmap.i4,
                R.mipmap.i4,R.mipmap.i4, R.mipmap.i4,R.mipmap.i4};

        for (int i = 0 ; i < itemFree.length ; i++) {
            freeList.add(itemFree[i]);
        }

        for (int i = 0 ; i < itemHigh.length ; i++) {
            highList.add(itemHigh[i]);
        }

        for (int i =0 ; i < freeList.size() ; i++){
            moduleBeanList.add(freeList.get(i));
        }
    }

    public class myRefreshListener implements PullRefreshLayout.OnRefreshListener {
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

    public class FreeHighListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mFreeButton.setBackgroundResource(R.drawable.travel_btn_bg);
            mFreeButton.setTextColor(getResources().getColor(R.color.white));
            mSelfButton.setBackgroundResource(R.color.transparent);
            mSelfButton.setTextColor(getResources().getColor(R.color.black));
            updateDate(FREE_HIGH);
//            handler.obtainMessage(FREE_HIGH).sendToTarget();
//            moduleBeanList = freeList;
//            mTravelRecycleAdapter = new TravelRecycleViewAdapter(mActivity, moduleBeanList);
//            mRecyclerView.setAdapter(mTravelRecycleAdapter);
            Toast.makeText(mActivity , "1111" , Toast.LENGTH_SHORT).show();
        }
    }

    public class GoodSelfListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mFreeButton.setBackgroundResource(R.color.transparent);
            mFreeButton.setTextColor(getResources().getColor(R.color.black));
            mSelfButton.setBackgroundResource(R.drawable.travel_btn_bg);
            mSelfButton.setTextColor(getResources().getColor(R.color.white));
            updateDate(GOOD_SELF);
//            handler.obtainMessage(GOOD_SELF).sendToTarget();
//            moduleBeanList = highList;
//            mTravelRecycleAdapter = new TravelRecycleViewAdapter(mActivity, moduleBeanList);
//            mRecyclerView.setAdapter(mTravelRecycleAdapter);
            Toast.makeText(mActivity , "2222" , Toast.LENGTH_SHORT).show();
        }
    }

    public class backListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (mListener != null){
                mListener.travelBackListener();
            }
        }
    }

    public void setBackListener(ItmeCallBackListener listener){
        mListener = listener;
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case FREE_HIGH:
                    mTravelRecycleAdapter.notifyDataSetChanged();
                    break;
                case GOOD_SELF:
                    mTravelRecycleAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public void runOnUiThread() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTravelRecycleAdapter.notifyItemRangeInserted(0,moduleBeanList.size());
            }
        });}

    public void updateDate(int flag) {

        moduleBeanList.clear();
        switch (flag){
            case FREE_HIGH:
                mTravelRecycleAdapter.notifyItemRangeRemoved(0,highList.size());
                moduleBeanList.addAll(freeList);
                break;
            case GOOD_SELF:
                mTravelRecycleAdapter.notifyItemRangeRemoved(0,freeList.size());
                moduleBeanList.addAll(highList);
                break;
        }
        runOnUiThread();
    }
}
