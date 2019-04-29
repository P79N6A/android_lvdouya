package com.hhkj.dyedu.bean.gson;

import com.hhkj.dyedu.bean.model.CourseBean;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/6/30
 */
public class featured extends  BaseHttpResponse {


    /**
     * time : 1530327649
     * data : []
     */

    private String time;
    private List<CourseBean> data;

    public String getTime() { return time;}

    public void setTime(String time) { this.time = time;}

    public List<CourseBean> getData() { return data;}

    public void setData(List<CourseBean> data) { this.data = data;}
}
