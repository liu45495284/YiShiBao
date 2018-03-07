package com.xnliang.yishibao.module.bean;

import java.util.List;

/**
 * Created by JackLiu on 2018-03-06.
 */

public class IndexBean {

    /**
     * code : 200
     * msg : 获取成功！
     * data : {"slides":[{"title":"banner1","image":"http://ysb.appxinliang.cn/upload/admin/20180227/ac2cedf452a958b98d6b5f1aa12f8162.jpg","url":"http://www.xnliang.com"},{"title":"banner2","image":"http://ysb.appxinliang.cn/upload/admin/20180227/d484b027476b14d018545e70fc7d6ac0.jpg","url":"#"},{"title":"banner3","image":"http://ysb.appxinliang.cn/upload/admin/20180227/a9938fd4434f529398ef929e006c9ea1.jpg","url":"#"}],"areas":[{"id":1,"name":"三亚","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/174bbed8a1b82d9701acc7e4b38642ba.jpg"},{"id":2,"name":"丽江","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/a24f9a889de2efb2004f0199ce92a4fe.jpg"},{"id":3,"name":"厦门","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/2952d71bfe91f45c376e22310fa962d8.jpg"},{"id":4,"name":"东北","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/449f5f717ce7d9fa940170ce9fb88008.jpg"},{"id":5,"name":"桂林","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/931cb800fa59a5569e4a60524fb62796.jpg"},{"id":6,"name":"北京","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/887429a36b43792f7a5f06e9342543fb.jpg"}],"cates":[{"id":1,"name":"洗发水","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/b4278963f4d4f2fadd5382eed5c7668f.png"},{"id":2,"name":"服装","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/69527c6400060f0dcc7010b181c78caa.png"},{"id":3,"name":"口红","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/bbe65beecd960368eef05d068a6f361f.png"},{"id":4,"name":"日化","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/73377b0cad496a12c0b4d1e8b1644275.png"},{"id":5,"name":"梳子","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/948d95a9fe1cbf1ac0e3576ba0897b88.png"},{"id":6,"name":"厨具","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/56d016cc5e2bfa47b221b2e7ca3ad097.png"},{"id":7,"name":"盆栽","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/366cf6fc7fb01f2f04e6b93e2d254105.png"},{"id":8,"name":"洗护","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/6331922304db5fdd05f4269f8a5cd34c.png"},{"id":9,"name":"纸品","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/439625fa13daac8563e2bd690ef40dd2.png"},{"id":10,"name":"洗漱","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/87ee134bd60879a6a084f4c38b49beca.png"}],"lists":[{"id":1,"goods_name":"澳洲Aussie 袋鼠 奇迹保湿天然植物洗发水300ml 2瓶 7722","image":"http://ysb.appxinliang.cn/upload/shop/20180227/aaa45ecf1767b7146a5942f66864ae15.jpg","shop_price":150},{"id":2,"goods_name":"Givenchy/纪梵希 高级定制小羊皮唇膏 #315号姨妈色 3.4g-10690","image":"http://ysb.appxinliang.cn/upload/shop/20180227/fd30a3b953ff4b70e296fd5f3d4ca84a.jpg","shop_price":304},{"id":6,"goods_name":"日本SANA/莎娜 豆乳美肌细滑洗面奶150ML+滋润型化妆水200ML+乳液150ML 组合装-4802","image":"http://ysb.appxinliang.cn/upload/shop/20180227/26c48a7e87885d3b4baff6d5548a210d.jpg","shop_price":182},{"id":7,"goods_name":"韩国 SNP 燕窝海洋水库面膜深层补水保湿提亮美白 10片-6627","image":"http://ysb.appxinliang.cn/upload/shop/20180227/6f3615e0c655402de6cb1ff5f8eaa08f.jpg","shop_price":150},{"id":10,"goods_name":"金号全棉提缎素色绣花素色毛巾  10条装（颜色随机发货）","image":"http://ysb.appxinliang.cn/upload/shop/20180227/45d446705cdbaa2ceab5595b5a9aae12.jpg","shop_price":119},{"id":11,"goods_name":"金号纯棉米菲卡通加厚毛巾  6条装 绿棕红各2条","image":"http://ysb.appxinliang.cn/upload/shop/20180227/5c0b9b5d67d077d306247168cf32f1df.jpg","shop_price":95},{"id":13,"goods_name":"美国California加州宝宝 镇静舒缓 护发素 251mL-9327","image":"http://ysb.appxinliang.cn/upload/shop/20180227/12851da54e4ea0ba4e57849e62a0ed04.jpg","shop_price":246}],"turntable":"http://ysb.appxinliang.cn/api/h5/turntable.html"}
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
         * slides : [{"title":"banner1","image":"http://ysb.appxinliang.cn/upload/admin/20180227/ac2cedf452a958b98d6b5f1aa12f8162.jpg","url":"http://www.xnliang.com"},{"title":"banner2","image":"http://ysb.appxinliang.cn/upload/admin/20180227/d484b027476b14d018545e70fc7d6ac0.jpg","url":"#"},{"title":"banner3","image":"http://ysb.appxinliang.cn/upload/admin/20180227/a9938fd4434f529398ef929e006c9ea1.jpg","url":"#"}]
         * areas : [{"id":1,"name":"三亚","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/174bbed8a1b82d9701acc7e4b38642ba.jpg"},{"id":2,"name":"丽江","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/a24f9a889de2efb2004f0199ce92a4fe.jpg"},{"id":3,"name":"厦门","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/2952d71bfe91f45c376e22310fa962d8.jpg"},{"id":4,"name":"东北","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/449f5f717ce7d9fa940170ce9fb88008.jpg"},{"id":5,"name":"桂林","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/931cb800fa59a5569e4a60524fb62796.jpg"},{"id":6,"name":"北京","image":"http://ysb.appxinliang.cn/upload/tourism/20180227/887429a36b43792f7a5f06e9342543fb.jpg"}]
         * cates : [{"id":1,"name":"洗发水","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/b4278963f4d4f2fadd5382eed5c7668f.png"},{"id":2,"name":"服装","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/69527c6400060f0dcc7010b181c78caa.png"},{"id":3,"name":"口红","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/bbe65beecd960368eef05d068a6f361f.png"},{"id":4,"name":"日化","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/73377b0cad496a12c0b4d1e8b1644275.png"},{"id":5,"name":"梳子","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/948d95a9fe1cbf1ac0e3576ba0897b88.png"},{"id":6,"name":"厨具","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/56d016cc5e2bfa47b221b2e7ca3ad097.png"},{"id":7,"name":"盆栽","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/366cf6fc7fb01f2f04e6b93e2d254105.png"},{"id":8,"name":"洗护","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/6331922304db5fdd05f4269f8a5cd34c.png"},{"id":9,"name":"纸品","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/439625fa13daac8563e2bd690ef40dd2.png"},{"id":10,"name":"洗漱","app_icon":"http://ysb.appxinliang.cn/upload/shop/20180227/87ee134bd60879a6a084f4c38b49beca.png"}]
         * lists : [{"id":1,"goods_name":"澳洲Aussie 袋鼠 奇迹保湿天然植物洗发水300ml 2瓶 7722","image":"http://ysb.appxinliang.cn/upload/shop/20180227/aaa45ecf1767b7146a5942f66864ae15.jpg","shop_price":150},{"id":2,"goods_name":"Givenchy/纪梵希 高级定制小羊皮唇膏 #315号姨妈色 3.4g-10690","image":"http://ysb.appxinliang.cn/upload/shop/20180227/fd30a3b953ff4b70e296fd5f3d4ca84a.jpg","shop_price":304},{"id":6,"goods_name":"日本SANA/莎娜 豆乳美肌细滑洗面奶150ML+滋润型化妆水200ML+乳液150ML 组合装-4802","image":"http://ysb.appxinliang.cn/upload/shop/20180227/26c48a7e87885d3b4baff6d5548a210d.jpg","shop_price":182},{"id":7,"goods_name":"韩国 SNP 燕窝海洋水库面膜深层补水保湿提亮美白 10片-6627","image":"http://ysb.appxinliang.cn/upload/shop/20180227/6f3615e0c655402de6cb1ff5f8eaa08f.jpg","shop_price":150},{"id":10,"goods_name":"金号全棉提缎素色绣花素色毛巾  10条装（颜色随机发货）","image":"http://ysb.appxinliang.cn/upload/shop/20180227/45d446705cdbaa2ceab5595b5a9aae12.jpg","shop_price":119},{"id":11,"goods_name":"金号纯棉米菲卡通加厚毛巾  6条装 绿棕红各2条","image":"http://ysb.appxinliang.cn/upload/shop/20180227/5c0b9b5d67d077d306247168cf32f1df.jpg","shop_price":95},{"id":13,"goods_name":"美国California加州宝宝 镇静舒缓 护发素 251mL-9327","image":"http://ysb.appxinliang.cn/upload/shop/20180227/12851da54e4ea0ba4e57849e62a0ed04.jpg","shop_price":246}]
         * turntable : http://ysb.appxinliang.cn/api/h5/turntable.html
         */

        private String turntable;
        private List<SlidesBean> slides;
        private List<AreasBean> areas;
        private List<CatesBean> cates;
        private List<ListsBean> lists;

        public String getTurntable() {
            return turntable;
        }

        public void setTurntable(String turntable) {
            this.turntable = turntable;
        }

        public List<SlidesBean> getSlides() {
            return slides;
        }

        public void setSlides(List<SlidesBean> slides) {
            this.slides = slides;
        }

        public List<AreasBean> getAreas() {
            return areas;
        }

        public void setAreas(List<AreasBean> areas) {
            this.areas = areas;
        }

        public List<CatesBean> getCates() {
            return cates;
        }

        public void setCates(List<CatesBean> cates) {
            this.cates = cates;
        }

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public static class SlidesBean {
            /**
             * title : banner1
             * image : http://ysb.appxinliang.cn/upload/admin/20180227/ac2cedf452a958b98d6b5f1aa12f8162.jpg
             * url : http://www.xnliang.com
             */

            private String title;
            private String image;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class AreasBean {
            /**
             * id : 1
             * name : 三亚
             * image : http://ysb.appxinliang.cn/upload/tourism/20180227/174bbed8a1b82d9701acc7e4b38642ba.jpg
             */

            private int id;
            private String name;
            private String image;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }

        public static class CatesBean {
            /**
             * id : 1
             * name : 洗发水
             * app_icon : http://ysb.appxinliang.cn/upload/shop/20180227/b4278963f4d4f2fadd5382eed5c7668f.png
             */

            private int id;
            private String name;
            private String app_icon;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getApp_icon() {
                return app_icon;
            }

            public void setApp_icon(String app_icon) {
                this.app_icon = app_icon;
            }
        }

        public static class ListsBean {
            /**
             * id : 1
             * goods_name : 澳洲Aussie 袋鼠 奇迹保湿天然植物洗发水300ml 2瓶 7722
             * image : http://ysb.appxinliang.cn/upload/shop/20180227/aaa45ecf1767b7146a5942f66864ae15.jpg
             * shop_price : 150
             */

            private int id;
            private String goods_name;
            private String image;
            private int shop_price;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getShop_price() {
                return shop_price;
            }

            public void setShop_price(int shop_price) {
                this.shop_price = shop_price;
            }
        }
    }
}
