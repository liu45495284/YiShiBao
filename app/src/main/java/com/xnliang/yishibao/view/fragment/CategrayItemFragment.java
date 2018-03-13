package com.xnliang.yishibao.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baoyz.widget.PullRefreshLayout;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.CategrayItemAdapter;
import com.xnliang.yishibao.module.bean.CategroyListBean;
import com.xnliang.yishibao.module.bean.ShoppingCartBean;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.SearchViewActivity;
import com.xnliang.yishibao.view.ShoppingCartActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by JackLiu on 2018-02-08.
 */

public class CategrayItemFragment extends BaseFragment implements View.OnClickListener {


    private View mView;
    private MainActivity mActivity;
    public List moduleBeanList = new ArrayList();
    private PullRefreshLayout mPullRefreshLayout;
    private ImageView mNoramalArrow;
    private ImageView mSalesUpArrow;
    private ImageView mPriceUpArrow;
    private static final String mCategroyUrl = "http://ysb.appxinliang.cn/api/shop/lists";
    private JSONObject mIndexData;
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private static final int CATEGROY_INDEX = 0;
    private static final int SALES_SORT = 1;
    private static final int PRICE_SORT = 2;
    private boolean sales = false;
    private boolean price = false;
    private ListDecoration mDecoration;
    private List<CategroyListBean.DataBeanX.DataBean> mListDatas;
    private int mCateId;
    private CategrayItemAdapter mCatgrayItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (MainActivity)getActivity();
        mView = inflater.inflate(R.layout.fragment_categray , container ,false);
        initView();
        initData();
        return mView;
    }

    private void initView() {
        ImageButton itemBack = mView.findViewById(R.id.ib_categray_back);
        RelativeLayout searchLayout = mView.findViewById(R.id.rl_categray_search);
        ImageButton cateCrat = mView.findViewById(R.id.ib_categray_cart);

        itemBack.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        cateCrat.setOnClickListener(this);

        TextView normalSort = mView.findViewById(R.id.tv_normal);
        mNoramalArrow = mView.findViewById(R.id.iv_normal_arrow_up);
        mNoramalArrow.setSelected(true);
        TextView salesSort = mView.findViewById(R.id.tv_sales);
        mSalesUpArrow = mView.findViewById(R.id.iv_sales_arrow_up);
//        ImageView downArrow = mView.findViewById(R.id.iv_sales_arrow_down);
        TextView priceSort = mView.findViewById(R.id.tv_price);
        mPriceUpArrow = mView.findViewById(R.id.iv_price_arrow_up);
//        ImageView priceDownArrow = mView.findViewById(R.id.iv_price_arrow_down);

        normalSort.setOnClickListener(new NormalOnClickListener());
        salesSort.setOnClickListener(new SalesOnClickListener());
        priceSort.setOnClickListener(new PriceOnClickListener());

        mPullRefreshLayout = mView.findViewById(R.id.categray_refresh);
        mPullRefreshLayout.setOnRefreshListener(new CateRefreshListener());
        mPullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

    }

    public void initData() {
        moduleBeanList.clear();
        int iconBanner[] = { R.mipmap.i4, R.mipmap.i2, R.mipmap.i3,R.mipmap.i4, R.mipmap.i2,
                R.mipmap.i3,R.mipmap.i4, R.mipmap.i2};
        ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
        for (int i = 0 ; i < iconBanner.length ; i++) {
//            shoppingCartBean.setId(iconBanner[i]);
            moduleBeanList.add(iconBanner[i]);
        }

        mCateId = getArguments().getInt("id");

        getDataFromNet(mCategroyUrl ,1 , mCateId,null ,"zh" ,"desc");
    }

    //排序 zh 综合 xl 销量 jg 价格
    //asc 升序 desc倒序
    private void getDataFromNet(String url ,int page ,int cate_id ,String keyword , String sort ,String sort_type) {
        OkHttpUtils
                .get()
                .url(url)
                .addParams("page" ,page + "")
                .addParams("cate_id" ,cate_id + "")
                .addParams("sort" ,sort)
                .addParams("sort_type" ,sort_type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                        Toast.makeText(mActivity ,R.string.register_failure ,Toast.LENGTH_SHORT).show();
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

        if (sales){
            handler.obtainMessage(SALES_SORT).sendToTarget();
        }else if (price) {
            handler.obtainMessage(PRICE_SORT).sendToTarget();
        }else {
            handler.obtainMessage(CATEGROY_INDEX).sendToTarget();
        }

        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(code) == SUCCESSFUL_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_categray_back:
                onBackPressed();
                break;
            case R.id.rl_categray_search:
                if (mActivity != null) {
                    Intent intent = new Intent(mActivity, SearchViewActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ib_categray_cart:
                if (mActivity != null) {
                    Intent intent = new Intent(mActivity, ShoppingCartActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    public class CateRefreshListener implements PullRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPullRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "更新了1条数据...", Toast.LENGTH_SHORT).show();
                }
            } , 1000);
        }
    }

    public class NormalOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mNoramalArrow.setSelected(true);
            mSalesUpArrow.setSelected(false);
            mPriceUpArrow.setSelected(false);
            sales = false;
            price = false;
            getDataFromNet(mCategroyUrl ,1 ,mCateId ,null ,"xl" ,"desc");
        }
    }

    public class SalesOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mNoramalArrow.setSelected(false);
            mSalesUpArrow.setSelected(true);
            mPriceUpArrow.setSelected(false);
            sales = true;
            price = false;
            getDataFromNet(mCategroyUrl ,1 ,mCateId ,null ,"xl" ,"asc");
        }
    }

    public class PriceOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mNoramalArrow.setSelected(false);
            mSalesUpArrow.setSelected(false);
            mPriceUpArrow.setSelected(true);
            sales = false;
            price = true;
            getDataFromNet(mCategroyUrl ,1 ,mCateId ,null ,"jg" ,"asc");
        }
    }

    @Override
    public boolean onBackPressed() {
        int flag = getArguments().getInt("flag");
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (flag == 0) {
            HomeFragment homeFragment = (HomeFragment) manager.findFragmentByTag("home");
            transaction.hide(this);
            transaction.show(homeFragment);
            transaction.commit();
        }else if(flag == 1){
            ShopFragment shopFragment = (ShopFragment) manager.findFragmentByTag("shop");
            transaction.hide(this);
            transaction.show(shopFragment);
            transaction.commit();
        }
        return true;
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CATEGROY_INDEX:
                    RecyclerView cateView = mView.findViewById(R.id.rv_categray_item);
                    mCatgrayItemAdapter = new CategrayItemAdapter(mActivity , mListDatas);
                    cateView.setAdapter(mCatgrayItemAdapter);

                    LinearLayoutManager manager = new LinearLayoutManager(mActivity , LinearLayoutManager.VERTICAL ,false);
                    cateView.setLayoutManager(manager);
                    if (mDecoration == null) {
                        mDecoration = new ListDecoration(mActivity, ListDecoration.VERTICAL_LIST, R.drawable.list_divide);
                        cateView.addItemDecoration(mDecoration);
                    }
                    break;
                case SALES_SORT:
                    mCatgrayItemAdapter.notifyDataSetChanged();
                    break;
                case PRICE_SORT:
                    mCatgrayItemAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sales = false;
        price = false;
    }
}
