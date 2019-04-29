package com.hhkj.dyedu.bean.gson;

/**
 * Created by zangyi_shuai_ge on 2018/6/25
 */

public class orderStatus extends  BaseHttpResponse{


    /**
     * time : 1529889615
     * data : {"money":"0.00","expiration":1810027644}
     */

    private DataBean data;

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        /**
         * money : 0.00
         * expiration : 1810027644
         */

        private String money;
        private String expiration;

        public String getMoney() { return money;}

        public void setMoney(String money) { this.money = money;}

        public String getExpiration() { return expiration;}

        public void setExpiration(String expiration) { this.expiration = expiration;}
    }
}
