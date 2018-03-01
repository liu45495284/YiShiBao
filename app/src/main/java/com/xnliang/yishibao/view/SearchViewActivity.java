package com.xnliang.yishibao.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.SearchGoodsRecycleViewAdapter;
import com.xnliang.yishibao.module.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JackLiu on 2018-01-26.
 */

public class SearchViewActivity extends BaseActivity implements View.OnClickListener ,TextWatcher{

    public List moduleBeanList = new ArrayList();

    @Bind(R.id.rl_search_back)
    RelativeLayout mSearchBack;
    @Bind(R.id.et_search)
    EditText mSearch;
    @Bind(R.id.rv_search)
    RecyclerView mSearchView;
    @Bind(R.id.tv_search_no_result)
    TextView mNoResult;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        moduleBeanList.clear();
        int iconBanner[] = { R.mipmap.i4, R.mipmap.i2, R.mipmap.i3,R.mipmap.i4, R.mipmap.i2,
                R.mipmap.i3,R.mipmap.i4, R.mipmap.i2,R.mipmap.i4, R.mipmap.i2};
        for (int i = 0 ; i < iconBanner.length ; i++) {
            moduleBeanList.add(iconBanner[i]);
        }
    }

    private void initView() {
        mSearchBack.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mSearch.addTextChangedListener(this);

        SearchGoodsRecycleViewAdapter searchAdapter = new SearchGoodsRecycleViewAdapter(this ,moduleBeanList);
        mSearchView.setAdapter(searchAdapter);
        mSearchView.addItemDecoration(new DividerGridItemDecoration(this));
        GridLayoutManager manager = new GridLayoutManager(this ,2);
        mSearchView.setLayoutManager(manager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_search_back:
                finish();
                break;
            case R.id.et_search:

                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
