package com.hhkj.dyedu.bean.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/6/27
 */
public class ScheduleUnit implements Serializable {

    private String week;
    private String type;
    private List<ScheduleCourseBean> scheduleCourseBeans;

    public ScheduleUnit(String week, String type, List<ScheduleCourseBean> scheduleCourseBeans) {
        this.week = week;
        this.type = type;
        this.scheduleCourseBeans = scheduleCourseBeans;
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

    public List<ScheduleCourseBean> getMon() { return scheduleCourseBeans;}

    public void setMon(List<ScheduleCourseBean> Mon) { this.scheduleCourseBeans = Mon;}


}
