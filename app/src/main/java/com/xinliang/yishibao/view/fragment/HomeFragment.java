package com.xinliang.yishibao.view.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.xinliang.yishibao.R;
import com.xinliang.yishibao.module.adapter.HomeRecycleViewAdapter;
import com.xinliang.yishibao.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

//    private final MainActivity mActivity;
    public List moduleBeanList = new ArrayList();
    private PullRefreshLayout mPullRefreshLayout;
    private HomeRecycleViewAdapter mHomeRecycleAdapter;
    private View mView;
    private MainActivity mActivity;


    public HomeFragment() {
        // Required empty public constructor
//        mActivity = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = (MainActivity)getActivity();
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        initDate();

        return mView;
    }

    public void initView() {
        mPullRefreshLayout = mView.findViewById(R.id.swipeRefreshLayout);
        mPullRefreshLayout.setOnRefreshListener(new myRefreshListener());
        mPullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        RecyclerView recyclerView = mView.findViewById(R.id.rv_home);
        mHomeRecycleAdapter = new HomeRecycleViewAdapter(mActivity, moduleBeanList);
        recyclerView.setAdapter(mHomeRecycleAdapter);

        //recycleView不仅要设置适配器还要设置布局管理者,否则图片不显示
        //第一个参数是上下文，第二个参数是只有一列
        GridLayoutManager manager = new GridLayoutManager(mActivity, 1);
        recyclerView.setLayoutManager(manager);
        recyclerView.getAdapter().getItemViewType(1);
    }

    public void initDate() {
        int iconBanner[] = { R.mipmap.i4, R.mipmap.i2, R.mipmap.i3,};
        for (int i = 0 ; i < iconBanner.length ; i++) {
            moduleBeanList.add(iconBanner[i]);
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
            } , 5000);
        }
    }
}
