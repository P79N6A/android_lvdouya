package com.hhkj.dyedu.bean.model;

/**
 * Created by zangyi_shuai_ge on 2018/5/25
 * 发送验证码需要的参数
 */

public class SmsBean {

    private String mobile;

    private String event;//

    
    private String areaCode="86";
    
    public String getAreaCode() {
        return areaCode;
    }
    
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    
    public SmsBean(String mobile, String event, String  areaCode) {
        this.mobile = mobile;
        this.event = event;
        this.areaCode=areaCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
