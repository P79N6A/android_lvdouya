package com.hhkj.dyedu.bean.gson;

import java.util.List;

public class notion extends BaseHttpResponse{


    /**
     * time : 1555062765
     * data : [{"id":2,"title":"欢迎使用课轻松商家版APP","content":"<p>欢迎使用课轻松商家版APP，课轻松APP即将登陆安卓商店，精彩课程内容不断更新中，敬请关注！<br/><\/p>","createtime":1555040258,"updatetime":1555040258,"status":"normal"},{"id":1,"title":"111","content":"<p>1111\r\n1111\r\n222\r\n3<\/p>","createtime":1553150489,"updatetime":1553157875,"status":"normal"}]
     */

    private String time;
    private List<DataBean> data;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * title : 欢迎使用课轻松商家版APP
         * content : <p>欢迎使用课轻松商家版APP，课轻松APP即将登陆安卓商店，精彩课程内容不断更新中，敬请关注！<br/></p>
         * createtime : 1555040258
         * updatetime : 1555040258
         * status : normal
         */

        private int id;
        private String title;
        private String content;
        private int createtime;
        private int updatetime;
        private String status;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
