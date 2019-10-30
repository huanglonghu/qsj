package com.example.qsl.bean;

import java.util.List;

public class BankCardMsg {


    /**
     * code : 0
     * msg : success
     * data : {"items":[{"bank":"光大银行","subBranch":"广州天河北路","name":"大猫","card":"888 8888 8888 8888","id":1}]}
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
             * bank : 光大银行
             * subBranch : 广州天河北路
             * name : 大猫
             * card : 888 8888 8888 8888
             * id : 1
             */

            private String bank;
            private String subBranch;
            private String name;
            private String card;
            private int id;

            public String getBank() {
                return bank;
            }

            public void setBank(String bank) {
                this.bank = bank;
            }

            public String getSubBranch() {
                return subBranch;
            }

            public void setSubBranch(String subBranch) {
                this.subBranch = subBranch;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCard() {
                return card;
            }

            public void setCard(String card) {
                this.card = card;
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
