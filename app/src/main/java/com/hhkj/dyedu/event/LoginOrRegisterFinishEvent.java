package com.hhkj.dyedu.event;

/**
 * Created by zangyi_shuai_ge on 17.11.30
 */

public class LoginOrRegisterFinishEvent {
    private int code;

    public LoginOrRegisterFinishEvent(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
