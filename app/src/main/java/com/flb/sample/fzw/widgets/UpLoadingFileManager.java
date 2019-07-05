package com.flb.sample.fzw.widgets;

import android.graphics.Bitmap;

import com.flb.sample.fzw.model.FileUploadJson;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * author : 冯张伟
 * date : 2019/5/17
 */
public class UpLoadingFileManager {

    private static MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
    private static MediaType MEDIA_TYPE_MP4 = MediaType.parse("audio/mp4");
    private static MediaType MEDIA_TYPE_MP3 = MediaType.parse("audio/mpeg");
    private static String FILE_TYPE_IMAGE = "1";
    private static String FILE_TYPE_MP4 = "4";
    private static String FILE_TYPE_MP3 = "3";
    private static OkHttpClient client = new OkHttpClient();


    public interface CallBack{
        void onProgressBar(long p);
        void onError(String msg);
        void onSuccess(List<String>  mList);
    }


    public  void UpLoadingFileImage(File file,CallBack callBack){
        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        requestBody.addFormDataPart("fileType", FILE_TYPE_IMAGE);
        requestBody.addFormDataPart("fileExtName", "jpg");
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, file);
        requestBody.addFormDataPart("file",file.getName(),fileBody);
        sendResponse(requestBody.build(),callBack);
    }

    public  void UpLoadingFileImage(List<Bitmap> list, CallBack callBack){

        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        requestBody.addFormDataPart("fileType", FILE_TYPE_IMAGE);
        requestBody.addFormDataPart("fileExtName", "jpg");
        for (int i=0;i<list.size();i++){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            list.get(i).compress(Bitmap.CompressFormat.JPEG,80,outputStream);
            byte[] byteArray = outputStream.toByteArray();
            //2.创建RequestBody
            RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, byteArray);
            requestBody.addFormDataPart("file","image",fileBody);
        }
        sendResponse(requestBody.build(),callBack);
    }

    public  void UpLoadingFileMp4(File file,CallBack callBack){
        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        requestBody.addFormDataPart("fileType", FILE_TYPE_MP4);
        requestBody.addFormDataPart("fileExtName", "mp4");
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_MP4, file);
        requestBody.addFormDataPart("file",file.getName(),fileBody);
        sendResponse(requestBody.build(),callBack);
    }

    public  void UpLoadingFileMp3(File file,CallBack callBack){
        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        requestBody.addFormDataPart("fileType", FILE_TYPE_MP3);
        requestBody.addFormDataPart("fileExtName", "mp3");
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_MP3, file);
        requestBody.addFormDataPart("file",file.getName(),fileBody);
        sendResponse(requestBody.build(),callBack);
    }



    public  void UpLoadingFileImage(String path,CallBack callBack){
        UpLoadingFileImage(new File(path),callBack);
    }

    public  void UpLoadingFileMp4(String path,CallBack callBack){
        UpLoadingFileMp4(new File(path),callBack);
    }

    public  void UpLoadingFileMp3(String path,CallBack callBack){
        UpLoadingFileMp3(new File(path),callBack);
    }


    private static void sendResponse(MultipartBody build,CallBack callBack) {

        //4.构建请求
        Request request = new Request.Builder()
                .url("http://xkadmin.xinyixy.com/sys/attach/appFiles")
                .post(new CmRequestBody(build) {
                    @Override
                    public void loading(long current, long total, boolean done) {
                        if (callBack != null){
                            callBack.onProgressBar(current);
                        }
                    }
                })
                .build();

        //5.发送请求
        Call response = client.newCall(request);
        response.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null){
                    callBack.onError(e.toString());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                  List<String>  mList = new ArrayList<>();
                    if (response.isSuccessful() || response != null || response.body() != null){
                        String json = response.body().string();
                        FileUploadJson uploadJson = new Gson().fromJson(json, FileUploadJson.class);
                        List<FileUploadJson.ContentBean.ListBean> listBeanList = uploadJson.getContent().getList();
                        for (int i=0;i< listBeanList.size();i++){
                            mList.add(listBeanList.get(i).getUrl());
                        }
                        if (callBack != null){
                            callBack.onSuccess(mList);
                        }
                    }else {
                        if (callBack != null){
                            callBack.onError("失败");
                        }
                    }

            }
        });
    }
}
