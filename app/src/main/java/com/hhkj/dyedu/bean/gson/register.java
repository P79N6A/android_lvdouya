package com.hhkj.dyedu.bean.gson;

import com.hhkj.dyedu.bean.model.UserInfo;

/**
 * Created by zangyi_shuai_ge on 2018/5/25
 */

public class register extends BaseHttpResponse {


    /**
     * time : 1527216432
     * data : {"userinfo":{"id":6,"group_id":0,"username":"15168212338","nickname":"15168212338","mobile":"15168212338","avatar":"/assets/img/avatar.png","token":"17ee563e-984c-4962-a2ca-9c3e632fefea","user_id":6,"createtime":1527216433,"expiretime":1529808433,"expires_in":2592000}}
     */

    private DataBean data;

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        /**
         * userinfo : {"id":6,"group_id":0,"username":"15168212338","nickname":"15168212338","mobile":"15168212338","avatar":"/assets/img/avatar.png","token":"17ee563e-984c-4962-a2ca-9c3e632fefea","user_id":6,"createtime":1527216433,"expiretime":1529808433,"expires_in":2592000}
         */

        private UserInfo userinfo;

        public UserInfo getUserinfo() { return userinfo;}

        public void setUserinfo(UserInfo userinfo) { this.userinfo = userinfo;}
    }
}
