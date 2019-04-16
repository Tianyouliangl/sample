package com.flb.sample.model;

import java.util.List;

/**
 * author : 冯张伟
 * date : 2019/4/15
 */
public class FileUploadJson {


    /**
     * header : {"status":1,"message":"请求成功"}
     * content : {"list":[{"msg":"success","code":0,"compressUrl":"","url":"http://xkdown.xinyixy.com/img/fa356a731021470393801be6132093138127-image.jpg"},{"msg":"success","code":0,"compressUrl":"http://xkdown.xinyixy.com/img/compress/b4414db6cd96437d8fbba404ed78fd263234-image.jpg","url":"http://xkdown.xinyixy.com/img/b4414db6cd96437d8fbba404ed78fd263234-image.jpg"}]}
     * data : null
     */

    private HeaderBean header;
    private ContentBean content;
    private Object data;

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static class HeaderBean {
        /**
         * status : 1
         * message : 请求成功
         */

        private int status;
        private String message;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class ContentBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * msg : success
             * code : 0
             * compressUrl :
             * url : http://xkdown.xinyixy.com/img/fa356a731021470393801be6132093138127-image.jpg
             */

            private String msg;
            private int code;
            private String compressUrl;
            private String url;

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public String getCompressUrl() {
                return compressUrl;
            }

            public void setCompressUrl(String compressUrl) {
                this.compressUrl = compressUrl;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
