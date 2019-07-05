package com.flb.sample.fzw.model;

/**
 * author : 冯张伟
 * date : 2019/5/16
 */
public class VideoMd5Bean{

    /**
     * status : ok
     * message : success
     * userSimpleForm :
     * roleAndPermission :
     * id : 987
     * url : https://guwu-varys.obs.cn-north-1.myhuaweicloud.com/dev/user/video/ac37c3be367a774a6da0c5b9e98d5c22.mp4
     * td : 173
     */

    private String status;
    private String message;
    private int id;
    private String url;
    private int td;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTd() {
        return td;
    }

    public void setTd(int td) {
        this.td = td;
    }

    @Override
    public String toString() {
        return "VideoMd5Bean{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", id=" + id +
                ", url='" + url + '\'' +
                ", td=" + td +
                '}';
    }
}
