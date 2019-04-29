package com.hhkj.dyedu.bean.model;

import java.io.Serializable;

/**
 * Created by zangyi_shuai_ge on 2018/6/26
 * 课表单元格   1格代表  周一上午  周二上午为1单元格
 */

public class ScheduleCourseBean implements Serializable {
    private int id;
    private String week;
    private String type;
    private String time;

    private int end_time;
    private  int start_time;

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
