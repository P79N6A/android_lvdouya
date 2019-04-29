package com.hhkj.dyedu.bean.gson;

import com.hhkj.dyedu.bean.model.CourseBean;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/6/11
 */

public class theme_info extends BaseHttpResponse {


    /**
     * time : 1528695338
     * data : {"header":{"level":"机器人课程","level2":"3-4岁","level3":"这是什么"},"theme":{"title":"这是什么","image":"/uploads/20180605/db37d6dae0d4ef5333f61fdf466621dd.jpg","intro":"爱爱爱","keywords":"秋天,风筝"},"info":[{"id":2,"title":"水彩画","image":"/uploads/20180607/430ec3fad7fa9836b1c3b04deea5fb23.png","duration":50,"price":"200","type":1,"star":2}]}
     */

    private DataBean data;

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        /**
         * header : {"level":"机器人课程","level2":"3-4岁","level3":"这是什么"}
         * theme : {"title":"这是什么","image":"/uploads/20180605/db37d6dae0d4ef5333f61fdf466621dd.jpg","intro":"爱爱爱","keywords":"秋天,风筝"}
         * info : [{"id":2,"title":"水彩画","image":"/uploads/20180607/430ec3fad7fa9836b1c3b04deea5fb23.png","duration":50,"price":"200","type":1,"star":2}]
         */

        private HeaderBean header;
        private ThemeBean theme;
        private List<CourseBean> info;

        public HeaderBean getHeader() { return header;}

        public void setHeader(HeaderBean header) { this.header = header;}

        public ThemeBean getTheme() { return theme;}

        public void setTheme(ThemeBean theme) { this.theme = theme;}

        public List<CourseBean> getInfo() { return info;}

        public void setInfo(List<CourseBean> info) { this.info = info;}

        public static class HeaderBean {
            /**
             * level : 机器人课程
             * level2 : 3-4岁
             * level3 : 这是什么
             */

            private String level;
            private String level2;
            private String level3;

            public String getLevel() { return level;}

            public void setLevel(String level) { this.level = level;}

            public String getLevel2() { return level2;}

            public void setLevel2(String level2) { this.level2 = level2;}

            public String getLevel3() { return level3;}

            public void setLevel3(String level3) { this.level3 = level3;}
        }

        public static class ThemeBean {
            /**
             * title : 这是什么
             * image : /uploads/20180605/db37d6dae0d4ef5333f61fdf466621dd.jpg
             * intro : 爱爱爱
             * keywords : 秋天,风筝
             */

            private String title;
            private String image;
            private String intro;
            private String keywords;

            private int type;

            private int total;

            private double price;

            private String config;

            private String config_title;

            private int page;
            private String tiptop;

            public String getConfig() {
                return config;
            }

            public void setConfig(String config) {
                this.config = config;
            }

            public String getConfig_title() {
                return config_title;
            }

            public void setConfig_title(String config_title) {
                this.config_title = config_title;
            }


            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTitle() { return title;}

            public void setTitle(String title) { this.title = title;}

            public String getImage() { return image;}

            public void setImage(String image) { this.image = image;}

            public String getIntro() { return intro;}

            public void setIntro(String intro) { this.intro = intro;}

            public String getKeywords() { return keywords;}

            public void setKeywords(String keywords) { this.keywords = keywords;}

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public String getTiptop() {
                return tiptop;
            }

            public void setTiptop(String tiptop) {
                this.tiptop = tiptop;
            }
        }

        public static class InfoBean {
            /**
             * id : 2
             * title : 水彩画
             * image : /uploads/20180607/430ec3fad7fa9836b1c3b04deea5fb23.png
             * duration : 50
             * price : 200
             * type : 1
             * star : 2
             */

            private int id;
            private String title;
            private String image;
            private int duration;
            private String price;
            private int type;
            private int star;

            public int getId() { return id;}

            public void setId(int id) { this.id = id;}

            public String getTitle() { return title;}

            public void setTitle(String title) { this.title = title;}

            public String getImage() { return image;}

            public void setImage(String image) { this.image = image;}

            public int getDuration() { return duration;}

            public void setDuration(int duration) { this.duration = duration;}

            public String getPrice() { return price;}

            public void setPrice(String price) { this.price = price;}

            public int getType() { return type;}

            public void setType(int type) { this.type = type;}

            public int getStar() { return star;}

            public void setStar(int star) { this.star = star;}
        }
//        private static class WarnBean{
//
//            /** 提醒
//             * [{"page":"1","dis":"/uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg",
//             * "sec":"/uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg",
//             * "cus":"/uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg"}]
//             */
//
//            private int page;
//            private String tiptop;
//
//            public int getPage() {
//                return page;
//            }
//
//            public void setPage(int page) {
//                this.page = page;
//            }
//
//            public String getTiptop() {
//                return tiptop;
//            }
//
//            public void setTiptop(String tiptop) {
//                this.tiptop = tiptop;
//            }
//        }
    }
}
