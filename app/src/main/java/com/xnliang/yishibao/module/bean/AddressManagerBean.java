package com.xnliang.yishibao.module.bean;

import java.util.List;

/**
 * Created by JackLiu on 2018-03-16.
 */

public class AddressManagerBean {

    /**
     * code : 200
     * msg : 获取成功！
     * data : {"total":1,"per_page":15,"current_page":1,"last_page":1,"data":[{"consignee":"里斯","mobile":"15394315510","address":"哈哈哈哈哈","district":220203,"is_default":0,"name":"吉林省吉林市龙潭区","id":9}]}
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
         * total : 1
         * per_page : 15
         * current_page : 1
         * last_page : 1
         * data : [{"consignee":"里斯","mobile":"15394315510","address":"哈哈哈哈哈","district":220203,"is_default":0,"name":"吉林省吉林市龙潭区","id":9}]
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
             * consignee : 里斯
             * mobile : 15394315510
             * address : 哈哈哈哈哈
             * district : 220203
             * is_default : 0
             * name : 吉林省吉林市龙潭区
             * id : 9
             */

            private String consignee;
            private String mobile;
            private String address;
            private int district;
            private int is_default;
            private String name;
            private int id;

            public String getConsignee() {
                return consignee;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getDistrict() {
                return district;
            }

            public void setDistrict(int district) {
                this.district = district;
            }

            public int getIs_default() {
                return is_default;
            }

            public void setIs_default(int is_default) {
                this.is_default = is_default;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
