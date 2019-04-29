package com.hhkj.dyedu.bean.gson;

import com.hhkj.dyedu.bean.model.Theme;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/6/29
 */
public class schedule_info extends  BaseHttpResponse {


    /**
     * time : 1530249604
     * data : {"id":6,"class_name":"33333","start_time":725,"end_time":730,"theme":[]}
     */

    private String time;
    private DataBean data;

    public String getTime() { return time;}

    public void setTime(String time) { this.time = time;}

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        /**
         * id : 6
         * class_name : 33333
         * start_time : 725
         * end_time : 730
         * theme : []
         */

        private int id;
        private String class_name;
        private int start_time;
        private int end_time;

        private String class_num;

        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getClass_num() {
            return class_num;
        }

        public void setClass_num(String class_num) {
            this.class_num = class_num;
        }

        private List<Theme> theme;

        public int getId() { return id;}

        public void setId(int id) { this.id = id;}

        public String getClass_name() { return class_name;}

        public void setClass_name(String class_name) { this.class_name = class_name;}

        public int getStart_time() { return start_time;}

        public void setStart_time(int start_time) { this.start_time = start_time;}

        public int getEnd_time() { return end_time;}

        public void setEnd_time(int end_time) { this.end_time = end_time;}

        public List<Theme> getTheme() { return theme;}

        public void setTheme(List<Theme> theme) { this.theme = theme;}
    }
}
