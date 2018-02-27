package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.view.SelfListActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-02-26.
 */

public class MyTeamMemberFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.rl_team_member_back)
    RelativeLayout relativeLayout;
    @Bind(R.id.rv_my_team_member)
    RecyclerView mMemberView;
    private View mView;
    private ImemBerBackListener mListener;

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
        ButterKnife.bind(this, mView);
        initView();
        initData();
        return mView;
    }

    private void initView() {
        relativeLayout.setOnClickListener(this);

        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                MyViewHolder mHolder = new MyViewHolder((LayoutInflater.from(getActivity()).inflate(R.layout.self_my_team_member_item ,parent,false)));
                return mHolder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                if(holder != null){
                    MyViewHolder myViewHolder = (MyViewHolder) holder;
                    myViewHolder.setData();
                }
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        };

        mMemberView.setAdapter(adapter);
        mMemberView.addItemDecoration(new ListDecoration(getActivity() ,ListDecoration.VERTICAL_LIST , R.drawable.list_divide));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity() ,LinearLayoutManager.VERTICAL ,false);
        mMemberView.setLayoutManager(manager);
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_team_member_back:
                mListener.memberBack();
                break;

        }
    }

    public interface ImemBerBackListener{
        void memberBack();
    }

    public void setMemberBack(ImemBerBackListener listener){
        this.mListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View itemView) {
            super(itemView);
        }

        public void setData() {
        }
    }
}
