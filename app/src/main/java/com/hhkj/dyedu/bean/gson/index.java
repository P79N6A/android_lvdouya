package com.hhkj.dyedu.bean.gson;

import com.hhkj.dyedu.bean.model.ThemeCategory;
import com.hhkj.dyedu.bean.model.Featured;
import com.hhkj.dyedu.bean.model.Theme;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/6/8
 */

public class index extends  BaseHttpResponse {

    /**
     * time : 1528855642
     * data : {"BannerGson":[{"image":"/uploads/20180611/1ecb5b3c166242589d667a37b0a8ae7d.png"},{"image":"/uploads/20180611/f3a2ba1eac50fbf17d73ec3e7c1254a2.png"},{"image":"/uploads/20180608/a2092e03e4f43ad09370f4cc108dfbe2.png"}],"featured":[{"id":2,"title":"水彩画","type":1,"image":"/uploads/20180607/430ec3fad7fa9836b1c3b04deea5fb23.png","price":"200"},{"id":4,"title":"擎天柱","type":1,"image":"/uploads/20180608/86f4e9ff577cac43c129fa52fdaa183b.png","price":"98"},{"id":3,"title":"汽车人","type":1,"image":"/uploads/20180608/86f4e9ff577cac43c129fa52fdaa183b.png","price":"300"},{"id":1,"title":"简笔画","type":1,"image":"/uploads/20180601/db37d6dae0d4ef5333f61fdf466621dd.jpg","price":"100"}],"theme":[{"id":19,"title":"机器人课程","info":[{"id":1,"image":"/uploads/20180605/db37d6dae0d4ef5333f61fdf466621dd.jpg","title":"这是什么","price":"100.00"},{"id":4,"image":"/uploads/20180608/86f4e9ff577cac43c129fa52fdaa183b.png","title":"童年回忆","price":"400.00"}]},{"id":20,"title":"汽车课程","info":[{"id":2,"image":"/uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg","title":"吴彦祖","price":"200.00"},{"id":3,"image":"/uploads/20180607/63b324372f50d537ec8e155cf626d475.png","title":"七彩世界","price":"300.00"}]}],"category":[{"id":25,"name":"轮船课程","image":"/uploads/20180608/86f4e9ff577cac43c129fa52fdaa183b.png"},{"id":24,"name":"火车课程","image":"/uploads/20180608/86f4e9ff577cac43c129fa52fdaa183b.png"},{"id":20,"name":"汽车课程","image":"/uploads/20180608/86f4e9ff577cac43c129fa52fdaa183b.png"},{"id":19,"name":"机器人课程","image":"/uploads/20180608/86f4e9ff577cac43c129fa52fdaa183b.png"}]}
     */

    private DataBean data;

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        private List<BannerBean> banner;
        private List<Featured> featured;
        private List<ThemeBean> theme;
        private List<ThemeCategory> category;

        public List<BannerBean> getBanner() { return banner;}

        public void setBanner(List<BannerBean> banner) { this.banner = banner;}

        public List<Featured> getFeatured() { return featured;}

        public void setFeatured(List<Featured> featured) { this.featured = featured;}

        public List<ThemeBean> getTheme() { return theme;}

        public void setTheme(List<ThemeBean> theme) { this.theme = theme;}

        public List<ThemeCategory> getCategory() { return category;}

        public void setCategory(List<ThemeCategory> category) { this.category = category;}

        public static class BannerBean {
            /**
             * image : /uploads/20180611/1ecb5b3c166242589d667a37b0a8ae7d.png
             */

            private String image;

            public String getImage() { return image;}

            public void setImage(String image) { this.image = image;}
        }

        public static class FeaturedBean {
            /**
             * id : 2
             * title : 水彩画
             * type : 1
             * image : /uploads/20180607/430ec3fad7fa9836b1c3b04deea5fb23.png
             * price : 200
             */

            private int id;
            private String title;
            private int type;
            private String image;
            private String price;

            public int getId() { return id;}

            public void setId(int id) { this.id = id;}

            public String getTitle() { return title;}

            public void setTitle(String title) { this.title = title;}

            public int getType() { return type;}

            public void setType(int type) { this.type = type;}

            public String getImage() { return image;}

            public void setImage(String image) { this.image = image;}

            public String getPrice() { return price;}

            public void setPrice(String price) { this.price = price;}
        }

        public static class ThemeBean {
            /**
             * id : 19
             * title : 机器人课程
             * info : [{"id":1,"image":"/uploads/20180605/db37d6dae0d4ef5333f61fdf466621dd.jpg","title":"这是什么","price":"100.00"},{"id":4,"image":"/uploads/20180608/86f4e9ff577cac43c129fa52fdaa183b.png","title":"童年回忆","price":"400.00"}]
             */

            private int id;
            private String title;
            private List<Theme> info;

            public int getId() { return id;}

            public void setId(int id) { this.id = id;}

            public String getTitle() { return title;}

            public void setTitle(String title) { this.title = title;}

            public List<Theme> getInfo() { return info;}

            public void setInfo(List<Theme> info) { this.info = info;}

            public static class InfoBean {
                /**
                 * id : 1
                 * image : /uploads/20180605/db37d6dae0d4ef5333f61fdf466621dd.jpg
                 * title : 这是什么
                 * price : 100.00
                 */

                private int id;
                private String image;
                private String title;
                private String price;

                public int getId() { return id;}

                public void setId(int id) { this.id = id;}

                public String getImage() { return image;}

                public void setImage(String image) { this.image = image;}

                public String getTitle() { return title;}

                public void setTitle(String title) { this.title = title;}

                public String getPrice() { return price;}

                public void setPrice(String price) { this.price = price;}
            }
        }

        public static class CategoryBean {
            /**
             * id : 25
             * name : 轮船课程
             * image : /uploads/20180608/86f4e9ff577cac43c129fa52fdaa183b.png
             */

            private int id;
            private String name;
            private String image;

            public int getId() { return id;}

            public void setId(int id) { this.id = id;}

            public String getName() { return name;}

            public void setName(String name) { this.name = name;}

            public String getImage() { return image;}

            public void setImage(String image) { this.image = image;}
        }
    }
}
