package com.hhkj.dyedu.bean.gson;

import com.hhkj.dyedu.bean.model.BuyVip;

import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/6/22
 */

public class payList  extends BaseHttpResponse {


    /**
     * time : 1529646151
     * data : [{"id":1,"price":"2.00","remark":"一个月"},{"id":2,"price":"3.00","remark":"一个季度"},{"id":3,"price":"4.00","remark":"半年"},{"id":4,"price":"20.00","remark":"一年"}]
     */

    private String time;
    private List<BuyVip> data;

    public String getTime() { return time;}

    public void setTime(String time) { this.time = time;}

    public List<BuyVip> getData() { return data;}

    public void setData(List<BuyVip> data) { this.data = data;}


}
