package com.xnliang.yishibao.view;

/**
 * Created by JackLiu on 2018-03-13.
 */

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xnliang.yishibao.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import static android.media.AudioRecord.SUCCESS;
import static android.provider.Telephony.ThreadsColumns.ERROR;

/**
 * 大图预览 功能描述：一般我们浏览一个应用，别人发布的状态或新闻都会有很多配图， 我们点击图片时可以浏览大图，这张大图一般可以放大，放大到超过屏幕后
 * 可以移动 需要从activity的Intent传参数过来 传入参数：url 大图下载地址 smallPath 缩略图存在本地的地址
 *
 * @author Administrator
 */
public class PicturePreviewActivity extends BaseActivity {
//    private ZoomImageView zoomView;
    private ImageView zoomView;
    private Context ctx;
    private GestureDetector gestureDetector;
    private RelativeLayout layout;
    private boolean isFile;
    private String TAG = PicturePreviewActivity.class.getSimpleName();
    private static final int ERROR = 1;
    private static final int SUCCESS = 2 ;
    private Message msg;
    private ProgressDialog pb;
    private int widthPixels;
    private int heightPixels;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //不显示系统的标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_picture_preview);
        ctx = this;
        zoomView = findViewById(R.id.picture_preview);
        layout = (RelativeLayout) this.findViewById(R.id.layout_picture_preview);
        /* 大图的下载地址 */
        url = getIntent().getStringExtra("url");

        if (url.startsWith("http:")) {//是网络地址
            this.isFile = false;
        } else {
            this.isFile = true;
        }

        /* 缩略图存储在本地的地址 */
        final String smallPath = getIntent().getStringExtra("smallPath");
        final int identify = getIntent().getIntExtra("indentify", -1);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        widthPixels = metrics.widthPixels;
        heightPixels = metrics.heightPixels;
        File bigPicFile = new File(getLocalPath(url));

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "背景被点击");
                PicturePreviewActivity.this.finish();
            }
        });
        showDialog();
        if (isFile) {
            zoomView.setImageBitmap(zoomBitmap(
                    BitmapFactory.decodeFile(url), widthPixels,
                    heightPixels));
            pb.dismiss();
        } else {

            if (bigPicFile.exists()) {/* 如果已经下载过了,直接从本地文件中读取 */
                zoomView.setImageBitmap(zoomBitmap(
                        BitmapFactory.decodeFile(getLocalPath(url)), widthPixels,
                        heightPixels));
                pb.dismiss();
            } else if (!TextUtils.isEmpty(url)) {
                        if (!isFile)  //从网络读取
                                getBitMapFromUrl(url);

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void recycle() {
        if (zoomView != null && zoomView.getDrawable() != null) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) zoomView
                    .getDrawable();
            if (bitmapDrawable != null && bitmapDrawable.getBitmap() != null
                    && !bitmapDrawable.getBitmap().isRecycled())

            {
                bitmapDrawable.getBitmap().recycle();
            }
        }
    }


    public void getBitMapFromUrl(final String url) {
        Bitmap bitmap = null;

        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new BitmapCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        Message msg = Message.obtain();
                        msg.what = SUCCESS;
                        msg.obj = response;
                        handler.sendMessage(msg);
                    }

                });

    }

    public void showDialog(){
        if (pb == null){
            pb = new ProgressDialog(this);
        }
        pb.setMessage(getResources().getString(R.string.found_loading));
        pb.setCancelable(false);
        pb.show();
    }

    public void showSaveDialog(){
        if (pb == null){
            pb = new ProgressDialog(this);
        }
        pb.setMessage(getResources().getString(R.string.found_saving));
        pb.setCancelable(false);
        pb.show();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESS:
                    final Bitmap bitmap = (Bitmap) msg.obj;
                    zoomView.setImageBitmap(bitmap);
                    if (bitmap != null) {
                            zoomView.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    showSaveDialog();
                                    savePhotoToSDCard(
                                            zoomBitmap(bitmap, widthPixels, heightPixels),
                                            getLocalPath(url));
                                    return false;
                                }
                            });

                        }
                    pb.dismiss();
                    break;

                case ERROR:
                    Toast.makeText(PicturePreviewActivity.this, "请求超时", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * Resize the bitmap
     *
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        if (bitmap == null)
            return bitmap;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        if (scaleWidth < scaleHeight) {
            matrix.postScale(scaleWidth, scaleWidth);
        } else {
            matrix.postScale(scaleHeight, scaleHeight);
        }
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    public static String getLocalPath(String url) {
        String fileName = "temp.png";
        if (url != null) {
            if (url.contains("/")) {
                fileName = url
                        .substring(url.lastIndexOf("/") + 1, url.length());
            }
            if (fileName != null && fileName.contains("&")) {
                fileName = fileName.replaceAll("&", "");
            }
            if (fileName != null && fileName.contains("%")) {
                fileName = fileName.replaceAll("%", "");
            }
        }
        return Environment.getExternalStorageDirectory() + "/yishibao/PracticeImage/" + fileName;
    }


    public void savePhotoToSDCard(Bitmap photoBitmap, String fullPath) {
        if (checkSDCardAvailable()) {
            File file = new File(fullPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            File photoFile = new File(fullPath);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
                            fileOutputStream)) {
                        fileOutputStream.flush();
                    }
                }
            } catch (FileNotFoundException e) {
                photoFile.delete();
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                        pb.dismiss();
                    }
                    // if (photoBitmap != null && !photoBitmap.isRecycled()) {
                    // photoBitmap.recycle();
                    // }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean checkSDCardAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }


    public void closeActivity() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recycle();
    }
}
