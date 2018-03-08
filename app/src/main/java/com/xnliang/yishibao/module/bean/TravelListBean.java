package com.xnliang.yishibao.module.bean;

import java.util.List;

/**
 * Created by JackLiu on 2018-03-08.
 */

public class TravelListBean {

    /**
     * code : 200
     * msg : 获取成功！
     * data : {"total":22,"per_page":15,"current_page":1,"last_page":2,"data":[{"id":4,"title":"&lt;海南三亚亚龙湾-蜈支洲双飞5日游&gt;全程连住国际五星温德姆/洲际等品牌，万人出游0购物，蜈支洲专用通道免排队，9元购","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/c89a4dc1b48ea12bd44da132cf046763.jpg"},{"id":5,"title":"&lt;海南三亚-蜈支洲岛双飞5日游&gt;错峰爆款0购物，全程三亚湾一线海景房，1分钟到沙滩，蜈支洲专用通道免排队，24H专车","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/8dbe444d2292a48481c4198c7ee4c7e9.jpg"},{"id":6,"title":"水乐园、游艇出海（含潜水）、景点等N选1+三亚湾红树林 提前预定升豪华园景房","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/a8768ef48c1945780e769581aee72d61.jpg"},{"id":7,"title":"三亚湾红树林 成长季亲子套餐，北京烤鸭+儿童营养餐+探险王国+水乐园亲子体验","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":8,"title":"三亚湾红树林 成长季亲子套餐，北京烤鸭+儿童营养餐+探险王国+水乐园亲子体验","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":9,"title":"水乐园、游艇出海（含潜水）、景点等N选1+三亚湾红树林 提前预定升豪华园景房","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":10,"title":"水乐园、游艇出海（含潜水）、景点等N选1+三亚湾红树林 提前预定升豪华园景房","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":11,"title":"三亚湾红树林 成长季亲子套餐，北京烤鸭+儿童营养餐+探险王国+水乐园亲子体验","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":12,"title":"三亚湾红树林 成长季亲子套餐，北京烤鸭+儿童营养餐+探险王国+水乐园亲子体验","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":13,"title":"水乐园、游艇出海（含潜水）、景点等N选1+三亚湾红树林 提前预定升豪华园景房","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":14,"title":"三亚湾红树林 成长季亲子套餐，北京烤鸭+儿童营养餐+探险王国+水乐园亲子体验","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":15,"title":"三亚湾红树林 成长季亲子套餐，北京烤鸭+儿童营养餐+探险王国+水乐园亲子体验","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":16,"title":"水乐园、游艇出海（含潜水）、景点等N选1+三亚湾红树林 提前预定升豪华园景房","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":17,"title":"三亚湾红树林 成长季亲子套餐，北京烤鸭+儿童营养餐+探险王国+水乐园亲子体验","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":18,"title":"三亚湾红树林 成长季亲子套餐，北京烤鸭+儿童营养餐+探险王国+水乐园亲子体验","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"}]}
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
         * total : 22
         * per_page : 15
         * current_page : 1
         * last_page : 2
         * data : [{"id":4,"title":"&lt;海南三亚亚龙湾-蜈支洲双飞5日游&gt;全程连住国际五星温德姆/洲际等品牌，万人出游0购物，蜈支洲专用通道免排队，9元购","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/c89a4dc1b48ea12bd44da132cf046763.jpg"},{"id":5,"title":"&lt;海南三亚-蜈支洲岛双飞5日游&gt;错峰爆款0购物，全程三亚湾一线海景房，1分钟到沙滩，蜈支洲专用通道免排队，24H专车","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/8dbe444d2292a48481c4198c7ee4c7e9.jpg"},{"id":6,"title":"水乐园、游艇出海（含潜水）、景点等N选1+三亚湾红树林 提前预定升豪华园景房","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/a8768ef48c1945780e769581aee72d61.jpg"},{"id":7,"title":"三亚湾红树林 成长季亲子套餐，北京烤鸭+儿童营养餐+探险王国+水乐园亲子体验","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":8,"title":"三亚湾红树林 成长季亲子套餐，北京烤鸭+儿童营养餐+探险王国+水乐园亲子体验","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":9,"title":"水乐园、游艇出海（含潜水）、景点等N选1+三亚湾红树林 提前预定升豪华园景房","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":10,"title":"水乐园、游艇出海（含潜水）、景点等N选1+三亚湾红树林 提前预定升豪华园景房","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":11,"title":"三亚湾红树林 成长季亲子套餐，北京烤鸭+儿童营养餐+探险王国+水乐园亲子体验","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":12,"title":"三亚湾红树林 成长季亲子套餐，北京烤鸭+儿童营养餐+探险王国+水乐园亲子体验","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":13,"title":"水乐园、游艇出海（含潜水）、景点等N选1+三亚湾红树林 提前预定升豪华园景房","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":14,"title":"三亚湾红树林 成长季亲子套餐，北京烤鸭+儿童营养餐+探险王国+水乐园亲子体验","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":15,"title":"三亚湾红树林 成长季亲子套餐，北京烤鸭+儿童营养餐+探险王国+水乐园亲子体验","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":16,"title":"水乐园、游艇出海（含潜水）、景点等N选1+三亚湾红树林 提前预定升豪华园景房","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":17,"title":"三亚湾红树林 成长季亲子套餐，北京烤鸭+儿童营养餐+探险王国+水乐园亲子体验","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"},{"id":18,"title":"三亚湾红树林 成长季亲子套餐，北京烤鸭+儿童营养餐+探险王国+水乐园亲子体验","price":"1500.00","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/3664f085246b3924231294e0cb30a1f5.jpg"}]
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
             * id : 4
             * title : &lt;海南三亚亚龙湾-蜈支洲双飞5日游&gt;全程连住国际五星温德姆/洲际等品牌，万人出游0购物，蜈支洲专用通道免排队，9元购
             * price : 1500.00
             * image : http://ysb.appxinliang.cn/upload/tourism/20180227/c89a4dc1b48ea12bd44da132cf046763.jpg
             */

            private int id;
            private String title;
            private String price;
            private String image;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}
