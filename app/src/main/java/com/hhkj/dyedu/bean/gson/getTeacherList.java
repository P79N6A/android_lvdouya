package com.hhkj.dyedu.bean.gson;

import com.hhkj.dyedu.bean.model.Teacher;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/6/25
 */

public class getTeacherList extends BaseHttpResponse {


    /**
     * time : 1529910520
     * data : [{"id":17,"username":"1111","nickname":"1111","avatar":""}]
     */

    private List<Teacher> data;

    public List<Teacher> getData() { return data;}

    public void setData(List<Teacher> data) { this.data = data;}
    
    private int restrict;
    
    public int getRestrict() {
        return restrict;
    }
    
    public void setRestrict(int restrict) {
        this.restrict = restrict;
    }
}
