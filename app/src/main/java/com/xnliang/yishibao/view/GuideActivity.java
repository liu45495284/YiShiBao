package com.xnliang.yishibao.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xnliang.yishibao.R;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.indicator.ZoomIndicator;
import com.zhengsr.viewpagerlib.view.GlideViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackLiu on 2018-01-26.
 */

public class GuideActivity extends BaseActivity {

    private TextView tvTime;
    private MyCountDownTimer myCountDownTimer;
    private static final int[] RES = {R.mipmap.guide1,R.mipmap.guide2,R.mipmap.guide3 };
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        initView();
    }

    public void initView (){
        GlideViewPager viewPager = (GlideViewPager) findViewById(R.id.splase_viewpager);
        ZoomIndicator zoomIndicator = (ZoomIndicator) findViewById(R.id.splase_bottom_layout);
        button = (Button) findViewById(R.id.splase_start_btn);
        tvTime = (TextView) this.findViewById(R.id.tv_time);

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this , MainActivity.class);
                startActivity(intent);
                myCountDownTimer.cancel();
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this , MainActivity.class);
                startActivity(intent);
                myCountDownTimer.cancel();
                finish();
            }
        });


        //先把本地的图片 id 装进 list 容器中
        List<Integer> images = new ArrayList<>();
        for (int i = 0; i < RES.length; i++) {
            images.add(RES[i]);

        }
        //配置pagerbean，这里主要是为了viewpager的指示器的作用，注意记得写上泛型
        PageBean bean = new PageBean.Builder<Integer>()
                .setDataObjects(images)
                .setIndicator(zoomIndicator)
                .setOpenView(button)
                .builder();

        // 把数据添加到 viewpager中，并把view提供出来，这样除了方便调试，也不会出现一个view，多个
        // parent的问题，这里在轮播图比较明显
        viewPager.setPageListener(bean, R.layout.image_layout, new PageHelperListener() {
            @Override
            public void getItemView(View view, Object data) {
                //通过获取到这个view，你可以随意定制你的内容
                ImageView imageView = view.findViewById(R.id.icon);
                imageView.setImageResource((Integer) data);
            }
        });

        myCountDownTimer=new MyCountDownTimer(5000,1000);
        //开始倒计时
        myCountDownTimer.start();
    }

    class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String skip = String.format(getResources().getString(R.string.guide_skip),  millisUntilFinished/1000);
            tvTime.setText(skip+"s");
        }

        @Override
        public void onFinish() {
            myCountDownTimer.cancel();
            Intent intent = new Intent(GuideActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myCountDownTimer.cancel();
    }
}
