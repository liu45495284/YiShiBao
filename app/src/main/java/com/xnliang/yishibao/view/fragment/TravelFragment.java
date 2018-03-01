package com.xnliang.yishibao.view.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import com.baoyz.widget.PullRefreshLayout;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.adapter.TravelRecycleViewAdapter;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.presenter.ItmeCallBackListener;
import com.xnliang.yishibao.presenter.TravelLinearLayoutManager;
import com.xnliang.yishibao.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

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
    private Button mFreeButton;
    private Button mSelfButton;
    private ItmeCallBackListener mListener;
    private RecyclerView mRecyclerView;
    private static final int FREE_HIGH = 0;
    private static final int GOOD_SELF = 1;
    private TextView mChooseCity;

    public TravelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mActivity = (MainActivity)getActivity();
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

//        mChooseCity = mView.findViewById(R.id.tv_city_name);
//        mChooseCity.setOnClickListener(this);
//        ImageButton dropButton = mView.findViewById(R.id.ib_drop);
//        dropButton.setOnClickListener(new DropButtonListener());

        Spinner citySpinner = mView.findViewById(R.id.spinner_city);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity() ,android.R.layout.simple_spinner_item , cityList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(arrayAdapter);

        citySpinner.setOnItemSelectedListener(this);



        mPullRefreshLayout = mView.findViewById(R.id.tr_swipeRefreshLayout);
        mPullRefreshLayout.setOnRefreshListener(new myRefreshListener());
        mPullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        mRecyclerView = mView.findViewById(R.id.rv_travel);
        mTravelRecycleAdapter = new TravelRecycleViewAdapter(mActivity, moduleBeanList,this);
        mRecyclerView.setAdapter(mTravelRecycleAdapter);
        mRecyclerView.addItemDecoration(new ListDecoration(mActivity,ListDecoration.VERTICAL_LIST,R.drawable.list_divide));
        //recycleView不仅要设置适配器还要设置布局管理者,否则图片不显示
        //第一个参数是上下文，第二个参数是只有一列
        TravelLinearLayoutManager manager = new TravelLinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mPullRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });
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
        String city[] = {"上海" ,"北京" ,"深圳" ,"广州","南京","成都"};
        for(String item : city){
            cityList.add(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String str=parent.getItemAtPosition(position).toString();
//        mChooseCity.setText(str);
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
                    mPullRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "更新了1条数据...", Toast.LENGTH_SHORT).show();
                }
            } , 1000);
        }
    }

    public class FreeHighListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mFreeButton.setBackgroundResource(R.drawable.travel_btn_bg);
            mFreeButton.setTextColor(getResources().getColor(R.color.white));
            mSelfButton.setBackgroundResource(R.drawable.travel_btn_bg_normal);
            mSelfButton.setTextColor(getResources().getColor(R.color.black));
            updateDate(FREE_HIGH);
//            handler.obtainMessage(FREE_HIGH).sendToTarget();
            Toast.makeText(mActivity , "1111" , Toast.LENGTH_SHORT).show();
        }
    }

    public class GoodSelfListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mFreeButton.setBackgroundResource(R.drawable.travel_btn_bg_normal);
            mFreeButton.setTextColor(getResources().getColor(R.color.black));
            mSelfButton.setBackgroundResource(R.drawable.travel_btn_bg);
            mSelfButton.setTextColor(getResources().getColor(R.color.white));
            updateDate(GOOD_SELF);
//            handler.obtainMessage(GOOD_SELF).sendToTarget();
            Toast.makeText(mActivity , "2222" , Toast.LENGTH_SHORT).show();
        }
    }

    public class DropButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Toast.makeText(mActivity,"44444" , Toast.LENGTH_SHORT).show();
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
            }
        }
    };

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
