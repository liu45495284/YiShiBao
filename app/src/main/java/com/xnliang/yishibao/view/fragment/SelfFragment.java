package com.xnliang.yishibao.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.SelfRecycleViewAdapter;
import com.xnliang.yishibao.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelfFragment extends BaseFragment {


    private View mView;
    private MainActivity mActivity;
    public List moduleBeanList = new ArrayList();

    public SelfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mActivity = (MainActivity) getActivity();
        mView = inflater.inflate(R.layout.fragment_self, container, false);
        initView();
        initData();
        return mView;
    }

    private void initView() {
        RecyclerView recyclerView = mView.findViewById(R.id.rv_self);
        SelfRecycleViewAdapter adapter = new SelfRecycleViewAdapter(mActivity,moduleBeanList ,this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL , false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void initData() {
    }
}
