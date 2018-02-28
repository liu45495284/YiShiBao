package com.xnliang.yishibao.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.view.fragment.LoginFragment;

/**
 * Created by JackLiu on 2018-02-28.
 */

public class LoginAndRegisterActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_container);
        initView();
    }

    private void initView() {
        LoginFragment loginFragment = new LoginFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.login_container , loginFragment ,"login");
        transaction.addToBackStack("login");
        transaction.commit();
    }
}
