package com.xnliang.yishibao.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.view.OrderPayActivity;

/**
 * Created by JackLiu on 2018-02-02.
 */

public class TravelInviteCodeFragment extends BaseFragment {

    private FragmentManager mFragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel_invite,container , false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ImageButton detailBack = view.findViewById(R.id.ib_invite_back);
        RelativeLayout relativeLayout = view.findViewById(R.id.rl_invite_back);
        EditText inviteCode = view.findViewById(R.id.et_invite_code);
        Button next = view.findViewById(R.id.bt_next);

        mFragmentManager = getActivity().getSupportFragmentManager();
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager.popBackStack();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , OrderPayActivity.class);
                startActivity(intent);
            }
        });
    }
}
