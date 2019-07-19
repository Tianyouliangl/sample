package com.flb.sample.fzw.model;

import com.tencent.cos.xml.model.tag.ListBucket;

/**
 * author : fengzhangwei
 * date : 2019/7/15
 */
public class FileBean {

    public String key;
    public String lastModified;
    public String eTag;
    public long size;
    public ListBucket.Owner owner;
    public String storageClass;
    public int type;    // 0 没有下载   1 已下载

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public ListBucket.Owner getOwner() {
        return owner;
    }

    public void setOwner(ListBucket.Owner owner) {
        this.owner = owner;
    }

    public String getStorageClass() {
        return storageClass;
    }

    public void setStorageClass(String storageClass) {
        this.storageClass = storageClass;
    }
}
