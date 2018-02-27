package com.xnliang.yishibao.view.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.utils.ListDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-02-24.
 */

public class MyAddressFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.rl_address_back)
    RelativeLayout relativeLayout;
    @Bind(R.id.bt_confirm)
    Button confirmButton;
    @Bind(R.id.rv_address)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_path_choose , container ,false);
        ButterKnife.bind(this,view);
        initView();
        initData();
        return view;
    }

    private void initView() {
        relativeLayout.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.self_address_item ,null));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                if(holder != null){
                    MyViewHolder viewHolder = (MyViewHolder) holder;
                    viewHolder.setData();
                }
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ListDecoration(getActivity() , ListDecoration.VERTICAL_LIST ,R.drawable.cart_list_divide));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity() ,LinearLayoutManager.VERTICAL ,false);
        recyclerView.setLayoutManager(manager);
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_address_back:
                getActivity().finish();
                break;
            case R.id.bt_confirm:
                break;
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.cb_address)
        CheckBox mCheckBox;
        @Bind(R.id.tv_address_desc)
        TextView mAddress;
       @Bind(R.id.iv_address_edit)
        ImageView mEdit;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddressEditFragment editFragment = new AddressEditFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.self_container ,editFragment ,"edit");
                    transaction.addToBackStack("edit");
                    transaction.commit();
                }
            });
        }

        public void setData(){
            //TODO
//            mAddress.setText();
        }
    }
}
