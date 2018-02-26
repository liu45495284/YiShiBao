package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.OrderDetailRecycleViewAdapter;
import com.xnliang.yishibao.module.utils.ListDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-02-24.
 */

public class MyOrderDetailFragment extends BaseFragment {

    private static final String ORDER_DETAIL = "order_detail";
    private List moduleBeanList = new ArrayList();
    private List categoryList = new ArrayList();
    private View mView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.my_order_detail , container ,false);
        ButterKnife.bind(this, mView);
        Bundle arguments = getArguments();
        int cate = arguments.getInt(ORDER_DETAIL);
        initView(cate);
        initData(cate);
        return mView;
    }

    public static MyOrderDetailFragment newInstance(int type){
        Bundle args = new Bundle();
        args.putInt(ORDER_DETAIL , type);

        MyOrderDetailFragment detailFragment = new MyOrderDetailFragment();
        detailFragment.setArguments(args);
        return detailFragment;
    }

    private void initView(int cate) {
        RecyclerView recyclerView = mView.findViewById(R.id.rv_my_order_detail);

        switch (cate) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
        OrderDetailRecycleViewAdapter detailAdapter = new OrderDetailRecycleViewAdapter(getActivity() ,moduleBeanList );
        recyclerView.setAdapter(detailAdapter);

        recyclerView.addItemDecoration(new ListDecoration(getActivity() ,ListDecoration.VERTICAL_LIST , R.drawable.cart_list_divide));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity() , LinearLayoutManager.VERTICAL ,false);
        recyclerView.setLayoutManager(manager);

    }

    private void initData(int cate) {
        moduleBeanList.clear();
        switch (cate) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
        int iconBanner[] = { R.mipmap.i4, R.mipmap.i2, R.mipmap.i3,R.mipmap.i4, R.mipmap.i2,
                R.mipmap.i3,R.mipmap.i4, R.mipmap.i2};
        for (int i = 0 ; i < iconBanner.length ; i++) {
            moduleBeanList.add(iconBanner[i]);
        }
    }
}
