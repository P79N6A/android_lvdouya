package com.hhkj.dyedu.bean.model;

/**
 * Created by zangyi_shuai_ge on 2018/5/25
 */

public class UserInfo {
    /**
     * id : 6
     * group_id : 0
     * username : 15168212338
     * nickname : 15168212338
     * mobile : 15168212338
     * avatar : /assets/img/avatar.png
     * token : 17ee563e-984c-4962-a2ca-9c3e632fefea
     * user_id : 6
     * createtime : 1527216433
     * expiretime : 1529808433
     * expires_in : 2592000
     */

    private int id;
    private int group_id;
    private String username;
    private String nickname;
    private String mobile;
    private String avatar;
    private String token;
    private int user_id;
    private int createtime;
    private int expiretime;
    private int expires_in;

    private int expiration;//会员

    private  String money;



    private String bio;//职业
    private int age;
    private int gender;//性别


    private String realname;
    private String phone;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public int getGroup_id() { return group_id;}

    public void setGroup_id(int group_id) { this.group_id = group_id;}

    public String getUsername() { return username;}

    public void setUsername(String username) { this.username = username;}

    public String getNickname() { return nickname;}

    public void setNickname(String nickname) { this.nickname = nickname;}

    public String getMobile() { return mobile;}

    public void setMobile(String mobile) { this.mobile = mobile;}

    public String getAvatar() { return avatar;}

    public void setAvatar(String avatar) { this.avatar = avatar;}

    public String getToken() { return token;}

    public void setToken(String token) { this.token = token;}

    public int getUser_id() { return user_id;}

    public void setUser_id(int user_id) { this.user_id = user_id;}

    public int getCreatetime() { return createtime;}

    public void setCreatetime(int createtime) { this.createtime = createtime;}

    public int getExpiretime() { return expiretime;}

    public void setExpiretime(int expiretime) { this.expiretime = expiretime;}

    public int getExpires_in() { return expires_in;}

    public void setExpires_in(int expires_in) { this.expires_in = expires_in;}
}
