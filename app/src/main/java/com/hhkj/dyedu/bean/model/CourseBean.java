package com.hhkj.dyedu.bean.model;

import java.io.Serializable;

/**
 * Created by zangyi_shuai_ge on 2018/6/11
 * 主题 课程
 */

public class CourseBean  implements Serializable{
    /**
     * Vip课程
     */
    public static final  int VIP_COURSE=2;
    /**
     * 普通课程
     */
    public static final  int NORMAL_COURSE=1;

    /**
     * theme_id : 1
     * id : 2
     * title : 水彩画
     * image : /uploads/20180607/430ec3fad7fa9836b1c3b04deea5fb23.png
     * price : 200
     * type : 1
     */

    private int theme_id;
    private int id;
    private String title;
    private String image;
    private String price;
    private int type;
    private int duration;//时长
    private int star;//星星数量

    private int knots;//总节数


    private  int buy;


    private String preview;

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public int getBuy() {
        return buy;
    }

    public void setBuy(int buy) {
        this.buy = buy;
    }

    public int getKnots() {
        return knots;
    }

    public void setKnots(int knots) {
        this.knots = knots;
    }

    private boolean isChoose;

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
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

    public int getTheme_id() { return theme_id;}

    public void setTheme_id(int theme_id) { this.theme_id = theme_id;}

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
