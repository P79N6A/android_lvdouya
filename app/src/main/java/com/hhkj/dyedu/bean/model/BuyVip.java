package com.hhkj.dyedu.bean.model;

/**
 * Created by zangyi_shuai_ge on 2018/6/22
 */

public class BuyVip {
    /**
     * id : 1
     * price : 2.00
     * remark : 一个月
     */
    private int id;
    private String price;
    private String remark;

    private boolean isChoose;

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getPrice() { return price;}

    public void setPrice(String price) { this.price = price;}

    public String getRemark() { return remark;}

    public void setRemark(String remark) { this.remark = remark;}
}
