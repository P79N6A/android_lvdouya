package com.hhkj.dyedu.bean.gson;


public class custom extends BaseHttpResponse {


    /**
     * time : 1554707906
     * data : {"id":9,"category_id":58,"temp_id":8,"temp_content":" ","createtime":1553061989,"updatetime":1553225592}
     */

    private String time;
    private DataBean data;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 9
         * category_id : 58
         * temp_id : 8
         * temp_content :
         * createtime : 1553061989
         * updatetime : 1553225592
         */

        private int id;
        private int category_id;
        private int temp_id;
        private String temp_content;
        private int createtime;
        private int updatetime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public int getTemp_id() {
            return temp_id;
        }

        public void setTemp_id(int temp_id) {
            this.temp_id = temp_id;
        }

        public String getTemp_content() {
            return temp_content;
        }

        public void setTemp_content(String temp_content) {
            this.temp_content = temp_content;
        }

        public int getCreatetime() {
            return createtime;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }

        public int getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(int updatetime) {
            this.updatetime = updatetime;
        }
    }
}
