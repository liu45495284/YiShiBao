package com.xnliang.yishibao.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xnliang.yishibao.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import okhttp3.Call;

/**
 * Created by JackLiu on 2018-02-02.
 */

public class TravelDetailFragment extends BaseFragment implements View.OnClickListener{

    private FragmentManager mFragmentManager;
    private WebView webView;
    private static final String mTravelDetailIndex ="http://ysb.appxinliang.cn/api/tourism/h5/detail";
    private TextView mDetailTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel_detail , container ,false);
        initView(view);
        initWebView(view);
        return view;
    }

    private void initWebView(View view) {
        webView = view.findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }

        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setUserAgentString(webSettings.getUserAgentString());
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);//设置webview显示屏幕宽度 不能滑动

        int id = getActivity().getIntent().getIntExtra("detail" , 0);
        getWebPageFromNet(mTravelDetailIndex ,id);
    }

    private void getWebPageFromNet(String url ,int id ) {
        OkHttpUtils
                .get()
//                .addParams("id" , "id")
                .addParams("id" , "1")
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                        Toast.makeText(getActivity() ,R.string.register_failure ,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==" + response);
                        if (response.isEmpty()){
                            return;
                        }
                        webView.loadDataWithBaseURL(null, response, "text/html", "utf-8", null);
                        Document doc = Jsoup.parse(response);
                        String title = doc.getElementsByTag("title").text();
                        mDetailTitle.setText(title);
                    }
                });
    }

    private void initView(View view) {
        ImageButton detailBack = view.findViewById(R.id.ib_detail_back);
        RelativeLayout relativeLayout = view.findViewById(R.id.rl_detail_back);
        mDetailTitle = view.findViewById(R.id.tv_travel_detail_name);
        Button snapUp = view.findViewById(R.id.bt_snap_up);

//        detailTitle.setText(getActivity().getIntent().getStringExtra("title"));
        relativeLayout.setOnClickListener(this);
        snapUp.setOnClickListener(this);

        mFragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public boolean onBackPressed() {
        getActivity().finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_detail_back:
                getActivity().finish();
                break;
            case R.id.bt_snap_up:
                TravelInviteCodeFragment fragment = new TravelInviteCodeFragment();
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_base ,fragment , "invite" );
                fragmentTransaction.addToBackStack("invite");
                fragmentTransaction.commitAllowingStateLoss();
                break;
        }
    }
}
