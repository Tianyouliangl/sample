package com.flb.sample.dynamic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.flb.sample.adapter.DynamicAdapter;
import com.flb.sample.model.FileUploadJson;
import com.flb.sample.widgets.CmRequestBody;
import com.flb.sample.widgets.DisplayChildGridView;
import com.flb.sample.widgets.FileUtils;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DynamicActivity extends BaseActivity implements DynamicAdapter.onItemInterface, Callback, View.OnClickListener {

    private DisplayChildGridView mGridView;
    private List<String> mList;
    private DynamicAdapter adapter;
    private FrameLayout mLoading;
    private List<LocalMedia> selectList;
    private TextView tv_progress;
    private TextView tv_hint;
    private Button btn_save_bitmap;
    private ScrollView mScrollView;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int w = msg.what;
            String m = (String) msg.obj;
            int current = msg.arg1;
            int total = msg.arg2;
            tv_hint.setText(m);
            switch (w){
                case 1:
                    tv_hint.setText("上传失败 !_!");
                    tv_progress.setText(0+"%");
                    mHandler.sendEmptyMessageDelayed(4,500);
                    break;
                case 2:
                    tv_progress.setText(100+"%");
                    mHandler.sendEmptyMessageDelayed(4,500);
                    break;
                case 3:
                    tv_progress.setText(current+"%");
                    break;
                case 4:
                    updateAdapter();
                    mLoading.setVisibility(View.GONE);
                    break;
                case 5:
                    String picFile = (String) msg.obj;
                    String[] split = picFile.split("/");
                    String fileName = split[split.length - 1];
                    try {
                        MediaStore.Images.Media.insertImage(getApplicationContext()
                                .getContentResolver(), picFile, fileName, null);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    sendBroadcast(new Intent(
                            Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"
                            + picFile)));
                    Toast.makeText(DynamicActivity.this, "图片保存图库成功", Toast.LENGTH_LONG).show();
                    break;
               default:
                   break;
            }
        }
    };
    private Button btn_video;

    private void updateAdapter() {
        if (adapter == null){
            adapter = new DynamicAdapter(this, mList);
            adapter.setOnChangImageListener(this);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_dynamic;
    }

    @Override
    public void initView() {
        mScrollView = findViewById(R.id.mScrollView);
        mGridView = findViewById(R.id.dis_gridView);
        mLoading = findViewById(R.id.fl_get_code);
        tv_progress = findViewById(R.id.tv_progress);
        tv_hint = findViewById(R.id.tv_hint);
        btn_video = findViewById(R.id.btn_video);
        btn_save_bitmap = findViewById(R.id.btn_save_bitmap);
        mLoading.setVisibility(View.GONE);
        btn_save_bitmap.setOnClickListener(this);
        btn_video.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();
        updateAdapter();
        mGridView.setAdapter(adapter);
    }

    @Override
    public void onExamine(Integer position) {
        List<LocalMedia> mediaList = new ArrayList<>();
        for (int i=0; i<mList.size();i++){
            LocalMedia media = new LocalMedia();
            media.setPath(mList.get(i));
            mediaList.add(media);
        }
        PictureSelector.create(this).externalPicturePreview(position, mediaList);
    }

    @Override
    public void onAddImage() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(9)
                .minSelectNum(1)
                .imageSpanCount(6)
                .selectionMode(PictureConfig.MULTIPLE)
                .previewImage(true)
                .isCamera(true)
                .compress(true)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            tv_hint.setText("正在上传...");
            tv_progress.setText(0+"%");
            mLoading.setVisibility(View.VISIBLE);
            switch (requestCode){
                case PictureConfig.CHOOSE_REQUEST:
                    List<Bitmap> list = new ArrayList<>();
                    selectList = PictureSelector.obtainMultipleResult(data);
                    for (int i=0;i<selectList.size();i++) {
                        try {
                            FileInputStream inputStream = new FileInputStream(selectList.get(i).getPath());
                            list.add(BitmapFactory.decodeStream(inputStream));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    uploadImage(list);

                    break;

            }
        }else {
            if (mLoading.getVisibility() == View.VISIBLE){
                mLoading.setVisibility(View.GONE);
            }
        }
    }


    //1.创建对应的MediaType
    private    MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
    private   OkHttpClient client = new OkHttpClient();

    public  void uploadImage(List<Bitmap> imageList){

        //3.构建MultipartBody
        MultipartBody.Builder requestBody = new MultipartBody.Builder();//构建者模式
        requestBody.addFormDataPart("fileType", "1");
        requestBody.addFormDataPart("fileExtName", "jpg");

        for (int i=0;i<imageList.size();i++){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            imageList.get(i).compress(Bitmap.CompressFormat.JPEG,80,outputStream);
            byte[] byteArray = outputStream.toByteArray();
            //2.创建RequestBody
            RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, byteArray);
            requestBody.addFormDataPart("file","image",fileBody);
        }
        CmRequestBody cmBody = new CmRequestBody(requestBody.build()) {

            @Override
            public void loading(long current, long total, boolean done) {
                Log.i("fzw","上传进度---" + current + "/" + total);
                sendMessage(3,current,total," 正在上传... ");
            }
        };

        //4.构建请求
        Request request = new Request.Builder()
                .url("http://xkadmin.xinyixy.com/sys/attach/appFiles")
                .post(cmBody)
                .build();

        //5.发送请求
        Call response = client.newCall(request);
        response.enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.i("fzw","----image-----" + e.toString());
        sendMessage(1,0,0,"上传失败 !-! ");
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

        if (response.isSuccessful() || response != null || response.body() != null){
            String json = response.body().string();
            sendMessage(2,0,0,"上传完成 ^!^ ");
            Log.i("fzw","----json-----" + json);
            FileUploadJson uploadJson = new Gson().fromJson(json, FileUploadJson.class);
            List<FileUploadJson.ContentBean.ListBean> listBeanList = uploadJson.getContent().getList();
            for (int i=0;i< listBeanList.size();i++){
                mList.add(listBeanList.get(i).getUrl());
                Log.i("fzw","----image-----" + listBeanList.get(i).getUrl());
            }
        }else {
            sendMessage(1,0,0,"上传出错 !-! ");
        }


    }

    public void sendMessage(int what, long current, long total, String msg) {
        Message message = Message.obtain();
        message.what = what;
        message.arg1 = (int) current;
        message.arg2 = (int) total;
        message.obj = msg;
        mHandler.sendMessage(message);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save_bitmap){
            if (Build.VERSION.SDK_INT >= 26 && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            Bitmap bitmap = getScrollViewBitmap(mScrollView);
            saveBitMap("screen",bitmap);
        }

        if (v.getId() == R.id.btn_video){
            startActivity(new Intent(this,DyVideoActivity.class));
            finish();
        }
    }

    //使用IO流将bitmap对象存到本地指定文件夹
    public  void saveBitMap(final String bitName,final Bitmap bitmap){
        new Thread(){
            @Override
            public void run() {
                String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
                FileOutputStream outputStream = null;
                String imageDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
                File appDir = new File(storePath);
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String lastPath = storePath + "/screen/captrue/" + imageDate + bitName + ".jpeg";
                File newFile = FileUtils.createNewFile(new File(lastPath));
                try {
                    outputStream = new FileOutputStream(newFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                    Message msg = Message.obtain();
                    msg.what = 5;
                    msg.obj = newFile.getPath();
                    mHandler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public static Bitmap getScrollViewBitmap(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }



}
