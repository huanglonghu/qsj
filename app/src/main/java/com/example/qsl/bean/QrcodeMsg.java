package com.example.qsl.bean;

import java.util.List;

public class QrcodeMsg {


    /**
     * code : 0
     * msg : success
     * data : {"items":[{"picture":"/Admin/1/0ceb4fa5031d45fb8d7aac1ac352ae6c.png","describe":"支付宝二维码","id":1},{"picture":"/Admin/1/152a526ee8c249478324313d79b5185c.png","describe":"微信二维码","id":2}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ItemsBean> items;

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * picture : /Admin/1/0ceb4fa5031d45fb8d7aac1ac352ae6c.png
             * describe : 支付宝二维码
             * id : 1
             */

            private String picture;
            private String describe;
            private int id;

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
