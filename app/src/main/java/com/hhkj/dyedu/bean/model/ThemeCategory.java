package com.hhkj.dyedu.bean.model;

import java.io.Serializable;

/**
 * Created by zangyi_shuai_ge on 2018/6/11
 * 主题分类
 */

public class ThemeCategory implements Serializable{

    public ThemeCategory(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    /**
     * id : 20
     * name : 汽车课程
     * image : /uploads/20180608/86f4e9ff577cac43c129fa52fdaa183b.png
     */

    private int id;
    private String name;
    private String image;

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getName() {
        
        
        return name;}

    public void setName(String name) { this.name = name;}

    public String getImage() { return image;}

    public void setImage(String image) { this.image = image;}
}
