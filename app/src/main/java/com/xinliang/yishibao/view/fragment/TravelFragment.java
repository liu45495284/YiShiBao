package com.xinliang.yishibao.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinliang.yishibao.R;
import com.xinliang.yishibao.view.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TravelFragment extends BaseFragment {


    private MainActivity mActivity;

    public TravelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mActivity = (MainActivity)getActivity();
        View view = inflater.inflate(R.layout.fragment_travel, container, false);
        return view;
    }



}
