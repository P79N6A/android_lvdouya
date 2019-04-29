package com.hhkj.dyedu.bean.model;

/**
 * Created by zangyi_shuai_ge on 2018/6/8
 */

public class Featured {
    /**
     * id : 1
     * title : 简笔画
     * type : 1
     * image : /uploads/20180601/db37d6dae0d4ef5333f61fdf466621dd.jpg
     * price : 100
     */

    private int id;
    private String title;
    private int type;
    private String image;
    private String price;

    private int theme_id;

    private int star;

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    private String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(int theme_id) {
        this.theme_id = theme_id;
    }

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getTitle() { return title;}

    public void setTitle(String title) { this.title = title;}

    public int getType() { return type;}

    public void setType(int type) { this.type = type;}

    public String getImage() { return image;}

    public void setImage(String image) { this.image = image;}

    public String getPrice() { return price;}

    public void setPrice(String price) { this.price = price;}
}
