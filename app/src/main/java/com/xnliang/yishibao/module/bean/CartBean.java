package com.xnliang.yishibao.module.bean;

import java.util.List;

/**
 * Created by JackLiu on 2018-03-14.
 */

public class CartBean {

    /**
     * code : 200
     * msg : 成功
     * data : {"amount":3300,"lists":[{"goods_id":1,"goods_name":"精致粉底盒","goods_price":"150.00","goods_num":11,"image":"http://www.xl.com/yishibao/public/upload/shop/20180205/51a66146ce6f380295e2b41502597993.png","shop_price":"150.00"},{"goods_id":3,"goods_name":"精致粉底盒","goods_price":"150.00","goods_num":11,"image":"http://www.xl.com/yishibao/public/upload/shop/20180205/4bec22e60fb39e24016501810390f94d.png","shop_price":"12312.00"}]}
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
        /**
         * amount : 3300
         * lists : [{"goods_id":1,"goods_name":"精致粉底盒","goods_price":"150.00","goods_num":11,"image":"http://www.xl.com/yishibao/public/upload/shop/20180205/51a66146ce6f380295e2b41502597993.png","shop_price":"150.00"},{"goods_id":3,"goods_name":"精致粉底盒","goods_price":"150.00","goods_num":11,"image":"http://www.xl.com/yishibao/public/upload/shop/20180205/4bec22e60fb39e24016501810390f94d.png","shop_price":"12312.00"}]
         */

        private int amount;
        private List<ListsBean> lists;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public static class ListsBean {
            /**
             * goods_id : 1
             * goods_name : 精致粉底盒
             * goods_price : 150.00
             * goods_num : 11
             * image : http://www.xl.com/yishibao/public/upload/shop/20180205/51a66146ce6f380295e2b41502597993.png
             * shop_price : 150.00
             */

            private int goods_id;
            private String goods_name;
            private String goods_price;
            private int goods_num;
            private String image;
            private String shop_price;
            private boolean isChoosed;

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public int getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(int goods_num) {
                this.goods_num = goods_num;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getShop_price() {
                return shop_price;
            }

            public void setShop_price(String shop_price) {
                this.shop_price = shop_price;
            }

            public boolean isChoosed() {
                return isChoosed;
            }

            public void setChoosed(boolean choosed) {
                isChoosed = choosed;
            }
        }
    }
}
