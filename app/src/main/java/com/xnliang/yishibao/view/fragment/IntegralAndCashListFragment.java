package com.xnliang.yishibao.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.presenter.SelfItemBackListener;
import com.xnliang.yishibao.view.SelfListActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-02-28.
 */

public class IntegralAndCashListFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.rl_list_back)
    RelativeLayout mListBack;
    @Bind(R.id.tv_list_name)
    TextView mlistName;
    @Bind(R.id.rv_list)
    RecyclerView mListRecyclerView;

    private SelfItemBackListener mListener;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListBack((SelfListActivity)getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_integral_and_cash ,container ,false);
        ButterKnife.bind(this ,view);
        initView();
        return view;
    }

    private void initView() {
        mListBack.setOnClickListener(this);
        Intent intent = getActivity().getIntent();
        final int style = intent.getIntExtra("list" , 0);
        if (style == 0){
            return;
        }
        switch (style){
            case 1:
                mlistName.setText(R.string.integral_list);
                break;
            case 2:
                mlistName.setText(R.string.cash_list);
                break;
        }

        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                MyViewHolder mHolder = new MyViewHolder((LayoutInflater.from(getActivity()).inflate(R.layout.self_integral_cash_item ,parent,false)));
                return mHolder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                if(holder != null){
                    MyViewHolder myViewHolder = (MyViewHolder) holder;
                    myViewHolder.setData(style);
                }
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        };

        mListRecyclerView.setAdapter(adapter);
        mListRecyclerView.addItemDecoration(new ListDecoration(getActivity() ,ListDecoration.VERTICAL_LIST , R.drawable.list_divide));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity() ,LinearLayoutManager.VERTICAL ,false);
        mListRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_list_back:
                mListener.viewBackListener();
                break;

        }
    }

    public void setListBack(SelfItemBackListener listener){
        this.mListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_item_desc)
        TextView mItemDesc;
        @Bind(R.id.tv_item_time)
        TextView mItemTime;
        @Bind(R.id.tv_item_style)
        TextView mItemStyle;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);
        }

        public void setData(int style) {

            switch (style) {
                case 1:
                    mItemDesc.setText(R.string.list_temp_integral);
                    mItemTime.setText(R.string.my_order_temp_time);
                    mItemStyle.setText(R.string.style_shopping);
                    break;
                case 2:
                    mItemDesc.setText(R.string.list_temp_cash);
                    mItemTime.setText(R.string.my_order_temp_time);
                    mItemStyle.setText(R.string.list_temp_cash_audit);
                    break;
            }
        }
    }
}
