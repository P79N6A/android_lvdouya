package com.hhkj.dyedu.bean.model;

import android.support.annotation.IntDef;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zangyi_shuai_ge on 2018/6/8
 */

public class CourseCenterBean implements MultiItemEntity {


    public static final int ITEM_1 = 2;//
    public static final int ITEM_2 = 3;//主题推荐
    public static final int ITEM_3 = 5;//课程分类推荐

    public static final int LINE_4 = 1;
    //标题栏
    public static final int BAR = 7;
    //线
    public static final int LINE_1 = 10;

    public static final int ITEM_4 = 6;


    @IntDef({ITEM_1, ITEM_2, ITEM_3, LINE_4,ITEM_4,BAR,LINE_1})
    @Retention(RetentionPolicy.SOURCE)
    private  @interface CourseCenterType {
    }

    private Theme theme;

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    private Featured featured;

    public Featured getFeatured() {
        return featured;
    }

    public void setFeatured(Featured featured) {
        this.featured = featured;
    }

    private int itemType;


    private ThemeCategory category;

    public ThemeCategory getCategory() {
        return category;
    }

    public void setCategory(ThemeCategory category) {
        this.category = category;
    }

    private CourseBean courseBean;

    public CourseBean getCourseBean() {
        return courseBean;
    }

    public void setCourseBean(CourseBean courseBean) {
        this.courseBean = courseBean;
    }

    private  BarInfo barInfo;

    public BarInfo getBarInfo() {
        return barInfo;
    }

    public void setBarInfo(BarInfo barInfo) {
        this.barInfo = barInfo;
    }

    @Override
    public int getItemType() {
        return itemType;
    }


    public void setItemType(@CourseCenterType  int itemType) {
        this.itemType = itemType;
    }

}
