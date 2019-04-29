package com.hhkj.dyedu.bean.gson;

import com.hhkj.dyedu.bean.model.Level;
import com.hhkj.dyedu.bean.model.Theme;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/6/28
 */
public class category_category extends  BaseHttpResponse {


    /**
     * time : 1530164724
     * data : {"level":[{"id":25,"name":"轮船课程"},{"id":24,"name":"火车课程"},{"id":20,"name":"汽车课程"},{"id":19,"name":"机器人课程"}],"level2":[{"id":26,"name":"2-3岁"},{"id":27,"name":"3-4岁"}],"info":[{"id":8,"title":"轮船来了","image":"/uploads/20180608/86f4e9ff577cac43c129fa52fdaa183b.png","star":1,"knots":20,"time":50,"type":2}]}
     */

    private DataBean data;

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        private List<Level> level;
        private List<Level> level2;
        private List<Theme> info;

        public List<Level> getLevel() { return level;}

        public void setLevel(List<Level> level) { this.level = level;}

        public List<Level> getLevel2() { return level2;}

        public void setLevel2(List<Level> level2) { this.level2 = level2;}

        public List<Theme> getInfo() { return info;}

        public void setInfo(List<Theme> info) { this.info = info;}



//        public static class InfoBean {
//            /**
//             * id : 8
//             * title : 轮船来了
//             * image : /uploads/20180608/86f4e9ff577cac43c129fa52fdaa183b.png
//             * star : 1
//             * knots : 20
//             * time : 50
//             * type : 2
//             */
//
//            private int id;
//            private String title;
//            private String image;
//            private int star;
//            private int knots;
//            private int time;
//            private int type;
//
//            public int getId() { return id;}
//
//            public void setId(int id) { this.id = id;}
//
//            public String getTitle() { return title;}
//
//            public void setTitle(String title) { this.title = title;}
//
//            public String getImage() { return image;}
//
//            public void setImage(String image) { this.image = image;}
//
//            public int getStar() { return star;}
//
//            public void setStar(int star) { this.star = star;}
//
//            public int getKnots() { return knots;}
//
//            public void setKnots(int knots) { this.knots = knots;}
//
//            public int getTime() { return time;}
//
//            public void setTime(int time) { this.time = time;}
//
//            public int getType() { return type;}
//
//            public void setType(int type) { this.type = type;}
//        }
    }
}
