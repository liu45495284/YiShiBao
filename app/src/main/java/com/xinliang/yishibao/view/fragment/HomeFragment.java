package com.xinliang.yishibao.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinliang.yishibao.R;
import com.xinliang.yishibao.module.adapter.HomeRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    public List moduleBeanList = new ArrayList();


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_home);

        initDate();

        HomeRecycleViewAdapter homeRecycleAdapter = new HomeRecycleViewAdapter(getActivity(), moduleBeanList);
        recyclerView.setAdapter(homeRecycleAdapter);

            //recycleView不仅要设置适配器还要设置布局管理者,否则图片不显示
            //第一个参数是上下文，第二个参数是只有一列
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(manager);
        return view;
    }

    public void initDate() {
        int iconBanner[] = { R.mipmap.i4, R.mipmap.i2, R.mipmap.i3,};
        for (int i = 0 ; i < iconBanner.length ; i++) {
            moduleBeanList.add(iconBanner[i]);
        }
    }
}
