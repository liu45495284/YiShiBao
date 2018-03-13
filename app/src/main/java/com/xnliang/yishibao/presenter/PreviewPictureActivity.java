//package com.xnliang.yishibao.presenter;
//
//import com.xnliang.yishibao.R;
//import com.xnliang.yishibao.view.BaseActivity;
//import android.os.Bundle;
//import android.app.Activity;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.util.DisplayMetrics;
//import android.view.Display;
//import android.view.MotionEvent;
//import android.widget.ImageView;
///**
// * Created by JackLiu on 2018-03-13.
// */
//
//public class PreviewPictureActivity extends BaseActivity {
//    /** Called when the activity is first created. */
//    Bitmap bp=null;
//    ImageView imageview;
//    float scaleWidth;
//    float scaleHeight;
//
//    int h;
//    boolean num=false;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        DisplayMetrics dm=new DisplayMetrics();//创建矩阵
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        imageview=(ImageView)findViewById(R.id.picture_preview);
//        bp=BitmapFactory.decodeResource(getResources(),R.drawable.xiaoyua);
//        int width=bp.getWidth();
//        int height=bp.getHeight();
//        int w=dm.widthPixels; //得到屏幕的宽度
//        int h=dm.heightPixels; //得到屏幕的高度
//        scaleWidth=((float)w)/width;
//        scaleHeight=((float)h)/height;
//        imageview.setImageBitmap(bp);
//    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        switch(event.getAction()){
//
//            case MotionEvent.ACTION_DOWN:  //当屏幕检测到第一个触点按下之后就会触发到这个事件。
//                if(num==true)        {
//                    Matrix matrix=new Matrix();
//                    matrix.postScale(scaleWidth,scaleHeight);
//
//                    Bitmap newBitmap=Bitmap.createBitmap(bp, 0, 0, bp.getWidth(), bp.getHeight(), matrix, true);
//                    imageview.setImageBitmap(newBitmap);
//                    num=false;
//                }
//                else{
//                    Matrix matrix=new Matrix();
//                    matrix.postScale(1.0f,1.0f);
//                    Bitmap newBitmap=Bitmap.createBitmap(bp, 0, 0, bp.getWidth(), bp.getHeight(), matrix, true);
//                    imageview.setImageBitmap(newBitmap);
//                    num=true;
//                }
//                break;
//        }
//
//
//        return super.onTouchEvent(event);
//    }
//}
