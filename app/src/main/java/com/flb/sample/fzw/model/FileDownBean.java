package com.flb.sample.fzw.model;

import java.io.Serializable;

/**
 * author : fengzhangwei
 * date : 2019/7/5
 */
public class FileDownBean implements Serializable{

    private int DownType = 0;  // 下载状态  0 等待下载 1下载中  2暂停 3取消 4完成
    private int UploadType = 0;// 上传状态  0 等待上传 1上传中  2暂停 3取消 4完成
    private String cosPath;   // 对象键
    private int type;      // 上传还是下载 0 上传 1 下载
    private String path;     // 文件路径(上传/下载)
    private String DownName; // 下载到本地的文件名(默认为下载时的文件名)
    private String bucketName; // 储存桶名称
    private int pb;          // 上传/下载 进度


    public int getPb() {
        return pb;
    }

    public void setPb(int pb) {
        this.pb = pb;
    }

    public int getDownType() {
        return DownType;
    }

    public void setDownType(int downType) {
        DownType = downType;
    }

    public int getUploadType() {
        return UploadType;
    }

    public void setUploadType(int uploadType) {
        UploadType = uploadType;
    }

    public String getCosPath() {
        return cosPath;
    }

    public void setCosPath(String cosPath) {
        this.cosPath = cosPath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDownName() {
        return DownName;
    }

    public void setDownName(String downName) {
        DownName = downName;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
