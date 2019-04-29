package com.hhkj.dyedu.bean.model;

/**
 * Created by zangyi_shuai_ge on 2018/6/25
 */

public class Teacher {
    /**
     * id : 17
     * username : 1111
     * nickname : 1111
     * avatar :
     */

    private int id;
    private String username;
    private String nickname;
    private String avatar;
    private String status;
    
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getUsername() { return username;}

    public void setUsername(String username) { this.username = username;}

    public String getNickname() { return nickname;}

    public void setNickname(String nickname) { this.nickname = nickname;}

    public String getAvatar() { return avatar;}

    public void setAvatar(String avatar) { this.avatar = avatar;}
}
