package com.hhkj.dyedu.bean.gson;

/**
 * Created by zangyi_shuai_ge on 2017/9/27
 */

public class BaseHttpResponse {
    /**
     * code : 1
     * msg : 发送成功
     */

    private int code;

    private String msg;

    public int getCode() { return code;}

    public void setCode(int code) { this.code = code;}

    public String getMsg() { return msg;}

    public void setMsg(String msg) { this.msg = msg;}

}
