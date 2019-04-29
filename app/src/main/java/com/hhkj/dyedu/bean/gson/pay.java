package com.hhkj.dyedu.bean.gson;

/**
 * Created by zangyi_shuai_ge on 2018/6/14
 */

public class pay extends  BaseHttpResponse {


    /**
     * time : 1528963573
     * data : {"out_trade_no":"2018061453981019","info":"<form id='alipaysubmit' name='ali  "}
     */

    private DataBean data;

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        /**
         * out_trade_no : 2018061453981019
         * info : <form id='alipaysubmit' name='ali
         */

        private String out_trade_no;
        private String info;
        private String money;
        private String info2;

        public String getInfo2() {
            return info2;
        }

        public void setInfo2(String info2) {
            this.info2 = info2;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getOut_trade_no() { return out_trade_no;}

        public void setOut_trade_no(String out_trade_no) { this.out_trade_no = out_trade_no;}

        public String getInfo() { return info;}

        public void setInfo(String info) { this.info = info;}
    }
}
