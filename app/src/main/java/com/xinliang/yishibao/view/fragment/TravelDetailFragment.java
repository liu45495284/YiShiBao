package com.xinliang.yishibao.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xinliang.yishibao.R;

/**
 * Created by JackLiu on 2018-02-02.
 */

public class TravelDetailFragment extends TravelFragmentContainer {

    private FragmentManager mFragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel_detail , container ,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ImageButton detailBack = view.findViewById(R.id.ib_detail_back);
        TextView detailTitle = view.findViewById(R.id.tv_travel_detail_name);
        Button snapUp = view.findViewById(R.id.bt_snap_up);

        mFragmentManager = getActivity().getSupportFragmentManager();
        detailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        snapUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TravelInviteCodeFragment fragment = new TravelInviteCodeFragment();
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_base ,fragment , "invite" );
                fragmentTransaction.addToBackStack("invite");
                fragmentTransaction.commitAllowingStateLoss();
            }
        });
    }
}
