package com.hhkj.dyedu.bean.gson;

import com.hhkj.dyedu.bean.model.UserInfo;

/**
 * Created by zangyi_shuai_ge on 2018/6/21
 */

public class getUserInfo extends  BaseHttpResponse {


    /**
     * time : 1529564005
     * data : {"id":10,"group_id":1,"nickname":"15168212330","avatar":"/uploads/20180621/e740ff44e966e44fd68f301c010e765d.jpg","gender":0,"bio":"","expiration":0,"age":0,"token":"6562d6fd-f45f-4102-9e3c-ff9a39536280","user_id":10,"createtime":1528782952,"expiretime":1531374952,"expires_in":1810947}
     */

    private UserInfo data;

    public UserInfo getData() { return data;}

    public void setData(UserInfo data) { this.data = data;}
}
