package com.xnliang.yishibao.module.bean;

import java.util.List;

/**
 * Created by JackLiu on 2018-03-15.
 */

public class OrderBean {

    /**
     * code : 200
     * msg :
     * data : {"total":3,"per_page":15,"current_page":1,"last_page":1,"data":[{"order_sn":"2018022849994910","create_time":"2018-02-28","total_amount":"304.00","pay_status":0,"order_status":0,"value":[{"goods_name":"Givenchy/纪梵希 高级定制小羊皮唇膏 #315号姨妈色 3.4g-10690","goods_num":1,"goods_price":"304.00","goods_image":"http://ysb.appxinliang.cn/upload/shop/20180227/fd30a3b953ff4b70e296fd5f3d4ca84a.jpg"}]},{"order_sn":"2018022850505555","create_time":"2018-02-28","total_amount":"2002.00","pay_status":0,"order_status":0,"value":[{"goods_name":"Givenchy/纪梵希 高级定制小羊皮唇膏 #315号姨妈色 3.4g-10690","goods_num":6,"goods_price":"304.00","goods_image":"http://ysb.appxinliang.cn/upload/shop/20180227/fd30a3b953ff4b70e296fd5f3d4ca84a.jpg"},{"goods_name":"MAC/魅可 子弹头口红 Relentlessly Red 3g-9693","goods_num":1,"goods_price":"178.00","goods_image":"http://ysb.appxinliang.cn/upload/shop/20180227/2d9ba8b1b9113281b0e83119473008d9.jpg"}]}]}
     */

    private int code;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * total : 3
         * per_page : 15
         * current_page : 1
         * last_page : 1
         * data : [{"order_sn":"2018022849994910","create_time":"2018-02-28","total_amount":"304.00","pay_status":0,"order_status":0,"value":[{"goods_name":"Givenchy/纪梵希 高级定制小羊皮唇膏 #315号姨妈色 3.4g-10690","goods_num":1,"goods_price":"304.00","goods_image":"http://ysb.appxinliang.cn/upload/shop/20180227/fd30a3b953ff4b70e296fd5f3d4ca84a.jpg"}]},{"order_sn":"2018022850505555","create_time":"2018-02-28","total_amount":"2002.00","pay_status":0,"order_status":0,"value":[{"goods_name":"Givenchy/纪梵希 高级定制小羊皮唇膏 #315号姨妈色 3.4g-10690","goods_num":6,"goods_price":"304.00","goods_image":"http://ysb.appxinliang.cn/upload/shop/20180227/fd30a3b953ff4b70e296fd5f3d4ca84a.jpg"},{"goods_name":"MAC/魅可 子弹头口红 Relentlessly Red 3g-9693","goods_num":1,"goods_price":"178.00","goods_image":"http://ysb.appxinliang.cn/upload/shop/20180227/2d9ba8b1b9113281b0e83119473008d9.jpg"}]}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * order_sn : 2018022849994910
             * create_time : 2018-02-28
             * total_amount : 304.00
             * pay_status : 0
             * order_status : 0
             * value : [{"goods_name":"Givenchy/纪梵希 高级定制小羊皮唇膏 #315号姨妈色 3.4g-10690","goods_num":1,"goods_price":"304.00","goods_image":"http://ysb.appxinliang.cn/upload/shop/20180227/fd30a3b953ff4b70e296fd5f3d4ca84a.jpg"}]
             */

            private String order_sn;
            private String create_time;
            private String total_amount;
            private int pay_status;
            private int order_status;
            private List<ValueBean> value;

            public String getOrder_sn() {
                return order_sn;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getTotal_amount() {
                return total_amount;
            }

            public void setTotal_amount(String total_amount) {
                this.total_amount = total_amount;
            }

            public int getPay_status() {
                return pay_status;
            }

            public void setPay_status(int pay_status) {
                this.pay_status = pay_status;
            }

            public int getOrder_status() {
                return order_status;
            }

            public void setOrder_status(int order_status) {
                this.order_status = order_status;
            }

            public List<ValueBean> getValue() {
                return value;
            }

            public void setValue(List<ValueBean> value) {
                this.value = value;
            }

            public static class ValueBean {
                /**
                 * goods_name : Givenchy/纪梵希 高级定制小羊皮唇膏 #315号姨妈色 3.4g-10690
                 * goods_num : 1
                 * goods_price : 304.00
                 * goods_image : http://ysb.appxinliang.cn/upload/shop/20180227/fd30a3b953ff4b70e296fd5f3d4ca84a.jpg
                 */

                private String goods_name;
                private int goods_num;
                private String goods_price;
                private String goods_image;

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public int getGoods_num() {
                    return goods_num;
                }

                public void setGoods_num(int goods_num) {
                    this.goods_num = goods_num;
                }

                public String getGoods_price() {
                    return goods_price;
                }

                public void setGoods_price(String goods_price) {
                    this.goods_price = goods_price;
                }

                public String getGoods_image() {
                    return goods_image;
                }

                public void setGoods_image(String goods_image) {
                    this.goods_image = goods_image;
                }
            }
        }
    }
}
