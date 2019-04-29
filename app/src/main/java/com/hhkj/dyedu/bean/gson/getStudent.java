package com.hhkj.dyedu.bean.gson;

/**
 * Created by zangyi_shuai_ge on 2018/6/27
 */
public class getStudent extends  BaseHttpResponse {


    /**
     * time : 1530084466
     * data : {"id":27,"username":"----","nickname":"3699","gender":1,"age":44,"study":":::"}
     */

    private DataBean data;

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        /**
         * id : 27
         * username : ----
         * nickname : 3699
         * gender : 1
         * age : 44
         * study : :::
         */

        private int id;
        private String username;
        private String nickname;
        private int gender;
        private int age;
        private String study;

        public int getId() { return id;}

        public void setId(int id) { this.id = id;}

        public String getUsername() { return username;}

        public void setUsername(String username) { this.username = username;}

        public String getNickname() { return nickname;}

        public void setNickname(String nickname) { this.nickname = nickname;}

        public int getGender() { return gender;}

        public void setGender(int gender) { this.gender = gender;}

        public int getAge() { return age;}

        public void setAge(int age) { this.age = age;}

        public String getStudy() { return study;}

        public void setStudy(String study) { this.study = study;}
    }
}
