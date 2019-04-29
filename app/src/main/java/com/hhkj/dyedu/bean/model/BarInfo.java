package com.hhkj.dyedu.bean.model;

/**
 * Created by zangyi_shuai_ge on 2018/6/11
 */

public class BarInfo {

    public final static int TYPE_1 = 1;//主题分类
    public final static int TYPE_2 = 2;//推荐课程

    private int id;
    private String title;
    private int type;

    public BarInfo(int id, String title, int type) {
        this.id = id;
        this.title = title;
        this.type = type;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
