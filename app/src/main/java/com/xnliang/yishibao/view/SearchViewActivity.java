package com.xnliang.yishibao.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.SearchGoodsRecycleViewAdapter;
import com.xnliang.yishibao.module.bean.CategroyListBean;
import com.xnliang.yishibao.module.utils.DividerGridItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by JackLiu on 2018-01-26.
 */

public class SearchViewActivity extends BaseActivity implements View.OnClickListener ,TextWatcher{

    public List moduleBeanList = new ArrayList();
    private static final String searchIndex = "http://ysb.appxinliang.cn/api/shop/lists";
    private JSONObject mIndexData;
    private List<CategroyListBean.DataBeanX.DataBean> mListDatas;
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private static final int SEARCH_INDEX = 0;

    @Bind(R.id.rl_search_back)
    RelativeLayout mSearchBack;
    @Bind(R.id.et_search)
    EditText mSearch;
    @Bind(R.id.rv_search)
    RecyclerView mSearchView;
    @Bind(R.id.tv_search_no_result)
    TextView mNoResult;
    private SearchGoodsRecycleViewAdapter mSearchAdapter;
    private DividerGridItemDecoration mDecoration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
//        moduleBeanList.clear();
//        int iconBanner[] = { R.mipmap.i4, R.mipmap.i2, R.mipmap.i3,R.mipmap.i4, R.mipmap.i2,
//                R.mipmap.i3,R.mipmap.i4, R.mipmap.i2,R.mipmap.i4, R.mipmap.i2};
//        for (int i = 0 ; i < iconBanner.length ; i++) {
//            moduleBeanList.add(iconBanner[i]);
//        }
    }

    //排序 zh 综合 xl 销量 jg 价格
    //asc 升序 desc倒序
    private void getDataFromNet(String url ,int page ,int cate_id ,String keyword , String sort ,String sort_type) {
        OkHttpUtils
                .get()
                .url(url)
                .addParams("page" ,page + "")
                .addParams("cate_id" ,cate_id + "")
                .addParams("keyword" ,keyword)
                .addParams("sort" ,sort)
                .addParams("sort_type" ,sort_type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                        Toast.makeText(SearchViewActivity.this ,R.string.register_failure ,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==" + response);
                        if (response.isEmpty()){
                            return;
                        }
                        processData(response);
                    }
                });
    }

    private void processData(String response) {
        JSONObject jsonObject = JSON.parseObject(response);

        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");
        mIndexData = jsonObject.getJSONObject("data");

        mListDatas = JSON.parseObject(mIndexData.getString("data") ,
                new TypeReference<ArrayList<CategroyListBean.DataBeanX.DataBean>>(){}.getType());

//        if (sales){
//            handler.obtainMessage(SALES_SORT).sendToTarget();
//        }else if (price) {
//            handler.obtainMessage(PRICE_SORT).sendToTarget();
//        }else {
            handler.obtainMessage(SEARCH_INDEX).sendToTarget();
//        }

        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(SearchViewActivity.this, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(code) == SUCCESSFUL_CODE) {
            Toast.makeText(SearchViewActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case  SEARCH_INDEX:
                    if(mSearchAdapter == null) {
                        mSearchAdapter = new SearchGoodsRecycleViewAdapter(SearchViewActivity.this, mListDatas);
                        mSearchView.setAdapter(mSearchAdapter);
                    }

                    if(mDecoration == null){
                        mDecoration = new DividerGridItemDecoration(SearchViewActivity.this);
                        mSearchView.addItemDecoration(mDecoration);
                    }

                    GridLayoutManager manager = new GridLayoutManager(SearchViewActivity.this ,2);
                    mSearchView.setLayoutManager(manager);
                    break;
            }
        }
    };

    private void initView() {
        mSearchBack.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mSearch.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_search_back:
                finish();
                break;
            case R.id.et_search:
                String inputText = mSearch.getText().toString();
                if(TextUtils.isEmpty(inputText)){
                    return;
                }
                getDataFromNet(searchIndex , 1 ,0 ,inputText ,"zh" ,"desc");
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
