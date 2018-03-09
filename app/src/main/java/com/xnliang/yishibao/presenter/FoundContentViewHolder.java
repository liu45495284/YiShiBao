package com.xnliang.yishibao.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.FoundItemRecycleViewAdapter;
import com.xnliang.yishibao.module.adapter.FoundRecycleViewAdapter;
import com.xnliang.yishibao.module.bean.FoundListBean;
import com.xnliang.yishibao.module.utils.DividerGridItemDecoration;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.view.MainActivity;
import com.xnliang.yishibao.view.fragment.FoundFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

/**
 * Created by JackLiu on 2018-02-05.
 */

public class FoundContentViewHolder extends BaseViewHolder implements FoundFragment.LoadMoreListener{

    private final Context mContext;
    private final RecyclerView mItemView;
    private ListDecoration mListDecoration;
    private FoundItemRecycleViewAdapter mItemAdapter;
    private int count = 1;
    private LinearLayoutManager manager;
    private static final String mFoundIndex ="http://ysb.appxinliang.cn/api/discover";
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private static final int SUCCESSFUL = 0;
    private JSONObject mJsonData;
    private JSONObject mListsData;
    private String mTotal;
    private String mPerPage;
    private String mCurrentPage;
    private String mLastPage;
    private boolean isLoadingMore;
    private int mPage = 1;
    private MainActivity mActivity;
    private ArrayList<FoundListBean.DataBeanX.ListsBean.DataBean> mDatasBean = new ArrayList();
    private List<FoundListBean.DataBeanX.ListsBean.DataBean> datasBean = new ArrayList();
    private LinkedHashMap<String , List> linkedHashMap = new LinkedHashMap<>();

    public FoundContentViewHolder(Context context, View itemView , FoundFragment foundFragment) {
        super(itemView);
        this.mContext = context;
        this.mActivity = (MainActivity) context;
        mItemView = itemView.findViewById(R.id.rv_found_list);
        foundFragment.setLoadingMore(this);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESSFUL:
                    //1.已有数据
                    //2.设置适配器：-->设置文本和recycleView的数据
                    if (mItemAdapter == null){
                        mItemAdapter = new FoundItemRecycleViewAdapter(mContext,linkedHashMap);
                        mItemView.setAdapter(mItemAdapter);
                    }

                    if(mListDecoration == null){
                        mListDecoration = new ListDecoration(mContext,ListDecoration.VERTICAL_LIST, R.drawable.list_divide);
                        mItemView.addItemDecoration(mListDecoration);
                        manager = new LinearLayoutManager(mContext ,LinearLayoutManager.VERTICAL ,false);
                        mItemView.setLayoutManager(manager);
                    }

                    if (Integer.valueOf(mCurrentPage) > 1) {
                        int position = mItemAdapter.getItemCount();
                        mItemAdapter.notifyItemRangeChanged(position - mDatasBean.size() , mDatasBean.size());
//                        mItemAdapter.notifyDataSetChanged();
                        mItemView.scrollToPosition(position);
                    }

                    mItemView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            Log.d("test", "StateChanged = " + newState);
                            int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == mItemAdapter.getItemCount()) {
                                Log.d("test", "loading executed");


                            }
                        }

                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        }
                    });
                    break;
            }
        }
    };

    public void setData() {
        initData(mFoundIndex , 1);
    }

    private void loadMore(int i) {
        isLoadingMore = true;
        initData(mFoundIndex , i);
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
        mDatasBean.clear();

        JSONObject jsonObject = JSON.parseObject(json);

        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");
        mJsonData = jsonObject.getJSONObject("data");
        mListsData = mJsonData.getJSONObject("lists");
        mTotal = mListsData.getString("total");
        mPerPage = mListsData.getString("per_page");
        mCurrentPage = mListsData.getString("current_page");
        mLastPage = mListsData.getString("last_page");


        mDatasBean = JSON.parseObject(mJsonData.getJSONObject("lists").getString("data"),
                new TypeReference<ArrayList<FoundListBean.DataBeanX.ListsBean.DataBean>>(){}.getType());

        for (int i = 0; i < mDatasBean.size(); i++ ){
            datasBean.add(mDatasBean.get(i));
        }
        linkedHashMap.put("data" , datasBean);


        handler.obtainMessage(SUCCESSFUL).sendToTarget();

        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(code) == SUCCESSFUL_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loadMore() {
        if (!isLoadingMore) {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int currentPage = Integer.valueOf(mCurrentPage);
                    int lastPage = Integer.valueOf(mLastPage);
                    if( currentPage >= lastPage ){
                        isLoadingMore = false;
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mActivity , R.string.refresh_no_more ,Toast.LENGTH_SHORT).show();
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
