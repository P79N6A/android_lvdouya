package com.hhkj.dyedu.bean.gson;

import com.hhkj.dyedu.bean.model.Theme;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/6/30
 */
public class course extends  BaseHttpResponse {


    /**
     * time : 1530327649
     * data : []
     */

    private String time;
    private List<Theme> data;

    public String getTime() { return time;}

    public void setTime(String time) { this.time = time;}

    public List<Theme> getData() { return data;}

    public void setData(List<Theme> data) { this.data = data;}
}
