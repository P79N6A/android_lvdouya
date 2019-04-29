package com.hhkj.dyedu.bean.gson;

/**
 * Created by zangyi_shuai_ge on 2018/6/25
 */

public class getTeacherInfo extends  BaseHttpResponse {


    /**
     * time : 1529918153
     * data : {"id":17,"username":"1111","nickname":"1111","bio":"44444"}
     */

    private DataBean data;

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        /**
         * id : 17
         * username : 1111
         * nickname : 1111
         * bio : 44444
         */

        private int id;
        private String username;
        private String nickname;
        private String bio;

        private String phone;
        private String realname;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRealname() {
            if(realname==null){
                return "";
            }
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public int getId() { return id;}

        public void setId(int id) { this.id = id;}

        public String getUsername() { return username;}

        public void setUsername(String username) { this.username = username;}

        public String getNickname() { return nickname;}

        public void setNickname(String nickname) { this.nickname = nickname;}

        public String getBio() { return bio;}

        public void setBio(String bio) { this.bio = bio;}
    }
}
