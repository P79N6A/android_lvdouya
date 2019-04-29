package com.hhkj.dyedu.bean.gson;

import com.hhkj.dyedu.bean.model.Level;
import com.hhkj.dyedu.bean.model.Theme;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/6/11
 */

public class category extends  BaseHttpResponse {


    /**
     * time : 1528686332
     * data : {"level2":[{"id":21,"name":"0-1岁"},{"id":22,"name":"2-3岁"},{"id":23,"name":"3-4岁"}],"info":[{"id":2,"title":"吴彦祖","image":"/uploads/20180523/32e7a7542bfbf8dfad7b509c7954a36d.jpg","price":"200.00","type":1},{"id":3,"title":"七彩世界","image":"/uploads/20180607/63b324372f50d537ec8e155cf626d475.png","price":"300.00","type":0}]}
     */

    private String time;
    private DataBean data;

    public String getTime() { return time;}

    public void setTime(String time) { this.time = time;}

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        private List<Level> level2;
        private List<Theme> info;

        public List<Level> getLevel2() { return level2;}

        public void setLevel2(List<Level> level2) { this.level2 = level2;}

        public List<Theme> getInfo() { return info;}

        public void setInfo(List<Theme> info) { this.info = info;}

        public static class Level2Bean {
            /**
             * id : 21
             * name : 0-1岁
             */

            private int id;
            private String name;

            public int getId() { return id;}

            public void setId(int id) { this.id = id;}

            public String getName() { return name;}

            public void setName(String name) { this.name = name;}
        }


    }
}
