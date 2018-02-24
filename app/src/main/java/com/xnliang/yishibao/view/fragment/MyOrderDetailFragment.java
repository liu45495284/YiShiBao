package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xnliang.yishibao.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-02-24.
 */

public class MyOrderDetailFragment extends BaseFragment {
    private static final String ORDER_DETAIL = "order_detail";
    @Bind(R.id.first)
    TextView mTv;
    private View mView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle arguments = getArguments();
        mTv.setText(arguments.getString(ORDER_DETAIL));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.my_order_detail , container ,false);
        ButterKnife.bind(this, mView);

        initView();
        return mView;
    }

    private void initView() {
        RecyclerView recyclerView = mView.findViewById(R.id.rv_my_order_detail);

    }

    public static MyOrderDetailFragment newInstance(String type){
        Bundle args = new Bundle();
        args.putString(ORDER_DETAIL , type);

        MyOrderDetailFragment detailFragment = new MyOrderDetailFragment();
        detailFragment.setArguments(args);
        return detailFragment;
    }
}
