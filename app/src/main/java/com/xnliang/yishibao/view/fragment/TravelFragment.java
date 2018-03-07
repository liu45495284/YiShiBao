package com.xnliang.yishibao.view.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baoyz.widget.PullRefreshLayout;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.TravelRecycleViewAdapter;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.presenter.ItmeCallBackListener;
import com.xnliang.yishibao.presenter.TravelLinearLayoutManager;
import com.xnliang.yishibao.view.MainActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class TravelFragment extends BaseFragment implements View.OnClickListener , AdapterView.OnItemSelectedListener {

    private MainActivity mActivity;
    private View mView;
    private PullRefreshLayout mPullRefreshLayout;
    private TravelRecycleViewAdapter mTravelRecycleAdapter;
    public List moduleBeanList = new ArrayList();
    public List freeList = new ArrayList();
    public List highList = new ArrayList();
    public List cityList = new ArrayList();
    public List cityIdList = new ArrayList();
    private Button mFreeButton;
    private Button mSelfButton;
    private ItmeCallBackListener mListener;
    private RecyclerView mRecyclerView;
    private static final int FREE_HIGH = 0;
    private static final int GOOD_SELF = 1;
    private static final int SUCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private JSONArray mIndexData;
    private JSONArray mItemData;
    private static final String mTravelAreaIndex ="http://ysb.appxinliang.cn/api/tourism/area";
    private static final String mTravelListIndex ="http://ysb.appxinliang.cn/api/tourism/lists";
    private static final String mTravelDetailIndex ="http://ysb.appxinliang.cn/api/tourism/detail";
    private static final int AREAS = 2;
    private static final int ITEM_LIST = 3;
    private static int mTypeFlag = 1 ;
    private TravelFragment mFragment;
    private int mSelectedPosition;
    private ListDecoration mDecoration;
    private String mTotal;
    private String mPerPage;
    private String mCurrentPage;
    private String mLastPage;
    private boolean isLoadingMore;
    private JSONObject mJsonData;

    public TravelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mActivity = (MainActivity)getActivity();
        mFragment = this;
        mView = inflater.inflate(R.layout.fragment_travel, container, false);
        initDate();
        initView();
        setBackListener(mActivity);
        return mView;
    }

    public void initView() {
        Toolbar toolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mActivity.setSupportActionBar(toolbar);
        ImageButton back = mView.findViewById(R.id.ib_back);
        back.setOnClickListener(new backListener());

        mFreeButton = mView.findViewById(R.id.free_high);
        mSelfButton = mView.findViewById(R.id.good_self);
        mFreeButton.setOnClickListener(new FreeHighListener());
        mFreeButton.setBackgroundResource(R.drawable.travel_btn_bg);
        mFreeButton.setTextColor(getResources().getColor(R.color.white));
        mSelfButton.setOnClickListener(new GoodSelfListener());
        mSelfButton.setBackgroundResource(R.drawable.travel_btn_bg_normal);



        mPullRefreshLayout = mView.findViewById(R.id.tr_swipeRefreshLayout);
        mPullRefreshLayout.setOnRefreshListener(new myRefreshListener());
        mPullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);


    }

    public void initDate() {
        moduleBeanList.clear();
        freeList.clear();
        highList.clear();
        int itemFree[] = { R.mipmap.i3, R.mipmap.i3, R.mipmap.i3,R.mipmap.i3, R.mipmap.i3,
                R.mipmap.i3,R.mipmap.i3, R.mipmap.i3, R.mipmap.i3,R.mipmap.i3, R.mipmap.i3, R.mipmap.i3};
        int itemHigh[] = { R.mipmap.i4, R.mipmap.i4,R.mipmap.i4, R.mipmap.i4,R.mipmap.i4,
                R.mipmap.i4,R.mipmap.i4, R.mipmap.i4,R.mipmap.i4};

        for (int i = 0 ; i < itemFree.length ; i++) {
            freeList.add(itemFree[i]);
        }

        for (int i = 0 ; i < itemHigh.length ; i++) {
            highList.add(itemHigh[i]);
        }

        for (int i =0 ; i < freeList.size() ; i++){
            moduleBeanList.add(freeList.get(i));
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible()) {//视图可见并且控件准备好了，每次都会调用
            if (null == mIndexData) {//如果数据为空了，则需要重新联网请求
                getDataFromNet(mTravelAreaIndex);
                getListDataFromNet(mTravelListIndex ,1 ,1 ,1);
            }
        }
    }

    public void getDataFromNet(String url){
        OkHttpUtils
                .get()
                .url(url)
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

    public void getListDataFromNet(String url , int page , int type , int id){
        OkHttpUtils
                .get()
                .url(url)
                .addParams("page" ,String.valueOf(page))
                .addParams("type" ,String.valueOf(type))
                .addParams("area" ,String.valueOf(id))
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
                        processCityData(response);
                    }
                });
    }

    private void processCityData(String response) {
        JSONObject jsonObject = JSON.parseObject(response);

        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");
        mJsonData = jsonObject.getJSONObject("data");
        mTotal = mJsonData.getString("total");
        mPerPage = mJsonData.getString("per_page");
        mCurrentPage = mJsonData.getString("current_page");
        mLastPage = mJsonData.getString("last_page");
        mItemData = mJsonData.getJSONArray("data");
        handler.obtainMessage(ITEM_LIST).sendToTarget();

        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(code) == SUCESSFUL_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void processData(String json) {
        JSONObject jsonObject = JSON.parseObject(json);

        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");
        mIndexData = jsonObject.getJSONArray("data");
        handler.obtainMessage(AREAS).sendToTarget();

        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(code) == SUCESSFUL_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String str=parent.getItemAtPosition(position).toString();
//        mChooseCity.setText(str);
        mSelectedPosition = position + 1;
        getListDataFromNet(mTravelListIndex ,1 ,1 ,mSelectedPosition);
        Toast.makeText(getActivity(), "你点击的是:"+str, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class myRefreshListener implements PullRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getListDataFromNet(mTravelListIndex ,1 ,mTypeFlag ,mSelectedPosition);
                    mPullRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "更新了1条数据...", Toast.LENGTH_SHORT).show();
                }
            } , 3000);
        }
    }

    public class FreeHighListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mTypeFlag = 1;
            mFreeButton.setBackgroundResource(R.drawable.travel_btn_bg);
            mFreeButton.setTextColor(getResources().getColor(R.color.white));
            mSelfButton.setBackgroundResource(R.drawable.travel_btn_bg_normal);
            mSelfButton.setTextColor(getResources().getColor(R.color.black));
            getListDataFromNet(mTravelListIndex ,1 ,mTypeFlag ,mSelectedPosition);
            updateDate(FREE_HIGH);
//            handler.obtainMessage(FREE_HIGH).sendToTarget();
            Toast.makeText(mActivity , "1111" , Toast.LENGTH_SHORT).show();
        }
    }

    public class GoodSelfListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mTypeFlag = 2;
            mFreeButton.setBackgroundResource(R.drawable.travel_btn_bg_normal);
            mFreeButton.setTextColor(getResources().getColor(R.color.black));
            mSelfButton.setBackgroundResource(R.drawable.travel_btn_bg);
            mSelfButton.setTextColor(getResources().getColor(R.color.white));
            getListDataFromNet(mTravelListIndex ,1 ,mTypeFlag ,mSelectedPosition);
            updateDate(GOOD_SELF);
//            handler.obtainMessage(GOOD_SELF).sendToTarget();
            Toast.makeText(mActivity , "2222" , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tv_city_name:
//                break;
        }
    }

    public class backListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (mListener != null){
                mListener.travelBackListener();
            }
        }
    }

    public void setBackListener(ItmeCallBackListener listener){
        mListener = listener;
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case FREE_HIGH:
                    mTravelRecycleAdapter.notifyDataSetChanged();
                    break;
                case GOOD_SELF:
                    mTravelRecycleAdapter.notifyDataSetChanged();
                    break;
                case AREAS:
                    cityList.clear();
                    cityIdList.clear();
                    for(int i=0 ; i < mIndexData.size() ;i++){
                        cityList.add(mIndexData.getJSONObject(i).getString("name"));
                        cityIdList.add(mIndexData.getJSONObject(i).getString("id"));
                    }
                    Spinner citySpinner = mView.findViewById(R.id.spinner_city);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity() ,android.R.layout.simple_spinner_item , cityList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    citySpinner.setAdapter(arrayAdapter);
                    citySpinner.setOnItemSelectedListener(mFragment);
                    break;
                case ITEM_LIST:
                    if(mRecyclerView == null) {
                        mRecyclerView = mView.findViewById(R.id.rv_travel);
                    }
                    if(mTravelRecycleAdapter == null) {
                        mTravelRecycleAdapter = new TravelRecycleViewAdapter(mActivity, mItemData     , mFragment);
                    }
                    mRecyclerView.setAdapter(mTravelRecycleAdapter);
                    if (mDecoration == null) {
                        mDecoration = new ListDecoration(mActivity,ListDecoration.VERTICAL_LIST, R.drawable.list_divide);
                        mRecyclerView.addItemDecoration(mDecoration);
                    }
                    //recycleView不仅要设置适配器还要设置布局管理者,否则图片不显示
                    //第一个参数是上下文，第二个参数是只有一列
                    final TravelLinearLayoutManager manager = new TravelLinearLayoutManager(mActivity);
                    mRecyclerView.setLayoutManager(manager);

                    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            int topRowVerticalPosition =
                                    (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                            mPullRefreshLayout.setEnabled(topRowVerticalPosition >= 0);

                            int lastVisibleItem = manager.findLastVisibleItemPosition();
                            int totalItemCount = manager.getItemCount();
                            int currentDataCount = mItemData.size();
                            //lastVisibleItem >= totalItemCount - 3 表示剩下3个item自动加载，各位自由选择
                            // dy>0 表示向下滑动
                                if (!isLoadingMore && Integer.valueOf(mTotal) > currentDataCount && dy > 0 && totalItemCount < lastVisibleItem + 5){
                                    if(Integer.valueOf(mCurrentPage).equals(Integer.valueOf(mLastPage))){
                                        isLoadingMore = true;
                                        Toast.makeText(getActivity() , R.string.refresh_no_more ,Toast.LENGTH_SHORT).show();
                                        Log.d(TAG,"ignore manually update!");
                                    }else{
//                                        loadMore();
                                        isLoadingMore = false;
                                    }
                                }
                        }
                    });

                    break;
            }
        }
    };

    private void loadMore() {
        int page = Integer.valueOf(mCurrentPage);

        getListDataFromNet(mTravelListIndex , 1,mTypeFlag ,mSelectedPosition);
    }

    public void runOnUiThread() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTravelRecycleAdapter.notifyItemRangeInserted(0,moduleBeanList.size());
            }
        });}

    public void updateDate(int flag) {

        moduleBeanList.clear();
        switch (flag){
            case FREE_HIGH:
                mTravelRecycleAdapter.notifyItemRangeRemoved(0,highList.size());
                moduleBeanList.addAll(freeList);
                break;
            case GOOD_SELF:
                mTravelRecycleAdapter.notifyItemRangeRemoved(0,freeList.size());
                moduleBeanList.addAll(highList);
                break;
        }
        runOnUiThread();
    }
}
