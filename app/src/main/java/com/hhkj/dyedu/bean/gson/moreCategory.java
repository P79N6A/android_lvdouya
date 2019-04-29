package com.hhkj.dyedu.bean.gson;

import com.hhkj.dyedu.bean.model.ThemeCategory;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/6/12
 */

public class moreCategory extends  BaseHttpResponse {


    /**
     * time : 1528780511
     * data : [{"id":25,"name":"轮船课程","image":"/uploads/20180608/86f4e9ff577cac43c129fa52fdaa183b.png"},{"id":24,"name":"火车课程","image":"/uploads/20180608/86f4e9ff577cac43c129fa52fdaa183b.png"},{"id":20,"name":"汽车课程","image":"/uploads/20180608/86f4e9ff577cac43c129fa52fdaa183b.png"},{"id":19,"name":"机器人课程","image":"/uploads/20180608/86f4e9ff577cac43c129fa52fdaa183b.png"}]
     */

    private List<ThemeCategory> data;

    public List<ThemeCategory> getData() { return data;}

    public void setData(List<ThemeCategory> data) { this.data = data;}


}
