package com.xnliang.yishibao.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;

import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.utils.HandlerBackUtil;
import com.xnliang.yishibao.module.utils.SharedPreferencesHelper;
import com.xnliang.yishibao.presenter.ItmeCallBackListener;
import com.xnliang.yishibao.view.fragment.FoundFragment;
import com.xnliang.yishibao.view.fragment.HomeContainerFragment;
import com.xnliang.yishibao.view.fragment.HomeFragment;
import com.xnliang.yishibao.view.fragment.SelfFragment;
import com.xnliang.yishibao.view.fragment.ShopFragment;
import com.xnliang.yishibao.view.fragment.TravelFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements ItmeCallBackListener {

    private ViewPager mViewPager;
    private Fragment[] mFragments;
    private RadioGroup mHomeGroup;
    private MainActivity mContext;
    private Toolbar toolbar;
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        checkSecurityPermissions();

        setContentView(R.layout.activity_main);
        initView();
        sharedPreferencesHelper = new SharedPreferencesHelper(this, "login");
    }

    public void initView () {

        mViewPager = (ViewPager)findViewById(R.id.pager);

//        Fragment homeFragment = new HomeFragment();
        Fragment containerFragment = new HomeContainerFragment();
        Fragment travelFragment = new TravelFragment();
        Fragment foundFragment = new FoundFragment();
        Fragment shopFragment = new ShopFragment();
        Fragment selfFragment = new SelfFragment();

        mFragments = new Fragment[]{containerFragment,travelFragment,foundFragment,shopFragment,selfFragment};

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        mHomeGroup = findViewById(R.id.home_group);
        mHomeGroup.setOnCheckedChangeListener(new myRadionGroupCheckedChangeListener());

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mHomeGroup.getChildAt(position).performClick();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }

    }

    @Override
    public void gridViewItemClickListener(AdapterView<?> parent, View view, int position, long id) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(1, false);
        }
    }

    @Override
    public void travelBackListener() {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(0, false);
        }
    }

    @Override
    public void ivFoundClickListener() {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(2, false);
        }
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {
            return mFragments[index];
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }

    }

    public class myRadionGroupCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.home_radio_one:
                    mViewPager.setCurrentItem(0,false);
                    break;
                case R.id.home_radio_two:
                    mViewPager.setCurrentItem(1,false);
                    break;
                case R.id.home_radio_three:
                    mViewPager.setCurrentItem(2,false);
                    break;
                case R.id.home_radio_four:
                    mViewPager.setCurrentItem(3,false);
                    break;
                case R.id.home_radio_five:
                    Boolean isLogion = (Boolean) sharedPreferencesHelper.getSharedPreference("isLogin" ,false);
                        if (!isLogion) {
                            Intent intent = new Intent(mContext, LoginAndRegisterActivity.class);
                            startActivity(intent);
                        }
                    mViewPager.setCurrentItem(4,false);
                    break;
            }
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!HandlerBackUtil.handleBackPress(this)) {
            super.onBackPressed();
        }
    }

    private final int CHECK_PERMISSIONS_REQUEST = 10010;

    //检查权限
    private void checkSecurityPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissionForM();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissionForM() {
        String[] PERMISSIONS = new String[]{
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };

        List<String> perList = new ArrayList<>();
        int length = PERMISSIONS.length;
        for (int i = 0; i < length; i++) {
            if (ActivityCompat.checkSelfPermission(this, PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED) {
                perList.add(PERMISSIONS[i]);
            }
        }
        int size = perList.size();
        if (size > 0) {
            String[] permisGroup = (String[]) perList.toArray(new String[size]);
            requestPermissions(permisGroup, CHECK_PERMISSIONS_REQUEST);
        }
    }

    //权限授权结果
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CHECK_PERMISSIONS_REQUEST:
                String str = "";
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//                        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
//                            showDialog();
//                            return;
//                        }
                        finish();
                    }
                }
                break;
        }
    }
}
