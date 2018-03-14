package com.xnliang.yishibao.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xnliang.yishibao.R;
import com.xnliang.yishibao.module.utils.DividerGridItemDecoration;
import com.xnliang.yishibao.module.utils.ListDecoration;
import com.xnliang.yishibao.module.utils.SharedPreferencesHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by JackLiu on 2018-03-12.
 */

public class UploadPictureActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.rv_upload_picture)
    RecyclerView mUploadPicture;
    @Bind(R.id.iv_plus_picture)
    ImageView mPlusPicture;
    @Bind(R.id.et_upload_text)
    EditText mUploadText;
    @Bind(R.id.bt_upload)
    Button mUpload;

    private List<String> picNum = new ArrayList();
    private List<String> uploadPicName = new ArrayList();
    private List<File> uploadPicFile = new ArrayList();
    private LayoutInflater mLayoutInflater;
    private ListDecoration mDecoration;
    private GridLayoutManager mManager;
    private static final int GALLERY_REQUEST_CODE = 0;    // 相册选图标记
    private static final int CAMERA_REQUEST_CODE = 1;    // 相机拍照标记
    private static final int SUCCESSFUL_CODE = 200;
    private static final int FAILURE_CODE = 10001;
    private static final int MAX_UPLOAD = 9;
    private MyAdapter mUploadAdapter;
    private UploadPictureActivity mActivity;
    private static final String mAddUrl = "http://ysb.appxinliang.cn/api/discover/add";
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.upload_picture);
        ButterKnife.bind(this);
        sharedPreferencesHelper = new SharedPreferencesHelper(this, "login");

        initView();
    }

    private void initView() {

        mUploadAdapter = new MyAdapter(picNum);
        mUploadPicture.setAdapter(mUploadAdapter);
        if(mDecoration == null) {
            mDecoration = new ListDecoration(this,ListDecoration.VERTICAL_LIST ,R.drawable.list_divide_transparent);
            mUploadPicture.addItemDecoration(mDecoration);
        }

        if(mManager == null) {
            mManager = new GridLayoutManager(this, 4);
            mUploadPicture.setLayoutManager(mManager);
        }

        mPlusPicture.setOnClickListener(this);
        mUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_plus_picture:
                if(uploadPicFile.size() > MAX_UPLOAD){
                    Toast.makeText(mActivity, R.string.found_max_upload, Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(pickIntent, GALLERY_REQUEST_CODE);
                break;
            case R.id.bt_upload:
                upload(mAddUrl);
                break;
        }
    }

    private void upload(String url) {
        final ProgressDialog pb= new ProgressDialog(this);

        pb.setMessage(getResources().getString(R.string.found_loading));
        pb.setCancelable(false);
        pb.show();

        String token = sharedPreferencesHelper.getSharedPreference("token" ,"").toString();

        PostFormBuilder builder = OkHttpUtils.post();
        builder.url(url);
        for (int i = 0 ; i < uploadPicFile.size() ; i++){
            if (!uploadPicFile.get(i).exists()) {
                Toast.makeText(mActivity ,R.string.found_no_file ,Toast.LENGTH_SHORT).show();
                return;
            }
            builder.addFile("discover[]" , uploadPicName.get(i) ,uploadPicFile.get(i));
        }
        builder.addHeader("XX-Token" , token)
                .addHeader("XX-Device-Type" , "android")
                .addParams("content" ,mUploadText.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==" + response);
                        if (response.isEmpty()){
                            return;
                        }
                        processData(response);
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        int i = Math.round(progress);
                        if(i == 1){
                            pb.dismiss();
                            finish();
                        }
                        Log.e("TAG" , "progress = " + progress);
                    }

                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);

                    }
                });
    }

    private void processData(String response) {
        JSONObject jsonObject = JSON.parseObject(response);

        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");

        if (Integer.parseInt(code) == FAILURE_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(code) == SUCCESSFUL_CODE) {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK ) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            picNum.add(picturePath);
            int positionLine = picturePath.lastIndexOf("/");
            String pictureName = picturePath.substring(positionLine + 1);
            File file = new File(picturePath);
            uploadPicName.add(pictureName);
            uploadPicFile.add(file);
            mUploadAdapter.notifyItemRangeChanged(0 ,uploadPicFile.size());
            cursor.close();


        }
        }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        private ImageView mImageView;
         public MyViewHolder(View itemView) {
             super(itemView);
             mImageView = itemView.findViewById(R.id.iv_upload_item_picture);
             itemView.setOnLongClickListener(this);
         }

         public void setData(List list , int position) {
             if (list.size() == 0){
                 return;
             }
             RequestOptions options = new RequestOptions()
                     .centerCrop()
                     .override(250 ,250);

             Glide.with(getApplicationContext())
                     .load(list.get(position))
                     .apply(options)
                     .into(mImageView);
         }

        @Override
        public boolean onLongClick(View v) {
            showRemoveDialog();
            return false;
        }

        private void showRemoveDialog() {
            final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle(R.string.remove_goods);
            builder.setNegativeButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    removeData(getAdapterPosition());
                }
            });

            builder.setPositiveButton(R.string.cancle_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }

        public void removeData(int position){
            if (picNum.size() != 0) {
                uploadPicFile.remove(position);
                uploadPicName.remove(position);
                picNum.remove(position);
                mUploadAdapter.notifyItemRemoved(position);
            }
        }
    }



     class MyAdapter extends RecyclerView.Adapter{

        private List mList;

         public MyAdapter(List list) {
             this.mList = list;
             mLayoutInflater = LayoutInflater.from(getApplicationContext());
         }

         @Override
         public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
             return new MyViewHolder(mLayoutInflater.inflate(R.layout.upload_picture_item_view , null));
         }

         @Override
         public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder != null){
                MyViewHolder viewHolder = (MyViewHolder) holder;
                viewHolder.setData(mList , position);
            }
         }

         @Override
         public int getItemCount() {
             return MAX_UPLOAD;
         }
     }

}
