package com.xnliang.yishibao.view.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
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
import com.alibaba.fastjson.TypeReference;
import com.baoyz.widget.PullRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.TravelRecycleViewAdapter;
import com.xnliang.yishibao.module.bean.TravelListBean;
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
    private ArrayList  dataBean = new ArrayList();
    private int mPage = 1;
    private ArrayList<TravelListBean.DataBeanX.DataBean> mData;

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
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible()) {//视图可见并且控件准备好了，每次都会调用
            if (null == mIndexData) {//如果数据为空了，则需要重新联网请求
                getDataFromNet(mTravelAreaIndex);
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
        dataBean.clear();
        JSONObject jsonObject = JSON.parseObject(response);

        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");
        mJsonData = jsonObject.getJSONObject("data");
        mTotal = mJsonData.getString("total");
        mPerPage = mJsonData.getString("per_page");
        mCurrentPage = mJsonData.getString("current_page");
        mLastPage = mJsonData.getString("last_page");
        mItemData = mJsonData.getJSONArray("data");


//        Gson gson = new Gson();
//        ArrayList<TravelListBean.DataBeanX.DataBean> dataBean = gson.fromJson(mJsonData.getString("data") ,
//                new TypeToken<ArrayList<TravelListBean.DataBeanX.DataBean>>(){}.getType());

        mData = JSON.parseObject(mJsonData.getString("data") ,
                new TypeReference<ArrayList<TravelListBean.DataBeanX.DataBean>>(){}.getType());


        for(int i = 0; i< mData.size() ; i++){
            dataBean.add(mData.get(i));
        }

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
        mSelectedPosition = position + 1;
        moduleBeanList.clear();
        getListDataFromNet(mTravelListIndex ,1 ,mTypeFlag ,mSelectedPosition);
//        Toast.makeText(getActivity(), "你点击的是:"+str, Toast.LENGTH_SHORT).show();

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
//                    if (mPage == 1){
//                        Toast.makeText(getActivity(), R.string.refresh_no_more, Toast.LENGTH_SHORT).show();
//                    }else {
//                        getListDataFromNet(mTravelListIndex, 1, mTypeFlag, mSelectedPosition);
//                        mPage--;
//                        Toast.makeText(getActivity(), R.string.refresh_complete, Toast.LENGTH_SHORT).show();
//                    }
                    mPullRefreshLayout.setRefreshing(false);
                }
            } , 3000);
        }
    }

    public class FreeHighListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mTypeFlag = 1;
            mPage = 1;
            mFreeButton.setBackgroundResource(R.drawable.travel_btn_bg);
            mFreeButton.setTextColor(getResources().getColor(R.color.white));
            mSelfButton.setBackgroundResource(R.drawable.travel_btn_bg_normal);
            mSelfButton.setTextColor(getResources().getColor(R.color.black));
            moduleBeanList.clear();
            getListDataFromNet(mTravelListIndex ,1 ,mTypeFlag ,mSelectedPosition);
//            handler.obtainMessage(FREE_HIGH).sendToTarget();
        }
    }

    public class GoodSelfListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mTypeFlag = 2;
            mPage = 1;
            mFreeButton.setBackgroundResource(R.drawable.travel_btn_bg_normal);
            mFreeButton.setTextColor(getResources().getColor(R.color.black));
            mSelfButton.setBackgroundResource(R.drawable.travel_btn_bg);
            mSelfButton.setTextColor(getResources().getColor(R.color.white));
            moduleBeanList.clear();
            getListDataFromNet(mTravelListIndex ,1 ,mTypeFlag ,mSelectedPosition);
//            handler.obtainMessage(GOOD_SELF).sendToTarget();
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
                    moduleBeanList.addAll(dataBean);
                    if(mRecyclerView == null) {
                        mRecyclerView = mView.findViewById(R.id.rv_travel);
                    }
                    if(mTravelRecycleAdapter == null) {
                        mTravelRecycleAdapter = new TravelRecycleViewAdapter(mActivity, moduleBeanList, mFragment);
                        mRecyclerView.setAdapter(mTravelRecycleAdapter);
                    }
                    if (mDecoration == null) {
                        mDecoration = new ListDecoration(mActivity,ListDecoration.VERTICAL_LIST, R.drawable.list_divide);
                        mRecyclerView.addItemDecoration(mDecoration);
                    }
                    //recycleView不仅要设置适配器还要设置布局管理者,否则图片不显示
                    //第一个参数是上下文，第二个参数是只有一列
                    final TravelLinearLayoutManager manager = new TravelLinearLayoutManager(mActivity);
                    mRecyclerView.setLayoutManager(manager);

                    int position = mTravelRecycleAdapter.getItemCount();
                    ((SimpleItemAnimator)mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                    mTravelRecycleAdapter.notifyItemRangeInserted( position - dataBean.size(), dataBean.size());
                    mRecyclerView.scrollToPosition(position - dataBean.size());

                    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            Log.d("test", "StateChanged = " + newState);
                            int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == mTravelRecycleAdapter.getItemCount()) {
                                Log.d("test", "loading executed");

                                if (!isLoadingMore) {

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            int currentPage = Integer.valueOf(mCurrentPage);
                                            int lastPage = Integer.valueOf(mLastPage);
                                              if( currentPage >= lastPage ){
                                                isLoadingMore = false;
                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(getActivity() , R.string.refresh_no_more ,Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                                Log.d(TAG,"ignore manually update!");
                                                return;
                                            }

                                                mPage++;
                                                loadMore(mPage);
                                                Log.d("test", "load more completed");
                                                isLoadingMore = false;
                                        }
                                    }, 1000);
                                }
                            }
                        }

                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            int topRowVerticalPosition =
                                    (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                            mPullRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
                        }
                    });

                    break;
            }
        }
    };

    private void loadMore(int i) {
        isLoadingMore = true;
        getListDataFromNet(mTravelListIndex , i,mTypeFlag ,mSelectedPosition);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dataBean.clear();
    }
}
