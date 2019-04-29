package com.hhkj.dyedu.bean.model;

import java.io.Serializable;

/**
 * Created by zangyi_shuai_ge on 2018/6/12
 * 主题课程
 */

public class Theme implements Serializable{


    /**
     * id : 1
     * title : 这是什么
     * image : /uploads/20180605/db37d6dae0d4ef5333f61fdf466621dd.jpg
     * price : 100.00
     * type : 1
     */

    private int id;
    private String title;
    private String image;
    private String price;
    private int type;

    private int duration;//时长
    private int star;//星星数量
    private int knots;//总节数
    private int finish;//已上小结束


    private String config;
    private String config_title;


    private int count;
    
    private int lock;
    
    public int getLock() {
        return lock;
    }
    
    public void setLock(int lock) {
        this.lock = lock;
    }
    
    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getConfig_title() {
        return config_title;
    }

    public void setConfig_title(String config_title) {
        this.config_title = config_title;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getKnots() {
        return knots;
    }

    public void setKnots(int knots) {
        this.knots = knots;
    }

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getTitle() { return title;}

    public void setTitle(String title) { this.title = title;}

    public String getImage() { return image;}

    public void setImage(String image) { this.image = image;}

    public String getPrice() { return price;}

    public void setPrice(String price) { this.price = price;}

    public int getType() { return type;}

    public void setType(int type) { this.type = type;}
}
