package com.hhkj.dyedu.bean.gson;

/**
 * Created by zangyi_shuai_ge on 2018/6/27
 */
public class schedule_create extends  BaseHttpResponse {


    /**
     * time : 1530254086
     * data : {"id":"100","type":1}
     */

    private DataBean data;

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        /**
         * id : 100
         * type : 1
         */

        private String id;
        private int type;

        public String getId() { return id;}

        public void setId(String id) { this.id = id;}

        public int getType() { return type;}

        public void setType(int type) { this.type = type;}
    }
}
