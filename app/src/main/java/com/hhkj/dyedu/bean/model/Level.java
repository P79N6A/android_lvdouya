package com.hhkj.dyedu.bean.model;

/**
 * Created by zangyi_shuai_ge on 2018/6/12
 */

public class Level {

    private int id;
    private String name;
    private boolean isChoose;

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}
}
