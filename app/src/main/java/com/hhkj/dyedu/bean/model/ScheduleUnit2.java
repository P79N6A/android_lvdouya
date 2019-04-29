package com.hhkj.dyedu.bean.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/6/27
 */
public class ScheduleUnit2 {

    private List<ScheduleCourseBean> Mon;
    private List<ScheduleCourseBean> Tues;
    private List<ScheduleCourseBean> Wed;
    private List<ScheduleCourseBean> Thur;
    private List<ScheduleCourseBean> Fri;
    private List<ScheduleCourseBean> Sat;
    private List<ScheduleCourseBean> Sun;

    public List<ScheduleCourseBean> getMon() {
        if (Mon == null) {
            return new ArrayList<>();
        }
        return Mon;
    }

    public void setMon(List<ScheduleCourseBean> Mon) { this.Mon = Mon;}

    public List<ScheduleCourseBean> getTues() {
        if (Tues == null) {
            return new ArrayList<>();
        }

        return Tues;
    }

    public void setTues(List<ScheduleCourseBean> Tues) {


        this.Tues = Tues;
    }

    public List<ScheduleCourseBean> getWed() {
        if (Wed == null) {
            return new ArrayList<>();
        }

        return Wed;
    }

    public void setWed(List<ScheduleCourseBean> Wed) { this.Wed = Wed;}

    public List<ScheduleCourseBean> getThur() {
        if (Thur == null) {
            return new ArrayList<>();
        }
        return Thur;
    }

    public void setThur(List<ScheduleCourseBean> Thur) { this.Thur = Thur;}

    public List<ScheduleCourseBean> getFri() {
        if (Fri == null) {
            return new ArrayList<>();
        }

        return Fri;
    }

    public void setFri(List<ScheduleCourseBean> Fri) { this.Fri = Fri;}

    public List<ScheduleCourseBean> getSat() {

        if (Sat == null) {
            return new ArrayList<>();
        }
        return Sat;
    }

    public void setSat(List<ScheduleCourseBean> Sat) { this.Sat = Sat;}

    public List<ScheduleCourseBean> getSun() {

        if (Sun == null) {
            return new ArrayList<>();
        }
        return Sun;
    }

    public void setSun(List<ScheduleCourseBean> Sun) { this.Sun = Sun;}


}
