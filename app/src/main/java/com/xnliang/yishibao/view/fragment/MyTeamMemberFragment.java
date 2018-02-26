package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.view.SelfListActivity;

/**
 * Created by JackLiu on 2018-02-26.
 */

public class MyTeamMemberFragment extends BaseFragment {

    private View mView;
    ImemBerBackListener mListener;

    public MyTeamMemberFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setMemberBack((SelfListActivity)getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.self_my_team_member ,container ,false);
        initView();
        initData();
        return mView;
    }

    private void initView() {
        RelativeLayout relativeLayout = mView.findViewById(R.id.rl_team_member_back);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.memberBack();
            }
        });
    }

    private void initData() {
    }

    public interface ImemBerBackListener{
        void memberBack();
    }

    public void setMemberBack(ImemBerBackListener listener){
        this.mListener = listener;
    }
}
