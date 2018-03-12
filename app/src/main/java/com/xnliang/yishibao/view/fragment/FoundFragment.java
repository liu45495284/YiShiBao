package com.xnliang.yishibao.view.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baoyz.widget.PullRefreshLayout;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.FoundRecycleViewAdapter;
import com.xnliang.yishibao.module.bean.FoundListBean;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.presenter.FoundLinearLayoutManager;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.UploadPictureActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoundFragment extends BaseFragment implements View.OnClickListener{


    private MainActivity mActivity;
    private View mView;
    private PullRefreshLayout mPullRefreshLayout;
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private static final int SUCCESSFUL = 0;
    private JSONObject mJsonData;
    private JSONObject mListsData;
    private String mTotal;
    private String mPerPage;
    private String mCurrentPage;
    private String mLastPage;
    private static final String mFoundIndex ="http://ysb.appxinliang.cn/api/discover";
    private ArrayList<FoundListBean.DataBeanX.ListsBean> mListsBean = new ArrayList();
    private JSONArray mSlides;
    private LinkedHashMap<String , String> linkedHashMap = new LinkedHashMap<>();
    private LoadMoreListener mListener;
    private FoundFragment mFragment;
    private FoundRecycleViewAdapter mFoundAdapter;
    private ListDecoration mDecoration;
    private FoundLinearLayoutManager manager;
    private int mPage = 1;

    @Bind(R.id.rv_found)
    RecyclerView mRecyclerView;
    @Bind(R.id.bt_send)
    Button mSendButton;

    public FoundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = (MainActivity)getActivity();
        mFragment = this;
        mView = inflater.inflate(R.layout.fragment_found, container, false);
        ButterKnife.bind(this , mView);
        initView();
        initData(mFoundIndex , 1);
        return mView;

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mActivity.setSupportActionBar(toolbar);

        mPullRefreshLayout = mView.findViewById(R.id.fo_swipeRefreshLayout);
        mPullRefreshLayout.setOnRefreshListener(new myRefreshListener());
        mPullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

//        handler.obtainMessage(SUCCESSFUL).sendToTarget();

        mSendButton.setOnClickListener(this);
    }

    public void initData(String url , int page) {
        OkHttpUtils
                .get()
                .url(url)
                .addParams("page" ,String.valueOf(page))
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

    private void processData(String json) {
        mListsBean.clear();

        JSONObject jsonObject = JSON.parseObject(json);

        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");
        mJsonData = jsonObject.getJSONObject("data");
        mListsData = mJsonData.getJSONObject("lists");
        mSlides = mJsonData.getJSONArray("slides");
        mTotal = mListsData.getString("total");
        mPerPage = mListsData.getString("per_page");
        mCurrentPage = mListsData.getString("current_page");
        mLastPage = mListsData.getString("last_page");

        linkedHashMap.put("current" , mCurrentPage);
        linkedHashMap.put("last" , mLastPage);

        handler.obtainMessage(SUCCESSFUL).sendToTarget();

        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(code) == SUCCESSFUL_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESSFUL:
                    if (mFoundAdapter == null){
                        mFoundAdapter = new FoundRecycleViewAdapter(mActivity, linkedHashMap , mFragment);
                        mRecyclerView.setAdapter(mFoundAdapter);
                    }
                    if (mDecoration == null){
                        mDecoration = new ListDecoration(mActivity ,ListDecoration.VERTICAL_LIST , R.drawable.list_divide);
                        mRecyclerView.addItemDecoration(mDecoration);
                    }
                    mRecyclerView.setItemAnimator(null);
                    //recycleView不仅要设置适配器还要设置布局管理者,否则图片不显示
                    //第一个参数是上下文，第二个参数是只有一列
                    manager = new FoundLinearLayoutManager(mActivity);
                    mRecyclerView.setLayoutManager(manager);

                       mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            Log.d("test", "StateChanged = " + newState);
                           int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == mFoundAdapter.getItemCount()) {
                               Log.d("test", "loading executed");
                                mPage++;
                               mListener.loadMore();
                               if (mPage > Integer.valueOf(mLastPage)){
                                   linkedHashMap.put("current" , mPage + "");
                                   mFoundAdapter.notifyItemChanged(2);
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



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible()) {//视图可见并且控件准备好了，每次都会调用
            if (null == mJsonData) {//如果数据为空了，则需要重新联网请求
//                initData(mFoundIndex , 1);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity() , UploadPictureActivity.class);
        startActivityForResult(intent , 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && data != null){
            mListener.refreshNew();
        }
        mPage = 1;
    }

    public class myRefreshListener implements PullRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mListener.pullRefresh();
                    mPullRefreshLayout.setRefreshing(false);
                }
            } , 0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPage = 1;
    }

    public interface LoadMoreListener{
        void loadMore();
        void refreshNew();
        void pullRefresh();
    }

    public void setLoadingMore(LoadMoreListener listener){
        this.mListener = listener;
    }
}
