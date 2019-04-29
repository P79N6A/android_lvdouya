package com.hhkj.dyedu.bean.gson;

/**
 * Created by zangyi_shuai_ge on 2018/6/25
 */

public class withdraw extends  BaseHttpResponse{


    /**
     * code : 1
     * msg : 申请成功
     * time : 1529904801
     * data : {"money":95495,"expiration":1864459644}
     */

    private DataBean data;

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        /**
         * money : 95495
         * expiration : 1864459644
         */

        private int money;
        private int expiration;

        public int getMoney() { return money;}

        public void setMoney(int money) { this.money = money;}

        public int getExpiration() { return expiration;}

        public void setExpiration(int expiration) { this.expiration = expiration;}
    }
}
