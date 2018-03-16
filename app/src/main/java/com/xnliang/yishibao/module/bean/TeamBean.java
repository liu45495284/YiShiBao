package com.xnliang.yishibao.module.bean;

import java.util.List;

/**
 * Created by JackLiu on 2018-03-16.
 */

public class TeamBean {

    /**
     * code : 200
     * msg : 获取成功！
     * data : {"total":5,"per_page":15,"current_page":1,"last_page":1,"data":[{"id":6,"user_nickname":"test4","avatar":"http://www.xl.com/static/images/headicon.png","total":46},{"id":5,"user_nickname":"test3","avatar":"http://www.xl.com/static/images/headicon.png","total":46},{"id":4,"user_nickname":"test2","avatar":"http://www.xl.com/static/images/headicon.png","total":46},{"id":3,"user_nickname":"test1","avatar":"http://www.xl.com/static/images/headicon.png","total":46},{"id":2,"user_nickname":"","avatar":"http://www.xl.com/static/images/headicon.png","total":46}]}
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
         * total : 5
         * per_page : 15
         * current_page : 1
         * last_page : 1
         * data : [{"id":6,"user_nickname":"test4","avatar":"http://www.xl.com/static/images/headicon.png","total":46},{"id":5,"user_nickname":"test3","avatar":"http://www.xl.com/static/images/headicon.png","total":46},{"id":4,"user_nickname":"test2","avatar":"http://www.xl.com/static/images/headicon.png","total":46},{"id":3,"user_nickname":"test1","avatar":"http://www.xl.com/static/images/headicon.png","total":46},{"id":2,"user_nickname":"","avatar":"http://www.xl.com/static/images/headicon.png","total":46}]
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
             * id : 6
             * user_nickname : test4
             * avatar : http://www.xl.com/static/images/headicon.png
             * total : 46
             */

            private int id;
            private String user_nickname;
            private String avatar;
            private int total;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUser_nickname() {
                return user_nickname;
            }

            public void setUser_nickname(String user_nickname) {
                this.user_nickname = user_nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }
        }
    }
}
