package com.xinliang.yishibao.view;

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
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.xinliang.yishibao.R;
import com.xinliang.yishibao.presenter.ItmeCallBackListener;
import com.xinliang.yishibao.presenter.TravelViewHolder;
import com.xinliang.yishibao.view.fragment.FindFragment;
import com.xinliang.yishibao.view.fragment.HomeFragment;
import com.xinliang.yishibao.view.fragment.SelfFragment;
import com.xinliang.yishibao.view.fragment.ShopFragment;
import com.xinliang.yishibao.view.fragment.TravelFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements ItmeCallBackListener {

    private ViewPager mViewPager;
    private Fragment[] mFragments;
    private RadioGroup mHomeGroup;
    private MainActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        checkSecurityPermissions();

        setContentView(R.layout.activity_main);
        initView();


    }

    public void initView () {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnClickListener(new SearchViewClickListener());

        ImageButton shoppingButton = (ImageButton) findViewById(R.id.shopping_cart);
        shoppingButton.setOnClickListener(new ShopClickListener());

        mViewPager = (ViewPager)findViewById(R.id.pager);

        Fragment homeFragment = new HomeFragment();
        Fragment travelFragment = new TravelFragment();
        Fragment findFragment = new FindFragment();
        Fragment shopFragment = new ShopFragment();
        Fragment selfFragment = new SelfFragment();

        mFragments = new Fragment[]{homeFragment,travelFragment,findFragment,shopFragment,selfFragment};
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,homeFragment).commitAllowingStateLoss();

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
    }

    @Override
    public void gridViewItemClickListener(AdapterView<?> parent, View view, int position, long id) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(1, false);
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
                    mViewPager.setCurrentItem(4,false);
                    break;
            }
        }
    }

    public class SearchViewClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,SearchViewActivity.class);
            startActivity(intent);
        }
    }

    public class ShopClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,ShoppingActivity.class);
            startActivity(intent);
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
                Manifest.permission.READ_EXTERNAL_STORAGE,
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
