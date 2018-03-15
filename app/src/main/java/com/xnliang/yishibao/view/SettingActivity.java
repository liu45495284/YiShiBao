package com.xnliang.yishibao.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.utils.SharedPreferencesHelper;
import com.xnliang.yishibao.view.fragment.MyModifyFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by JackLiu on 2018-02-26.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.rl_setting_back)
    RelativeLayout mSettingBack;
    @Bind(R.id.rl_modify_password)
    RelativeLayout mModifyLayout;
    @Bind(R.id.rl_exit)
    RelativeLayout mExit;

    private SharedPreferencesHelper sharedPreferencesHelper;
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private static final int EXIT_SUCCESSFUL = 0;

    private static final String exitIndex = "http://ysb.appxinliang.cn/api/user/logout";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_setting);
        ButterKnife.bind(this);
        sharedPreferencesHelper = new SharedPreferencesHelper(this, "login");
        initView();
    }

    private void initView() {
        mSettingBack.setOnClickListener(this);
        mModifyLayout.setOnClickListener(this);
        mExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_setting_back:
                finish();
                break;
            case R.id.rl_modify_password:
                Bundle bundle = new Bundle();
                bundle.putInt("pos" , 6 );
                Intent intent = new Intent(this , SelfListActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.rl_exit:
                boolean isLogion = (boolean) sharedPreferencesHelper.getSharedPreference("isLogin" ,false);
                if(!isLogion){
                    return;
                }
                exitLogin(exitIndex);
                break;
        }
    }

    private void exitLogin(String url) {
        String token = sharedPreferencesHelper.getSharedPreference("token" ,"").toString();
        OkHttpUtils
                .get()
                .url(url)
                .addHeader("XX-Token" , token)
                .addHeader("XX-Device-Type" , "android")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==" + response);

                        getData(response);
                    }
                });
    }

    private void getData(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");

        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(code) == SUCCESSFUL_CODE) {
            handler.obtainMessage(EXIT_SUCCESSFUL).sendToTarget();
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case EXIT_SUCCESSFUL:
                    Intent intent = new Intent(SettingActivity.this ,MainActivity.class);
                    intent.putExtra("exit" , true);
                    startActivity(intent);
                    sharedPreferencesHelper.put("isLogin" ,false);
                    finish();
                    break;
            }
        }
    };
}
