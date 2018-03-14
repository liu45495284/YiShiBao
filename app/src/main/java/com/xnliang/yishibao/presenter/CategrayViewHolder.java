package com.xnliang.yishibao.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.CategrayAdapter;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.customview.CategrayGridView;
import com.xnliang.yishibao.view.fragment.CategrayItemFragment;
import com.xnliang.yishibao.view.fragment.HomeContainerFragment;
import com.xnliang.yishibao.view.fragment.HomeFragment;

import java.util.List;

/**
 * Created by JackLiu on 2018-01-31.
 */

public class CategrayViewHolder extends BaseViewHolder {

    private final Context mContext;
    private CategrayGridView gridView;
    private final MainActivity mActivity;
    private HomeFragment mFragment;
    public static final String CATEGORY_TAG = "category";

    public CategrayViewHolder(Context context, View itemView , HomeFragment fragment) {
        super(itemView);
        this.mContext = context;
        this.mActivity = (MainActivity)context;
        this.mFragment = fragment;
        gridView = itemView.findViewById(R.id.gv_channel_categray);
    }

    public void setData (JSONArray data ){
        //已得到数据了
        //设置适配器
        CategrayAdapter adapter = new CategrayAdapter(mContext, data , mFragment);
        gridView.setAdapter(adapter);

    }
}
