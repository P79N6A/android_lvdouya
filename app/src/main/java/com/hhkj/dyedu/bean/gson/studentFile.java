package com.hhkj.dyedu.bean.gson;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/6/27
 */
public class studentFile extends  BaseHttpResponse {


    /**
     * time : 1530083629
     * data : [{"id":1,"username":"admin","nickname":"哈哈","age":0,"study":"","gender":1}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() { return data;}

    public void setData(List<DataBean> data) { this.data = data;}

    public static class DataBean {
        /**
         * id : 1
         * username : admin
         * nickname : 哈哈
         * age : 0
         * study :
         * gender : 1
         */

        private int id;
        private String username;
        private String nickname;
        private int age;
        private String study;
        private int gender;

        private boolean isChoose;

        public boolean isChoose() {
            return isChoose;
        }

        public void setChoose(boolean choose) {
            isChoose = choose;
        }

        public int getId() { return id;}

        public void setId(int id) { this.id = id;}

        public String getUsername() { return username;}

        public void setUsername(String username) { this.username = username;}

        public String getNickname() { return nickname;}

        public void setNickname(String nickname) { this.nickname = nickname;}

        public int getAge() { return age;}

        public void setAge(int age) { this.age = age;}

        public String getStudy() { return study;}

        public void setStudy(String study) { this.study = study;}

        public int getGender() { return gender;}

        public void setGender(int gender) { this.gender = gender;}
    }
}
