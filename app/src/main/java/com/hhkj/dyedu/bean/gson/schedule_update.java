package com.hhkj.dyedu.bean.gson;

/**
 * Created by zangyi_shuai_ge on 2018/6/29
 */
public class schedule_update extends BaseHttpResponse {


    /**
     * time : 1530241401
     * data : {"class_name":"333","start_time":-1,"end_time":-1}
     */

    private DataBean data;

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        /**
         * class_name : 333
         * start_time : -1
         * end_time : -1
         */

        private String class_name;
        private int start_time;
        private int end_time;
        private  int  status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getClass_name() { return class_name;}

        public void setClass_name(String class_name) { this.class_name = class_name;}

        public int getStart_time() { return start_time;}

        public void setStart_time(int start_time) { this.start_time = start_time;}

        public int getEnd_time() { return end_time;}

        public void setEnd_time(int end_time) { this.end_time = end_time;}
    }
}
